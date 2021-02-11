package com.example.seriestracker.utils;

import com.example.seriestracker.model.TvShow;
import com.example.seriestracker.model.TvShowDetails;
import com.example.seriestracker.model.UserDataWithKey;

import java.util.List;

public class GlobalValues {
    public static final int SPLASH_SCREEN_LENGTH = 1500;
    public static final int COUNTDOWN_INTERVAL = 1000;
    public static final int MIN_STRING_LENGTH = 6;
    public static final int ZERO = 0;
    public static final int TOAST_OFFSET = 40;
    public static final int PADDING_VERTICAL = 15;
    public static final int PADDING_HORIZONTAL = 35;
    public static final int NOTIFICATION_ID = 0;
    public static final int SUCCESSFUL_CODE = 200;
    //db values
    public static final String BASE_URL = "https://api.themoviedb.org/";
    public static final String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w500/";
    public static final String API_KEY = "55ad3422681300f02641616898bdbee0";
    public static final String USERS = "users";
    public static final String TV_SHOWS = "tv_shows";
    public static final String TV_SHOW_ID = "tvShow_id";
    public static final String USER_DATA = "user_data";
    public static final String USER_ID = "user_id";
    public static final String NAME = "name";
    public static final String DB_ID = "db_id";//movie db id
    public static final String IMAGE_URL = "image_url";
    public static final String SEASON_NUMBER = "season_number";
    public static final String EPISODE_NUMBER = "episode_number";
    public static final String SEEN = "seen";
    public static final String LIKED = "liked";

    public static final String TABLE_TV_SHOWS = "CREATE TABLE IF NOT EXISTS tv_shows (\n" +
            "tvShow_id TEXT PRIMARY KEY,\n" +
            "db_id INTEGER NOT NULL,\n" +
            "image_url TEXT NOT NULL,\n" +
            "name TEXT NOT NULL,\n" +
            "season_number INTEGER NOT NULL,\n" +
            "user_id TEXT NOT NULL\n" +
            ");\n" +
            "\n" +
            "INSERT INTO tv_shows (tvShow_id, db_id, image_url, name, season_number, user_id) VALUES";

    public static final String TABLE_USER_DATA = "CREATE TABLE IF NOT EXISTS user_data (\n" +
            "key TEXT PRIMARY KEY,\n" +
            "db_id INTEGER NOT NULL,\n" +
            "image_url TEXT NOT NULL,\n" +
            "name TEXT NOT NULL,\n" +
            "season_number INTEGER NOT NULL,\n" +
            "episode_number INTEGER NOT NULL,\n" +
            "user_id TEXT NOT NULL,\n" +
            "liked INTEGER NOT NULL,\n" +
            "seen INTEGER NOT NULL\n" +
            ");\n" +
            "\n" +
            "INSERT INTO user_data (key, db_id, image_url, name, season_number, episode_number, user_id, liked, seen) VALUES";

    public static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    public static String CURRENT_USER;
    public static String CURRENT_USER_ID;

    public static List<UserDataWithKey> USERDATAS;
    public static List<TvShow> TVSHOWS;
    public static List<TvShowDetails> TV_SHOW_DETAILS;
    public static TvShow TVSHOW;

}
