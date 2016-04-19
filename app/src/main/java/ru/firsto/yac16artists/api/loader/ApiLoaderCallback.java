package ru.firsto.yac16artists.api.loader;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import ru.firsto.yac16artists.api.ApiErrorResponse;
import ru.firsto.yac16artists.api.response.ApiResponse;

public abstract class ApiLoaderCallback <T extends ApiResponse> implements LoaderManager.LoaderCallbacks<ApiLoaderResult<T>> {

    @Override
    public void onLoadFinished(Loader<ApiLoaderResult<T>> loader, ApiLoaderResult<T> data) {
        if (data.error == null) {
            onSuccess(data.result);
            return;
        }

        onFail(data.error);
    }

    public abstract void onSuccess(T result);

    public void onFail(ApiErrorResponse error) {
    }

    @Override
    public void onLoaderReset(Loader<ApiLoaderResult<T>> loader) {
    }
}
