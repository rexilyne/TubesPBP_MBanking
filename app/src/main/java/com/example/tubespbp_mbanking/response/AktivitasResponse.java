package com.example.tubespbp_mbanking.response;

import com.example.tubespbp_mbanking.model.Aktivitas;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AktivitasResponse {
    private String message;

    @SerializedName("data")
    private List<Aktivitas> aktivitasList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Aktivitas> getAktivitasList() {
        return aktivitasList;
    }

    public void setAktivitasList(List<Aktivitas> aktivitasList) {
        this.aktivitasList = aktivitasList;
    }
}
