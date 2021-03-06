package com.example.tubespbp_mbanking.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

public class User extends BaseObservable {
    private String uid;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String accountNumber;
    private String pin;
    private int nominal;
    private String imgUrl;

    public User() {}

    @Ignore
    public User(String uid, String firstName, String lastName, String email,
                String password, String accountNumber, String pin, int nominal, String imgUrl) {
        this.setUid(uid);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPassword(password);
        this.setAccountNumber(accountNumber);
        this.setPin(pin);
        this.setNominal(nominal);
        this.setImgUrl(imgUrl);
    }

    @Bindable
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
        notifyPropertyChanged(BR.uid);
    }

    @Bindable
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        notifyPropertyChanged(BR.firstName);
    }

    @Bindable
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        notifyPropertyChanged(BR.lastName);
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    @Bindable
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        notifyPropertyChanged(BR.accountNumber);
    }

    @Bindable
    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
        notifyPropertyChanged(BR.pin);
    }

    @Bindable
    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
        notifyPropertyChanged(BR.nominal);
    }

    @Bindable
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        notifyPropertyChanged(BR.imgUrl);
    }
}
