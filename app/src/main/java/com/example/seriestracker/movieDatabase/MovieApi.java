package com.example.seriestracker.movieDatabase;

import com.example.seriestracker.model.SearchSeriesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieApi {
    @GET("/3/search/tv")
    Call<SearchSeriesResponse> getSearchSeries(@Query("api_key") String api_key, @Query("language") String language,
                                               @Query("page") String page, @Query("query") String query,
                                               @Query("include_adult") String include_adult);

}
