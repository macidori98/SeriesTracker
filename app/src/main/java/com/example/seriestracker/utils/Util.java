package com.example.seriestracker.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class Util {

    public static boolean isStringLengthOk(String string) {
        return string.length() >= 6;
    }

    public static void makeToast(Context context, int textId, int backgroundColor) {
        Toast toast = Toast.makeText(context, textId, Toast.LENGTH_SHORT);
        View view = toast.getView();
        view.setBackgroundResource(backgroundColor);
        TextView text = (TextView) view.findViewById(android.R.id.message);
        text.setTextColor(Color.WHITE);
        toast.show();
    }

    public static void setSharedPref(Context context, String str) {
        SharedPreferences.Editor sharedPreferences = Objects.requireNonNull(context).getSharedPreferences(GlobalValues.USERS, MODE_PRIVATE).edit();
        sharedPreferences.putString(GlobalValues.USERS, str);
        sharedPreferences.apply();
    }

    public static String getSharedPref(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(str, Context.MODE_PRIVATE);
        return sharedPreferences.getString(str, "");
    }
}
