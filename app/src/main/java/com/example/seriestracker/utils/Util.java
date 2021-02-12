package com.example.seriestracker.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.seriestracker.R;
import com.example.seriestracker.model.NextEpisode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static com.example.seriestracker.utils.GlobalValues.PADDING_HORIZONTAL;
import static com.example.seriestracker.utils.GlobalValues.PADDING_VERTICAL;
import static com.example.seriestracker.utils.GlobalValues.TOAST_OFFSET;
import static com.example.seriestracker.utils.GlobalValues.ZERO;

public class Util {

    public static boolean isStringLengthOk(String string) {
        return string.length() >= GlobalValues.MIN_STRING_LENGTH;
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NonConstantResourceId")
    public static void makeToast(Context context, int textId, int backgroundColor) {
        Toast toast = Toast.makeText(context, textId, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, ZERO, TOAST_OFFSET);
        View view = toast.getView();

        switch (backgroundColor) {
            case R.color.primaryColor:
                view.setBackgroundResource(R.drawable.toast_background_warning);
                break;
            case R.color.green:
                view.setBackgroundResource(R.drawable.toast_background_green);
                break;
            case R.color.red:
                view.setBackgroundResource(R.drawable.toast_background_red);
                break;
        }

        view.setPadding(PADDING_HORIZONTAL, PADDING_VERTICAL, PADDING_HORIZONTAL, PADDING_VERTICAL);
        TextView text = view.findViewById(android.R.id.message);
        text.setTextColor(Color.WHITE);
        toast.show();
    }

    public static void setSharedPref(Context context, String key, String str) {
        SharedPreferences.Editor sharedPreferences = Objects.requireNonNull(context).getSharedPreferences(GlobalValues.USERS, MODE_PRIVATE).edit();
        sharedPreferences.putString(key, str);
        sharedPreferences.apply();
    }

    public static String getSharedPref(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalValues.USERS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static <T> void setSharedPrefList(Context context, String key, List<T> list) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalValues.USERS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(list);

        editor.putString(key, json);
        editor.apply();
    }

    public static List<NextEpisode> getList(Context context, String key) {
        List<NextEpisode> arrayItems;
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalValues.USERS, Context.MODE_PRIVATE);
        String serializedObject = sharedPreferences.getString(key, null);

        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<NextEpisode>>() {
            }.getType();
            arrayItems = gson.fromJson(serializedObject, type);
            return arrayItems;
        }

        return null;
    }
}
