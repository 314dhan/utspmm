package com.example.myapplication;

public class Barang {
    private int id;
    private String namaBarang;
    private int jumlahBarang;
    private double hargaBarang;
    private boolean statusPembelian;

    public Barang(int id, String namaBarang, int jumlahBarang, double hargaBarang, boolean statusPembelian) {
        this.id = id;
        this.namaBarang = namaBarang;
        this.jumlahBarang = jumlahBarang;
        this.hargaBarang = hargaBarang;
        this.statusPembelian = statusPembelian;
    }

    public int getId() {
        return id;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public int getJumlahBarang() {
        return jumlahBarang;
    }

    public double getHargaBarang() {
        return hargaBarang;
    }

    public boolean isStatusPembelian() {
        return statusPembelian;
    }

    public void setStatusPembelian(boolean statusPembelian) {
        this.statusPembelian = statusPembelian;
    }
}
