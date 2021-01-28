package com.example.seriestracker.splash;

import android.app.Activity;
import android.os.CountDownTimer;

import com.example.seriestracker.common.INavigation;
import com.example.seriestracker.utils.ActivityManager;
import com.example.seriestracker.utils.Constants;

public class SplashscreenPresenter implements ISpalshscreenPresenter{

    private INavigation iNavigation;

    public SplashscreenPresenter(INavigation iNavigation) {
        this.iNavigation = iNavigation;
    }

    @Override
    public void setUpCountDown(Activity activity) {
        new CountDownTimer(Constants.SPLASH_SCREEN_LENGTH, Constants.COUNTDOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                iNavigation.nextPage();
            }
        }.start();
    }
}
