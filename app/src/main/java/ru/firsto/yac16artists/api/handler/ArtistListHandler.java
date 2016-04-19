package ru.firsto.yac16artists.api.handler;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ru.firsto.yac16artists.Injector;
import ru.firsto.yac16artists.api.ApiErrorResponse;
import ru.firsto.yac16artists.api.RestError;
import ru.firsto.yac16artists.api.database.DatabaseProcessing;
import ru.firsto.yac16artists.api.database.table.ArtistTable;
import ru.firsto.yac16artists.api.json.ArtistJson;
import ru.firsto.yac16artists.api.request.ArtistListRequest;
import ru.firsto.yac16artists.api.response.ApiResponse;
import ru.firsto.yac16artists.api.response.ListResponse;

/**
 * @author razor
 * @created 18.04.16
 **/
public class ArtistListHandler extends RestRequestHandler<ArtistListRequest, ListResponse<ArtistTable>> {
    public ArtistListHandler(ArtistListRequest request) {
        super(request);
    }

    @Override
    protected void onRestRequestSuccess(final JsonArray body) {
        runOnDbThread(new Runnable() {
            @Override
            public void run() {
                ArrayList<ArtistTable> artists = new ArrayList<>();
                try {
                    if (body != null && body.size() > 0)
                        for (JsonElement jsonElement : body) {
                            artists.add(DatabaseProcessing.saveToDatabase(getObjectJson(jsonElement)));
                        }
                    ListResponse<ArtistTable> response = new ListResponse<>(artists);
                    response.type = ApiResponse.Type.FROM_API;
                    dispatchSuccess(new ListResponse<>(artists));
                } catch (SQLException e) {
                    e.printStackTrace();
                    dispatchFail(new ApiErrorResponse(RestError.SQL_ERROR, e.getMessage()));
                }
            }
        });
    }

    private ArtistJson getObjectJson(JsonElement jsonElement) {
        return Injector.getGson().fromJson(jsonElement, ArtistJson.class);
    }

    
    @Override
    protected void processForResultImpl() {
        Injector.getServerMethods().getArtists(this);

        try {
            ListResponse<ArtistTable> response = new ListResponse<>(loadFromDatabase());
            response.type = ApiResponse.Type.FROM_BD_ONLY;
            dispatchSuccess(response);
        } catch (SQLException e) {
            dispatchFail(new ApiErrorResponse(RestError.SQL_ERROR, e.getMessage()));
            e.printStackTrace();
        }
//        Injector.getServerMethods().getArtistsS(new Callback<List<ArtistTable>>() {
//            @Override
//            public void success(List<ArtistTable> artistTables, Response response) {
//                Log.d("tag", "success");
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Log.d("tag", "failure");
//            }
//        });
    }

    private List<ArtistTable> loadFromDatabase() throws SQLException {
        Dao<ArtistTable, Long> dao = Injector.getDatabase().getDao(ArtistTable.class);
        return dao.queryBuilder().query();
    }
}
