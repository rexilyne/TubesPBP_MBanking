package com.example.tubespbp_mbanking.model;

public class UserExtra {
    private String uid;
    private String firstName;
    private String lastName;
    private String email;
    private String accountNumber;
    private String pin;
    private int nominal;
    private String imgUrl;
    private String imgB64;

    public UserExtra(String uid, String firstName, String lastName, String email, String accountNumber, String pin, int nominal, String imgUrl, String imgB64) {
        this.setUid(uid);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setAccountNumber(accountNumber);
        this.setPin(pin);
        this.setNominal(nominal);
        this.setImgUrl(imgUrl);
        this.setImgB64(imgB64);
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgB64() {
        return imgB64;
    }

    public void setImgB64(String imgB64) {
        this.imgB64 = imgB64;
    }
}
