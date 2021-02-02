package com.example.seriestracker.model;

import com.google.gson.annotations.SerializedName;

public class TvShowDetails {
    @SerializedName("number_of_seasons")
    private int numberOfSeasons;

    public TvShowDetails(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public int getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(int numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }
}
