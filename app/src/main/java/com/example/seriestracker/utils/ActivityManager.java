package com.example.seriestracker.utils;

import android.app.Activity;
import android.content.Intent;

import com.example.seriestracker.login.LoginActivity;
import com.example.seriestracker.register.RegisterActivity;

public class ActivityManager {

    public static void startLoginActivity(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    public static void startRegisterActivity(Activity activity) {
        Intent intent = new Intent(activity, RegisterActivity.class);
        activity.startActivity(intent);
    }

}
