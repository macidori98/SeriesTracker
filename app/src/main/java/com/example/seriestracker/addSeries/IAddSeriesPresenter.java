package com.example.seriestracker.addSeries;

import com.example.seriestracker.common.IBasePresenter;
import com.example.seriestracker.model.SearchSeries;
import com.example.seriestracker.model.TvShow;

public interface IAddSeriesPresenter extends IBasePresenter {
    void search(String name);

    void addToFirebase(SearchSeries series);

    void addShowSeasonsAndEpisodesToFirebase(TvShow tvShow);
}
