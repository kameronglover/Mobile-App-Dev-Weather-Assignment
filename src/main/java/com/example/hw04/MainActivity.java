package com.example.hw04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
/**
 * Kameron Glover and Maya Hamilton
 * hw 04
 * MainActivity.java
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, new CitiesFragment()).commit();
    }
}