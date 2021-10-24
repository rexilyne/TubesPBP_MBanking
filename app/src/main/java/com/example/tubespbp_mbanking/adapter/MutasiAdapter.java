package com.example.tubespbp_mbanking.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tubespbp_mbanking.databinding.RecyclerItemMutasiBinding;
import com.example.tubespbp_mbanking.model.Mutasi;

import java.util.ArrayList;
import java.util.List;


public class MutasiAdapter extends RecyclerView.Adapter<MutasiAdapter.RecyclerViewHolder>{
    List<Mutasi> mutasiList;

    public MutasiAdapter(List<Mutasi> mutasiList) {
        this.mutasiList = mutasiList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerItemMutasiBinding binding =
                RecyclerItemMutasiBinding.inflate(layoutInflater, parent, false);
        return new RecyclerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Mutasi mutasi = mutasiList.get(position);
        holder.bind(mutasi);
    }

    @Override
    public int getItemCount() {
        return mutasiList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerItemMutasiBinding binding;

        public RecyclerViewHolder(RecyclerItemMutasiBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Mutasi mutasi) {
            binding.setMutasi(mutasi);
            binding.executePendingBindings();
        }
    }
}
