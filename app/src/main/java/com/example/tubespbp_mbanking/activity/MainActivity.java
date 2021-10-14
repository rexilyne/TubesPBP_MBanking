package com.example.tubespbp_mbanking.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;

import com.example.tubespbp_mbanking.R;
import com.example.tubespbp_mbanking.fragment.FragmentHome;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();;

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