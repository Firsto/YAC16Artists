package ru.firsto.yac16artists.api.loader;

import ru.firsto.yac16artists.api.ApiErrorResponse;
import ru.firsto.yac16artists.api.response.ApiResponse;

public class ApiLoaderResult<T extends ApiResponse> {
    public final T result;
    public final ApiErrorResponse error;

    public ApiLoaderResult(T result, ApiErrorResponse error) {
        this.result = result;
        this.error = error;
    }

    public static <T extends ApiResponse> ApiLoaderResult<T> newSuccess(T result) {
        return new ApiLoaderResult<T>(result, null);
    }

    public static <T extends ApiResponse> ApiLoaderResult<T> newError(ApiErrorResponse error) {
        return new ApiLoaderResult<T>(null, error);
    }
}
