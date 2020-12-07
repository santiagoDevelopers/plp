package com.example.mapas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.ncorti.slidetoact.SlideToActView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapter2 extends RecyclerView.Adapter<adapter2.ViewHolder> {

    ArrayList<Cardf> locations;

    private OnItemCLickListener mListener;

    public interface OnItemCLickListener {
        void OnItemClick(int position);
    }


    public void setOnItemClickListener(OnItemCLickListener listener) {

        mListener = listener;
    }

    public adapter2(ArrayList<Cardf> locations) {
        this.locations = locations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.design2, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view, mListener);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Cardf helper = locations.get(i);
        Picasso.get().load(locations.get(i).getImage()).resize(500, 500).centerCrop().into(viewHolder.img);
        Picasso.get().load(locations.get(i).getVehc()).resize(500, 500).centerCrop().into(viewHolder.vehc);
        viewHolder.tit.setText(helper.getTitle());
        viewHolder.name.setText(helper.getName());
        viewHolder.dir.setText(helper.getDir());
        viewHolder.tiempo.setText(helper.getTiempo());

    }

    @Override
    public int getItemCount() {
        return locations.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircularImageView img, vehc;
        TextView tit, name, dir, tiempo;


        public ViewHolder(@NonNull View itemView, final OnItemCLickListener listener) {
            super(itemView);

            img = itemView.findViewById(R.id.cardIm);
            tit = itemView.findViewById(R.id.cardTit);
            vehc = itemView.findViewById(R.id.foto_vehc);
            name = itemView.findViewById(R.id.name);
            dir = itemView.findViewById(R.id.tetPrecio2);
           tiempo = itemView.findViewById(R.id.min);


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


        }
    }

}