package ru.firsto.yac16artists.api.response;

import java.util.List;

public class ListResponse<T> extends ApiResponse {
    public List<T> result;

    public ListResponse(List<T> result) {
        this.result = result;
    }

    public boolean isEmpty() {
        return result == null || result.isEmpty();
    }
}
