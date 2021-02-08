package com.example.seriestracker.home;

import com.example.seriestracker.model.TvShow;
import com.example.seriestracker.model.UserDataWithKey;

import java.util.List;

public interface IHomeActivityView {
    void setUpRecyclerView(List<TvShow> tvShows, List<UserDataWithKey> userData);

    void export();
}
