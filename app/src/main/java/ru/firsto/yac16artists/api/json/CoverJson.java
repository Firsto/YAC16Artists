package ru.firsto.yac16artists.api.json;

import com.google.gson.annotations.SerializedName;

/**
 * @author razor
 * @created 18.04.16
 **/
public class CoverJson {
    @SerializedName("small")
    public String small;
    @SerializedName("big")
    public String big;

    public CoverJson() {
    }
}
