package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public DatabaseManager(Context context) {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase(); // Gunakan writable database untuk operasi insert/update/delete
    }

    // Method untuk login user
    public Integer loginUser(String username, String password) {
        Cursor cursor = db.query(
                "users",                // Nama tabel
                new String[]{"id", "username", "password"}, // Kolom yang ingin diambil
                "username=? AND password=?", // Kondisi pencarian (username dan password cocok)
                new String[]{username, password}, // Argumen untuk username dan password
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int userId = cursor.getInt(cursor.getColumnIndex("id"));
            cursor.close();
            return userId; // Mengembalikan ID pengguna yang berhasil login
        } else {
            if (cursor != null) cursor.close();
            return null; // Jika username dan password tidak cocok
        }
    }

    // Method untuk register user
    public long registerUser(String username, String password) {
        Cursor cursor = db.query(
                "users",
                new String[]{"username"},
                "username=?",
                new String[]{username},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            cursor.close();
            return -1; // Username sudah terdaftar
        }

        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);

        long userId = db.insert("users", null, values); // Menyimpan data user ke database
        if (cursor != null) cursor.close();
        return userId;
    }

    // Method untuk menambahkan barang baru ke tabel shopping_list
    public void tambahBarang(String nama, int jumlah, double harga) {
        ContentValues values = new ContentValues();
        values.put("nama_barang", nama);
        values.put("jumlah_barang", jumlah);
        values.put("harga_barang", harga);
        values.put("status_pembelian", 0); // Default belum dibeli
        db.insert("shopping_list", null, values);
    }

    // Method untuk mengambil semua data barang dari tabel shopping_list
    public List<Barang> getAllBarang() {
        List<Barang> barangList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM shopping_list", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String namaBarang = cursor.getString(cursor.getColumnIndex("nama_barang"));
                int jumlahBarang = cursor.getInt(cursor.getColumnIndex("jumlah_barang"));
                double hargaBarang = cursor.getDouble(cursor.getColumnIndex("harga_barang"));
                boolean statusPembelian = cursor.getInt(cursor.getColumnIndex("status_pembelian")) == 1;

                barangList.add(new Barang(id, namaBarang, jumlahBarang, hargaBarang, statusPembelian));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return barangList;
    }

    // Method untuk memperbarui status pembelian barang
    public void updateStatusPembelian(int id, boolean status) {
        ContentValues values = new ContentValues();
        values.put("status_pembelian", status ? 1 : 0);
        db.update("shopping_list", values, "id=?", new String[]{String.valueOf(id)});
    }

    // Method untuk menghapus barang dari tabel shopping_list
    public void hapusBarang(int id) {
        db.delete("shopping_list", "id=?", new String[]{String.valueOf(id)});
    }
}
