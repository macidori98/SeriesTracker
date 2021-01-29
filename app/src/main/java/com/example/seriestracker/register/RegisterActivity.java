package com.example.seriestracker.register;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;

import com.example.seriestracker.R;
import com.example.seriestracker.common.BaseActivity;
import com.example.seriestracker.utils.ActivityManager;

public class RegisterActivity extends BaseActivity {

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

        presenter = new RegisterPresenter(this);

        setUpOnClickListeners();
    }

    @Override
    public void nextPage() {
        ActivityManager.startHomeActivity(this);
    }


    private void setUpOnClickListeners() {
        ibClose.setOnClickListener(v -> RegisterActivity.this.finish());

        btnRegister.setOnClickListener(v -> {
            String name = etName.getText().toString();
            presenter.registerUser(name);
        });
    }
}
