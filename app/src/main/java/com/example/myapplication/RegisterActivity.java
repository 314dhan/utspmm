package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnRegister;
    private DatabaseManager dbManager;  // Instance DatabaseManager untuk berinteraksi dengan database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);  // Menghubungkan layout untuk RegisterActivity

        // Inisialisasi komponen UI
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);

        // Inisialisasi DatabaseManager
        dbManager = new DatabaseManager(this);

        // Listener untuk tombol register
        btnRegister.setOnClickListener(v -> {
            String username = etUsername.getText().toString();  // Ambil username dari input
            String password = etPassword.getText().toString();  // Ambil password dari input

            // Panggil method registerUser dari DatabaseManager untuk daftar pengguna baru
            long userId = dbManager.registerUser(username, password);

            if (userId != -1) {
                // Jika pendaftaran berhasil
                Toast.makeText(RegisterActivity.this, "Registration successful.", Toast.LENGTH_SHORT).show();

                // Pindah ke LoginActivity setelah registrasi berhasil
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();  // Tutup RegisterActivity
            } else {
                // Jika username sudah ada
                Toast.makeText(RegisterActivity.this, "Username already exists.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

