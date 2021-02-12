package com.example.seriestracker.home;

import android.os.AsyncTask;

import com.example.seriestracker.helper.FirebaseHelper;
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

@SuppressWarnings("deprecation")
public class UploadNewSeasonAsyncTask extends AsyncTask<Void, Void, Void> {

    private int dbId, seasonNumber;
    private List<UserData> userDataList;

    public UploadNewSeasonAsyncTask(int dbId, int seasonNumber) {
        this.dbId = dbId;
        this.seasonNumber = seasonNumber;
        this.userDataList = new ArrayList<>();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        final MovieApi api = NetworkConnection.getRetrofit();
        Call<TvShowSeasonDetailsRoot> call = api.getSeasonDetails(dbId, String.valueOf(seasonNumber), GlobalValues.API_KEY,
                "en-US");
        int finalSeason = seasonNumber;
        call.enqueue(new Callback<TvShowSeasonDetailsRoot>() {
            @Override
            @EverythingIsNonNull
            public void onResponse(Call<TvShowSeasonDetailsRoot> call, Response<TvShowSeasonDetailsRoot> response) {
                if (response.code() == GlobalValues.SUCCESSFUL_CODE) {
                    TvShowSeasonDetailsRoot seasonDetails = response.body();
                    List<TvShowEpisode> showEpisode = Objects.requireNonNull(seasonDetails).getEpisodes();

                    prepareDataForFirebase(showEpisode, dbId);

                    if (finalSeason == seasonNumber) {
                        FirebaseHelper.getInstance().addUserData(userDataList);
                    }
                }
            }

            @Override
            @EverythingIsNonNull
            public void onFailure(Call<TvShowSeasonDetailsRoot> call, Throwable t) {
            }
        });


        return null;
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
