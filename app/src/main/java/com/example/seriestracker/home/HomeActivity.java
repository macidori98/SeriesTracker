package com.example.seriestracker.home;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seriestracker.R;
import com.example.seriestracker.common.BaseActivity;
import com.example.seriestracker.model.TvShow;
import com.example.seriestracker.model.UserDataWithKey;
import com.example.seriestracker.utils.ActivityManager;
import com.example.seriestracker.utils.GlobalValues;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class HomeActivity extends BaseActivity implements IHomeActivityView {
    private ImageButton ibExport, ibLogout;
    private FloatingActionButton fabAdd;
    private RecyclerView recyclerView;
    private IHomeActivityPresenter presenter;
    private ProgressBar progressBar;
    private SeriesCardAdapter adapter;
    private List<TvShow> tvShows;
    private TextView tvNoEntries;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ibExport = findViewById(R.id.imageButtonExport);
        ibLogout = findViewById(R.id.imageButtonLogout);
        fabAdd = findViewById(R.id.floatingActionButtonAddSeries);
        recyclerView = findViewById(R.id.recyclerViewSeries);
        progressBar = findViewById(R.id.progressBar);
        tvNoEntries = findViewById(R.id.textViewNoEntries);

        presenter = new HomeActivityPresenter(this);

        setOnClickListeners();
        setRecyclerViewSwipeAction();
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
    public void onActionFailure(Context context, int message, int color) {
        super.onActionFailure(context, message, color);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onActionSuccess(Context context, int message, int color) {
        super.onActionSuccess(context, message, color);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setUpRecyclerView(List<TvShow> tvShows, List<UserDataWithKey> userData) {
        this.tvShows = tvShows;

        if (tvShows.size()==0) {
            tvNoEntries.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvNoEntries.setVisibility(View.GONE);

            adapter = new SeriesCardAdapter(tvShows, userData, this, this);
            adapter.setOnClickListener(position -> ActivityManager.startDetailsActivity(this, tvShows.get(position), userData));

            recyclerView.setVisibility(View.VISIBLE);
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
    }

    private void setOnClickListeners() {
        fabAdd.setOnClickListener(v -> ActivityManager.startAddSeriesActivity(HomeActivity.this));

        ibLogout.setOnClickListener(v -> {
            GlobalValues.CURRENT_USER = GlobalValues.CURRENT_USER_ID = "";
            ActivityManager.startLoginActivity(HomeActivity.this);
            HomeActivity.this.finish();
        });

        ibExport.setOnClickListener(v -> Toast.makeText(this, "under work", Toast.LENGTH_SHORT).show());
    }

    private void setRecyclerViewSwipeAction() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                progressBar.setVisibility(View.VISIBLE);

                int position = viewHolder.getAdapterPosition();

                presenter.deleteTvShow(tvShows.get(position));
                adapter.notifyItemRemoved(position);
            }

            @SuppressWarnings("deprecation")
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

                new RecyclerViewSwipeDecorator.Builder(HomeActivity.this, c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(HomeActivity.this, R.color.red))
                        .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                        .setActionIconTint(ContextCompat.getColor(recyclerView.getContext(), android.R.color.white))
                        .create()
                        .decorate();
            }
        }).attachToRecyclerView(recyclerView);
    }
}
