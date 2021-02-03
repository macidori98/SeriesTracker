package com.example.seriestracker.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowSeasonDetailsRoot {
    @SerializedName("_id")
    public String _id;
    @SerializedName("air_date")
    public String airDate;
    @SerializedName("episodes")
    public List<TvShowEpisode> episodes;
    @SerializedName("name")
    public String name;
    @SerializedName("overview")
    public String overview;
    @SerializedName("id")
    public int id;
    @SerializedName("poster_path")
    public String posterPath;
    @SerializedName("season_number")
    public int seasonNumber;

    public TvShowSeasonDetailsRoot(String _id, String airDate, List<TvShowEpisode> episodes, String name, String overview, int id, String posterPath, int seasonNumber) {
        this._id = _id;
        this.airDate = airDate;
        this.episodes = episodes;
        this.name = name;
        this.overview = overview;
        this.id = id;
        this.posterPath = posterPath;
        this.seasonNumber = seasonNumber;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public List<TvShowEpisode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<TvShowEpisode> episodes) {
        this.episodes = episodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }
}
