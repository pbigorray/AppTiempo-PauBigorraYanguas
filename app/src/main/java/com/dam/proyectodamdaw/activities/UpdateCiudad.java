package com.dam.proyectodamdaw.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dam.proyectodamdaw.Ciudad;
import com.dam.proyectodamdaw.R;
import com.dam.proyectodamdaw.api.API;
import com.dam.proyectodamdaw.api.Connector;
import com.dam.proyectodamdaw.api.Result;
import com.dam.proyectodamdaw.base.BaseActivity;
import com.dam.proyectodamdaw.base.CallInterface;

import java.util.List;

public class UpdateCiudad extends BaseActivity implements CallInterface{
    private ArrayAdapter arrayAdapter;
    private List<Ciudad> ciudadList;
    private Spinner spinnerCi;
    private Ciudad selected;

    private Button aceptar;
    private Button cancelar;

    private String name,img;
    private double lat,lon;
    private EditText latitud;
    private EditText longitud;
    private EditText imagen;


    private Result result;
    private Ciudad auxCiudad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_ciudad);
        spinnerCi=findViewById(R.id.spinnerCiudad);


        aceptar=findViewById(R.id.aceptar);
        cancelar=findViewById(R.id.cancelar);

        latitud=findViewById(R.id.latitud2);
        longitud=findViewById(R.id.longitud2);
        imagen=findViewById(R.id.imagen2);




        executeCall(UpdateCiudad.this);



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
                executeCall(new CallInterface() {
                    @Override
                    public void doInBackground() {
                        datosCiudad();
                        auxCiudad =new Ciudad(name, lat,lon,img);
                        result= Connector.getConector().putResult(Ciudad.class,auxCiudad, API.UPDATE);
                    }

                    @Override
                    public void doInUI() {
                        if (result instanceof Result.Success){
                            Toast.makeText(getApplicationContext(), "Ciudad actualizada: " + name , Toast.LENGTH_LONG).show();
                        }else {
                            Result.Error error =(Result.Error)result;
                            Toast.makeText(getApplicationContext(), error.getError(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                intent.putExtra("name",name);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    @Override
    public void doInBackground() {
        ciudadList = Connector.getConector().getAsListDB(Ciudad.class, API.CIUDADES);
    }

    @Override
    public void doInUI() {

        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,ciudadList);
        spinnerCi.setAdapter(arrayAdapter);

        spinnerCi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected= (Ciudad)adapterView.getSelectedItem();
                name= ciudadList.get(i).getName();

                latitud.setText(String.valueOf(ciudadList.get(i).getLat()));
                longitud.setText(String.valueOf(ciudadList.get(i).getLon()));
                imagen.setText(String.valueOf(ciudadList.get(i).getImagen()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                spinnerCi.setSelection(0);
                name= ciudadList.get(0).getName();

                latitud.setText(String.valueOf(ciudadList.get(0).getLat()));
                longitud.setText(String.valueOf(ciudadList.get(0).getLon()));
                imagen.setText(String.valueOf(ciudadList.get(0).getImagen()));
            }
        });
    }


    private void datosCiudad() {
        lat=Double.valueOf(latitud.getText().toString());
        lon=Double.valueOf(longitud.getText().toString());
        img=imagen.getText().toString();
    }


}