package ru.firsto.yac16artists.api.loader;

import android.support.v4.app.FragmentActivity;
import android.support.v4.content.Loader;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import ru.firsto.yac16artists.api.loader.event.OnLoaderEvent;

public class LoaderManager {
    private List<Integer> loaders;
    private android.support.v4.app.LoaderManager manager;
    private boolean showProgress;

    public LoaderManager(FragmentActivity owner) {
        this(owner, true);
    }

    public LoaderManager(FragmentActivity owner, boolean showProgress) {
        loaders = new ArrayList<>();
        this.manager = owner.getSupportLoaderManager();
        this.showProgress = showProgress;
    }

    public <D> Loader<D> initLoader(int loaderId, android.support.v4.app.LoaderManager.LoaderCallbacks<D> loaderCallbacks) {
        if (loaders.size() == 0 && showProgress) {
            EventBus.getDefault().post(new OnLoaderEvent(OnLoaderEvent.EventType.START));
        }
        loaders.add(loaderId);
        return manager.initLoader(loaderId, null, loaderCallbacks);
    }

    public void destroyLoader(int loaderId) {
        loaders.remove(Integer.valueOf(loaderId));
        if (loaders.size() == 0 && showProgress) {
            EventBus.getDefault().post(new OnLoaderEvent(OnLoaderEvent.EventType.FINISH));
        }
        manager.destroyLoader(loaderId);
    }

    public void destroyAllLoaders() {
        for (Integer loaderId : loaders) {
            manager.destroyLoader(loaderId);
        }
        loaders.clear();
        EventBus.getDefault().post(new OnLoaderEvent(OnLoaderEvent.EventType.FINISH));
    }

    public void destroy() {
        manager = null;
    }
}
