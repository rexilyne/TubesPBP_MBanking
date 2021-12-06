package com.example.tubespbp_mbanking.fragment;

import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tubespbp_mbanking.R;
import com.example.tubespbp_mbanking.api.ApiClient;
import com.example.tubespbp_mbanking.api.ApiInterface;
import com.example.tubespbp_mbanking.databinding.FragmentHomeBinding;
import com.example.tubespbp_mbanking.databinding.FragmentTransferBinding;
import com.example.tubespbp_mbanking.dialog.PinDialog;
import com.example.tubespbp_mbanking.dialog.PinDialogListener;
import com.example.tubespbp_mbanking.model.Aktivitas;
import com.example.tubespbp_mbanking.model.Mutasi;
import com.example.tubespbp_mbanking.model.User;
import com.example.tubespbp_mbanking.model.UserExtra;
import com.example.tubespbp_mbanking.preferences.UserPreferences;
import com.example.tubespbp_mbanking.response.AktivitasResponse;
import com.example.tubespbp_mbanking.response.MutasiResponse;
import com.example.tubespbp_mbanking.response.UserExtraResponse;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private UserExtra userPenerima;
    private Aktivitas aktivitas;
    private Mutasi mutasiPengirim, mutasiPenerima, mutasiAdmin;
    private FragmentTransferBinding binding;
    private UserPreferences userPreferences;
    private List<User> userList;
    private List<UserExtra> userPenerimaList;
    private ApiInterface apiService;

    public static final String TAG = FragmentTransfer.class.getSimpleName();

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
        apiService = ApiClient.getClient().create(ApiInterface.class);
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
        mutasiPengirim = new Mutasi();
        mutasiPenerima = new Mutasi();
        mutasiAdmin = new Mutasi();

        binding.setUser(userLogin);
        binding.setAktivitas(aktivitas);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
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

                getUserByAccNumber(aktivitas.getAccountNumberDest());
                if(userPenerimaList.isEmpty()) {
                    Toast.makeText(getActivity(), "Rekening tujuan tidak ditemukan", Toast.LENGTH_SHORT).show();
                    return;
                }
                userPenerima = userPenerimaList.get(0);
                if(userPenerima.getAccountNumber().equals(userLogin.getAccountNumber())) {
                    Toast.makeText(getActivity(), "Tidak bisa mengirim ke rekening sendiri", Toast.LENGTH_SHORT).show();
                    return;
                }

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
        // TODO
        binding.loadUpdate.setVisibility(View.VISIBLE);
        Call<AktivitasResponse> call = apiService.createAktivitas(aktivitas);
        call.enqueue(new Callback<AktivitasResponse>() {
            @Override
            public void onResponse(Call<AktivitasResponse> call, Response<AktivitasResponse> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getActivity(),
                            response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Add Aktivitas Berhasil");
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
                binding.loadUpdate.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<AktivitasResponse> call, Throwable t) {
                Toast.makeText(getActivity(),
                        t.getMessage(), Toast.LENGTH_SHORT).show();
                binding.loadUpdate.setVisibility(View.GONE);
            }
        });
    }

    private void addMutasi(Mutasi mutasi) {
        // TODO
        binding.loadUpdate.setVisibility(View.VISIBLE);
        Call<MutasiResponse> call = apiService.createMutasi(mutasi);
        call.enqueue(new Callback<MutasiResponse>() {
            @Override
            public void onResponse(Call<MutasiResponse> call, Response<MutasiResponse> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getActivity(),
                            response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Add Mutasi Berhasil");
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
                binding.loadUpdate.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MutasiResponse> call, Throwable t) {
                Toast.makeText(getActivity(),
                        t.getMessage(), Toast.LENGTH_SHORT).show();
                binding.loadUpdate.setVisibility(View.GONE);
            }
        });
    }

    private void getUserByAccNumber(String search) {
        // TODO
        binding.loadUpdate.setVisibility(View.VISIBLE);
        Call<UserExtraResponse> call = apiService.checkAccountNumber(search);
        call.enqueue(new Callback<UserExtraResponse>() {
            @Override
            public void onResponse(Call<UserExtraResponse> call, Response<UserExtraResponse> response) {
                if(response.isSuccessful()) {
                    userPenerimaList = response.body().getUserExtraList();
                    if(userPenerimaList.isEmpty()) {
                        Toast.makeText(getActivity(), "Rekening tujuan tidak ditemukan", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    userPenerima = userPenerimaList.get(0);
                    if(userPenerima.getAccountNumber().equals(userLogin.getAccountNumber())) {
                        Toast.makeText(getActivity(), "Tidak bisa mengirim ke rekening sendiri", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    authenticatePin();
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
                binding.loadUpdate.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<UserExtraResponse> call, Throwable t) {
                Toast.makeText(getActivity(),
                        "Network error", Toast.LENGTH_SHORT).show();
                binding.loadUpdate.setVisibility(View.GONE);
            }
        });

    }

    private void updateUser(User user) {
        // TODO
        binding.loadUpdate.setVisibility(View.VISIBLE);

        UserExtra userExtra = new UserExtra(
                user.getUid(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAccountNumber(),
                user.getPin(),
                user.getNominal(),
                user.getImgUrl(),
                ""
        );

        Call<UserExtraResponse> call = apiService.updateUserExtra(userExtra, userExtra.getUid());
        call.enqueue(new Callback<UserExtraResponse>() {
            @Override
            public void onResponse(Call<UserExtraResponse> call, Response<UserExtraResponse> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getActivity(),
                            response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    userPreferences.setLogin(userLogin);
                } else {
                    try {
                        JSONObject jObjError = new
                                JSONObject(response.errorBody().string());
                        Toast.makeText(getActivity(),
                                jObjError.getString("message"),
                                Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getActivity(), "error1", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getActivity(),
                                e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                binding.loadUpdate.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<UserExtraResponse> call, Throwable t) {
                Toast.makeText(getActivity(),
                        t.getMessage(), Toast.LENGTH_SHORT).show();
                binding.loadUpdate.setVisibility(View.GONE);
            }
        });
    }

    public void changeFragment(Fragment fragment){
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_app_content,fragment)
                .commit();
    }

    private void authenticatePin() {
        PinDialog pinDialog = new PinDialog(getActivity(), userLogin.getPin());
        pinDialog.setPinDialogListener(new PinDialogListener() {
            @Override
            public void pinConfirmed(String pin) {
                if(userLogin.getPin().equals(pin)) {
                    //Aktivitas
                    aktivitas.setAccountNumberOri(userLogin.getAccountNumber());
                    aktivitas.setNoReferensi(userLogin.getUid() + userLogin.getAccountNumber());
                    aktivitas.setNama(userPenerima.getFirstName() + " " + userPenerima.getLastName());
                    Date c = Calendar.getInstance().getTime();

                    SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy hh:mm", Locale.forLanguageTag("in-ID"));
                    String formattedDate = df.format(c);
                    aktivitas.setTanggal(formattedDate);
                    aktivitas.setJenis("Transfer");
                    aktivitas.setBiayaAdmin(1000);
                    aktivitas.setTotal(aktivitas.getNominal() + aktivitas.getBiayaAdmin());

                    addAktivitas(aktivitas);

                    //Mutasi Pengirim
                    mutasiPengirim.setAccountNumber(userLogin.getAccountNumber());
                    mutasiPengirim.setNama(userLogin.getFirstName() + " " + userLogin.getLastName());
                    mutasiPengirim.setTanggal(formattedDate);
                    mutasiPengirim.setNominal(aktivitas.getNominal());
                    mutasiPengirim.setJenis("Transfer Keluar");

                    userLogin.setNominal(userLogin.getNominal() - mutasiPengirim.getNominal());
                    updateUser(userLogin);
                    userPreferences.setLogin(userLogin);

                    addMutasi(mutasiPengirim);

                    //Mutasi Biaya Admin
                    mutasiAdmin.setAccountNumber(userLogin.getAccountNumber());
                    mutasiAdmin.setNama(userLogin.getFirstName() + " " + userLogin.getLastName());
                    mutasiAdmin.setTanggal(formattedDate);
                    mutasiAdmin.setNominal(aktivitas.getBiayaAdmin());
                    mutasiAdmin.setJenis("Biaya Admin");

                    addMutasi(mutasiAdmin);

                    //Mutasi Penerima
                    mutasiPenerima.setAccountNumber(userPenerima.getAccountNumber());
                    mutasiPenerima.setNama(userPenerima.getFirstName() + " " + userPenerima.getLastName());
                    mutasiPenerima.setTanggal(formattedDate);
                    mutasiPenerima.setNominal(aktivitas.getNominal());
                    mutasiPenerima.setJenis("Transfer Masuk");

                    userPenerima.setNominal(userPenerima.getNominal() + mutasiPenerima.getNominal());
                    User userPenerimaUpdate = new User (
                            userPenerima.getUid(),
                            userPenerima.getFirstName(),
                            userPenerima.getLastName(),
                            userPenerima.getEmail(),
                            "",
                            userPenerima.getAccountNumber(),
                            userPenerima.getPin(),
                            userPenerima.getNominal(),
                            userPenerima.getImgUrl()
                    );
                    updateUser(userPenerimaUpdate);

                    addMutasi(mutasiPenerima);

                    //Success
                    Toast.makeText(getActivity(), "Transfer berhasil", Toast.LENGTH_SHORT).show();

                    FragmentDetailTransfer fragmentDetailTransfer = new FragmentDetailTransfer();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("aktivitas_bundle", aktivitas);
                    fragmentDetailTransfer.setArguments(bundle);

                    changeFragment(fragmentDetailTransfer);
                } else {
                    Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        pinDialog.show();
    }
}