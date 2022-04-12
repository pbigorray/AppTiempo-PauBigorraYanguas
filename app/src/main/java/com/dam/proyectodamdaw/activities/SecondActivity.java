package com.dam.proyectodamdaw.activities;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;

import com.dam.proyectodamdaw.Ciudad;
import com.dam.proyectodamdaw.Parameters;
import com.dam.proyectodamdaw.R;
import com.dam.proyectodamdaw.base.ImageDownloader;

import java.util.Date;
import java.util.Locale;

public class SecondActivity extends AppCompatActivity {
    Ciudad ciudad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        int position= getIntent().getExtras().getInt("position");
        Root root= (Root)getIntent().getExtras().get("roo");
        ciudad = (Ciudad)getIntent().getExtras().get("city");

        ImageView icon2;
        TextView dia2;
        TextView fecha2;
        TextView hora2;
        TextView decripcion2;
        TextView datosTemp2;
        TextView datosMax2;
        TextView datosMin2;
        TextView vientoVe;
        Button atras;

        icon2=findViewById(R.id.icon2);
        dia2=findViewById(R.id.dia2);
        fecha2=findViewById(R.id.fecha2);
        hora2=findViewById(R.id.hora2);
        decripcion2=findViewById(R.id.descripcion2);
        datosTemp2=findViewById(R.id.datosTemp2);
        datosMax2=findViewById(R.id.datosMax2);
        datosMin2=findViewById(R.id.datosMIn2);
        vientoVe=findViewById(R.id.vientoVe);
        atras=findViewById(R.id.atrasMasInfo);


        List l =root.list.get(position);
        String url= Parameters.URL_ICON_PRE+l.weather.get(0).icon+Parameters.URL_ICON_POST;
        ImageDownloader.DownloadImage(url, icon2);

        decripcion2.setText(l.weather.get(0).description);
        datosTemp2.setText(""+l.main.temp);
        datosMax2.setText(""+l.main.temp_max);
        datosMin2.setText(""+l.main.temp_min);

        Date date= new Date((long) l.dt*1000);
//        simpleDateFormat.setTimeZoneFormat(TimeZone.getDefault());

        fecha2.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
        dia2.setText(new SimpleDateFormat("EEEE",new Locale("es","ES")).format(date));
        hora2.setText(new SimpleDateFormat("HH:mm").format(date));

        vientoVe.setText(""+l.wind.speed+" km/h");

        atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("rootAux",root);
                intent.putExtra("auxcity",ciudad);
                finish();
            }
        });
    }
}
