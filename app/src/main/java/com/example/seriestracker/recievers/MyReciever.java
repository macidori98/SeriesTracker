package com.example.seriestracker.recievers;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.seriestracker.R;
import com.example.seriestracker.model.TvShowDetails;
import com.example.seriestracker.splash.SplashscreenActivity;
import com.example.seriestracker.utils.GlobalValues;
import com.example.seriestracker.utils.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
        checkIfNotificationIsNeeded(context);
    }

    @SuppressLint("SimpleDateFormat")
    private void checkIfNotificationIsNeeded(Context context) {
        List<TvShowDetails> nextEpisodes = Util.getList(context, GlobalValues.TV_SHOW_LIST);
        if (nextEpisodes != null) {
            for (TvShowDetails nextEpisode : nextEpisodes) {
                Date today;
                String output;
                SimpleDateFormat formatter;

                formatter = new SimpleDateFormat("dd-MM-yyyy");
                today = new Date();
                output = formatter.format(today);

                //if (output.compareTo(nextEpisode.getNextEpisodeToAir().getAirDate()) == 0) {
                if (output.compareTo(output) == 0) {
                    deliverNotification(context, nextEpisode.getName());
                }
            }
        }
    }

    private void deliverNotification(Context context, String title) {
        Intent contentIntent = new Intent(context, SplashscreenActivity.class);

        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, NOTIFICATION_ID, contentIntent, PendingIntent
                        .FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder
                (context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
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
