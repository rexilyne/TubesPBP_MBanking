package com.example.tubespbp_mbanking.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;

import com.example.tubespbp_mbanking.R;
import com.example.tubespbp_mbanking.databinding.ActivityMainBinding;
import com.example.tubespbp_mbanking.fragment.FragmentAktivitas;
import com.example.tubespbp_mbanking.fragment.FragmentAkun;
import com.example.tubespbp_mbanking.fragment.FragmentHome;
import com.example.tubespbp_mbanking.fragment.FragmentMutasi;
import com.example.tubespbp_mbanking.fragment.FragmentTransfer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseMessaging.getInstance().subscribeToTopic("tubesPBP");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);
        binding.bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_home:
                                changeFragment(new FragmentHome());
                                return true;
                            case R.id.menu_mutasi:
                                changeFragment(new FragmentMutasi());
                                return true;
                            case R.id.menu_transfer:
                                changeFragment(new FragmentTransfer());
                                return true;
                            case R.id.menu_aktivitas:
                                changeFragment(new FragmentAktivitas());
                                return true;
                            case R.id.menu_akun:
                                changeFragment(new FragmentAkun());
                                return true;
                        }
                        return false;
                    }
                }
        );

        getSupportActionBar().hide();

        changeFragment(new FragmentHome());
    }

    //  Method untuk mengubah fragment
    public void changeFragment(Fragment fragment){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.layout_app_content,fragment)
                .commit();
    }
}