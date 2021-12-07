package com.example.tubespbp_mbanking.api;

import com.example.tubespbp_mbanking.model.Aktivitas;
import com.example.tubespbp_mbanking.model.Mutasi;
import com.example.tubespbp_mbanking.model.UserExtra;
import com.example.tubespbp_mbanking.response.AktivitasResponse;
import com.example.tubespbp_mbanking.response.MutasiResponse;
import com.example.tubespbp_mbanking.response.UserExtraResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {
    // UserExtra
    @Headers({"Accept: application/json"})
    @GET("userextra")
    Call<UserExtraResponse> getAllUserExtra();

    @Headers({"Accept: application/json"})
    @GET("userextra/{uid}")
    Call<UserExtraResponse> getUserExtraByUid(@Path("uid") String uid);

    @Headers({"Accept: application/json"})
    @POST("userextra")
    Call<UserExtraResponse> createUserExtra(@Body UserExtra userExtra);

    @Headers({"Accept: application/json"})
    @PUT("userextra/{uid}")
    Call<UserExtraResponse> updateUserExtra(@Body UserExtra userExtra, @Path("uid") String uid);

    @Headers({"Accept: application/json"})
    @GET("userextra/accountnumber/{accountNumber}")
    Call<UserExtraResponse> checkAccountNumber(@Path("accountNumber") String accountNumber);

    // Aktivitas
    @Headers({"Accept: application/json"})
    @GET("aktivitas/{accountNumber}")
    Call<AktivitasResponse> getAktivitasByAccountNumber(@Path("accountNumber") String accountNumber);

    @Headers({"Accept: application/json"})
    @POST("aktivitas")
    Call<AktivitasResponse> createAktivitas(@Body Aktivitas aktivitas);

    // Mutasi
    @Headers({"Accept: application/json"})
    @GET("mutasi/{accountNumber}")
    Call<MutasiResponse> getMutasiByAccountNumber(@Path("accountNumber") String accountNumber);

    @Headers({"Accept: application/json"})
    @POST("mutasi")
    Call<MutasiResponse> createMutasi(@Body Mutasi mutasi);
}
