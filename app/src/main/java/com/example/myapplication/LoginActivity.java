package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvRegisterLink;
    private DatabaseManager dbManager; // Untuk akses database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Menghubungkan layout

        // Inisialisasi komponen UI
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegisterLink = findViewById(R.id.tvRegisterLink);

        // Inisialisasi DatabaseManager untuk akses database
        dbManager = new DatabaseManager(this);

        // Listener untuk tombol login
        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString(); // Ambil username
            String password = etPassword.getText().toString(); // Ambil password

            // Panggil method untuk login user dari DatabaseManager
            Integer userId = dbManager.loginUser(username, password);
            if (userId != null) {
                SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("user_id", userId);  // Menyimpan userId
                editor.putString("username", username);  // Menyimpan username
                editor.apply();


                // Pindah ke MainActivity setelah login berhasil
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish(); // Menutup LoginActivity
            } else {
                // Jika login gagal, tampilkan pesan kesalahan
                Toast.makeText(LoginActivity.this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
            }
        });

        // Listener untuk link ke halaman registrasi
        tvRegisterLink.setOnClickListener(v -> {
            // Pindah ke RegisterActivity jika link register diklik
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }
}
