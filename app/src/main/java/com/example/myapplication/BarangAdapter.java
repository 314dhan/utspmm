package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.BarangViewHolder> {

    private List<Barang> barangList;
    private OnItemClickListener clickListener;
    private OnItemDeleteListener deleteListener;

    // Interface untuk klik item
    public interface OnItemClickListener {
        void onItemClick(Barang barang);
    }

    // Interface untuk hapus item
    public interface OnItemDeleteListener {
        void onItemDelete(Barang barang, int position);
    }

    // Constructor
    public BarangAdapter(List<Barang> barangList, OnItemClickListener clickListener, OnItemDeleteListener deleteListener) {
        this.barangList = barangList;
        this.clickListener = clickListener;
        this.deleteListener = deleteListener;
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
        holder.bind(barang);

        // Memberikan listener untuk delete
        holder.btnDelete.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onItemDelete(barang, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return barangList.size();
    }

    public class BarangViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNamaBarang;
        private TextView tvJumlahBarang;
        private TextView tvHargaBarang;
        private ImageButton btnDelete;

        public BarangViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNamaBarang = itemView.findViewById(R.id.tvNamaBarang);
            tvJumlahBarang = itemView.findViewById(R.id.tvJumlahBarang);
            tvHargaBarang = itemView.findViewById(R.id.tvHargaBarang);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            // Listener untuk klik item
            itemView.setOnClickListener(v -> {
                if (clickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Barang barang = barangList.get(position);
                        Context context = itemView.getContext(); // Mendapatkan context di sini
                        Intent intent = new Intent(context, UpdateItemActivity.class);
                        intent.putExtra("barang", barang); // Kirim data barang
                        context.startActivity(intent);
                    }
                }
            });
        }

        public void bind(Barang barang) {
            tvNamaBarang.setText(barang.getNamaBarang());
            tvJumlahBarang.setText("Jumlah: " + barang.getJumlahBarang());
            tvHargaBarang.setText("Harga: Rp " + barang.getHargaBarang());
        }
    }
}

