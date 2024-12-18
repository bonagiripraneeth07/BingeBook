package com.example.bingebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //SharedPreferences pref =getSharedPreferences("name",MODE_PRIVATE);
                    //Boolean check = pref.getBoolean("flag",false);
                    //Intent in;
                    // Get the SharedPreferences object
                    SharedPreferences sharedPreferences = getSharedPreferences("UserCredentials",MODE_PRIVATE);


                    String username = sharedPreferences.getString("username", null);
                    int userId = sharedPreferences.getInt("UserId",0);
                    Intent i;
                    if (userId!=0) {
                        // Use the stored email address
                        // For example, you can set it to a TextView
                        i=new Intent(MainActivity.this,Home.class);


                    } else {
                        // No stored email found
                        i=new Intent(MainActivity.this, Login.class);
                    }
                    startActivity(i);
                    //if (check){
                    //  in=new Intent(MainActivity.this, MainActivity2.class);
                    //}else {
                    //  in=new Intent(MainActivity.this, Login_activity.class);
                    //}
                    // startActivity(in);
                    // Intent i = new Intent(MainActivity.this, Login_activity.class);
                    //startActivity(i);
                    // finish();
                }
            }, 2000);
        }
    }
