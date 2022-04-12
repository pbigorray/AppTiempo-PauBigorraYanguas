package com.dam.proyectodamdaw.activities.preferencias;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dam.proyectodamdaw.R;

public class Preferencias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content,new PreferenciasFracment()).commit();
    }
}