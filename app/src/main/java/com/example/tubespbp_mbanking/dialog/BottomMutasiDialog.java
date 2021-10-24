package com.example.tubespbp_mbanking.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.tubespbp_mbanking.databinding.BottomMutasiDialogBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class BottomMutasiDialog extends BottomSheetDialog {
    private BottomMutasiDialogBinding binding;
    private BottomMutasiDialogListener bottomMutasiDialogListener;
    private String selectedValue;

    public BottomMutasiDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = BottomMutasiDialogBinding.inflate(LayoutInflater.from(getContext()));
        setContentView(binding.getRoot());

        binding.setDialog(this);
    }

    public View.OnClickListener btnHariIni = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            selectedValue = "hari_ini";
            getBottomMutasiDialogListener().userSelectedValue(selectedValue);
            dismiss();
        }
    };

    public View.OnClickListener btnPilihTanggalSendiri = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            selectedValue = "pilih_tanggal_sendiri";
            getBottomMutasiDialogListener().userSelectedValue(selectedValue);
            dismiss();
        }
    };

    public BottomMutasiDialogListener getBottomMutasiDialogListener() {
        return bottomMutasiDialogListener;
    }

    public void setBottomMutasiDialogListener(BottomMutasiDialogListener bottomMutasiDialogListener) {
        this.bottomMutasiDialogListener = bottomMutasiDialogListener;
    }
}
