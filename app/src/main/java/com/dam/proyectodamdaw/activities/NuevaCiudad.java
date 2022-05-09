package com.dam.proyectodamdaw.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dam.proyectodamdaw.Ciudad;
import com.dam.proyectodamdaw.R;
import com.dam.proyectodamdaw.api.API;
import com.dam.proyectodamdaw.api.Connector;
import com.dam.proyectodamdaw.api.Result;
import com.dam.proyectodamdaw.base.BaseActivity;
import com.dam.proyectodamdaw.base.CallInterface;

public class NuevaCiudad extends BaseActivity implements CallInterface {
    private Button aceptar;
    private Button cancelar;

    private EditText nomCiudad;
    private EditText latitud;
    private EditText longitud;
    private EditText imagen;

    private Result result;


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
                executeCall(NuevaCiudad.this);
                intent.putExtra("nomCiudad",nomCiudad.getText().toString());
                intent.putExtra("latitud",Double.valueOf(latitud.getText().toString()));
                intent.putExtra("longitud",Double.valueOf(longitud.getText().toString()));
                intent.putExtra("imagen",imagen.getText().toString());
                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }

    @Override
    public void doInBackground() {
        Ciudad aux= new Ciudad(nomCiudad.getText().toString(),Double.valueOf(latitud.getText().toString()),Double.valueOf(longitud.getText().toString()),imagen.getText().toString());
        result= Connector.getConector().postDB(Ciudad.class,aux, API.ADD);

    }

    @Override
    public void doInUI() {
        if (result instanceof Result.Success){
            Toast.makeText(this, "vamosssssss", Toast.LENGTH_SHORT).show();
        }else{
            Result.Error error =(Result.Error)result;
            Toast.makeText(this, error.getError(), Toast.LENGTH_SHORT).show();
        }
    }
}