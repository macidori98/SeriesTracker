package com.example.seriestracker.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.seriestracker.R;
import com.example.seriestracker.common.BaseActivity;
import com.example.seriestracker.utils.ActivityManager;

public class LoginActivity extends BaseActivity {

    private EditText etName;
    private Button btnLogin, btnRegister;
    private ILoginPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.buttonLogin);
        btnRegister = findViewById(R.id.buttonRegister);
        etName = findViewById(R.id.editTextName);

        presenter = new LoginPresenter(this);

        setOnClickListeners();
    }

    private void setOnClickListeners() {
        btnRegister.setOnClickListener(v -> ActivityManager.startRegisterActivity(LoginActivity.this));

        btnLogin.setOnClickListener(v -> {
            String name = etName.getText().toString();

            presenter.login(name);
        });
    }

    @Override
    public void nextPage() {

    }
}
