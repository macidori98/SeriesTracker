package com.example.seriestracker.utils;

import android.content.Context;

import com.example.seriestracker.R;
import com.example.seriestracker.home.IHomeActivityPresenter;
import com.example.seriestracker.model.TvShow;
import com.example.seriestracker.model.UserDataWithKey;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportData {
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void export(IHomeActivityPresenter presenter, Context context, List<TvShow> tvShows, List<UserDataWithKey> userDatas) {
        try {
            File root = context.getExternalFilesDir(context.getResources().getString(R.string.database));

            if (!root.exists()) {
                root.mkdirs();
            }

            writeTvShow(context, root, tvShows);
            writeUserData(context, root, userDatas);
            exportToJson(context, root, tvShows, userDatas);

            presenter.onSuccess(R.string.success, R.color.green);

        } catch (Exception e) {
            e.printStackTrace();
            presenter.onFailure(R.string.problem_at_export, R.color.red);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void writeTvShow(Context context, File root, List<TvShow> tvShows) throws IOException {
        File tvShowExport = new File(root + context.getResources().getString(R.string.tv_show_sql));
        if (tvShowExport.exists()) {
            tvShowExport.delete();
        }

        FileWriter writer = new FileWriter(tvShowExport);

        writer.append(GlobalValues.TABLE_TV_SHOWS);
        writer.append("\n");

        for (int i = 0; i < tvShows.size(); ++i) {
            TvShow show = tvShows.get(i);
            writer.append("(\"");
            writer.append(show.getTvShowId());
            writer.append("\",");
            writer.append(String.valueOf(show.getDbId()));
            writer.append(",\"");
            writer.append(show.getImage());
            writer.append("\",\"");
            writer.append(show.getName());
            writer.append("\",");
            writer.append(String.valueOf(show.getSeasonNumber()));
            writer.append(",\"");
            writer.append(show.getUserId());

            if (i != tvShows.size() - 1) {
                writer.append("\"),\n");
            } else {
                writer.append("\");");
            }
        }

        writer.flush();
        writer.close();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void writeUserData(Context context, File root, List<UserDataWithKey> userDatas) throws IOException {
        File tvShowExport = new File(root + context.getResources().getString(R.string.user_data_sql));
        if (tvShowExport.exists()) {
            tvShowExport.delete();
        }

        FileWriter writer = new FileWriter(tvShowExport);

        writer.append(GlobalValues.TABLE_USER_DATA);
        writer.append("\n");

        for (int i = 0; i < userDatas.size(); ++i) {
            UserDataWithKey data = userDatas.get(i);
            writer.append("(\"");
            writer.append(data.getKey());
            writer.append("\",");
            writer.append(String.valueOf(data.getDbId()));
            writer.append(",\"");
            writer.append(data.getImage());
            writer.append("\",\"");
            writer.append(data.getName());
            writer.append("\",");
            writer.append(String.valueOf(data.getSeasonNumber()));
            writer.append(",");
            writer.append(String.valueOf(data.getEpisodeNumber()));
            writer.append(",\"");
            writer.append(data.getUserId());
            writer.append("\",");
            writer.append(data.getLiked() ? "1" : "0");
            writer.append(",");
            writer.append(data.getSeen() ? "1" : "0");

            if (i != userDatas.size() - 1) {
                writer.append("),\n");
            } else {
                writer.append(");");
            }
        }

        writer.flush();
        writer.close();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void exportToJson(Context context, File root, List<TvShow> tvShows, List<UserDataWithKey> userData) throws IOException {
        Gson gson = new Gson();
        String tvShowString = gson.toJson(tvShows);
        String userDataString = gson.toJson(userData);

        File tvShowExport = new File(root + context.getResources().getString(R.string.tv_show_json));
        if (tvShowExport.exists()) {
            tvShowExport.delete();
        }

        FileWriter writer = new FileWriter(tvShowExport);

        writer.append(tvShowString);
        writer.flush();
        writer.close();

        File userDataExport = new File(root + context.getResources().getString(R.string.user_data_json));
        if (userDataExport.exists()) {
            userDataExport.delete();
        }

        FileWriter writer2 = new FileWriter(userDataExport);
        writer2.append(userDataString);
        writer2.flush();
        writer2.close();
    }
}
