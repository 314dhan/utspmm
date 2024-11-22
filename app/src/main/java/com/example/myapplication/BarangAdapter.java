package com.example.myapplication;

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
            tvNamaBarang = itemView.findViewById(R.id.tvNamaBarang);  // Hanya deklarasi sekali
            tvJumlahBarang = itemView.findViewById(R.id.tvJumlahBarang);
            tvHargaBarang = itemView.findViewById(R.id.tvHargaBarang);  // Pastikan menambahkan deklarasi untuk tvHargaBarang
            btnDelete = itemView.findViewById(R.id.btnDelete);

            // Listener untuk klik item
            itemView.setOnClickListener(v -> {
                if (clickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        clickListener.onItemClick(barangList.get(position));
                    }
                }
            });

            // Listener untuk tombol hapus
            btnDelete.setOnClickListener(v -> {
                if (deleteListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        deleteListener.onItemDelete(barangList.get(position), position);
                    }
                }
            });
        }


        public void bind(Barang barang) {
            tvNamaBarang.setText(barang.getNamaBarang());
            tvJumlahBarang.setText("Jumlah: " + barang.getJumlahBarang());
            tvHargaBarang.setText("Harga: Rp " + barang.getHargaBarang());  // Pastikan harga ditampilkan dengan benar
        }
    }
}
