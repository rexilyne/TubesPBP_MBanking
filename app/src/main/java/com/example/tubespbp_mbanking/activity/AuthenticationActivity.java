package com.example.tubespbp_mbanking.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import android.os.Bundle;

import com.example.tubespbp_mbanking.R;
import com.example.tubespbp_mbanking.fragment.FragmentLogin;
import com.google.firebase.messaging.FirebaseMessaging;

public class AuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseMessaging.getInstance().subscribeToTopic("tubesPBP");
        setContentView(R.layout.activity_authentication);

        getSupportActionBar().hide();

        changeFragment(new FragmentLogin());
    }

    //  Method untuk mengubah fragment
    public void changeFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_auth_frag,fragment)
//                .addToBackStack(null)
                .commit();
    }

//    @Override
//    public void onBackPressed() {
//        FragmentManager fm = getSupportFragmentManager();
//        if (fm.getBackStackEntryCount() > 0) {
//            fm.popBackStack();
//        } else {
//            super.onBackPressed();
//        }
//    }

}