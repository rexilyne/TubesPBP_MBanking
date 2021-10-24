package com.example.tubespbp_mbanking.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tubespbp_mbanking.databinding.RecyclerItemAktivitasBinding;
import com.example.tubespbp_mbanking.model.Aktivitas;

import java.util.ArrayList;
import java.util.List;

public class AktivitasAdapter extends RecyclerView.Adapter<AktivitasAdapter.RecyclerViewHolder> {
    List<Aktivitas> aktivitasList;

    public AktivitasAdapter(List<Aktivitas> aktivitasList) {
        this.aktivitasList = aktivitasList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerItemAktivitasBinding binding =
                RecyclerItemAktivitasBinding.inflate(layoutInflater, parent, false);
        return new RecyclerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Aktivitas aktivitas = aktivitasList.get(position);
        holder.bind(aktivitas);
    }

    @Override
    public int getItemCount() {
        return aktivitasList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerItemAktivitasBinding binding;

        public RecyclerViewHolder(RecyclerItemAktivitasBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Aktivitas aktivitas) {
            binding.setAktivitas(aktivitas);
            binding.executePendingBindings();
        }
    }
}
