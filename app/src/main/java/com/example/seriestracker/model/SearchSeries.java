package com.example.seriestracker.model;

import com.google.gson.annotations.SerializedName;

public class SearchSeries {
    private int id;

    @SerializedName("poster_path")
    private String image;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
