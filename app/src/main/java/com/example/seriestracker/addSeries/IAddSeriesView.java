package com.example.seriestracker.addSeries;

import com.example.seriestracker.model.SearchSeriesResponse;

public interface IAddSeriesView {
    void setUpRecyclerView(SearchSeriesResponse list);

    void tvShowAdded();
}
