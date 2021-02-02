package com.example.seriestracker.addSeries;

import com.example.seriestracker.R;
import com.example.seriestracker.helper.FirebaseHelper;
import com.example.seriestracker.model.SearchSeries;
import com.example.seriestracker.model.SearchSeriesResponse;
import com.example.seriestracker.model.TvShow;
import com.example.seriestracker.movieDatabase.MovieApi;
import com.example.seriestracker.movieDatabase.NetworkConnection;
import com.example.seriestracker.utils.GlobalValues;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class AddSeriesPresenter implements IAddSeriesPresenter {
    private final AddSeriesActivity activity;

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

                    if (searchSeriesResponse.getSearchSeriesList() == null) {
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
        FirebaseHelper.getInstance().checkIfUserAlreadyAddedTvShow(new TvShow(GlobalValues.CURRENT_USER_ID, series.getName(),
                series.getId(), series.getImage()), this);
    }

    @Override
    public void addShowSeasonsAndEpisodesToFirebase() {
        //todo
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
}
