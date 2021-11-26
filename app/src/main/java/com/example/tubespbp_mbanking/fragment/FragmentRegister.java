package com.example.tubespbp_mbanking.fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tubespbp_mbanking.R;
import com.example.tubespbp_mbanking.database.DatabaseUser;
import com.example.tubespbp_mbanking.databinding.FragmentRegisterBinding;
import com.example.tubespbp_mbanking.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    User userRegister, userCheck;
    FragmentRegisterBinding binding;
    private List<User> userList;
    private FirebaseAuth mAuth;
    public static final String TAG = "FragmentRegister";
    private boolean REGISTER_SUCCESS = false;
    private String errorMessage;

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
        mAuth = FirebaseAuth.getInstance();
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
        binding.setUser(userRegister);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void changeFragment(Fragment fragment){
        getParentFragmentManager().popBackStack("login", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_auth_frag,fragment)
                .commit();
    }

    public View.OnClickListener btnRegister = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            getUserByEmail(userRegister.getEmail());
//            if(!userList.isEmpty()) {
//                userCheck = userList.get(0);
//            }
            if(binding.etFirstName.getEditText().getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Nama depan tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else if(binding.etLastName.getEditText().getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Nama belakang tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else if(binding.etEmail.getEditText().getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else if(!isValidEmail(userRegister.getEmail())) {
                Toast.makeText(getActivity(), "Format email salah", Toast.LENGTH_SHORT).show();
            }
//            else if(!userList.isEmpty() && userCheck.getEmail().equals(userRegister.getEmail())) {
//                Toast.makeText(getActivity(), "Email sudah ada", Toast.LENGTH_SHORT).show();
//            }
            else if(binding.etPassword.getEditText().getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else if(binding.etAccountNumber.getEditText().getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Nomor rekening tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else if(!isNumeric(userRegister.getAccountNumber())) {
                Toast.makeText(getActivity(), "Nomor rekening harus angka", Toast.LENGTH_SHORT).show();
            }
//            else if(!userList.isEmpty() && userCheck.getAccountNumber().equals(userRegister.getAccountNumber())) {
//                Toast.makeText(getActivity(), "Nomor rekening sudah ada", Toast.LENGTH_SHORT).show();
//            }
            else if(binding.etPin.getEditText().getText().toString().isEmpty()) {
                Toast.makeText(getActivity(), "Pin tidak boleh ksoong", Toast.LENGTH_SHORT).show();
            } else if(!isNumeric(userRegister.getPin())) {
                Toast.makeText(getActivity(), "Pin harus angka", Toast.LENGTH_SHORT).show();
            } else if(userRegister.getPin().length() != 6) {
                Toast.makeText(getActivity(), "Pin harus 6 digit", Toast.LENGTH_SHORT).show();
            } else {
                Random rand = new Random();
                int n = rand.nextInt(10000000);
                n += 10000001;
                userRegister.setNominal(n);
                addUser(userRegister.getEmail(), userRegister.getPassword());

                if(REGISTER_SUCCESS) {
                    changeFragment(new FragmentLogin());
                }
            }
        }
    };

    private void addUser(String email, String password) {
        binding.loadRegister.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user != null) {
                                sendEmail(user);
                            }
                            REGISTER_SUCCESS = true;
                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                errorMessage = "Password anda terlalu lemah";
                                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
//                                binding.etPassword.requestFocus();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                errorMessage = "Email tidak valid";
                                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthUserCollisionException e) {
                                errorMessage = "Email sudah dipakai";
                                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                errorMessage = e.getMessage();
                            }
                        }
                        binding.loadRegister.setVisibility(View.GONE);
                    }
                });
//        binding.loadRegister.setVisibility(View.GONE);
    }

    private void sendEmail(FirebaseUser user) {
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                        }
                    }
                });
    }

//    private void getUserByEmail(String search) {
//        userList = DatabaseUser.getInstance(getActivity().getApplicationContext())
//                .getDatabase()
//                .userDao()
//                .getUserByEmail(search);
//    }

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

    public static boolean isValidEmail(String email)
    {
        if (email != null)
        {
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
            return matcher.find();
        }
        return false;
    }


}