package com.example.seriestracker.register;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.seriestracker.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText etName;
    private Button btnRegister;
    private ImageButton ibClose;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.editTextName);
        btnRegister = findViewById(R.id.buttonRegister);
        ibClose = findViewById(R.id.imageButtonClose);

        setUpOnClickListeners();
    }

    private void setUpOnClickListeners() {
        ibClose.setOnClickListener(v -> RegisterActivity.this.finish());
    }
}
