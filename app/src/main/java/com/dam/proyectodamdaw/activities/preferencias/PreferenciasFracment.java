package com.dam.proyectodamdaw.activities.preferencias;

import android.os.Bundle;
import android.widget.Switch;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;


import com.dam.proyectodamdaw.R;

import java.util.Arrays;
import java.util.List;

public class PreferenciasFracment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferencias,rootKey);

        ListPreference listPreference =findPreference("unidades");

        List<String> entries = Arrays.asList(getResources().getStringArray(R.array.unidades_entries));
        List<String> values = Arrays.asList(getResources().getStringArray(R.array.unidades_values));

        String val = entries.get(values.indexOf(GestionPreferencias.getInstance().getUnidades(getContext())));

        listPreference.setSummary("Seleccionado "+val);

        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                String val = entries.get(values.indexOf(newValue));

                listPreference.setSummary("Seleccionado "+val);
                return  true;
            }
        });
        //idioma
        ListPreference idiomas =findPreference("idiomas");

        List<String> idiEntries = Arrays.asList(getResources().getStringArray(R.array.idioma_entries));
        List<String> idiValues = Arrays.asList(getResources().getStringArray(R.array.idioma_values));

        String idiVal = idiEntries.get(idiValues.indexOf(GestionPreferencias.getInstance().getIdiomas(getContext())));

        idiomas.setSummary("Seleccionado "+idiVal);

        idiomas.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                String val = idiEntries.get(idiValues.indexOf(newValue));

                idiomas.setSummary("Seleccionado "+val);
                return  true;
            }
        });
        //Tema
        ListPreference themePreference = getPreferenceManager().findPreference(getString(R.string.settings_theme_key));
        if (themePreference.getValue() == null) {
            themePreference.setValue(ThemeSetup.Mode.DEFAULT.name());
        }
        themePreference.setOnPreferenceChangeListener((preference, newValue) -> {
            ThemeSetup.applyTheme(ThemeSetup.Mode.valueOf((String) newValue));
            return true;
        });
        //api
        SwitchPreference apiSwitch= findPreference("switch_apikey");
        EditTextPreference apiKey=findPreference("settings_apikey_key");
        apiSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                if (apiSwitch.isChecked()){
                    apiSwitch.setChecked(false);
                    apiKey.setEnabled(false);
                    return true;
                }else {
                    apiSwitch.setChecked(true);
                    apiKey.setEnabled(true);

                }
                return false;
            }
        });

    }
}