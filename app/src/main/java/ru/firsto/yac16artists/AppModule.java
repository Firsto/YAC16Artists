package ru.firsto.yac16artists;

import android.content.Context;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.firsto.yac16artists.api.Api;
import ru.firsto.yac16artists.api.database.DatabaseHelper;
import ru.firsto.yac16artists.api.net.NetClient;

@Module(
        injects = {
                Api.class,
                NetClient.class,
                DatabaseHelper.class,
                Context.class,
                Gson.class
        },

        library = true
)
public class AppModule {

    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    Context provideContext() {
        return context;
    }

    @Provides
    @Singleton
    Api provideApi() {
        return new Api();
    }

    @Provides
    @Singleton
    NetClient provideNetClient() {
        return new NetClient();
    }

    @Provides
    @Singleton
    DatabaseHelper provideDatabaseHelper(Context context) {
        return new DatabaseHelper(context);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }
}
