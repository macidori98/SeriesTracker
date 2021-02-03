package com.example.seriestracker.movieDatabase;

import com.example.seriestracker.model.SearchSeriesResponse;
import com.example.seriestracker.model.TvShowDetails;
import com.example.seriestracker.model.TvShowSeasonDetailsRoot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {
    @GET("/3/search/tv")
    Call<SearchSeriesResponse> getSearchSeries(@Query("api_key") String api_key, @Query("language") String language,
                                               @Query("page") String page, @Query("query") String query,
                                               @Query("include_adult") String include_adult);

    @GET("/3/tv/{tv_id}")
    Call<TvShowDetails> getSeasonNumber(@Path("tv_id") String tv_id,
                                        @Query("api_key") String api_key, @Query("language") String language);

    @GET("/3/tv/{tv_id}/season/{season_number}")
    Call<TvShowSeasonDetailsRoot> getSeasonDetails(@Path("tv_id") int tv_id, @Path("season_number") String season_number,
                                                   @Query("api_key") String api_key, @Query("language") String language);
}
