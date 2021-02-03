package com.example.seriestracker.home;

import com.example.seriestracker.common.IBasePresenter;
import com.example.seriestracker.model.TvShow;

import java.util.List;

public interface IHomeActivityPresenter extends IBasePresenter {
    void fetchTvShows();

    void fetchTvShowsDetails();

    void fetchTvShowsDone(List<TvShow> tvShowList);
}
