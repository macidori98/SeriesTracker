package com.example.seriestracker.home;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.seriestracker.helper.FirebaseHelper;
import com.example.seriestracker.model.TvShow;
import com.example.seriestracker.model.UserDataWithKey;
import com.example.seriestracker.utils.ExportData;
import com.example.seriestracker.utils.GlobalValues;

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

    @Override
    public void fetchTvShowsDone(List<TvShow> tvShows, List<UserDataWithKey> userData) {
        if (tvShows == null) {
            GlobalValues.TVSHOWS = new ArrayList<>();
        } else {
            GlobalValues.TVSHOWS = tvShows;
        }
        activity.setUpRecyclerView(tvShows, userData);
    }

    @Override
    public void deleteTvShow(TvShow tvShow) {
        FirebaseHelper.getInstance().deleteShow(tvShow, this);

        //deleteShowFromTvShowDetails(tvShow.getDbId());
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

    /*private void deleteShowFromTvShowDetails(int id) {
        for (int i = 0; i < GlobalValues.TV_SHOW_DETAILS.size(); ++i) {
            TvShowDetails details = GlobalValues.TV_SHOW_DETAILS.get(i);
            if (details.getId() == id) {
                GlobalValues.TV_SHOW_DETAILS.remove(i);
                break;
            }
        }
    }*/
}
