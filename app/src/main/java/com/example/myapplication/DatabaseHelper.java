package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "app_database";
    private static final int DATABASE_VERSION = 3; // Versi 2 untuk migrasi

    // Nama tabel dan kolom-kolomnya untuk tabel 'users'
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    // Nama tabel dan kolom-kolomnya untuk tabel 'shopping_list'
    private static final String TABLE_SHOPPING_LIST = "shopping_list";
    private static final String COLUMN_SHOPPING_ID = "id";
    private static final String COLUMN_NAMA_BARANG = "nama_barang";
    private static final String COLUMN_JUMLAH_BARANG = "jumlah_barang";
    private static final String COLUMN_HARGA_BARANG = "harga_barang";
    private static final String COLUMN_STATUS_PEMBELIAN = "status_pembelian";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String CREATE_USERS_TABLE = "CREATE TABLE users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT UNIQUE, " +
                    "password TEXT)";
            db.execSQL(CREATE_USERS_TABLE);

            String CREATE_SHOPPING_LIST_TABLE = "CREATE TABLE shopping_list (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nama_barang TEXT, " +
                    "jumlah_barang INTEGER, " +
                    "harga_barang REAL, " +
                    "status_pembelian INTEGER)";
            db.execSQL(CREATE_SHOPPING_LIST_TABLE);

            android.util.Log.d("DatabaseHelper", "Tabel berhasil dibuat!");
        } catch (Exception e) {
            android.util.Log.e("DatabaseHelper", "Error saat membuat tabel: " + e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Jika versi database berubah, hapus tabel lama dan buat tabel baru
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPING_LIST); // Hapus tabel shopping_list jika ada
        onCreate(db); // Membuat tabel baru
    }
}
