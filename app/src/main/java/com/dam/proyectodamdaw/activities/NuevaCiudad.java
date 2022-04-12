package com.dam.proyectodamdaw.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dam.proyectodamdaw.R;

public class NuevaCiudad extends AppCompatActivity {
    private Button aceptar;
    private Button cancelar;

    private EditText nomCiudad;
    private EditText latitud;
    private EditText longitud;
    private EditText imagen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_ciudad);

        aceptar=findViewById(R.id.aceptar);
        cancelar=findViewById(R.id.cancelar);

        nomCiudad=findViewById(R.id.nuevoNombreCiu);
        latitud=findViewById(R.id.latitud);
        longitud=findViewById(R.id.longitud);
        imagen=findViewById(R.id.imagen);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        });
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
//                intent.putExtra("nomCiudad",nomCiudad.getText().toString());
//                intent.putExtra("latitud",39.50263);
//                intent.putExtra("longitud",-0.44079);
//                intent.putExtra("imagen","fmdslmaksgadsggadag");

                intent.putExtra("nomCiudad",nomCiudad.getText().toString());
                intent.putExtra("latitud",Double.valueOf(latitud.getText().toString()));
                intent.putExtra("longitud",Double.valueOf(longitud.getText().toString()));
                intent.putExtra("imagen",imagen.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }
}