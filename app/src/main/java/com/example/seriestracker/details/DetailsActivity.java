package com.example.seriestracker.details;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.seriestracker.R;
import com.example.seriestracker.utils.GlobalValues;

public class DetailsActivity extends AppCompatActivity {

    private ImageView ivCover;
    private TextView tvTitle;
    private Spinner spinnerSeason;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvTitle = findViewById(R.id.textViewTitle);
        ivCover = findViewById(R.id.imageViewCover);
        spinnerSeason = findViewById(R.id.spinnerSeason);
        recyclerView = findViewById(R.id.recyclerViewEpisodes);

        setUpView();
    }

    private void setUpView() {
        loadImage();
        tvTitle.setText(GlobalValues.TVSHOW.getName());

        DetailsAdapter adapter = new DetailsAdapter(GlobalValues.USERDATAS, this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    private void loadImage() {
        String URL = GlobalValues.BASE_URL_IMAGE.concat(GlobalValues.TVSHOW.getImage());
        Glide.with(this).load(URL).into(ivCover);
    }
}
