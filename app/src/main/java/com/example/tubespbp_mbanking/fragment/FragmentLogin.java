package com.example.tubespbp_mbanking.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tubespbp_mbanking.R;
import com.example.tubespbp_mbanking.activity.MainActivity;
import com.example.tubespbp_mbanking.api.ApiClient;
import com.example.tubespbp_mbanking.api.ApiInterface;
import com.example.tubespbp_mbanking.databinding.FragmentLoginBinding;
import com.example.tubespbp_mbanking.model.User;
import com.example.tubespbp_mbanking.model.UserExtra;
import com.example.tubespbp_mbanking.preferences.UserPreferences;
import com.example.tubespbp_mbanking.response.UserExtraResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLogin extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private User userLogin;
    private FragmentLoginBinding binding;
    private UserPreferences userPreferences;
    private List<User> userList;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    public static final String TAG = FragmentLokasi.class.getSimpleName();
    private String errorMessage;
    private ApiInterface apiService;

    public FragmentLogin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentLogin.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLogin newInstance(String param1, String param2) {
        FragmentLogin fragment = new FragmentLogin();
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
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        binding.setFragment(this);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userPreferences = new UserPreferences(getActivity());
        userList = new ArrayList<>();

        userLogin = new User();
        binding.setUser(userLogin);

        checkLogin();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void changeFragment(Fragment fragment){
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_auth_frag,fragment)
                .addToBackStack("login")
                .commit();
    }

    public View.OnClickListener btnLogin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(validateForm()) {
//                getUserByEmail(userLogin.getEmail());
//                if(userList.isEmpty()) {
//                    Toast.makeText(getActivity(), "Email tidak ditemukan", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                login(userLogin.getEmail().trim(), userLogin.getPassword().trim());
            }
        }
    };

    public View.OnClickListener btnRegister = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            changeFragment(new FragmentRegister());
        }
    };

    private boolean validateForm() {
        if(binding.etEmail.getEditText().getText().toString().isEmpty() ||
                binding.etPassword.getEditText().getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Email atau Password kosong", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void checkLogin() {
        if(userPreferences.checkLogin()) {
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
        }
    }

    private void fetchUserData(String uid) {
        // TODO
        binding.loadLogin.setVisibility(View.VISIBLE);

        Call<UserExtraResponse> call = apiService.getUserExtraByUid(uid);
        call.enqueue(new Callback<UserExtraResponse>() {
            @Override
            public void onResponse(Call<UserExtraResponse> call, Response<UserExtraResponse> response) {
                if(response.isSuccessful()) {
                    UserExtra userExtra = response.body().getUserExtraList().get(0);
                    userLogin.setUid(userExtra.getUid());
                    userLogin.setFirstName(userExtra.getFirstName());
                    userLogin.setLastName(userExtra.getLastName());
                    userLogin.setEmail(userExtra.getEmail());
                    userLogin.setAccountNumber(userExtra.getAccountNumber());
                    userLogin.setPin(userExtra.getPin());
                    userLogin.setNominal(userExtra.getNominal());
                    userLogin.setImgUrl(userExtra.getImgUrl());
                    Toast.makeText(getActivity(), "Login berhasil", Toast.LENGTH_SHORT).show();
                    userPreferences.setLogin(userLogin);
                    checkLogin();
                } else {
                    try {
                        JSONObject jObjError = new
                                JSONObject(response.errorBody().string());
                        Toast.makeText(getActivity(),
                                jObjError.getString("message"),
                                Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(),
                                e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                binding.loadLogin.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<UserExtraResponse> call, Throwable t) {
                Toast.makeText(getActivity(),
                        "Network error", Toast.LENGTH_SHORT).show();
                binding.loadLogin.setVisibility(View.GONE);
            }
        });
    }

    private void login(String email, String password) {
        binding.loadLogin.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            firebaseUser = mAuth.getCurrentUser();
                            if(firebaseUser.isEmailVerified()) {
                                fetchUserData(firebaseUser.getUid());
                            } else {
                                Toast.makeText(getActivity(), "Email belum terverifikasi", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            try {
                                if(task.getException() != null) {
                                    throw task.getException();
                                }
                            } catch (FirebaseAuthInvalidUserException e) {
                                errorMessage = "Email tidak ditemukan";
                                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                errorMessage = "Password salah";
                                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                errorMessage = e.getMessage();
                                Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                        binding.loadLogin.setVisibility(View.GONE);
                    }
                });
    }
}