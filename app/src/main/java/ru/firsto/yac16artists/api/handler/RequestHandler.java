package ru.firsto.yac16artists.api.handler;

import android.os.Handler;
import android.os.Looper;

import junit.framework.Assert;

import java.util.concurrent.atomic.AtomicInteger;

import ru.firsto.yac16artists.api.Api;
import ru.firsto.yac16artists.api.ApiCallback;
import ru.firsto.yac16artists.api.ApiErrorResponse;
import ru.firsto.yac16artists.api.RestError;
import ru.firsto.yac16artists.api.request.ApiRequest;
import ru.firsto.yac16artists.api.response.ApiResponse;

public abstract class RequestHandler<R extends ApiRequest, T extends ApiResponse> implements ApiRequestHandler<R, T> {
    protected final R request;
    private final Handler handler;
    private volatile ApiCallback<T> callback;
    private AtomicInteger taskCount = new AtomicInteger(0);
    private volatile boolean resultDispatched;

    private IllegalStateException noResultStackTrace;

    public RequestHandler(R request) {
        this.request = request;
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        handler = new Handler(Looper.myLooper());
    }

    @Override
    public final void process() {
        processForResult(null);
    }

    @Override
    public final void processForResult(ApiCallback<T> callback) {
        this.callback = callback;
        Assert.assertTrue(isValidRequest());
        processForResultImpl();
    }

    protected boolean isValidRequest() {
        return true;
    }

    protected <M extends ApiResponse> void dispatch(M response) {
        if (response == null || response instanceof ApiErrorResponse) {
            if (response == null) {
                response = (M) new ApiErrorResponse(RestError.NONE);
            }
            dispatchFail((ApiErrorResponse) response);
        } else {
            dispatchSuccess((T) response);
        }
    }

    protected void dispatchFail(final ApiErrorResponse response) {
        resultDispatched = true;

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onFail(response);
                }
            }
        });
    }

    protected void dispatchSuccess(final T response) {
        resultDispatched = true;
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (callback != null) {
                    callback.onSuccess(response);
                }
            }
        });
    }

    protected void runOnOriginalThread(Runnable task) {
        handler.post(new WrapperRunnable(task));
    }

    protected void runOnDbThread(Runnable task) {
        Api.THREAD_DB.execute(new WrapperRunnable(task));
    }

    protected void runOnFileThread(Runnable task) {
        Api.THREAD_FILE.execute(new WrapperRunnable(task));
    }

    protected void runOnNetThread(Runnable task) {
        Api.THREAD_NET.execute(new WrapperRunnable(task));
    }

    protected abstract void processForResultImpl();

    private class WrapperRunnable implements Runnable {

        private final Runnable task;

        private WrapperRunnable(Runnable task) {
            this.task = task;
            taskCount.incrementAndGet();
            noResultStackTrace =
                    new IllegalStateException("Request handler hasn't dispatched a result "
                            + RequestHandler.this.getClass() + "  task = " + task.getClass()
                            + " --- original dialog creation stack");
            noResultStackTrace.fillInStackTrace();
        }

        @Override
        public void run() {
            task.run();
            int count = taskCount.decrementAndGet();
            if (count == 0 && !resultDispatched && callback != null) {
                throw noResultStackTrace;
            }
        }
    }
}