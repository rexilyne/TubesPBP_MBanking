package com.example.tubespbp_mbanking.response;

import com.example.tubespbp_mbanking.model.UserExtra;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserExtraResponse {
    private String message;

    @SerializedName("data")
    private List<UserExtra> userExtraList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<UserExtra> getUserExtraList() {
        return userExtraList;
    }

    public void setUserExtraList(List<UserExtra> userExtraList) {
        this.userExtraList = userExtraList;
    }
}
