package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvWelcomeMessage, tvNoDataMessage;
    private RecyclerView rvBarangData;
    private Button btnLogout;
    private DatabaseManager dbManager;
    private BarangAdapter barangAdapter;  // Adapter untuk RecyclerView
    private List<Barang> barangList;      // List data barang

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.deleteDatabase("app_database"); // Nama database sesuai dengan yang ada di DatabaseHelper
        setContentView(R.layout.activity_main);

        // Inisialisasi komponen UI
        tvWelcomeMessage = findViewById(R.id.tvWelcomeMessage);
        rvBarangData = findViewById(R.id.rvShoppingList);
        tvNoDataMessage = findViewById(R.id.tvNoDataMessage);
        btnLogout = findViewById(R.id.btnLogout);

        // Inisialisasi DatabaseManager
        dbManager = new DatabaseManager(this);

        // Ambil username dari SharedPreferences
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String username = prefs.getString("username", "User");

        // Tampilkan pesan welcome
        tvWelcomeMessage.setText("Welcome, " + username + "!");

        // Setup RecyclerView
        rvBarangData.setLayoutManager(new LinearLayoutManager(this));

        // Ambil data barang dari database
        barangList = dbManager.getAllBarang(); // Method untuk mengambil semua data barang

        // Cek apakah data barang ada
        if (barangList != null && !barangList.isEmpty()) {
            // Data ada, tampilkan di RecyclerView
            barangAdapter = new BarangAdapter(barangList);
            rvBarangData.setAdapter(barangAdapter);
            rvBarangData.setVisibility(View.VISIBLE);  // Tampilkan RecyclerView
            tvNoDataMessage.setVisibility(View.GONE);   // Sembunyikan pesan "Data is not available"
        } else {
            // Data tidak ada, tampilkan pesan
            rvBarangData.setVisibility(View.GONE);    // Sembunyikan RecyclerView
            tvNoDataMessage.setVisibility(View.VISIBLE); // Tampilkan pesan "Data is not available"
        }

        // Tombol logout
        btnLogout.setOnClickListener(v -> {
            // Logout dan kembali ke LoginActivity
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();  // Hapus semua data dari SharedPreferences
            editor.apply();

            // Pindah ke LoginActivity
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();  // Tutup MainActivity
        });
    }
}
