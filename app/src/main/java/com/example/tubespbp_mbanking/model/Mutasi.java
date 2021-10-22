package com.example.tubespbp_mbanking.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


@Entity(tableName = "mutasi")
public class Mutasi extends BaseObservable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "accountNumber")
    private String accountNumber;

    @ColumnInfo(name = "nama")
    private String nama;

    @ColumnInfo(name = "tanggal")
    private String tanggal;

    @ColumnInfo(name = "nominal")
    private int nominal;

    @ColumnInfo(name = "jenis")
    private String jenis;

    public Mutasi () {}

    @Ignore
    public Mutasi(int id, String accountNumber, String nama, String tanggal, int nominal, String jenis) {
        this.setId(id);
        this.setAccountNumber(accountNumber);
        this.setNama(nama);
        this.setTanggal(tanggal);
        this.setNominal(nominal);
        this.setJenis(jenis);
    }

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
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
}
