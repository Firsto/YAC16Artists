package ru.firsto.yac16artists.api.database.table;

import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;

public class BaseUserTable implements Comparable<BaseUserTable> { // as parent of ArtistTable
    public static final String FIELD_NAME_ID = BaseColumns._ID;
    public static final String FIELD_NAME_USER_NAME = "user_name";

    @DatabaseField(columnName = FIELD_NAME_ID, id = true)
    @SerializedName(FIELD_NAME_ID)
    public String id;
    @DatabaseField(columnName = FIELD_NAME_USER_NAME)
    public String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(@NonNull BaseUserTable another) {
        if (name == null && another.name == null) {
            return 0;
        }
        if (name != null) {
            return name.compareToIgnoreCase(another.name);
        } else {
            return -1;
        }
    }
}
