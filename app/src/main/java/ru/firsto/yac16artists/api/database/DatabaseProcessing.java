package ru.firsto.yac16artists.api.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;

import java.sql.SQLException;
import java.util.concurrent.Callable;

import ru.firsto.yac16artists.Injector;
import ru.firsto.yac16artists.api.database.table.ArtistTable;
import ru.firsto.yac16artists.api.json.ArtistJson;

public class DatabaseProcessing {
    public static ArtistTable saveToDatabase(final ArtistJson artistJson) throws SQLException {
        return TransactionManager.callInTransaction(Injector.getDatabase().getConnectionSource(),
                new Callable<ArtistTable>() {
                    @Override
                    public ArtistTable call() throws Exception {
                        Dao<ArtistTable, Long> dao = Injector.getDatabase().getDao(ArtistTable.class);
                        ArtistTable artistTable = findObjectFromValue(ArtistTable.class, ArtistTable.FIELD_NAME_ID, artistJson.id);
                        if (artistTable == null) {
                            artistTable = new ArtistTable();
                        }
                        artistTable.fill(artistJson);
                        dao.createOrUpdate(artistTable);
                        return artistTable;
                    }
                });
    }


    public static <T> T findObjectFromValue(Class<T> cls, String columnName,
                                            String columnValue) throws SQLException {
        Dao<T, Long> dao = Injector.getDatabase().getDao(cls);
        if (dao.isTableExists()) {
            return dao.queryBuilder()
                    .where()
                    .eq(columnName, columnValue)
                    .queryForFirst();
        } else {
            return null;
        }
    }


}
