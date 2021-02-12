package com.example.seriestracker.addSeries;

import com.example.seriestracker.R;
import com.example.seriestracker.helper.FirebaseHelper;
import com.example.seriestracker.model.SearchSeries;
import com.example.seriestracker.model.SearchSeriesResponse;
import com.example.seriestracker.model.TvShow;
import com.example.seriestracker.model.TvShowDetails;
import com.example.seriestracker.model.TvShowEpisode;
import com.example.seriestracker.model.TvShowSeasonDetailsRoot;
import com.example.seriestracker.model.UserData;
import com.example.seriestracker.movieDatabase.MovieApi;
import com.example.seriestracker.movieDatabase.NetworkConnection;
import com.example.seriestracker.utils.GlobalValues;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class AddSeriesPresenter implements IAddSeriesPresenter {
    private final AddSeriesActivity activity;
    private final List<UserData> userDataList = new ArrayList<>();

    public AddSeriesPresenter(AddSeriesActivity activity) {
        this.activity = activity;
    }

    @Override
    public void search(String name) {
        if (name.isEmpty()) {
            activity.onActionFailure(activity, R.string.empty_string, R.color.primaryColor);
            return;
        }

        final MovieApi api = NetworkConnection.getRetrofit();
        Call<SearchSeriesResponse> call = api.getSearchSeries(GlobalValues.API_KEY, activity.getResources().getString(R.string.language),
                activity.getResources().getString(R.string.page_start),
                name, activity.getResources().getString(R.string.falsee));
        call.enqueue(new Callback<SearchSeriesResponse>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<SearchSeriesResponse> call, Response<SearchSeriesResponse> response) {
                if (response.code() == 200) {
                    SearchSeriesResponse searchSeriesResponse = response.body();

                    if (Objects.requireNonNull(searchSeriesResponse).getSearchSeriesList() == null) {
                        searchSeriesResponse.setSearchSeriesList(new ArrayList<>());
                    }

                    if (searchSeriesResponse.getSearchSeriesList().size() == 0) {
                        activity.onActionSuccess(activity, R.string.no_entries_found, R.color.primaryColor);
                    }

                    activity.setUpRecyclerView(searchSeriesResponse);
                } else {
                    activity.onActionFailure(activity, R.string.something_went_wrong, R.color.red);
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<SearchSeriesResponse> call, Throwable t) {
                activity.onActionFailure(activity, R.string.fail, R.color.red);
            }
        });
    }

    @Override
    public void addToFirebase(SearchSeries series) {
        getShowSeasonNumber(series);
    }

    @Override
    public void addShowSeasonsAndEpisodesToFirebase(TvShow tvShow) {
        if (tvShow.getSeasonNumber() == 0) {
            activity.onActionSuccess(activity, R.string.success, R.color.green);
            activity.tvShowAdded();
            return;
        }

        for (int season = 1; season <= tvShow.getSeasonNumber(); ++season) {
            String seasonNumber = String.valueOf(season);

            final MovieApi api = NetworkConnection.getRetrofit();
            Call<TvShowSeasonDetailsRoot> call = api.getSeasonDetails(tvShow.getDbId(), seasonNumber, GlobalValues.API_KEY,
                    activity.getResources().getString(R.string.language));
            int finalSeason = season;
            call.enqueue(new Callback<TvShowSeasonDetailsRoot>() {
                @Override
                @EverythingIsNonNull
                public void onResponse(Call<TvShowSeasonDetailsRoot> call, Response<TvShowSeasonDetailsRoot> response) {
                    if (response.code() == GlobalValues.SUCCESSFUL_CODE) {
                        TvShowSeasonDetailsRoot seasonDetails = response.body();
                        List<TvShowEpisode> showEpisode = Objects.requireNonNull(seasonDetails).getEpisodes();

                        prepareDataForFirebase(showEpisode, tvShow.getDbId());

                        if (finalSeason == tvShow.getSeasonNumber()) {
                            FirebaseHelper.getInstance().addUserData(AddSeriesPresenter.this, userDataList);
                        }
                    } else {
                        activity.onActionFailure(activity, R.string.fail, R.color.red);
                    }
                }

                @Override
                @EverythingIsNonNull
                public void onFailure(Call<TvShowSeasonDetailsRoot> call, Throwable t) {
                    activity.onActionFailure(activity, R.string.fail, R.color.red);
                }
            });
        }
    }

    @Override
    public void onSuccess(int textId, int backgroundColorId) {
        activity.onActionSuccess(activity, textId, backgroundColorId);
        activity.tvShowAdded();
    }

    @Override
    public void onFailure(int textId, int backgroundColorId) {
        activity.onActionFailure(activity, textId, backgroundColorId);
    }

    private void getShowSeasonNumber(SearchSeries series) {
        final MovieApi api = NetworkConnection.getRetrofit();
        String id = String.valueOf(series.getId());
        Call<TvShowDetails> call = api.getSeasonNumber(id, GlobalValues.API_KEY, activity.getResources().getString(R.string.language));

        call.enqueue(new Callback<TvShowDetails>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<TvShowDetails> call, Response<TvShowDetails> response) {
                if (response.code() == GlobalValues.SUCCESSFUL_CODE) {
                    TvShowDetails detail = response.body();

                    int number = Objects.requireNonNull(detail).getNumberOfSeasons();
                    FirebaseHelper.getInstance().checkIfUserAlreadyAddedTvShow(new TvShow("", GlobalValues.CURRENT_USER_ID, series.getName(),
                            series.getId(), series.getImage(), number), AddSeriesPresenter.this);
                } else {
                    activity.onActionFailure(activity, R.string.fail, R.color.red);
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<TvShowDetails> call, Throwable t) {
                activity.onActionFailure(activity, R.string.fail, R.color.red);
            }
        });
    }

    private void prepareDataForFirebase(List<TvShowEpisode> showEpisode, int dbId) {
        for (TvShowEpisode episode : showEpisode) {
            String image = "";

            if (episode.getImage() != null) {
                image = episode.getImage();
            }

            UserData data = new UserData(GlobalValues.CURRENT_USER_ID, episode.getName(),
                    dbId, image, episode.getSeasonNumber(), episode.getEpisodeNumber(),
                    false, false);
            userDataList.add(data);
        }
    }
}
