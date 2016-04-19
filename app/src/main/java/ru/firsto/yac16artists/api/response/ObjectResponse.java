package ru.firsto.yac16artists.api.response;

public class ObjectResponse<T> extends ApiResponse {
    public T responseObject;

    public ObjectResponse(T responseObject) {
        this.responseObject = responseObject;
    }
}
