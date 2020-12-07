package com.example.mapas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapterReservas extends RecyclerView.Adapter<adapterReservas.ViewHolder> {

    ArrayList<Reservas> locations;
    private OnItemCLickListener mListener;

    public interface OnItemCLickListener {
        void OnItemClick(int position);

    }

    public void setOnItemClickListener(OnItemCLickListener listener) {

        mListener = listener;
    }


    public adapterReservas(ArrayList<Reservas> locations) {
        this.locations = locations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.agenda, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view, mListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Reservas helper = locations.get(i);
        Picasso.get().load(locations.get(i).getImagen()).resize(500, 500).centerCrop().into(viewHolder.img);
        viewHolder.nombre.setText(helper.getNombre());
        viewHolder.fecha.setText(helper.getFecha());
        viewHolder.hora.setText(helper.getHora());
        viewHolder.origen.setText(helper.getOrigen());
        viewHolder.destino.setText(helper.getDestino());

    }

    @Override
    public int getItemCount() {
        return locations.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircularImageView img;
        TextView fecha, nombre, origen, destino, hora;
        LinearLayout ruta;

        public ViewHolder(@NonNull View itemView, final OnItemCLickListener listener) {
            super(itemView);

            img = itemView.findViewById(R.id.cardIm);
            fecha = itemView.findViewById(R.id.fechaReserva);
            hora = itemView.findViewById(R.id.fechaHora);
            nombre = itemView.findViewById(R.id.nombreReserva);
            origen = itemView.findViewById(R.id.direcOrigen);
            destino = itemView.findViewById(R.id.direccDestino);
            ruta = itemView.findViewById(R.id.ruta);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            //listener.OnItemClick(position);
                            if (ruta.getVisibility() == View.VISIBLE)
                                ruta.setVisibility(View.GONE);
                            else
                                ruta.setVisibility(View.VISIBLE);
                        }
                    }

                }
            });


        }
    }

}