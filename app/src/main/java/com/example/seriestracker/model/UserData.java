package com.example.seriestracker.model;

import com.google.firebase.database.PropertyName;

public class UserData {
    @PropertyName("user_id")
    private String userId;

    @PropertyName("name") // episode
    private String name;

    @PropertyName("db_id")
    private int dbId;

    @PropertyName("image_url")
    private String image;

    @PropertyName("season_number")
    private int seasonNumber;

    @PropertyName("episode_number")
    private int episodeNumber;

    @PropertyName("seen")
    private boolean seen;

    @PropertyName("liked")
    private boolean liked;

    public UserData(String userId, String name, int dbId, String image, int seasonNumber, int episodeNumber, boolean seen, boolean liked) {
        this.userId = userId;
        this.name = name;
        this.dbId = dbId;
        this.image = image;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.seen = seen;
        this.liked = liked;
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

    @PropertyName("episode_number")
    public int getEpisodeNumber() {
        return episodeNumber;
    }

    @PropertyName("episode_number")
    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    @PropertyName("seen")
    public boolean getSeen() {
        return seen;
    }

    @PropertyName("seen")
    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    @PropertyName("liked")
    public boolean getLiked() {
        return liked;
    }

    @PropertyName("liked")
    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
