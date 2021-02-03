package com.example.seriestracker.model;

import com.google.firebase.database.PropertyName;

public class TvShow {
    @PropertyName("user_id")
    private String userId;

    @PropertyName("name")
    private String name;

    @PropertyName("db_id")
    private int dbId;

    @PropertyName("image_url")
    private String image;

    @PropertyName("season_number")
    private int seasonNumber;

    public TvShow(String userId, String name, int dbId, String image, int seasonNumber) {
        this.userId = userId;
        this.name = name;
        this.dbId = dbId;
        this.image = image;
        this.seasonNumber = seasonNumber;
    }

    @PropertyName("user_id")
    public String getUserId() {
        return userId;
    }

    @PropertyName("user_id")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @PropertyName("name")
    public String getName() {
        return name;
    }

    @PropertyName("name")
    public void setName(String name) {
        this.name = name;
    }

    @PropertyName("db_id")
    public int getDbId() {
        return dbId;
    }

    @PropertyName("db_id")
    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    @PropertyName("image_url")
    public String getImage() {
        return image;
    }

    @PropertyName("image_url")
    public void setImage(String image) {
        this.image = image;
    }

    @PropertyName("season_number")
    public int getSeasonNumber() {
        return seasonNumber;
    }

    @PropertyName("season_number")
    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }
}
