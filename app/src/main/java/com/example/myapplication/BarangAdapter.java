package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.BarangViewHolder> {

    private List<Barang> barangList;

    public BarangAdapter(List<Barang> barangList) {
        this.barangList = barangList;
    }

    @NonNull
    @Override
    public BarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang, parent, false);
        return new BarangViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BarangViewHolder holder, int position) {
        Barang barang = barangList.get(position);
        holder.tvNamaBarang.setText(barang.getNamaBarang());
        holder.tvJumlahBarang.setText("Jumlah: " + barang.getJumlahBarang());
        holder.tvHargaBarang.setText("Harga: Rp " + barang.getHargaBarang());
    }

    @Override
    public int getItemCount() {
        return barangList.size();
    }

    static class BarangViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaBarang, tvJumlahBarang, tvHargaBarang;

        public BarangViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaBarang = itemView.findViewById(R.id.tvNamaBarang);
            tvJumlahBarang = itemView.findViewById(R.id.tvJumlahBarang);
            tvHargaBarang = itemView.findViewById(R.id.tvHargaBarang);
        }
    }
}
