package com.example.bingebook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        BottomNavigationView btmnavigation;

        SharedPreferences sharedPreferences = getSharedPreferences("UserCredentials",MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        int userId = sharedPreferences.getInt("UserId",0);
        TextView usernameTextView ;
        usernameTextView = findViewById(R.id.usernameTextView);
        usernameTextView.setText(username);
        Toolbar toolbar;
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btmnavigation=findViewById(R.id.btmnavigation);
        toolbar = findViewById(R.id.toolbar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
           getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent next;
        btmnavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id =item.getItemId();

                if (id==R.id.item_nav_home){
                    loadfragment(new mainhome(),1);

                } else if (id==R.id.item_nav_search) {
                    loadfragment(new search(),0);
                } else if (id==R.id.item_nav_watchbucket) {
                    loadfragment(new watchbucket(),0);
                }
                return true;

            }

        });
        btmnavigation.setSelectedItemId(R.id.item_nav_home);
    }
    public void loadfragment(Fragment fragment ,int flag){
        FragmentManager fm= getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (flag==1)
            ft.add(R.id.frames,fragment);
        else
            ft.replace(R.id.frames,fragment);
        ft.commit();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}