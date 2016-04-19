package ru.firsto.yac16artists;

import android.app.Application;

import dagger.ObjectGraph;

/**
 * @author razor
 * @created 18.04.16
 **/
public class ArtistsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Injector.init(ObjectGraph.create(new AppModule(this)));
    }
}
