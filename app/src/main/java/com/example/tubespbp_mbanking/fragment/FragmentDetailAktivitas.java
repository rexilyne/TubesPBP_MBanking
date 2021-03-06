package com.example.tubespbp_mbanking.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tubespbp_mbanking.R;
import com.example.tubespbp_mbanking.databinding.FragmentDetailAktivitasBinding;
import com.example.tubespbp_mbanking.databinding.FragmentDetailTransferBinding;
import com.example.tubespbp_mbanking.databinding.FragmentHomeBinding;
import com.example.tubespbp_mbanking.model.Aktivitas;
import com.example.tubespbp_mbanking.model.User;
import com.example.tubespbp_mbanking.preferences.UserPreferences;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDetailAktivitas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDetailAktivitas extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private User userLogin;
    private FragmentDetailAktivitasBinding binding;
    private UserPreferences userPreferences;
    private List<User> userList;
    private Aktivitas showAktivitas;

    public FragmentDetailAktivitas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentDetailAktivitas.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDetailAktivitas newInstance(String param1, String param2) {
        FragmentDetailAktivitas fragment = new FragmentDetailAktivitas();
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
        binding = FragmentDetailAktivitasBinding.inflate(inflater, container, false);
        binding.setFragment(this);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        Aktivitas aktivitas = (Aktivitas)bundle.getSerializable("aktivitas_bundle");

        userPreferences = new UserPreferences(getActivity());

        userLogin = userPreferences.getUserLogin();
        binding.setUser(userLogin);
        binding.setAktivitas(aktivitas);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}