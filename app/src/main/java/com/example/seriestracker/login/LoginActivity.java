package com.example.seriestracker.login;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.example.seriestracker.R;
import com.example.seriestracker.common.BaseActivity;
import com.example.seriestracker.utils.ActivityManager;

public class LoginActivity extends BaseActivity {

    private EditText etName;
    private Button btnLogin, btnRegister;
    private ILoginPresenter presenter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.buttonLogin);
        btnRegister = findViewById(R.id.buttonRegister);
        etName = findViewById(R.id.editTextName);
        progressBar = findViewById(R.id.progressBar);

        presenter = new LoginPresenter(this);

        setOnClickListeners();
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
    public void nextPage() {
        ActivityManager.startHomeActivity(this);
    }

    private void setOnClickListeners() {
        btnRegister.setOnClickListener(v -> ActivityManager.startRegisterActivity(LoginActivity.this));

        btnLogin.setOnClickListener(v -> {
            progressBar.setVisibility(View.VISIBLE);
            String name = etName.getText().toString();

            presenter.login(name);
        });
    }
}
