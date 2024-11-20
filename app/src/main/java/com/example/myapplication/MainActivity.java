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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvWelcomeMessage, tvSummary, tvNoDataMessage;
    private RecyclerView rvBarangData;
    private Button btnLogout;
    private DatabaseManager dbManager;
    private BarangAdapter barangAdapter;  // Adapter untuk RecyclerView
    private List<Barang> barangList;      // List data barang

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi komponen UI
        rvBarangData = findViewById(R.id.rvShoppingList);  // Inisialisasi RecyclerView
        tvSummary = findViewById(R.id.tvSummary);
        tvWelcomeMessage = findViewById(R.id.tvWelcomeMessage);
        tvNoDataMessage = findViewById(R.id.tvNoDataMessage);
        btnLogout = findViewById(R.id.btnLogout);
        FloatingActionButton fabAddItem = findViewById(R.id.fabAddItem); // FloatingActionButton untuk tambah barang

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
        barangList = dbManager.getAllBarang();

        // Hitung total barang dan harga
        int totalBarang = 0;
        double totalHarga = 0.0;
        for (Barang barang : barangList) {
            totalBarang += barang.getJumlahBarang();
            totalHarga += barang.getHargaBarang() * barang.getJumlahBarang();
        }

        // Update TextView dengan total jumlah barang dan total harga
        tvSummary.setText("Total Barang: " + totalBarang + " | Total Harga: Rp " + totalHarga);

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

        // FloatingActionButton untuk tambah barang
        fabAddItem.setOnClickListener(v -> {
            // Pindah ke activity InsertBarangActivity untuk menambah barang
            Intent intent = new Intent(MainActivity.this, InsertBarangActivity.class);
            startActivity(intent);
        });

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
