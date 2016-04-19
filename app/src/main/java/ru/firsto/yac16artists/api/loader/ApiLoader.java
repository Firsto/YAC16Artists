package ru.firsto.yac16artists.api.loader;

import android.content.Context;
import android.support.v4.content.Loader;

import ru.firsto.yac16artists.Injector;
import ru.firsto.yac16artists.api.ApiCallback;
import ru.firsto.yac16artists.api.ApiErrorResponse;
import ru.firsto.yac16artists.api.request.ApiRequest;
import ru.firsto.yac16artists.api.response.ApiResponse;

public class ApiLoader<R extends ApiRequest, T extends ApiResponse> extends Loader<ApiLoaderResult<T>> {

    private ApiLoaderResult<T> result;
    private final R request;

    public ApiLoader(Context context, R request) {
        super(context);
        this.request = request;
    }

    @Override
    protected void onStartLoading() {
        if (result != null) {
            deliverResult(result);
        } else {
            Injector.getApi().makeRequestForResult(request, new ApiCallback<T>() {
                @Override
                public void onSuccess(T response) {
                    result = ApiLoaderResult.newSuccess(response);
                    deliverResult(result);
                }

                @Override
                public void onFail(ApiErrorResponse error) {
                    result = ApiLoaderResult.newError(error);
                    deliverResult(result);
                }
            });
        }
    }
}
