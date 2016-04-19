package ru.firsto.yac16artists.api.loader;

import android.support.v4.app.FragmentActivity;
import android.support.v4.content.Loader;

import ru.firsto.yac16artists.api.response.ApiResponse;

public abstract class SimpleApiLoaderCallback<T extends ApiResponse> extends ApiLoaderCallback<T> {
    protected FragmentActivity activity;
    protected int loaderId;
    protected boolean automaticDestroyLoader = true;

    public SimpleApiLoaderCallback(FragmentActivity activity, int loaderId) {
        this.activity = activity;
        this.loaderId = loaderId;
    }

    public boolean isAutomaticDestroyLoader() {
        return automaticDestroyLoader;
    }

    public SimpleApiLoaderCallback setAutomaticDestroyLoader(boolean automaticDestroyLoader) {
        this.automaticDestroyLoader = automaticDestroyLoader;
        return this;
    }

    @Override
    public void onLoadFinished(Loader<ApiLoaderResult<T>> loader, ApiLoaderResult<T> data) {
        super.onLoadFinished(loader, data);
        if (automaticDestroyLoader)
            activity.getSupportLoaderManager().destroyLoader(loaderId);

    }
}
