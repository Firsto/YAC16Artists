package ru.firsto.yac16artists.api.database.table;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;

import ru.firsto.yac16artists.api.database.field.Genre;
import ru.firsto.yac16artists.api.json.ArtistJson;

@DatabaseTable(tableName = ArtistTable.TABLE_NAME)
public class ArtistTable extends BaseUserTable implements Parcelable {

    public static final String TABLE_NAME = "artist_table";
    public static final String FIELD_NAME_TRACKS = "tracks";
    public static final String FIELD_NAME_ALBUMS = "albums";
    public static final String FIELD_NAME_LINK = "link";
    public static final String FIELD_NAME_DESCRIPTION = "description";
    public static final String FIELD_NAME_COVER_SMALL = "cover_small";
    public static final String FIELD_NAME_COVER_BIG = "cover_big";
    public static final String FIELD_NAME_GENRES = "genres";

    public static final Creator<ArtistTable> CREATOR = new Creator<ArtistTable>() {
        public ArtistTable createFromParcel(Parcel source) {
            return new ArtistTable(source);
        }

        public ArtistTable[] newArray(int size) {
            return new ArtistTable[size];
        }
    };

    @DatabaseField(columnName = FIELD_NAME_TRACKS)
    public int tracks;
    @DatabaseField(columnName = FIELD_NAME_ALBUMS)
    public int albums;
    @DatabaseField(columnName = FIELD_NAME_LINK)
    public String link;
    @DatabaseField(columnName = FIELD_NAME_DESCRIPTION)
    public String description;
    @DatabaseField(columnName = FIELD_NAME_COVER_SMALL)
    public String smallCover;
    @DatabaseField(columnName = FIELD_NAME_COVER_BIG)
    public String bigCover;
    @DatabaseField(columnName = FIELD_NAME_GENRES, dataType = DataType.SERIALIZABLE)
    public ArrayList<Genre> genres;


    public ArtistTable() {
    }

    public ArtistTable(ArtistJson artistJson) {
        this();
        fill(artistJson);
    }

    public ArtistTable(String name) {
        this();
        this.name = name;
    }

    private ArtistTable(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.tracks = in.readInt();
        this.albums = in.readInt();
        this.link = in.readString();
        this.description = in.readString();
        this.smallCover = in.readString();
        this.bigCover = in.readString();
        this.genres = in.readArrayList(Genre.class.getClassLoader());
    }

    public ArtistTable fill(ArtistJson artistJson) {
        this.id = artistJson.id;
        this.name = artistJson.name;
        this.tracks = artistJson.tracks;
        this.albums = artistJson.albums;
        this.link = artistJson.link;
        this.description = artistJson.description;
        if (artistJson.cover != null) {
            this.smallCover = artistJson.cover.small;
            this.bigCover = artistJson.cover.big;
        }
        this.genres = new ArrayList<>();
        if (artistJson.genres != null) {
            for (String genre : artistJson.genres) {
                this.genres.add(Genre.fromRest(genre));
            }
        }
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.tracks);
        dest.writeInt(this.albums);
        dest.writeString(this.link);
        dest.writeString(this.description);
        dest.writeString(this.smallCover);
        dest.writeString(this.bigCover);
        dest.writeList(genres);
    }
}
