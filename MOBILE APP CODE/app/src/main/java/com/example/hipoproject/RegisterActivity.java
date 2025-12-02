package com.example.hipoproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = FirebaseFirestore.getInstance();

        ImageView btnBack = findViewById(R.id.btnBack);
        Button btnSignup = findViewById(R.id.btnSignup);
        TextView tvLogin = findViewById(R.id.tvLogin);

        EditText etName = findViewById(R.id.etName);
        EditText etPhone = findViewById(R.id.etNotlpn);
        EditText etEmail = findViewById(R.id.etEmail);
        EditText etPassword = findViewById(R.id.etPassword);

        btnBack.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LandingActivity.class));
            finish();
        });

        btnSignup.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) ||
                    TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Simpan data ke Firestore
            Map<String, Object> user = new HashMap<>();
            user.put("name", name);
            user.put("phone", phone);
            user.put("email", email);
            user.put("password", password);

            db.collection("users")
                    .document(email) // gunakan email sebagai ID unik
                    .set(user)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to register: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

        tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }
}
