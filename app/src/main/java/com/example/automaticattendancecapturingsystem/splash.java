package com.example.automaticattendancecapturingsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import android.content.Intent;
import android.os.Bundle;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        Thread thread = new Thread(){
          public void run(){
              try {
                  sleep(1400);
              }
              catch (Exception e){
                  e.printStackTrace();
              }
              finally {
                  Intent intent = new Intent(splash.this,home.class);
                  startActivity(intent);
                  finish();
              }
          }
        };thread.start();
    }
}