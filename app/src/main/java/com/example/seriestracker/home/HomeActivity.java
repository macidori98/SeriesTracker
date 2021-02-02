package com.example.seriestracker.home;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seriestracker.R;
import com.example.seriestracker.common.BaseActivity;
import com.example.seriestracker.utils.ActivityManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends BaseActivity {
    private ImageButton ibExport;
    private FloatingActionButton fabAdd;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ibExport = findViewById(R.id.imageButtonExport);
        fabAdd = findViewById(R.id.floatingActionButtonAddSeries);
        recyclerView = findViewById(R.id.recyclerViewSeries);

        setOnClickListeners();
    }

    @Override
    public void nextPage() {

    }

    private void setOnClickListeners() {
        fabAdd.setOnClickListener(v -> ActivityManager.startAddSeriesActivity(HomeActivity.this));
    }
}
