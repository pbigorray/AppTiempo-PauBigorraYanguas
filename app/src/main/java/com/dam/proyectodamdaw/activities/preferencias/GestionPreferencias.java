package com.dam.proyectodamdaw.activities.preferencias;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.dam.proyectodamdaw.R;

public class GestionPreferencias {
    private  SharedPreferences preferences;
    private static GestionPreferencias intance;

    public   String getUnidades(Context context){
       inicializa(context);
       return preferences.getString("unidades","standard");
    }
    public   String getIdiomas(Context context){
        inicializa(context);
        return preferences.getString("idiomas","es");
    }

    public  String getApiKey(Context context){
        inicializa(context);
        return preferences.getString("settings_apikey_key","83a45ae2eaa9292fb37e84182f9ded07");
    }
    private  void inicializa(Context context) {
        if (preferences==null){
            preferences= PreferenceManager.getDefaultSharedPreferences(context);
        }
    }
    public static GestionPreferencias getInstance(){
        if (intance==null){
            intance=new GestionPreferencias();
        }
        return intance;
    }

    public String getTheme(Context context) {
        inicializa(context);
        return preferences.getString(context.getString(R.string.settings_theme_key),ThemeSetup.Mode.DEFAULT.name());
    }
}