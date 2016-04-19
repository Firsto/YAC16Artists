package ru.firsto.yac16artists.api.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import junit.framework.Assert;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import ru.firsto.yac16artists.api.database.table.ArtistTable;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "app.db";
    private static final int DATABASE_VERSION = 1;

    private static final Set<Class<?>> DATA_CLASSES = new HashSet<>(Arrays.asList(new Class<?>[]{
            ArtistTable.class,
    }));

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        createDatabase();
    }

    private void createDatabase() {
        try {
            createTables();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void createTables() throws SQLException {
        for (Class<?> dataClass : DATA_CLASSES) {
            TableUtils.createTableIfNotExists(connectionSource, dataClass);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        for (Class<?> dataClass : DATA_CLASSES) {
            try {
                TableUtils.dropTable(connectionSource, dataClass, true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        onCreate(database, connectionSource);
    }

    @Override
    public <D extends Dao<T, ?>, T> D getDao(Class<T> clazz) throws SQLException {
        Assert.assertTrue("class isn't a DAO class " + clazz, DATA_CLASSES.contains(clazz));
        D result = super.getDao(clazz);
        result.setObjectCache(true);
        return result;
    }

    public void clear() {
        for (Class<?> dataClass : DATA_CLASSES) {
            try {
                TableUtils.dropTable(connectionSource, dataClass, true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        createDatabase();
        for (Class<?> dataClass : DATA_CLASSES) {
            try {
                getDao(dataClass).clearObjectCache();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
