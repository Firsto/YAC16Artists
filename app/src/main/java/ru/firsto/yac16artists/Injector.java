package ru.firsto.yac16artists;

import android.content.Context;

import com.google.gson.Gson;

import junit.framework.Assert;

import dagger.ObjectGraph;
import ru.firsto.yac16artists.api.Api;
import ru.firsto.yac16artists.api.database.DatabaseHelper;
import ru.firsto.yac16artists.api.net.NetClient;
import ru.firsto.yac16artists.api.net.ServerMethods;

public class Injector {
    private static volatile ObjectGraph objectGraph;

    public static void init(ObjectGraph objectGraph) {
        Injector.objectGraph = objectGraph;
    }

    public static ObjectGraph getInjector() {
        Assert.assertNotNull(objectGraph);
        return objectGraph;
    }

    public static Context getAppContext() {
        return getInjector().get(Context.class);
    }

    public static Api getApi() {
        return getInjector().get(Api.class);
    }

    public static ServerMethods getServerMethods() {
        return getInjector().get(NetClient.class).serverMethods;
    }

    public static DatabaseHelper getDatabase() {
        return getInjector().get(DatabaseHelper.class);
    }

    public static Gson getGson() {
        return getInjector().get(Gson.class);
    }
}
