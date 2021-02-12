package com.example.seriestracker.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.example.seriestracker.model.NextEpisodeToAir;
import com.example.seriestracker.model.TvShow;
import com.example.seriestracker.model.TvShowDetails;
import com.example.seriestracker.movieDatabase.NetworkConnection;
import com.example.seriestracker.utils.GlobalValues;
import com.example.seriestracker.utils.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

@SuppressWarnings("deprecation")
public class GetTvShowDetailsAsyncTask extends AsyncTask<Void, Void, Void> {
    @SuppressLint("StaticFieldLeak")
    private final Context context;

    public GetTvShowDetailsAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (GlobalValues.NEXT_EPISODES == null) {
            GlobalValues.NEXT_EPISODES = new ArrayList<>();
        }

        for (TvShow tvShow : GlobalValues.TVSHOWS) {
            Call<TvShowDetails> call = NetworkConnection.getRetrofit().getSeasonNumber(String.valueOf(tvShow.getDbId()),
                    GlobalValues.API_KEY, "en-US");

            call.enqueue(new Callback<TvShowDetails>() {
                @EverythingIsNonNull
                @Override
                public void onResponse(Call<TvShowDetails> call, Response<TvShowDetails> response) {
                    if (response.code() == GlobalValues.SUCCESSFUL_CODE) {
                        TvShowDetails tvShowDetails = response.body();

                        NextEpisodeToAir date = tvShowDetails.getNextEpisodeToAir();
                        if (date != null) {
                            boolean alreadyAdded = false;
                            for (int i = 0; i < GlobalValues.NEXT_EPISODES.size(); ++i) {
                                TvShowDetails ne = GlobalValues.NEXT_EPISODES.get(i);

                                if (ne.getId() == tvShow.getDbId()) {
                                    GlobalValues.NEXT_EPISODES.get(i).getNextEpisodeToAir().setAirDate(ne.getNextEpisodeToAir().getAirDate());
                                    alreadyAdded = true;
                                    break;
                                }
                            }

                            if (!alreadyAdded) {
                                tvShowDetails.setName(tvShow.getName());
                                GlobalValues.NEXT_EPISODES.add(tvShowDetails);
                            }
                        }

                        if (tvShow.getDbId() == GlobalValues.TVSHOWS.get(GlobalValues.TVSHOWS.size() - 1).getDbId()) {
                            Util.setSharedPrefList(context, GlobalValues.TV_SHOW_LIST, GlobalValues.NEXT_EPISODES);
                        }

                    }
                }

                @EverythingIsNonNull
                @Override
                public void onFailure(Call<TvShowDetails> call, Throwable t) {

                }
            });
        }

        return null;
    }
}
