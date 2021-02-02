package com.example.seriestracker.addSeries;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seriestracker.R;
import com.example.seriestracker.common.BaseActivity;
import com.example.seriestracker.model.SearchSeriesResponse;

public class AddSeriesActivity extends BaseActivity implements IAddSeriesView {

    private ImageButton ibClose, ibSearch;
    private Button btnAdd;
    private EditText etTitle;
    private RecyclerView recyclerView;
    private IAddSeriesPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_series);

        ibClose = findViewById(R.id.imageButtonClose);
        ibSearch = findViewById(R.id.imageButtonSearch);
        etTitle = findViewById(R.id.editTextTitle);
        btnAdd = findViewById(R.id.buttonAddSeries);
        recyclerView = findViewById(R.id.recyclerViewChooseSeries);

        presenter = new AddSeriesPresenter(this);

        setOnClickListeners();
    }

    @Override
    public void nextPage() {

    }

    @Override
    public void setUpRecyclerView(SearchSeriesResponse list) {
        AddSeriesAdapter adapter = new AddSeriesAdapter(list.getSearchSeriesList(), this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListeners() {
        ibClose.setOnClickListener(v -> finish());

        ibSearch.setOnClickListener(v -> {
            String name = etTitle.getText().toString();

            presenter.search(name);
        });
    }
}
