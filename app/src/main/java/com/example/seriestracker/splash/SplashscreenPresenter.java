package com.example.seriestracker.splash;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.SystemClock;

import com.example.seriestracker.R;
import com.example.seriestracker.home.HomeActivity;
import com.example.seriestracker.recievers.MyReciever;
import com.example.seriestracker.utils.GlobalValues;
import com.example.seriestracker.utils.Util;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.seriestracker.utils.GlobalValues.NOTIFICATION_ID;
import static com.example.seriestracker.utils.GlobalValues.PRIMARY_CHANNEL_ID;

public class SplashscreenPresenter implements ISpalshscreenPresenter {

    private final SplashscreenActivity activity;
    private NotificationManager mNotificationManager;

    public SplashscreenPresenter(SplashscreenActivity activity) {
        this.activity = activity;
    }

    @Override
    public void setUpCountDown() {
        new CountDownTimer(GlobalValues.SPLASH_SCREEN_LENGTH, GlobalValues.COUNTDOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (checkIfUserAlreadyLoggedIn()) {
                    activity.nextPage();
                } else {
                    activity.nextPage(activity, new HomeActivity());
                }

                activity.finish();
            }
        }.start();
    }

    @Override
    public void setUpNotification() {
        Intent notifyIntent = new Intent(activity, MyReciever.class);

        final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                (activity, NOTIFICATION_ID, notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        final AlarmManager alarmManager = (AlarmManager) activity.getSystemService
                (ALARM_SERVICE);

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_HOUR,
                    AlarmManager.INTERVAL_HOUR,
                    notifyPendingIntent);
        }

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        mNotificationManager = (NotificationManager)
                activity.getSystemService(NOTIFICATION_SERVICE);

        mNotificationManager =
                (NotificationManager) activity.getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel
                    (PRIMARY_CHANNEL_ID,
                            activity.getString(R.string.new_episode_arrival),
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription(activity.getResources().getString(R.string.notify_new_episode));
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private boolean checkIfUserAlreadyLoggedIn() {
        GlobalValues.CURRENT_USER = Util.getSharedPref(activity, GlobalValues.NAME);
        GlobalValues.CURRENT_USER_ID = Util.getSharedPref(activity, GlobalValues.USER_ID);
        return GlobalValues.CURRENT_USER.isEmpty() && GlobalValues.CURRENT_USER_ID.isEmpty();
    }
}
