package com.example.seriestracker.register;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.example.seriestracker.R;
import com.example.seriestracker.common.BaseActivity;
import com.example.seriestracker.utils.ActivityManager;

public class RegisterActivity extends BaseActivity {

    private ProgressBar progressBar;
    private EditText etName;
    private Button btnRegister;
    private ImageButton ibClose;
    private IRegisterPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.editTextName);
        btnRegister = findViewById(R.id.buttonRegister);
        ibClose = findViewById(R.id.imageButtonClose);
        progressBar = findViewById(R.id.progressBar);

        presenter = new RegisterPresenter(this);

        setUpOnClickListeners();
    }

    @Override
    public void nextPage() {
        ActivityManager.startHomeActivity(this);
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

    private void setUpOnClickListeners() {
        ibClose.setOnClickListener(v -> RegisterActivity.this.finish());

        btnRegister.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);

            String name = etName.getText().toString();
            presenter.registerUser(name);
        });
    }
}
