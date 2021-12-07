package com.example.tubespbp_mbanking.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tubespbp_mbanking.R;
import com.example.tubespbp_mbanking.adapter.AktivitasAdapter;
import com.example.tubespbp_mbanking.api.ApiClient;
import com.example.tubespbp_mbanking.api.ApiInterface;
import com.example.tubespbp_mbanking.databinding.FragmentAktivitasBinding;
import com.example.tubespbp_mbanking.model.Aktivitas;
import com.example.tubespbp_mbanking.model.User;
import com.example.tubespbp_mbanking.preferences.UserPreferences;
import com.example.tubespbp_mbanking.response.AktivitasResponse;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAktivitas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAktivitas extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private User userLogin;
    private FragmentAktivitasBinding binding;
    private UserPreferences userPreferences;
    private List<User> userList;
    private RecyclerView recyclerView;
    private AktivitasAdapter aktivitasAdapter;
    private List<Aktivitas> aktivitasList;
    private ApiInterface apiService;

    public static final String TAG = FragmentAktivitas.class.getSimpleName();

    public FragmentAktivitas() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAktivitas.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAktivitas newInstance(String param1, String param2) {
        FragmentAktivitas fragment = new FragmentAktivitas();
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
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAktivitasBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userPreferences = new UserPreferences(getActivity());
        userLogin = userPreferences.getUserLogin();

        getAktivitasByAccNumber(userLogin.getAccountNumber());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getAktivitasByAccNumber(String search) {
        // TODO
        binding.loading.setVisibility(View.VISIBLE);
        Call<AktivitasResponse> call = apiService.getAktivitasByAccountNumber(search);
        call.enqueue(new Callback<AktivitasResponse>() {
            @Override
            public void onResponse(Call<AktivitasResponse> call, Response<AktivitasResponse> response) {
                if(response.isSuccessful()) {
                    aktivitasList = response.body().getAktivitasList();
                    Toast.makeText(getActivity(),
                            response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Get Aktivitas Berhasil");
                    if(aktivitasList.isEmpty()) {
                        Toast.makeText(FragmentAktivitas.this.getContext(), "Aktivitas kosong", Toast.LENGTH_SHORT).show();
                    }

                    aktivitasAdapter = new AktivitasAdapter(aktivitasList, getActivity());

                    recyclerView = binding.rvAktivitas;
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(aktivitasAdapter);
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
                binding.loading.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<AktivitasResponse> call, Throwable t) {
                Toast.makeText(getActivity(),
                        t.getMessage(), Toast.LENGTH_SHORT).show();
                binding.loading.setVisibility(View.GONE);
            }
        });
    }
}