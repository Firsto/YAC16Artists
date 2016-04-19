package ru.firsto.yac16artists.api;

import ru.firsto.yac16artists.api.response.RestResponse;

public class ApiErrorResponse extends RestResponse {
    public final RestError restError;
    public String error;
    public String code;

    public ApiErrorResponse(RestError error) {
        this.restError = error;
    }

    public ApiErrorResponse(RestError error, String restErrorMessage) {
        this.restError = error;
        this.error = restErrorMessage;
    }

    @Override
    public String toString() {
        return "[" + (restError != null ? restError : RestError.NONE) + "]" +
                (error != null ? " :" + error : "");
    }
}
