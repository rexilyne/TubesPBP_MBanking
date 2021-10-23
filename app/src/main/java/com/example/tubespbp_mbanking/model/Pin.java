package com.example.tubespbp_mbanking.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.room.Ignore;

import com.example.tubespbp_mbanking.BR;

public class Pin extends BaseObservable {
    private String d1;
    private String d2;
    private String d3;
    private String d4;
    private String d5;
    private String d6;

    public Pin() {}

    @Ignore
    public Pin(String d1, String d2, String d3, String d4, String d5, String d6) {
        this.setD1(d1);
        this.setD2(d2);
        this.setD3(d3);
        this.setD4(d4);
        this.setD5(d5);
        this.setD6(d6);
    }

    public String getAssembledPin() {
        return d1 + d2 + d3 + d4 + d5 + d6;
    }

    @Bindable
    public String getD1() {
        return d1;
    }

    public void setD1(String d1) {
        this.d1 = d1;
        notifyPropertyChanged(BR.d1);
    }

    @Bindable
    public String getD2() {
        return d2;
    }

    public void setD2(String d2) {
        this.d2 = d2;
        notifyPropertyChanged(BR.d2);
    }

    @Bindable
    public String getD3() {
        return d3;
    }

    public void setD3(String d3) {
        this.d3 = d3;
        notifyPropertyChanged(BR.d3);
    }

    @Bindable
    public String getD4() {
        return d4;
    }

    public void setD4(String d4) {
        this.d4 = d4;
        notifyPropertyChanged(BR.d4);
    }

    @Bindable
    public String getD5() {
        return d5;
    }

    public void setD5(String d5) {
        this.d5 = d5;
        notifyPropertyChanged(BR.d5);
    }

    @Bindable
    public String getD6() {
        return d6;
    }

    public void setD6(String d6) {
        this.d6 = d6;
        notifyPropertyChanged(BR.d6);
    }
}
