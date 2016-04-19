package ru.firsto.yac16artists.api.handler;

import com.google.gson.JsonArray;

import retrofit.RetrofitError;
import retrofit.client.Response;
import ru.firsto.yac16artists.api.ApiErrorResponse;
import ru.firsto.yac16artists.api.RestError;
import ru.firsto.yac16artists.api.request.ApiRequest;
import ru.firsto.yac16artists.api.response.ApiResponse;
import ru.firsto.yac16artists.api.response.RestResponse;

public abstract class RestRequestHandler<R extends ApiRequest, T extends ApiResponse>
        extends RequestHandler<R, T> implements retrofit.Callback<RestResponse> {

    public RestRequestHandler(R request) {
        super(request);
    }

    @Override
    public void success(RestResponse restResponse, Response response) {
        if (response.getStatus() < 400) {
            try {
                onRestRequestSuccess(restResponse.body);
            } catch (Exception e) {
                ApiErrorResponse errorResponse = new ApiErrorResponse(RestError.INVALIDATE_RESPONSE_STRING);
                errorResponse.error = e.getLocalizedMessage();
                errorResponse.code = String.valueOf(response.getStatus());
                dispatchFail(errorResponse);
                e.printStackTrace();
            }
        } else {
            ApiErrorResponse errorResponse = new ApiErrorResponse(RestError.INVALIDATE_RESPONSE_STRING);
            if (restResponse instanceof ApiErrorResponse && ((ApiErrorResponse) restResponse).error != null) {
                errorResponse.error = ((ApiErrorResponse) restResponse).error;
            } else {
                errorResponse.error = "code: " + response.getStatus() + " body: " + response.getBody();
            }
            errorResponse.code = String.valueOf(response.getStatus());
            dispatchFail(errorResponse);
        }
    }

    @Override
    public void failure(RetrofitError error) {
        onRequestFail(error);
    }

    protected abstract void onRestRequestSuccess(JsonArray body);

    protected void onRequestFail(RetrofitError error) {
        ApiErrorResponse errorResponse = null;
        try {
            errorResponse = (ApiErrorResponse) error.getBodyAs(ApiErrorResponse.class);
        } catch (RuntimeException e) {
        }
        if (errorResponse == null) {
            if (error.getKind() == RetrofitError.Kind.CONVERSION) {
                errorResponse = new ApiErrorResponse(RestError.INVALIDATE_RESPONSE_STRING);
            } else {
                errorResponse = new ApiErrorResponse(RestError.CONNECTION_PROBLEM);
            }
            errorResponse.error = error.getLocalizedMessage();
        }
        dispatchFail(errorResponse);
    }
}
