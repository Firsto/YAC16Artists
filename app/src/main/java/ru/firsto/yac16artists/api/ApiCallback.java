package ru.firsto.yac16artists.api;

import ru.firsto.yac16artists.api.response.ApiResponse;

public interface ApiCallback<R extends ApiResponse> {

    void onSuccess(R response);

    void onFail(ApiErrorResponse error);

}
