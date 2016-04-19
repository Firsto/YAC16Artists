package ru.firsto.yac16artists.api;

import junit.framework.Assert;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.firsto.yac16artists.api.request.ApiRequest;
import ru.firsto.yac16artists.api.response.ApiResponse;

public class Api {
    public static final ExecutorService THREAD_DB = Executors.newSingleThreadExecutor();
    public static final ExecutorService THREAD_FILE = Executors.newSingleThreadExecutor();
    public static final ExecutorService THREAD_NET = Executors.newFixedThreadPool(5);


    @SuppressWarnings("unused")
    public <R extends ApiRequest> void makeRequest(R request) {
        Assert.assertNotNull(request);
        ApiHandlerFactory.getHandler(request).process();
    }

    @SuppressWarnings("unchecked")
    public <R extends ApiRequest, T extends ApiResponse> void makeRequestForResult(R request, ApiCallback<T> callback) {
        Assert.assertNotNull(request);
        Assert.assertNotNull(callback);
        ApiHandlerFactory.getHandler(request).processForResult((ApiCallback<ApiResponse>) callback);
    }
}
