package com.example.tubespbp_mbanking.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.tubespbp_mbanking.model.User;

public class UserPreferences {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public static final String IS_LOGIN = "isLogin";
    public static final String KEY_UID = "id";
    public static final String KEY_FIRSTNAME = "firstName";
    public static final String KEY_LASTNAME = "lastName";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_ACCOUNTNUMBER = "accountNumber";
    public static final String KEY_PIN = "pin";
    public static final String KEY_NOMINAL = "nominal";
    public static final String KEY_IMGURL = "imgurl";

    public UserPreferences(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("userPreferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLogin(User userLogin) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_UID, userLogin.getUid());
        editor.putString(KEY_FIRSTNAME, userLogin.getFirstName());
        editor.putString(KEY_LASTNAME, userLogin.getLastName());
        editor.putString(KEY_EMAIL, userLogin.getEmail());
        editor.putString(KEY_PASSWORD, userLogin.getPassword());
        editor.putString(KEY_ACCOUNTNUMBER, userLogin.getAccountNumber());
        editor.putString(KEY_PIN, userLogin.getPin());
        editor.putString(KEY_NOMINAL, String.valueOf(userLogin.getNominal()));
        editor.putString(KEY_IMGURL, userLogin.getImgUrl());

        editor.commit();
    }

    public User getUserLogin() {
        int id, nominal;
        String uid, firstName, lastName, email, password, accountNumber, pin, stringNominal, imgUrl;

        uid = sharedPreferences.getString(KEY_UID, null);
        firstName = sharedPreferences.getString(KEY_FIRSTNAME, null);
        lastName = sharedPreferences.getString(KEY_LASTNAME, null);
        email = sharedPreferences.getString(KEY_EMAIL, null);
        password = sharedPreferences.getString(KEY_PASSWORD, null);
        accountNumber = sharedPreferences.getString(KEY_ACCOUNTNUMBER, null);
        pin = sharedPreferences.getString(KEY_PIN, null);
        stringNominal = sharedPreferences.getString(KEY_NOMINAL, null);
        nominal = Integer.parseInt(stringNominal);
        imgUrl = sharedPreferences.getString(KEY_IMGURL, null);

        return new User(uid, firstName, lastName, email, password, accountNumber, pin, nominal, imgUrl);
    }

    public boolean checkLogin() {
        return sharedPreferences.getBoolean(IS_LOGIN, false);
    }

    public void logout() {
        editor.clear();
        editor.commit();
    }
}
