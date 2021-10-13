package com.example.tubespbp_mbanking.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.os.Bundle;

import com.example.tubespbp_mbanking.R;
import com.example.tubespbp_mbanking.fragment.FragmentLogin;
import com.example.tubespbp_mbanking.fragment.FragmentRegister1;

public class AuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        getSupportActionBar().hide();

        changeFragment(new FragmentLogin());
    }

    //  Method untuk mengubah fragment
    public void changeFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_auth_frag,fragment)
                .commit();
    }
}