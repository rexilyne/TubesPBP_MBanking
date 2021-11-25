package com.example.tubespbp_mbanking.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tubespbp_mbanking.R;
import com.example.tubespbp_mbanking.adapter.AktivitasAdapter;
import com.example.tubespbp_mbanking.adapter.MutasiAdapter;
import com.example.tubespbp_mbanking.database.DatabaseAktivitas;
import com.example.tubespbp_mbanking.database.DatabaseMutasi;
import com.example.tubespbp_mbanking.databinding.FragmentMutasiBinding;
import com.example.tubespbp_mbanking.dialog.BottomMutasiDialog;
import com.example.tubespbp_mbanking.dialog.BottomMutasiDialogListener;
import com.example.tubespbp_mbanking.model.Mutasi;
import com.example.tubespbp_mbanking.model.User;
import com.example.tubespbp_mbanking.preferences.UserPreferences;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMutasi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMutasi extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private User userLogin;
    private FragmentMutasiBinding binding;
    private UserPreferences userPreferences;
    private List<User> userList;
    private RecyclerView recyclerView;
    private MutasiAdapter mutasiAdapter;
    private List<Mutasi> mutasiList, filteredList;
    private Mutasi checkMutasiDate;

    public FragmentMutasi() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMutasi.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMutasi newInstance(String param1, String param2) {
        FragmentMutasi fragment = new FragmentMutasi();
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
        binding = FragmentMutasiBinding.inflate(inflater, container, false);
        binding.setFragment(this);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userPreferences = new UserPreferences(getActivity());

        userLogin = userPreferences.getUserLogin();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public View.OnClickListener btnPilihTanggal = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            BottomMutasiDialog bottomMutasiDialog = new BottomMutasiDialog(getActivity());
            bottomMutasiDialog.setBottomMutasiDialogListener(new BottomMutasiDialogListener() {
                @Override
                public void userSelectedValue(String value) {
                    if(value.equals("hari_ini")) {

                    } else if(value.equals("pilih_tanggal_sendiri")) {
                        changeFragment(new FragmentMutasiAlt());
                    }
                }
            });
            bottomMutasiDialog.show();
        }
    };

    public View.OnClickListener btnCari = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mutasiList = new ArrayList<>();
            filteredList = new ArrayList<>();
            Date dateObj = new Date();
            getMutasiByAccNumber(userLogin.getAccountNumber());
            SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy hh:mm", Locale.forLanguageTag("in-ID"));

            for (int i = 0; i < mutasiList.size(); i++) {
                checkMutasiDate = mutasiList.get(i);
                try {
                    dateObj = df.parse(checkMutasiDate.getTanggal());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if(DateUtils.isToday(dateObj.getTime())) {
                    filteredList.add(checkMutasiDate);
                }
            }

            if(filteredList.isEmpty()) {
                Toast.makeText(FragmentMutasi.this.getContext(), "Tidak ada mutasi", Toast.LENGTH_SHORT).show();
            }

            mutasiAdapter = new MutasiAdapter(filteredList);

            recyclerView = binding.rvMutasi;
            recyclerView.setLayoutManager(new LinearLayoutManager(FragmentMutasi.this.getContext()));
            recyclerView.setAdapter(mutasiAdapter);
        }
    };

    public void changeFragment(Fragment fragment){
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_app_content,fragment)
                .addToBackStack("mutasi")
                .commit();
    }

    private void getMutasiByAccNumber(String search) {
        mutasiList = DatabaseMutasi.getInstance(getActivity().getApplicationContext())
                .getDatabase()
                .mutasiDao()
                .getMutasiByAccNumber(search);
    }

}