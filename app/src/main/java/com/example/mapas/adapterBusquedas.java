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

public class adapterBusquedas extends RecyclerView.Adapter<adapterBusquedas.ViewHolder> {

    ArrayList<BusquedaReciente> locations;
    private OnItemCLickListener mListener;

    public interface OnItemCLickListener {
        void OnItemClick(int position);
        void Delete(int position);

    }

    public void setOnItemClickListener(OnItemCLickListener listener) {

        mListener = listener;
    }


    public adapterBusquedas(ArrayList<BusquedaReciente> locations) {
        this.locations = locations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.busqueda, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view, mListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        BusquedaReciente helper = locations.get(i);
        viewHolder.busqueda.setText(helper.getBusqueda());
        viewHolder.latitud = helper.getLatitud();
        viewHolder.longitud = helper.getLongitud();

    }

    @Override
    public int getItemCount() {
        return locations.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView busqueda;
        ImageView delete;
        double latitud, longitud;


        public ViewHolder(@NonNull View itemView, final OnItemCLickListener listener) {
            super(itemView);

            busqueda = itemView.findViewById(R.id.busqueda);
           delete = itemView.findViewById(R.id.cross);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.OnItemClick(position);
                        }
                    }

                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.Delete(position);
                        }
                    }

                }
            });


        }
    }

}