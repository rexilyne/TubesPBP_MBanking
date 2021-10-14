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
import com.example.tubespbp_mbanking.databinding.FragmentRegisterBinding;
import com.example.tubespbp_mbanking.model.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentRegister#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRegister extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    User userRegister;
    FragmentRegisterBinding binding;

    public FragmentRegister() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRegister1.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRegister newInstance(String param1, String param2) {
        FragmentRegister fragment = new FragmentRegister();
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
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        binding.setFragment(this);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userRegister = new User();
        userRegister.setFirstName("");
        userRegister.setLastName("");
        userRegister.setEmail("");
        userRegister.setPassword("");
        userRegister.setAccountNumber("");
        userRegister.setPin("");
        binding.setUser(userRegister);
    }

    public void changeFragment(Fragment fragment){
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_auth_frag,fragment)
                .commit();
    }

    public View.OnClickListener btnRegister = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(userRegister.getFirstName().isEmpty()) {
                Toast.makeText(getActivity(), "Nama depan tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else if(userRegister.getLastName().isEmpty()) {
                Toast.makeText(getActivity(), "Nama belakang tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else if(userRegister.getEmail().isEmpty()) {
                Toast.makeText(getActivity(), "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else if(userRegister.getPassword().isEmpty()) {
                Toast.makeText(getActivity(), "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else if(userRegister.getAccountNumber().isEmpty()) {
                Toast.makeText(getActivity(), "Nomor rekening tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else if(userRegister.getPin().isEmpty()) {
                Toast.makeText(getActivity(), "Pink tidak boleh ksoong", Toast.LENGTH_SHORT).show();
            } else {
                addUser();
                Toast.makeText(getActivity(), "Berhasil menambahkan user", Toast.LENGTH_SHORT).show();
                changeFragment(new FragmentLogin());
            }
        }
    };

    private void addUser() {
        class AddUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                User user = binding.getUser();

                DatabaseUser.getInstance(getActivity().getApplicationContext())
                        .getDatabase()
                        .userDao()
                        .insertUser(user);

                return null;
            }
        }

        AddUser addUser = new AddUser(  );
        addUser.execute();
    }
}