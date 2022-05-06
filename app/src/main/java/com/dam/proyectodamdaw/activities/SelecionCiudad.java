package com.dam.proyectodamdaw.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.dam.proyectodamdaw.Ciudad;
import com.dam.proyectodamdaw.R;
import com.dam.proyectodamdaw.activities.preferencias.Preferencias;
import com.dam.proyectodamdaw.activities.preferencias.ThemeSetup;
import com.dam.proyectodamdaw.api.API;
import com.dam.proyectodamdaw.api.Connector;
import com.dam.proyectodamdaw.api.Result;
import com.dam.proyectodamdaw.base.BaseActivity;
import com.dam.proyectodamdaw.base.CallInterface;
import com.dam.proyectodamdaw.base.ImageDownloader;

import java.util.ArrayList;
import java.util.List;

public class SelecionCiudad extends BaseActivity implements AdapterView.OnItemSelectedListener, CallInterface {

    private Ciudad selected;
    private Button button;
    private Spinner spinnerCi;
    private ImageView imaCiu;
    private ArrayAdapter arrayAdapter;
    private Button añadir,gps;
    private final int ACTIVITY_CODE=1234;
    private List<Ciudad> ciudadList;

    private LocationManager locationManager;
    private Location location;
    private String proveedor;

    private Result result;


    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecion_ciudad);
        ThemeSetup.applyPreferenceTheme(getApplicationContext());
        añadir=findViewById(R.id.añadir);
        imaCiu=findViewById(R.id.imaCiu);
        button=findViewById(R.id.pronostico);
        spinnerCi=findViewById(R.id.spinnerCiudad);
        gps=findViewById(R.id.gps);

        executeCall(SelecionCiudad.this);
        //ciudadList=new ArrayList<Ciudad>();
//        ciudadList.add(new Ciudad("Valencia", 39.4702, -0.376805, "https://guias-viajar.com/wp-content/uploads/2017/03/valencia-ciudad-artes-ciencias-004.jpg"));
//        ciudadList.add(new Ciudad("Madrid", 40.4165, -3.70256, "https://www.easyjet.com/ejcms/cache/medialibrary/Images/JSS/Destinations/Hero/Spain_Madrid_3840x2160.jpg?mw=1920&hash=E8335D1B8641F2150C395A3EC48BA45CC0B5BA6D"));
//        ciudadList.add(new Ciudad("Barcelona", 41.38879, 2.15899, "https://haciendofotos.com/wp-content/uploads/sagrada-familia-600x409.jpg"));
        



        //Ubicacion
        locationManager= (LocationManager) getSystemService(LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);

        proveedor= locationManager.getBestProvider(criteria,true);

        location= locationManager.getLastKnownLocation(proveedor);


        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                Ciudad aux = new Ciudad("Mi ubicacion",location.getLatitude(),location.getLongitude(),null);
                intent.putExtra("city", aux);
                startActivity(intent);
            }
        });


        spinnerCi.setOnItemSelectedListener(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("city", selected);
                startActivity(intent);
            }
        });

        añadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),NuevaCiudad.class);
                startActivityForResult(intent,ACTIVITY_CODE);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.preferencias,menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.configuracion:
                Intent intent = new Intent(this, Preferencias.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
         selected= (Ciudad)adapterView.getSelectedItem();
        ImageDownloader.DownloadImage(this,selected.getImagen(),imaCiu, R.drawable.ic_launcher_background);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        spinnerCi.setSelection(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==ACTIVITY_CODE){
            if (resultCode==RESULT_CANCELED){
                Toast.makeText(this, "Cancelado por el usuario", Toast.LENGTH_SHORT).show();
            }else if (resultCode==RESULT_OK){
                String nom= data.getExtras().get("nomCiudad").toString();
                double la= (Double) data.getExtras().get("latitud");
                double lo= (Double) data.getExtras().get("longitud");
                String ima= data.getExtras().get("imagen").toString();
                Ciudad aux=new Ciudad(nom,la,lo,ima);

                ciudadList.add(aux);


                arrayAdapter.notifyDataSetChanged();

                Toast.makeText(this, "Añadido", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void doInBackground() {
       ciudadList = Connector.getConector().getAsListDB(Ciudad.class, API.CIUDADES);
    }

    @Override
    public void doInUI() {
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,ciudadList);
        spinnerCi.setAdapter(arrayAdapter);
    }
}