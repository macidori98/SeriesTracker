package com.example.seriestracker.home;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seriestracker.R;
import com.example.seriestracker.common.BaseActivity;
import com.example.seriestracker.model.TvShow;
import com.example.seriestracker.model.UserData;
import com.example.seriestracker.utils.ActivityManager;
import com.example.seriestracker.utils.GlobalValues;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeActivity extends BaseActivity implements IHomeActivityView {
    private ImageButton ibExport, ibLogout;
    private FloatingActionButton fabAdd;
    private RecyclerView recyclerView;
    private IHomeActivityPresenter presenter;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ibExport = findViewById(R.id.imageButtonExport);
        ibLogout = findViewById(R.id.imageButtonLogout);
        fabAdd = findViewById(R.id.floatingActionButtonAddSeries);
        recyclerView = findViewById(R.id.recyclerViewSeries);
        progressBar = findViewById(R.id.progressBar);

        presenter = new HomeActivityPresenter(this);

        setOnClickListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.VISIBLE);
        presenter.fetchTvShows();
    }

    @Override
    public void nextPage() {

    }

    @Override
    public void setUpRecyclerView(List<TvShow> tvShows, List<UserData> userData) {
        SeriesCardAdapter adapter = new SeriesCardAdapter(tvShows, userData, this);
        adapter.setOnClickListener(position -> Toast.makeText(HomeActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    private void setOnClickListeners() {
        fabAdd.setOnClickListener(v -> ActivityManager.startAddSeriesActivity(HomeActivity.this));

        ibLogout.setOnClickListener(v -> {
            GlobalValues.CURRENT_USER = GlobalValues.CURRENT_USER_ID = "";
            ActivityManager.startLoginActivity(HomeActivity.this);
            HomeActivity.this.finish();
        });
    }
}
