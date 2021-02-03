package com.example.seriestracker.utils;

import android.app.Activity;
import android.content.Intent;

import com.example.seriestracker.addSeries.AddSeriesActivity;
import com.example.seriestracker.details.DetailsActivity;
import com.example.seriestracker.home.HomeActivity;
import com.example.seriestracker.login.LoginActivity;
import com.example.seriestracker.model.TvShow;
import com.example.seriestracker.model.UserData;
import com.example.seriestracker.register.RegisterActivity;

import java.util.List;

public class ActivityManager {

    public static void startNextActivity(Activity prevActivity, Activity nextActivity) {
        Intent intent = new Intent(prevActivity, nextActivity.getClass());
        prevActivity.startActivity(intent);
    }

    public static void startLoginActivity(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    public static void startRegisterActivity(Activity activity) {
        Intent intent = new Intent(activity, RegisterActivity.class);
        activity.startActivity(intent);
    }

    public static void startHomeActivity(Activity activity) {
        Intent intent = new Intent(activity, HomeActivity.class);
        activity.startActivity(intent);
    }

    public static void startDetailsActivity(Activity activity, TvShow tvShows, List<UserData> userData) {
        Intent intent = new Intent(activity, DetailsActivity.class);
        GlobalValues.USERDATAS = userData;
        GlobalValues.TVSHOW = tvShows;
        activity.startActivity(intent);
    }

    public static void startAddSeriesActivity(Activity activity) {
        Intent intent = new Intent(activity, AddSeriesActivity.class);
        activity.startActivity(intent);
    }
}
