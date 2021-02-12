package com.example.seriestracker.details;

import com.example.seriestracker.model.UserDataWithKey;

import java.util.List;

public interface IDetailsActivityPresenter {
    List<String> getStringArrayFromList();

    List<UserDataWithKey> getSeasonEpisodes(int season);
}
