package ru.firsto.yac16artists.api.handler;

import ru.firsto.yac16artists.api.ApiCallback;
import ru.firsto.yac16artists.api.request.ApiRequest;
import ru.firsto.yac16artists.api.response.ApiResponse;

public interface ApiRequestHandler<R extends ApiRequest, T extends ApiResponse> {

    void process();

    void processForResult(ApiCallback<T> callback);
}
