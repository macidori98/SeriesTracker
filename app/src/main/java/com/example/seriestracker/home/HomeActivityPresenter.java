package com.example.seriestracker.home;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.seriestracker.helper.FirebaseHelper;
import com.example.seriestracker.model.TvShow;
import com.example.seriestracker.model.UserDataWithKey;
import com.example.seriestracker.utils.ActivityManager;
import com.example.seriestracker.utils.ExportData;
import com.example.seriestracker.utils.GlobalValues;
import com.example.seriestracker.utils.Util;

import java.util.ArrayList;
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

    @SuppressWarnings("deprecation")
    @Override
    public void fetchTvShowsDone(List<TvShow> tvShows, List<UserDataWithKey> userData) {
        if (tvShows == null) {
            GlobalValues.TVSHOWS = new ArrayList<>();
        } else {
            GlobalValues.TVSHOWS = tvShows;
        }

        //start async task for notification
        new GetTvShowDetailsAsyncTask(activity).execute();

        if (tvShows != null) {
            activity.setUpRecyclerView(tvShows, userData);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void deleteTvShow(TvShow tvShow) {
        FirebaseHelper.getInstance().deleteShow(tvShow, this);
        GlobalValues.NEXT_EPISODES = new ArrayList<>();
        Util.setSharedPrefList(activity, GlobalValues.TV_SHOW_LIST, new ArrayList<>());
        new GetTvShowDetailsAsyncTask(activity).execute();
    }

    @Override
    public void exportData(List<TvShow> tvShows, List<UserDataWithKey> userData) {
        ExportData.export(this, activity, tvShows, userData);
    }

    @Override
    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            activity.export();
        }
    }

    @Override
    public void logout() {
        GlobalValues.CURRENT_USER = GlobalValues.CURRENT_USER_ID = "";

        Util.setSharedPref(activity, GlobalValues.NAME, "");
        Util.setSharedPref(activity, GlobalValues.USER_ID, "");

        Util.setSharedPrefList(activity, GlobalValues.TV_SHOW_LIST, new ArrayList<>());

        ActivityManager.startLoginActivity(activity);
        activity.finish();
    }
}
