package com.example.seriestracker.model;

import com.google.gson.annotations.SerializedName;

public class TvShowDetails {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("number_of_seasons")
    private int numberOfSeasons;

    @SerializedName("next_episode_to_air")
    private NextEpisodeToAir nextEpisodeToAir;

    @SerializedName("last_air_date")
    private String lastAirDate;

    @SerializedName("last_episode_to_air")
    private NextEpisodeToAir lastEpisodeToAir;

    public TvShowDetails(int id, int numberOfSeasons, NextEpisodeToAir nextEpisodeToAir, String lastAirDate, NextEpisodeToAir lastEpisodeToAir) {
        this.id = id;
        this.numberOfSeasons = numberOfSeasons;
        this.nextEpisodeToAir = nextEpisodeToAir;
        this.lastAirDate = lastAirDate;
        this.lastEpisodeToAir = lastEpisodeToAir;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public NextEpisodeToAir getLastEpisodeToAir() {
        return lastEpisodeToAir;
    }

    public void setLastEpisodeToAir(NextEpisodeToAir lastEpisodeToAir) {
        this.lastEpisodeToAir = lastEpisodeToAir;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public NextEpisodeToAir getNextEpisodeToAir() {
        return nextEpisodeToAir;
    }

    public void setNextEpisodeToAir(NextEpisodeToAir nextEpisodeToAir) {
        this.nextEpisodeToAir = nextEpisodeToAir;
    }
}
