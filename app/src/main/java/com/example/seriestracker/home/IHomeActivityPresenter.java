package com.example.seriestracker.home;

import com.example.seriestracker.common.IBasePresenter;
import com.example.seriestracker.model.TvShow;
import com.example.seriestracker.model.UserData;

import java.util.List;

public interface IHomeActivityPresenter extends IBasePresenter {
    void fetchTvShows();

    void fetchTvShowsDone(List<TvShow> tvShowList, List<UserData> userData);
}
