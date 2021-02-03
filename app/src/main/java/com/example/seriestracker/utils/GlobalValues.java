package com.example.seriestracker.utils;

import com.example.seriestracker.model.TvShow;
import com.example.seriestracker.model.UserData;

import java.util.List;

public class GlobalValues {
    public static final int SPLASH_SCREEN_LENGTH = 1500;
    public static final int COUNTDOWN_INTERVAL = 1000;
    public static final int MIN_STRING_LENGTH = 6;
    public static final int ZERO = 0;
    public static final int TOAST_OFFSET = 40;
    public static final int PADDING_VERTICAL = 15;
    public static final int PADDING_HORIZONTAL = 35;
    //db values
    public static final String BASE_URL = "https://api.themoviedb.org/";
    public static final String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500/";
    public static final String API_KEY = "55ad3422681300f02641616898bdbee0";
    public static final String USERS = "users";
    public static final String TV_SHOWS = "tv_shows";
    public static final String USER_DATA = "user_data";
    public static final String USER_ID = "user_id";
    public static final String NAME = "name";
    public static final String DB_ID = "db_id";//movie db id
    public static final String IMAGE_URL = "image_url";
    public static final String SEASON_NUMBER = "season_number";
    public static final String EPISODE_NUMBER = "episode_number";
    public static final String SEEN = "seen";
    public static final String LIKED = "liked";

    public static String CURRENT_USER;
    public static String CURRENT_USER_ID;

    public static List<UserData> USERDATAS;
    public static TvShow TVSHOW;

}
