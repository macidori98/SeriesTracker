package com.example.seriestracker.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchSeriesResponse {
    @SerializedName("results")
    private List<SearchSeries> searchSeriesList;

    public SearchSeriesResponse(List<SearchSeries> searchSeriesList) {
        this.searchSeriesList = searchSeriesList;
    }

    public List<SearchSeries> getSearchSeriesList() {
        return searchSeriesList;
    }

    public void setSearchSeriesList(List<SearchSeries> searchSeriesList) {
        this.searchSeriesList = searchSeriesList;
    }
}
