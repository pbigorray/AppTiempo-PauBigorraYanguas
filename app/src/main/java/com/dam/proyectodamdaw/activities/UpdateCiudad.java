package com.dam.proyectodamdaw.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dam.proyectodamdaw.R;
import com.dam.proyectodamdaw.base.BaseActivity;
import com.dam.proyectodamdaw.base.CallInterface;

public class UpdateCiudad extends BaseActivity implements CallInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_ciudad);
    }

    @Override
    public void doInBackground() {

    }

    @Override
    public void doInUI() {

    }
}