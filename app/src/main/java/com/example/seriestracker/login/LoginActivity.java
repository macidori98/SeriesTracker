package com.example.seriestracker.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.seriestracker.R;
import com.example.seriestracker.utils.ActivityManager;

public class LoginActivity extends AppCompatActivity {

    private EditText etName;
    private Button btnLogin, btnRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.buttonLogin);
        btnRegister = findViewById(R.id.buttonRegister);
        etName = findViewById(R.id.editTextName);

        setOnClickListeners();
    }

    private void setOnClickListeners() {
        btnRegister.setOnClickListener(v -> ActivityManager.startRegisterActivity(LoginActivity.this));
    }
}
