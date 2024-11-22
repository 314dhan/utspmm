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

    private RecyclerView rvBarangData;
    private DatabaseManager dbManager;
    private BarangAdapter barangAdapter;
    private List<Barang> barangList;
    private TextView tvSummary, tvWelcomeMessage, tvNoDataMessage;
    private Button btnLogout;

    private void updateSummary() {
        int totalBarang = 0;
        int totalHarga = 0;

        for (Barang barang : barangList) {
            totalBarang += barang.getJumlahBarang();
            totalHarga += barang.getJumlahBarang() * barang.getHargaBarang();
        }

        // Update TextView
        tvSummary.setText("Total Barang: " + totalBarang + " | Total Harga: Rp " + totalHarga);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi komponen UI
        rvBarangData = findViewById(R.id.rvShoppingList);
        tvSummary = findViewById(R.id.tvSummary);
        tvWelcomeMessage = findViewById(R.id.tvWelcomeMessage);
        tvNoDataMessage = findViewById(R.id.tvNoDataMessage);
        btnLogout = findViewById(R.id.btnLogout);
        FloatingActionButton fabAddItem = findViewById(R.id.fabAddItem);

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

        // Cek apakah data barang ada
        if (barangList != null && !barangList.isEmpty()) {
            // Data ada, tampilkan di RecyclerView
            barangAdapter = new BarangAdapter(
                    barangList,
                    barang -> {
                        // Klik untuk edit barang
                        Intent intent = new Intent(this, UpdateItemActivity.class);
                        intent.putExtra("barang_id", barang.getIdBarang());
                        startActivity(intent);
                    },
                    (barang, position) -> {
                        // Klik untuk hapus barang
                        dbManager.deleteBarang(barang.getIdBarang());
                        barangList.remove(position);
                        barangAdapter.notifyItemRemoved(position);
                        updateSummary();
                    }
            );

            rvBarangData.setAdapter(barangAdapter);
            rvBarangData.setVisibility(View.VISIBLE);
            tvNoDataMessage.setVisibility(View.GONE);
        } else {
            // Data tidak ada
            rvBarangData.setVisibility(View.GONE);
            tvNoDataMessage.setVisibility(View.VISIBLE);
        }

        // FloatingActionButton untuk tambah barang
        fabAddItem.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, InsertBarangActivity.class);
            startActivity(intent);
        });

        // Tombol logout
        btnLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();

            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh data ketika kembali ke activity ini
        barangList.clear();
        barangList.addAll(dbManager.getAllBarang());
        if (barangAdapter != null) {
            barangAdapter.notifyDataSetChanged();
        }
        updateSummary();
    }
}
