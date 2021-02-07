package com.example.seriestracker.home;

import com.example.seriestracker.helper.FirebaseHelper;
import com.example.seriestracker.model.TvShow;
import com.example.seriestracker.model.UserDataWithKey;

import java.util.List;

public class HomeActivityPresenter implements IHomeActivityPresenter {
    private final HomeActivity activity;

    public HomeActivityPresenter(HomeActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onSuccess(int textId, int backgroundColorId) {
        activity.onActionSuccess(activity, textId, backgroundColorId);
    }

    @Override
    public void onFailure(int textId, int backgroundColorId) {
        activity.onActionFailure(activity, textId, backgroundColorId);
    }

    @Override
    public void fetchTvShows() {
        FirebaseHelper.getInstance().getUserTvShows(this);
    }

    @Override
    public void fetchTvShowsDone(List<TvShow> tvShows, List<UserDataWithKey> userData) {
        activity.setUpRecyclerView(tvShows, userData);
    }

    @Override
    public void deleteTvShow(TvShow tvShow) {
        FirebaseHelper.getInstance().deleteShow(tvShow, this);
    }
}
