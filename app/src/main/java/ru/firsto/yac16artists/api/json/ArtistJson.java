package ru.firsto.yac16artists.api.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArtistJson {
    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("tracks")
    public int tracks;
    @SerializedName("albums")
    public int albums;
    @SerializedName("link")
    public String link;
    @SerializedName("description")
    public String description;
    @SerializedName("genres")
    public List<String> genres;
    public CoverJson cover;


    public ArtistJson() {
    }

    public ArtistJson(String name) {
        this.name  = name;
    }
}
