package ru.firsto.yac16artists.api.net;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import ru.firsto.yac16artists.api.database.table.ArtistTable;
import ru.firsto.yac16artists.api.response.RestResponse;

public interface ServerMethods {

    @GET("/artists.json")
    void getArtists(Callback<RestResponse> cb);

    //TODO: test list response
    @GET("/artists.json")
    void getArtistsS(Callback<List<ArtistTable>> cb);

}