package com.example.seriestracker.model;

import com.google.gson.annotations.SerializedName;

public class TvShowDetails {
    @SerializedName("id")
    private int id;

    @SerializedName("number_of_seasons")
    private int numberOfSeasons;

    @SerializedName("next_episode_to_air")
    private NextEpisodeToAir nextEpisodeToAir;

    public TvShowDetails(int id, int numberOfSeasons, NextEpisodeToAir nextEpisodeToAir) {
        this.id = id;
        this.numberOfSeasons = numberOfSeasons;
        this.nextEpisodeToAir = nextEpisodeToAir;
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
