package com.example.seriestracker.splash;

import android.app.Activity;
import android.os.CountDownTimer;

import com.example.seriestracker.management.ActivityManager;

public class SplashscreenPresenter implements ISpalshscreenPresenter{

    @Override
    public void setUpCountDown(Activity activity) {
        new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                ActivityManager.startLoginActivity(activity);
            }
        }.start();
    }
}
