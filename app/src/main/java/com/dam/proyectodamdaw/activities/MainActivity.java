package com.dam.proyectodamdaw.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dam.proyectodamdaw.Ciudad;
import com.dam.proyectodamdaw.Parameters;
import com.dam.proyectodamdaw.activities.preferencias.GestionPreferencias;
import com.dam.proyectodamdaw.activities.preferencias.Preferencias;
import com.dam.proyectodamdaw.api.Connector;
import com.dam.proyectodamdaw.base.BaseActivity;
import com.dam.proyectodamdaw.base.CallInterface;
import com.dam.proyectodamdaw.R;
import com.dam.proyectodamdaw.base.ImageDownloader;

public class MainActivity extends BaseActivity implements CallInterface, View.OnClickListener {
    Root root , auxRoot;
    RecyclerView recyclerView;
    Ciudad ciudad,auxCiudad;
    TextView nombreCiudad;

    private final String TAG =MainActivity.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_main);

        ciudad = (Ciudad) getIntent().getExtras().get("city");


        auxRoot= (Root) getIntent().getExtras().get("rootAux");
        auxCiudad = (Ciudad) getIntent().getExtras().get("auxcity");

        Log.d(TAG,"Create");
    }



        @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"Start");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"Stop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        executeCall(this);
        showProgress();

    }

    @Override
    public void doInBackground() {

        if (auxRoot != null) {
            root = auxRoot;
            ciudad = auxCiudad;
        } else if (root == null){
            try {
                root = Connector.getConector().get(Root.class, "forecast?lang=" + GestionPreferencias.getInstance().getIdiomas(this) + "&units=" + GestionPreferencias.getInstance().getUnidades(this) + "&lat=" + ciudad.getLat() + "&lon=" + ciudad.getLon() + "&appid=" + GestionPreferencias.getInstance().getApiKey(this));
            } catch (Exception e) {
                root = Connector.getConector().get(Root.class, "forecast?lang=" + GestionPreferencias.getInstance().getIdiomas(this) + "&units=" + GestionPreferencias.getInstance().getUnidades(this) + "&lat=" + ciudad.getLat() + "&lon=" + ciudad.getLon() + "&appid=83a45ae2eaa9292fb37e84182f9ded07");
            }
    }

    }

    @Override
    public void doInUI() {
        nombreCiudad=findViewById(R.id.nombreCiudad);
        recyclerView= findViewById(R.id.recycle);


        nombreCiudad.setText(ciudad.getName());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, RecyclerView.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        hideProgress();
        MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(this, root);
        myRecyclerViewAdapter.setListener(this);
        recyclerView.setAdapter(myRecyclerViewAdapter);

        LinearLayoutManager myLinearLayaoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(myLinearLayaoutManager);
        recyclerView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent =new Intent(getApplicationContext(),SecondActivity.class);
        int position=recyclerView.getChildAdapterPosition(view);
        intent.putExtra("position",position);
        intent.putExtra("roo",root);
        intent.putExtra("city",ciudad);
        startActivity(intent);

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (root!=null&&ciudad!=null){
            outState.putSerializable("rootG",root);
            outState.putSerializable("ciudadG",ciudad);
        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState!=null){
            root=(Root) savedInstanceState.getSerializable("rootG");
            ciudad=(Ciudad) savedInstanceState.getSerializable("ciudadG");
        }
    }
}