package ru.firsto.yac16artists.api.database.field;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author razor
 * @created 18.04.16
 **/
public enum Genre implements Serializable {
    AFRICAN("african"),
    ALTERNATIVE("alternative"),
    BARD("bard"),
    BLUES("blues"),
    CLASSICAL("classical"),
    DANCE("dance"),
    DUB("dub"),
    ELECTRONICS("electronics"),
    ESTRADA("estrada"),
    FOLK("folk"),
    HOUSE("house"),
    JAZZ("jazz"),
    INDIE("indie"),
    INDUSTRIAL("industrial"),
    LATINFOLK("latinfolk"),
    LOCALINDIE("local-indie"),
    LOUNGE("lounge"),
    METAL("metal"),
    POP("pop"),
    PROG("prog"),
    PUNK("punk"),
    RAP("rap"),
    REGGAE("reggae"),
    RELAX("relax"),
    RNB("rnb"),
    ROCK("rock"),
    RUSRAP("rusrap"),
    RUSROCK("rusrock"),
    SOUNDTRACK("soundtrack"),
    SOUL("soul"),
    TRANCE("trance"),
    URBAN("urban"),
    VIDEOGAME("videogame");

    @SerializedName("name")
    public final String name;

    Genre() {
        this.name = null;
    }

    Genre(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public static Genre fromRest(String restGenre) {
        for (Genre value : values()) {
            if (value.toString().equals(restGenre)) {
                return value;
            }
        }
        return null;
    }
}