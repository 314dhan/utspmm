package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InsertBarangActivity extends AppCompatActivity {

    private EditText etNamaBarang, etHargaBarang, etQtyBarang;
    private Button btnSimpan;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_barang); // Pastikan layoutnya benar

        // Inisialisasi komponen UI
        etNamaBarang = findViewById(R.id.etNamaBarang);
        etHargaBarang = findViewById(R.id.etHargaBarang);
        etQtyBarang = findViewById(R.id.etQtyBarang);
        btnSimpan = findViewById(R.id.btnSimpan);

        // Inisialisasi DatabaseManager
        dbManager = new DatabaseManager(this);

        // Tombol Simpan
        btnSimpan.setOnClickListener(v -> {
            String namaBarang = etNamaBarang.getText().toString().trim();
            String hargaBarangStr = etHargaBarang.getText().toString().trim();
            String qtyBarangStr = etQtyBarang.getText().toString().trim();

            if (namaBarang.isEmpty() || hargaBarangStr.isEmpty() || qtyBarangStr.isEmpty()) {
                Toast.makeText(InsertBarangActivity.this, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int hargaBarang = Integer.parseInt(hargaBarangStr);
                int qtyBarang = Integer.parseInt(qtyBarangStr);

                // Panggil metode yang benar di DatabaseManager
                dbManager.tambahBarang(namaBarang, qtyBarang, hargaBarang);

                // Tampilkan pesan sukses
                Toast.makeText(InsertBarangActivity.this, "Barang berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                finish();  // Kembali ke MainActivity setelah menyimpan
            } catch (NumberFormatException e) {
                Toast.makeText(InsertBarangActivity.this, "Harga dan Quantity harus berupa angka", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

