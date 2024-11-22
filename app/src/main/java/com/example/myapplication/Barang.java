package com.example.myapplication;

import java.io.Serializable;

public class Barang implements Serializable {
    private int idBarang;
    private String namaBarang;
    private int jumlahBarang;
    private double hargaBarang;
    private boolean statusPembelian;

    // Konstruktor, getter, setter, dan metode lainnya

    public Barang(int idBarang, String namaBarang, int jumlahBarang, double hargaBarang, boolean statusPembelian) {
        this.idBarang = idBarang;
        this.namaBarang = namaBarang;
        this.jumlahBarang = jumlahBarang;
        this.hargaBarang = hargaBarang;
        this.statusPembelian = statusPembelian;
    }

    // Getter dan Setter
    public int getIdBarang() {
        return idBarang;
    }

    public void setIdBarang(int idBarang) {
        this.idBarang = idBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public int getJumlahBarang() {
        return jumlahBarang;
    }

    public void setJumlahBarang(int jumlahBarang) {
        this.jumlahBarang = jumlahBarang;
    }

    public double getHargaBarang() {
        return hargaBarang;
    }

    public void setHargaBarang(double hargaBarang) {
        this.hargaBarang = hargaBarang;
    }

    public boolean isStatusPembelian() {
        return statusPembelian;
    }

    public void setStatusPembelian(boolean statusPembelian) {
        this.statusPembelian = statusPembelian;
    }
}

