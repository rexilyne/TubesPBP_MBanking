package com.example.tubespbp_mbanking.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.tubespbp_mbanking.R;
import com.example.tubespbp_mbanking.adapter.MutasiAdapter;
import com.example.tubespbp_mbanking.database.DatabaseMutasi;
import com.example.tubespbp_mbanking.databinding.FragmentMutasiAltBinding;
import com.example.tubespbp_mbanking.databinding.FragmentMutasiBinding;
import com.example.tubespbp_mbanking.model.Mutasi;
import com.example.tubespbp_mbanking.model.User;
import com.example.tubespbp_mbanking.preferences.UserPreferences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentMutasiAlt#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMutasiAlt extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private User userLogin;
    private FragmentMutasiAltBinding binding;
    private UserPreferences userPreferences;
    private List<User> userList;
    private RecyclerView recyclerView;
    private MutasiAdapter mutasiAdapter;
    private List<Mutasi> mutasiList, filteredList;
    private Mutasi checkMutasiDate;
    private DatePickerDialog datePickerDialog;
    private Date min, max;

    public FragmentMutasiAlt() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMutasiAlt.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMutasiAlt newInstance(String param1, String param2) {
        FragmentMutasiAlt fragment = new FragmentMutasiAlt();
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
        binding = FragmentMutasiAltBinding.inflate(inflater, container, false);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        getParentFragmentManager().popBackStack("mutasi", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public View.OnClickListener btnCari = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(max == null || min == null) {
                Toast.makeText(FragmentMutasiAlt.this.getContext(), "Pilih tanggal dulu", Toast.LENGTH_SHORT).show();
                return;
            }

            if(min.after(max)) {
                Toast.makeText(FragmentMutasiAlt.this.getContext(), "Tanggal awal tidak boleh melebihi tanggal akhir", Toast.LENGTH_SHORT).show();
                if(mutasiAdapter!= null) {
                    filteredList = new ArrayList<>();
                    mutasiAdapter = new MutasiAdapter(filteredList);
                    recyclerView.setAdapter(mutasiAdapter);
                }
                return;
            }

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
                if(dateObj.after(min) && dateObj.before(max)) {
                    filteredList.add(checkMutasiDate);
                }
            }

            if(filteredList.isEmpty()) {
                Toast.makeText(FragmentMutasiAlt.this.getContext(), "Tidak ada mutasi", Toast.LENGTH_SHORT).show();
            }

            mutasiAdapter = new MutasiAdapter(filteredList);

            recyclerView = binding.rvMutasiAlt;
            recyclerView.setLayoutManager(new LinearLayoutManager(FragmentMutasiAlt.this.getContext()));
            recyclerView.setAdapter(mutasiAdapter);
        }
    };

    public View.OnClickListener btnTanggalAwal = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            min = new Date();
            Calendar newCalendar = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy", Locale.forLanguageTag("in-ID"));
            datePickerDialog = new DatePickerDialog(FragmentMutasiAlt.this.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);

                    min = newDate.getTime();

                    String formattedDate = df.format(min);
                    binding.txtHari.setText(formattedDate);
                }
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.show();
        }
    };

    public View.OnClickListener btnTanggalAkhir = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            max = new Date();
            Calendar newCalendar = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy", Locale.forLanguageTag("in-ID"));
            datePickerDialog = new DatePickerDialog(FragmentMutasiAlt.this.getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);

                    max = newDate.getTime();

                    String formattedDate = df.format(max);
                    binding.txtHari2.setText(formattedDate);
                }
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.show();
        }
    };

    private void getMutasiByAccNumber(String search) {
        mutasiList = DatabaseMutasi.getInstance(getActivity().getApplicationContext())
                .getDatabase()
                .mutasiDao()
                .getMutasiByAccNumber(search);
    }
}