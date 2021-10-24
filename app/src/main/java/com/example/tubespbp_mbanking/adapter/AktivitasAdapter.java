package com.example.tubespbp_mbanking.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tubespbp_mbanking.R;
import com.example.tubespbp_mbanking.activity.MainActivity;
import com.example.tubespbp_mbanking.databinding.RecyclerItemAktivitasBinding;
import com.example.tubespbp_mbanking.fragment.FragmentDetailAktivitas;
import com.example.tubespbp_mbanking.fragment.FragmentDetailTransfer;
import com.example.tubespbp_mbanking.model.Aktivitas;

import java.util.ArrayList;
import java.util.List;

public class AktivitasAdapter extends RecyclerView.Adapter<AktivitasAdapter.RecyclerViewHolder> {
    List<Aktivitas> aktivitasList;
    private Context context;

    public AktivitasAdapter(List<Aktivitas> aktivitasList, Context context) {
        this.aktivitasList = aktivitasList;
        this.context = context;
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentDetailAktivitas fragmentDetailAktivitas = new FragmentDetailAktivitas();
                Bundle bundle = new Bundle();
                bundle.putSerializable("aktivitas_bundle", aktivitasList.get(holder.getBindingAdapterPosition()));
                fragmentDetailAktivitas.setArguments(bundle);

                changeFragment(fragmentDetailAktivitas);
            }
        });
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

    public void changeFragment(Fragment fragment){
        ((MainActivity)context).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_app_content,fragment)
                .commit();
    }
}
