package com.example.tubespbp_mbanking.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tubespbp_mbanking.R;
import com.example.tubespbp_mbanking.activity.AuthenticationActivity;
import com.example.tubespbp_mbanking.databinding.FragmentAkunBinding;
import com.example.tubespbp_mbanking.databinding.FragmentLoginBinding;
import com.example.tubespbp_mbanking.model.User;
import com.example.tubespbp_mbanking.preferences.UserPreferences;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAkun#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAkun extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private User userLogin;
    private FragmentAkunBinding binding;
    private UserPreferences userPreferences;
    private List<User> userList;

    public FragmentAkun() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAkun.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAkun newInstance(String param1, String param2) {
        FragmentAkun fragment = new FragmentAkun();
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
        binding = FragmentAkunBinding.inflate(inflater, container, false);
        binding.setFragment(this);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userPreferences = new UserPreferences(getActivity());
        userLogin = userPreferences.getUserLogin();

        checkLogin();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    
    public View.OnClickListener btnDetailAkun = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            changeFragment(new FragmentDetailAkun());
        }
    };

    public View.OnClickListener btnLokasi = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            changeFragment(new FragmentLokasi());
        }
    };

    public View.OnClickListener btnKontak = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            changeFragment(new FragmentKontakKami());
        }
    };

    public View.OnClickListener btnLogout = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            userPreferences.logout();
            checkLogin();
        }
    };

    private void checkLogin() {
        if(!userPreferences.checkLogin()) {
            startActivity(new Intent(getActivity(), AuthenticationActivity.class));
            getActivity().finish();
        }
    }

    public void changeFragment(Fragment fragment){
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_app_content,fragment)
                .commit();
    }
}