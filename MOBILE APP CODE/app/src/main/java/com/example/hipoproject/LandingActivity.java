package com.example.hipoproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnSignUp = findViewById(R.id.btnSignUp);

        // Saat tombol LOGIN ditekan → pindah ke LoginActivity
        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(LandingActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        // Saat tombol SIGN UP ditekan → pindah ke RegisterActivity
        btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LandingActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
