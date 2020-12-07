package com.example.mapas;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.ncorti.slidetoact.SlideToActView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class adapter extends RecyclerView.Adapter<adapter.ViewHolder> {

    ArrayList<Msg> locations;
    private OnItemCLickListener mListener;

    public interface OnItemCLickListener {
        void OnItemClick(int position);

        void aceptar(int position);
        void rechazar( int position);
    }

    public void setOnItemClickListener(OnItemCLickListener listener) {

        mListener = listener;
    }


    public adapter(ArrayList<Msg> locations) {
        this.locations = locations;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.design, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view, mListener);


        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Msg helper = locations.get(i);
        Picasso.get().load(locations.get(i).getImage()).resize(500, 500).centerCrop().into(viewHolder.img);
        viewHolder.tit.setText(helper.getTitle());
        viewHolder.dir.setText(helper.getDirec());
        viewHolder.dir2.setText(helper.getDire2());
        viewHolder.nombre.setText(helper.getNombre());
        viewHolder.fecha.setText(helper.getFecha());



        if (Objects.equals(helper.getFecha(), "")) {
            viewHolder.fecha.setText(" ¡AHORA!");
        }
        else if(Objects.equals(helper.getFecha(), "  "))
            viewHolder.fecha.setText(" ¡AHORA!");
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        CircularImageView img;
        TextView tit, dir, nombre, dir2, fecha;
        Button b1, b2;
        LinearLayout booking;

        public ViewHolder(@NonNull View itemView, final OnItemCLickListener listener) {
            super(itemView);
            img = itemView.findViewById(R.id.cardIm);
            tit = itemView.findViewById(R.id.cardTit);
            dir = itemView.findViewById(R.id.direcc);
            dir2 = itemView.findViewById(R.id.direcc2);
            nombre = itemView.findViewById(R.id.nombre);
            b1 = itemView.findViewById(R.id.acceptar);
            b2 = itemView.findViewById(R.id.cancelar);
            booking = itemView.findViewById(R.id.booking);
            fecha = itemView.findViewById(R.id.fecha);

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

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.aceptar(position);
                        }
                    }
                }
            });
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.rechazar(position);
                        }
                    }
                }
            });

        }
    }

}