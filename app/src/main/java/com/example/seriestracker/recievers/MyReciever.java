package com.example.seriestracker.recievers;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.seriestracker.R;
import com.example.seriestracker.home.HomeActivity;
import com.example.seriestracker.model.TvShow;
import com.example.seriestracker.model.TvShowDetails;
import com.example.seriestracker.movieDatabase.MovieApi;
import com.example.seriestracker.movieDatabase.NetworkConnection;
import com.example.seriestracker.utils.GlobalValues;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class MyReciever extends BroadcastReceiver {
    // Notification ID.
    private static final int NOTIFICATION_ID = 0;
    // Notification channel ID.
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";
    private NotificationManager mNotificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        mNotificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        try {
            checkIfNotificationIsNeeded(context);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.d("PROBAA", "lefutott on recieve");
    }

    private void checkIfNotificationIsNeeded(Context context) throws ParseException {
        for (TvShowDetails tvShowDetails : GlobalValues.TV_SHOW_DETAILS) {
            @SuppressLint("SimpleDateFormat")
            Date date = Calendar.getInstance().getTime(); //new SimpleDateFormat("dd-MM-yyyy").parse(tvShowDetails.getNextEpisodeToAir().getAirDate());

            Log.d("PROBAA", date.toString());
            Log.d("PROBAA", Calendar.getInstance().getTime().toString());


            if (date.compareTo(Calendar.getInstance().getTime()) == 0) {
                deliverNotification(context, getTvShow(tvShowDetails.getId()).concat(" bla"));
            }
        }
    }

    private String getTvShow(int id) {
        for (TvShow tvShow : GlobalValues.TVSHOWS) {
            if (tvShow.getDbId() == id) {
                return tvShow.getName();
            }
        }

        return null;
    }

    /*private void addMissingTvShows() {
        if (GlobalValues.TV_SHOW_DETAILS == null) {
            GlobalValues.TV_SHOW_DETAILS = new ArrayList<>();
        }

        for (TvShow tvShow: GlobalValues.TVSHOWS) {
            if (!isContainingTvShow(tvShow.getDbId())) {
                addTvShowToDetails(tvShow.getDbId());
            }
        }
    }

    private boolean isContainingTvShow(int id) {
        for (TvShowDetails tvShowDetails : GlobalValues.TV_SHOW_DETAILS) {
            if (tvShowDetails.getId() == id) {
                return true;
            }
        }

        return false;
    }

    private void addTvShowToDetails(int id) {
        Call<TvShowDetails> call = NetworkConnection.getRetrofit().getSeasonNumber(String.valueOf(id), GlobalValues.API_KEY, "en-US");
        call.enqueue(new Callback<TvShowDetails>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<TvShowDetails> call, Response<TvShowDetails> response) {
                if (response.code() == GlobalValues.SUCCESSFUL_CODE) {
                    TvShowDetails tvShowDetails = response.body();
                    GlobalValues.TV_SHOW_DETAILS.add(tvShowDetails);
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<TvShowDetails> call, Throwable t) {

            }
        });
    }

    /*private void checkIfDataHasChanged(Context context) throws ParseException {
        if (GlobalValues.TVSHOWS != null) {
            for (int i = 0; i < GlobalValues.TVSHOWS.size(); ++i) {
                TvShow tvShow = GlobalValues.TVSHOWS.get(i);

                Log.d("PROBAA", "lefutott checkifdatahaschanged");

                TvShowDetails tvShowDetails = getTvShow(tvShow.getDbId());

                if (tvShowDetails != null && tvShowDetails.getNextEpisodeToAir() != null) {
                    @SuppressLint("SimpleDateFormat")
                    Date date = new SimpleDateFormat("dd-MM-yyyy").parse(tvShowDetails.getNextEpisodeToAir().getAirDate());

                    if (date.compareTo(Calendar.getInstance().getTime()) == 0) {
                        deliverNotification(context, tvShow.getName().concat(" bla"));
                    } else if (date.compareTo(Calendar.getInstance().getTime()) < 0) {
                        updateTvShowDetails(tvShowDetails.getId());
                    }
                }
            }
        }
    }

    private TvShowDetails getTvShow(int id) {
        for (TvShowDetails details : GlobalValues.TV_SHOW_DETAILS) {
            if (details.getId() == id) {
                return details;
            }
        }

        return null;
    }

    private void updateTvShowDetails(int id) {
        boolean wasAdded = false;

        for (int i = 0; i < GlobalValues.TV_SHOW_DETAILS.size(); ++i) {
            TvShowDetails showDetails = GlobalValues.TV_SHOW_DETAILS.get(i);

            if (showDetails.getId() == details.getId()) {
                GlobalValues.TV_SHOW_DETAILS.get(i).setNextEpisodeToAir(details.getNextEpisodeToAir());
                wasAdded = true;
                break;
            }
        }

        if (!wasAdded) {
            GlobalValues.TV_SHOW_DETAILS.add(details);
        }
    }*/

    private void deliverNotification(Context context, String title) {
        Log.d("PROBAA", "lefutott deliver");


        Intent contentIntent = new Intent(context, HomeActivity.class);

        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, NOTIFICATION_ID, contentIntent, PendingIntent
                        .FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder
                (context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_add_24)
                .setContentTitle(context.getResources().getString(R.string.new_episode))
                .setContentText(title)
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        // Deliver the notification
        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
