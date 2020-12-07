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

import java.util.ArrayList;

public class adapter3 extends RecyclerView.Adapter<adapter3.ViewHolder> {

    ArrayList<Direcc> locations;
    private OnItemCLickListener mListener;

    public interface OnItemCLickListener {
        void OnItemClick(int position);

    }

    public void setOnItemClickListener(OnItemCLickListener listener) {

        mListener = listener;
    }


    public adapter3(ArrayList<Direcc> locations) {
        this.locations = locations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.design3, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view, mListener);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Direcc helper = locations.get(i);
        viewHolder.img.setImageResource(helper.getImage());
        viewHolder.tit.setText(helper.getTitle());
        viewHolder.dire.setText(helper.getDireccion());
        viewHolder.dista.setText(helper.getDistancia());

    }

    @Override
    public int getItemCount() {
        return locations.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView tit, dire, dista;

        public ViewHolder(@NonNull View itemView, final OnItemCLickListener listener) {
            super(itemView);

            img = itemView.findViewById(R.id.cardIm);
            tit = itemView.findViewById(R.id.cardTit);
            dire = itemView.findViewById(R.id.direccion);
            dista = itemView.findViewById(R.id.distancia);

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