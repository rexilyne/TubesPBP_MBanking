package com.example.tubespbp_mbanking.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tubespbp_mbanking.R;
import com.example.tubespbp_mbanking.api.ApiClient;
import com.example.tubespbp_mbanking.api.ApiInterface;
import com.example.tubespbp_mbanking.databinding.FragmentDetailAkunBinding;
import com.example.tubespbp_mbanking.databinding.FragmentHomeBinding;
import com.example.tubespbp_mbanking.model.User;
import com.example.tubespbp_mbanking.model.UserExtra;
import com.example.tubespbp_mbanking.preferences.UserPreferences;
import com.example.tubespbp_mbanking.response.UserExtraResponse;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDetailAkun#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDetailAkun extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private User userLogin;
    private User userCheck;
    private FragmentDetailAkunBinding binding;
    private UserPreferences userPreferences;
    private Bitmap bitmap = null;
//    private List<User> userList;
    private String tempEmail;
    private ApiInterface apiService;

    private static final int PERMISSION_REQUEST_CAMERA = 100;
    private static final int CAMERA_REQUEST = 0;
    private static final int GALLERY_PICTURE = 1;
    public static final String TAG = FragmentDetailAkun.class.getSimpleName();

    public FragmentDetailAkun() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentDetailAkun.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDetailAkun newInstance(String param1, String param2) {
        FragmentDetailAkun fragment = new FragmentDetailAkun();
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
        binding = FragmentDetailAkunBinding.inflate(inflater, container, false);
        binding.setFragment(this);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userPreferences = new UserPreferences(getActivity());

        userLogin = userPreferences.getUserLogin();
        binding.setUser(userLogin);
        tempEmail = userLogin.getEmail();

        if(!userLogin.getImgUrl().isEmpty()) {
            Glide.with(getActivity().getApplicationContext())
                    .load(userLogin.getImgUrl())
                    .placeholder(R.drawable.nouser)
                    .dontAnimate()
                    .into(binding.profileImage);
        }

        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View selectMediaView = layoutInflater
                        .inflate(R.layout.layout_select_media, null);

                final AlertDialog alertDialog = new AlertDialog
                        .Builder(selectMediaView.getContext()).create();

                // TODO Data Binding
                Button btnKamera = selectMediaView.findViewById(R.id.btn_kamera);
                Button btnGaleri = selectMediaView.findViewById(R.id.btn_galeri);

                btnKamera.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (getActivity().checkSelfPermission(Manifest.permission.CAMERA) ==
                                PackageManager.PERMISSION_DENIED) {
                            String[] permission = {Manifest.permission.CAMERA};
                            requestPermissions(permission, PERMISSION_REQUEST_CAMERA);
                        } else {
                            // Membuka kamera
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, CAMERA_REQUEST);
                        }

                        alertDialog.dismiss();
                    }
                });

                btnGaleri.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // Membuka galeri
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, GALLERY_PICTURE);

                        alertDialog.dismiss();
                    }
                });

                alertDialog.setView(selectMediaView);
                alertDialog.show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public View.OnClickListener btnUpdate = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            getUserByEmail(userLogin.getEmail());
//            if(!userList.isEmpty()) {
//                userCheck = userList.get(0);
//            }
            if(userLogin.getFirstName().isEmpty()) {
                Toast.makeText(getActivity(), "Nama depan tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else if(userLogin.getLastName().isEmpty()) {
                Toast.makeText(getActivity(), "Nama belakang tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else if(userLogin.getEmail().isEmpty()) {
                Toast.makeText(getActivity(), "Email tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else if(!isValidEmail(userLogin.getEmail())) {
                Toast.makeText(getActivity(), "Format email salah", Toast.LENGTH_SHORT).show();
            }
//            else if(!userList.isEmpty() && !binding.etEmail.getEditText().getText().toString().equals(tempEmail)) {
//                Toast.makeText(getActivity(), "Email sudah ada", Toast.LENGTH_SHORT).show();
//            }
            else if(userLogin.getPassword().isEmpty()) {
                Toast.makeText(getActivity(), "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else {
                updateFirebaseEmail(userLogin.getEmail());
                updateFirebasePassword(userLogin.getPassword(), userLogin);
                updateUser(userLogin);
//                tempEmail = userLogin.getEmail();
            }
        }
    };

    private void getUserByEmail(String search) {
        // TODO
    }

    private void updateFirebaseEmail(String email) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();;

        firebaseUser.updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Log.d(TAG, "Berhasil update email");
                        } else {
                            try {
                                if(task.getException() != null) {
                                    throw task.getException();
                                }
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(getActivity(), "Email tidak valid", Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthUserCollisionException e) {
                                Toast.makeText(getActivity(), "Email sudah dipakai", Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthInvalidUserException e) {
                                Toast.makeText(getActivity(), "User tidak valid", Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthRecentLoginRequiredException e) {
                                Toast.makeText(getActivity(), "Login session tidak valid", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    private void updateFirebasePassword(String password, User user) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), user.getPassword());

        firebaseUser.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "User re-authenticated.");
                    }
                });

        firebaseUser.updatePassword(password)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Log.d(TAG, "Berhasil update password");
                        } else {
                            try {
                                if(task.getException() != null) {
                                    throw task.getException();
                                }
                            } catch (FirebaseAuthWeakPasswordException e) {
                                Toast.makeText(getActivity(), "Password anda terlalu lemah", Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthInvalidUserException e) {
                                Toast.makeText(getActivity(), "User tidak valid", Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthRecentLoginRequiredException e) {
                                Toast.makeText(getActivity(), "Login session tidak valid", Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
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
                bitmapToBase64(((BitmapDrawable)binding.profileImage.getDrawable()).getBitmap())
        );

        Call<UserExtraResponse> call = apiService.updateUserExtra(userExtra, userExtra.getUid());
        call.enqueue(new Callback<UserExtraResponse>() {
            @Override
            public void onResponse(Call<UserExtraResponse> call, Response<UserExtraResponse> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(getActivity(),
                            response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    String imgUrl = response.body().getUserExtraList().get(0).getImgUrl();
                    if(!imgUrl.isEmpty()) {
                        userLogin.setImgUrl(response.body().getUserExtraList().get(0).getImgUrl());
                        Glide.with(getActivity().getApplicationContext())
                                .load(userLogin.getImgUrl())
                                .placeholder(R.drawable.nouser)
                                .dontAnimate()
                                .into(binding.profileImage);
                    }
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

    public static boolean isValidEmail(String email)
    {
        if (email != null)
        {
            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
            return matcher.find();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Membuka kamera
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_REQUEST);
            } else {
                Toast.makeText(getActivity(), "Permission denied.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null)
            return;

        if (resultCode == getActivity().RESULT_OK && requestCode == GALLERY_PICTURE) {
            Uri selectedImage = data.getData();

            try {
                InputStream inputStream = getActivity().getContentResolver().openInputStream(selectedImage);
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                Toast.makeText(getActivity(), e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode == getActivity().RESULT_OK && requestCode == CAMERA_REQUEST) {
            bitmap = (Bitmap) data.getExtras().get("data");
        }

        bitmap = getResizedBitmap(bitmap, 512);
        binding.profileImage.setImageBitmap(bitmap);
    }

    private Bitmap getResizedBitmap(Bitmap bitmap, int maxSize) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        float bitmapRatio = (float) width / (float) height;

        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return "data:image/jpeg;base64," + encoded;
    }
}