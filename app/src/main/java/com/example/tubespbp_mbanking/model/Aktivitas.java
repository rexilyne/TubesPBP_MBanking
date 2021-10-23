package com.example.tubespbp_mbanking.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "aktivitas")
public class Aktivitas extends BaseObservable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "accountNumberOri")
    private String accountNumberOri;

    @ColumnInfo(name = "accountNumberDest")
    private String accountNumberDest;

    @ColumnInfo(name = "noReferensi")
    private int noReferensi;

    @ColumnInfo(name = "nama")
    private String nama;

    @ColumnInfo(name = "tanggal")
    private String tanggal;

    @ColumnInfo(name = "nominal")
    private int nominal;

    @ColumnInfo(name = "jenis")
    private String jenis;

    @ColumnInfo(name = "keterangan")
    private String keterangan;

    @ColumnInfo(name = "biayaAdmin")
    private int biayaAdmin;

    @ColumnInfo(name = "total")
    private int total;

    public Aktivitas() {}

    @Ignore
    public Aktivitas(int id, String accountNumberOri, String accountNumberDest,
                     int noReferensi, String nama, String tanggal, int nominal,
                     String jenis, String keterangan, int biayaAdmin, int total) {
        this.setId(id);
        this.setAccountNumberOri(accountNumberOri);
        this.setAccountNumberDest(accountNumberDest);
        this.setNoReferensi(noReferensi);
        this.setNama(nama);
        this.setTanggal(tanggal);
        this.setNominal(nominal);
        this.setJenis(jenis);
        this.setKeterangan(keterangan);
        this.setBiayaAdmin(biayaAdmin);
        this.setTotal(total);
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
    public String getAccountNumberOri() {
        return accountNumberOri;
    }

    public void setAccountNumberOri(String accountNumberOri) {
        this.accountNumberOri = accountNumberOri;
        notifyPropertyChanged(BR.accountNumberOri);
    }

    @Bindable
    public String getAccountNumberDest() {
        return accountNumberDest;
    }

    public void setAccountNumberDest(String accountNumberDest) {
        this.accountNumberDest = accountNumberDest;
        notifyPropertyChanged(BR.accountNumberDest);
    }

    @Bindable
    public int getNoReferensi() {
        return noReferensi;
    }

    public void setNoReferensi(int noReferensi) {
        this.noReferensi = noReferensi;
        notifyPropertyChanged(BR.noReferensi);
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
    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
        notifyPropertyChanged(BR.keterangan);
    }

    @Bindable
    public int getBiayaAdmin() {
        return biayaAdmin;
    }

    public void setBiayaAdmin(int biayaAdmin) {
        this.biayaAdmin = biayaAdmin;
        notifyPropertyChanged(BR.biayaAdmin);
    }

    @Bindable
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
        notifyPropertyChanged(BR.total);
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
