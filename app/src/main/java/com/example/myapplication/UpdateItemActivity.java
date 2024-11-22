package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateItemActivity extends AppCompatActivity {

    private EditText etNamaBarang, etJumlahBarang, etHargaBarang;
    private Button btnUpdate, btnCancel;
    private Barang barang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);

        // Inisialisasi UI components
        etNamaBarang = findViewById(R.id.etNamaBarang);
        etJumlahBarang = findViewById(R.id.etJumlahBarang);
        etHargaBarang = findViewById(R.id.etHargaBarang);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnCancel = findViewById(R.id.btnCancel);

        // Ambil data barang dari intent
        barang = (Barang) getIntent().getSerializableExtra("barang");

        // Tampilkan data barang yang ingin diupdate
        if (barang != null) {
            etNamaBarang.setText(barang.getNamaBarang());
            etJumlahBarang.setText(String.valueOf(barang.getJumlahBarang()));
            etHargaBarang.setText(String.valueOf(barang.getHargaBarang()));
        }

        // Update barang ketika tombol update ditekan
        btnUpdate.setOnClickListener(v -> {
            String namaBarang = etNamaBarang.getText().toString();
            int jumlahBarang = Integer.parseInt(etJumlahBarang.getText().toString());
            double hargaBarang = Double.parseDouble(etHargaBarang.getText().toString());

            // Update ke database
            DatabaseManager dbManager = new DatabaseManager(UpdateItemActivity.this);
            dbManager.updateBarang(barang.getIdBarang(), namaBarang, jumlahBarang, hargaBarang);

            // Kembali ke MainActivity setelah update
            Intent resultIntent = new Intent();
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        // Cancel tombol
        btnCancel.setOnClickListener(v -> finish());
    }
}

