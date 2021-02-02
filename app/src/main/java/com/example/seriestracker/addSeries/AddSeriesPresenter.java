package com.example.seriestracker.addSeries;

import com.example.seriestracker.R;
import com.example.seriestracker.model.SearchSeriesResponse;
import com.example.seriestracker.movieDatabase.MovieApi;
import com.example.seriestracker.movieDatabase.NetworkConnection;
import com.example.seriestracker.utils.GlobalValues;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class AddSeriesPresenter implements IAddSeriesPresenter{
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
        Call<SearchSeriesResponse> call = api.getSearchSeries(GlobalValues.API_KEY, "en-US", "1",
                name, "1");
        call.enqueue(new Callback<SearchSeriesResponse>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<SearchSeriesResponse> call, Response<SearchSeriesResponse> response) {
                if (response.code() == 200) {
                    SearchSeriesResponse searchSeriesResponse = response.body();
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
    public void onSuccess(int textId, int backgroundColorId) {

    }

    @Override
    public void onFailure(int textId, int backgroundColorId) {

    }
}
