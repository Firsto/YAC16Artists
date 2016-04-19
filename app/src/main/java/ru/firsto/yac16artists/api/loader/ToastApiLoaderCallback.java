package ru.firsto.yac16artists.api.loader;

import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import ru.firsto.yac16artists.api.ApiErrorResponse;
import ru.firsto.yac16artists.api.response.ApiResponse;

public abstract class ToastApiLoaderCallback<T extends ApiResponse> extends SimpleApiLoaderCallback<T> {
    public ToastApiLoaderCallback(FragmentActivity activity, int loaderId) {
        super(activity, loaderId);
    }

    @Override
    public void onFail(ApiErrorResponse error) {
        Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT).show();
    }
}
