package com.dam.proyectodamdaw.activities;

import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.provider.DocumentsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.dam.proyectodamdaw.Parameters;
import com.dam.proyectodamdaw.R;
import com.dam.proyectodamdaw.base.ImageDownloader;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>{

    private Root root ;
    private LayoutInflater inflater;
    private  View.OnClickListener listener;

    public MyRecyclerViewAdapter (Context context,Root root){
        this.root=root;
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view=inflater.inflate(R.layout.simple_element,parent,false);
        View view=inflater.inflate(R.layout.simple_element,parent,false);
        view.setOnClickListener(listener);
        return new ViewHolder(view);
    }

    public void  setListener(View.OnClickListener listener){this.listener=listener;}
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        List l = root.list.get(position);
        String url= Parameters.URL_ICON_PRE+l.weather.get(0).icon+Parameters.URL_ICON_POST;
        ImageDownloader.DownloadImage(url, holder.icon);

        holder.decripcion.setText(l.weather.get(0).description);
        holder.datosTemp.setText(""+l.main.temp);
        holder.datosMax.setText(""+l.main.temp_max);
        holder.datosMin.setText(""+l.main.temp_min);

        Date date= new Date((long) l.dt*1000);
        //simpleDateFormat.setTimeZoneFormat(TimeZone.getDefault());

        holder.fecha.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
        holder.dia.setText(new SimpleDateFormat("EEEE",new Locale("es","ES")).format(date));
        holder.hora.setText(new SimpleDateFormat("HH:mm").format(date));

    }


    @Override
    public int getItemCount() {
        return root.list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
       ImageView icon;
       TextView dia;
       TextView fecha;
       TextView hora;
       TextView decripcion;
       TextView datosTemp;
       TextView datosMax;
       TextView datosMin;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon=itemView.findViewById(R.id.icon);
            dia=itemView.findViewById(R.id.dia);
            fecha=itemView.findViewById(R.id.fecha);
            hora=itemView.findViewById(R.id.hora);
            decripcion=itemView.findViewById(R.id.descripcion);
            datosTemp=itemView.findViewById(R.id.datosTemp);
            datosMax=itemView.findViewById(R.id.datosMax);
            datosMin=itemView.findViewById(R.id.datosMIn);
        }
    }
}
