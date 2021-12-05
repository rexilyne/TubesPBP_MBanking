package com.example.tubespbp_mbanking.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

public class Mutasi extends BaseObservable {
    private String accountNumber;
    private String nama;
    private String tanggal;
    private int nominal;
    private String jenis;

    public Mutasi () {}

    @Ignore
    public Mutasi(String accountNumber, String nama, String tanggal, int nominal, String jenis) {
        this.setAccountNumber(accountNumber);
        this.setNama(nama);
        this.setTanggal(tanggal);
        this.setNominal(nominal);
        this.setJenis(jenis);
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
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
        notifyPropertyChanged(BR.nama);
    }

    @Bindable
    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
        notifyPropertyChanged(BR.tanggal);
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
    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
        notifyPropertyChanged(BR.jenis);
    }

    @Bindable
    public String getStringNominal() {
        return String.valueOf(nominal);
    }

    public void setStringNominal(String nominal) {
        if(nominal.isEmpty()) {
            this.nominal = 0;
        } else {
            this.nominal = Integer.parseInt(nominal);
        }
        notifyPropertyChanged(BR.nominal);
    }
}
