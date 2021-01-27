package com.example.seriestracker.splash;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.seriestracker.R;

public class SplashscreenActivity extends AppCompatActivity {

    private TextView tvName;
    private ISpalshscreenPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        tvName = findViewById(R.id.textViewName);
        presenter = new SplashscreenPresenter();

        setUpAnimation();
        startCountDown();
    }

    private void setUpAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.text_anim);
        tvName.setAnimation(animation);
    }

    private void startCountDown() {
        presenter.setUpCountDown(this);
    }
}
