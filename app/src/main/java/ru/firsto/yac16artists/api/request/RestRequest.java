package ru.firsto.yac16artists.api.request;

import java.util.HashMap;
import java.util.Map;

public class RestRequest extends ApiRequest {
    public RestRequest() {}

    public Map<String, String> getBody() {
        return new HashMap<String, String>() {
            {
            }
        };
    }
}
