package com.example.seriestracker.common;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.example.seriestracker.utils.Util;

public abstract class BaseActivity extends AppCompatActivity {
    public abstract void nextPage();

    public void onActionFailure(Context context, int message, int color) {
        Util.makeToast(context, message, color);
    }

    public void onActionSuccess(Context context, int message, int color) {
        Util.makeToast(context, message, color);
    }
}
