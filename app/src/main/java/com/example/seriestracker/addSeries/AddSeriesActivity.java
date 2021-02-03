package com.example.seriestracker.addSeries;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seriestracker.R;
import com.example.seriestracker.common.BaseActivity;
import com.example.seriestracker.model.SearchSeries;
import com.example.seriestracker.model.SearchSeriesResponse;
import com.example.seriestracker.utils.Util;

public class AddSeriesActivity extends BaseActivity implements IAddSeriesView {

    private ImageButton ibClose, ibSearch;
    private EditText etTitle;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private IAddSeriesPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_series);

        ibClose = findViewById(R.id.imageButtonClose);
        ibSearch = findViewById(R.id.imageButtonSearch);
        etTitle = findViewById(R.id.editTextTitle);
        recyclerView = findViewById(R.id.recyclerViewChooseSeries);
        progressBar = findViewById(R.id.progressBar);

        presenter = new AddSeriesPresenter(this);

        setOnClickListeners();
    }

    @Override
    public void nextPage() {

    }

    @Override
    public void onActionFailure(Context context, int message, int color) {
        super.onActionFailure(context, message, color);
        progressBar.setVisibility(View.GONE);
        recyclerView.setEnabled(true);
        etTitle.setEnabled(true);
        ibSearch.setEnabled(true);
        ibClose.setEnabled(true);
    }

    @Override
    public void setUpRecyclerView(SearchSeriesResponse list) {
        AddSeriesAdapter adapter = new AddSeriesAdapter(list.getSearchSeriesList(), this);

        adapter.setOnClickListener(position -> showAlertDialog(list.getSearchSeriesList().get(position)));

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
        recyclerView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        progressBar.setVisibility(View.GONE);
                        recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
    }

    @Override
    public void tvShowAdded() {
        progressBar.setVisibility(View.GONE);
        this.finish();
    }

    private void setOnClickListeners() {
        ibClose.setOnClickListener(v -> finish());

        ibSearch.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            String name = etTitle.getText().toString();
            Util.hideKeyboard(this);

            presenter.search(name);
        });
    }

    private void showAlertDialog(SearchSeries series) {
        String message = getResources().getString(R.string.add_new_series).concat(" ").concat(series.getName()).concat("?");

        AlertDialog alertDialog = new AlertDialog.Builder(AddSeriesActivity.this).create();
        alertDialog.setTitle(R.string.add_series);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                (dialog, which) -> {
                    progressBar.setVisibility(View.VISIBLE);
                    recyclerView.setEnabled(false);
                    etTitle.setEnabled(false);
                    ibSearch.setEnabled(false);
                    ibClose.setEnabled(false);

                    presenter.addToFirebase(series);

                    dialog.dismiss();
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                ((dialog, which) -> dialog.dismiss()));
        alertDialog.show();
    }
}
