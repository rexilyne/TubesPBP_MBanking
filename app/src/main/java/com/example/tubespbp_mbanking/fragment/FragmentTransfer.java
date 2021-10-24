package com.example.tubespbp_mbanking.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tubespbp_mbanking.R;
import com.example.tubespbp_mbanking.database.DatabaseUser;
import com.example.tubespbp_mbanking.databinding.FragmentHomeBinding;
import com.example.tubespbp_mbanking.databinding.FragmentTransferBinding;
import com.example.tubespbp_mbanking.dialog.PinDialog;
import com.example.tubespbp_mbanking.dialog.PinDialogListener;
import com.example.tubespbp_mbanking.model.Aktivitas;
import com.example.tubespbp_mbanking.model.Mutasi;
import com.example.tubespbp_mbanking.model.User;
import com.example.tubespbp_mbanking.preferences.UserPreferences;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTransfer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTransfer extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private User userLogin;
    private Aktivitas aktivitas;
    private Mutasi mutasi;
    private FragmentTransferBinding binding;
    private UserPreferences userPreferences;
    private List<User> userList;

    public FragmentTransfer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTransfer.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTransfer newInstance(String param1, String param2) {
        FragmentTransfer fragment = new FragmentTransfer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTransferBinding.inflate(inflater, container, false);
        binding.setFragment(this);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userPreferences = new UserPreferences(getActivity());

        userLogin = userPreferences.getUserLogin();
        aktivitas = new Aktivitas();

        binding.setUser(userLogin);
        binding.setAktivitas(aktivitas);
    }

    public View.OnClickListener btnTransfer = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(binding.etRekeningTujuan.getEditText().getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Rekening tujuan tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else if(!isNumeric(aktivitas.getAccountNumberDest())) {
                Toast.makeText(getActivity(), "Rekening tujuan harus angka", Toast.LENGTH_SHORT).show();
            } else if(binding.etJumlahTransfer.getEditText().getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Jumlah transfer tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else if(aktivitas.getNominal() < 10000) {
                Toast.makeText(getActivity(), "Jumlah transfer minimal Rp 10.000,-", Toast.LENGTH_SHORT).show();
            } else if(!isNumeric(aktivitas.getStringNominal())) {
                Toast.makeText(getActivity(), "Jumlah transfer harus angka", Toast.LENGTH_SHORT).show();
            } else if(aktivitas.getNominal() > userLogin.getNominal()) {
                Toast.makeText(getActivity(), "Jumlah transfer melebihi saldo", Toast.LENGTH_SHORT).show();
            } else {
                if(binding.etKeterangan.getEditText().getText().toString().isEmpty()) {
                    aktivitas.setKeterangan("");
                }

                PinDialog pinDialog = new PinDialog(getActivity(), userLogin.getPin());
                pinDialog.setPinDialogListener(new PinDialogListener() {
                    @Override
                    public void pinConfirmed(String pin) {
                        if(userLogin.getPin().equals(pin)) {
                            Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                pinDialog.show();
            }
        }
    };

    public View.OnClickListener btnBatal = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            binding.etRekeningTujuan.getEditText().setText("");
            binding.etJumlahTransfer.getEditText().setText("");
            binding.etKeterangan.getEditText().setText("");
        }
    };

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    private void addAktivitas(Aktivitas aktivitas) {
        class AddAktivitas extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseUser.getInstance(getActivity().getApplicationContext())
                        .getDatabase()
                        .aktivitasDao()
                        .insertAktivitas(aktivitas);

                return null;
            }
        }

        AddAktivitas addAktivitas = new AddAktivitas(  );
        addAktivitas.execute();
    }
}