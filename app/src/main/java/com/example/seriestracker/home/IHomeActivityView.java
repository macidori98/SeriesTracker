package com.example.seriestracker.home;

import com.example.seriestracker.model.TvShow;
import com.example.seriestracker.model.UserData;

import java.util.List;

public interface IHomeActivityView {
    void setUpRecyclerView(List<TvShow> tvShows, List<UserData> userData);
}
