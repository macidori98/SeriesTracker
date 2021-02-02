package com.example.seriestracker.addSeries;

import com.example.seriestracker.common.IBasePresenter;
import com.example.seriestracker.model.SearchSeries;

public interface IAddSeriesPresenter extends IBasePresenter {
    void search(String name);

    void addToFirebase(SearchSeries series);

    void addShowSeasonsAndEpisodesToFirebase();
}
