package com.example.seriestracker.splash;

import android.os.CountDownTimer;

import com.example.seriestracker.home.HomeActivity;
import com.example.seriestracker.utils.GlobalValues;
import com.example.seriestracker.utils.Util;

public class SplashscreenPresenter implements ISpalshscreenPresenter {

    private final SplashscreenActivity activity;

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
            }
        }.start();
    }

    private boolean checkIfUserAlreadyLoggedIn() {
        GlobalValues.CURRENT_USER = Util.getSharedPref(activity, GlobalValues.NAME);
        GlobalValues.CURRENT_USER_ID = Util.getSharedPref(activity, GlobalValues.USER_ID);
        return GlobalValues.CURRENT_USER.isEmpty() && GlobalValues.CURRENT_USER_ID.isEmpty();
    }
}
