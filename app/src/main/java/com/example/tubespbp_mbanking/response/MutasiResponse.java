package com.example.tubespbp_mbanking.response;

import com.example.tubespbp_mbanking.model.Mutasi;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MutasiResponse {
    private String message;

    @SerializedName("data")
    private List<Mutasi> mutasiList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Mutasi> getMutasiList() {
        return mutasiList;
    }

    public void setMutasiList(List<Mutasi> mutasiList) {
        this.mutasiList = mutasiList;
    }
}
