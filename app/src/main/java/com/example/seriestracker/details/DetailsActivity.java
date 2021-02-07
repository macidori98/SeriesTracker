package com.example.seriestracker.details;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.seriestracker.R;
import com.example.seriestracker.model.TvShow;
import com.example.seriestracker.model.UserDataWithKey;
import com.example.seriestracker.utils.GlobalValues;

import java.util.ArrayList;
import java.util.List;

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

        spinnerSeason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    List<UserDataWithKey> elements = getSeasonEpisodes(position);
                    DetailsAdapter adapter = new DetailsAdapter(elements, DetailsActivity.this);

                    recyclerView.setLayoutManager(new LinearLayoutManager(DetailsActivity.this));
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        GlobalValues.USERDATAS.clear();
        GlobalValues.TVSHOW = new TvShow();
    }

    private List<UserDataWithKey> getSeasonEpisodes(int season) {
        int id = GlobalValues.TVSHOW.getDbId();

        List<UserDataWithKey> userDataWithKeys = new ArrayList<>();
        for (UserDataWithKey udwk : GlobalValues.USERDATAS) {
            if (udwk.getSeasonNumber() == season && udwk.getDbId() == id) {
                userDataWithKeys.add(udwk);
            }
        }

        return userDataWithKeys;
    }

    private void setUpView() {
        loadImage();
        tvTitle.setText(GlobalValues.TVSHOW.getName());

        setSpinnerData();
    }

    private void loadImage() {
        String URL = GlobalValues.BASE_URL_IMAGE.concat(GlobalValues.TVSHOW.getImage());
        Glide.with(this).load(URL).into(ivCover);
    }

    private void setSpinnerData() {
        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, getStringArrayFromList());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSeason.setAdapter(spinnerAdapter);
    }

    private List<String> getStringArrayFromList() {
        List<String> array = new ArrayList<>();
        int seasonNumber = GlobalValues.TVSHOW.getSeasonNumber();

        array.add("Choose season");
        for (int season = 1; season <= seasonNumber; ++season) {
            array.add("Season ".concat(String.valueOf(season)));
        }

        return array;
    }
}
