package com.example.mapas;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.github.chrisbanes.photoview.PhotoView;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adaptador_Cond extends RecyclerView.Adapter<Adaptador_Cond.View_Holder_Conductor> {

    private ArrayList<Conductores>list_conductores;
    private OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener{
        void onFavClick(final int position);
        void reservar(final int position);
        void onFotoClick(int position, View view);
        void onFotoVehcClick(int position, View view);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
    public Adaptador_Cond(ArrayList<Conductores> list_conductores, Context context) {
        this.list_conductores = list_conductores;
        this.context = context;
    }

    @NonNull
    @Override
    public Adaptador_Cond.View_Holder_Conductor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_conductores,null, false);
        return new View_Holder_Conductor(view, this.listener);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador_Cond.View_Holder_Conductor holder, int position) {


        holder.item_conductores.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_anim));
        Picasso.get().load(list_conductores.get(position).getFoto()).into(holder.foto);
        Picasso.get().load(list_conductores.get(position).getFoto_vehc()).into(holder.foto_vehc);
        holder.nombre.setText(list_conductores.get(position).getNombre());
        holder.distancia.setText("a "+list_conductores.get(position).getDistancia()+"Km");
        if (list_conductores.get(position).isFavorito()){
            holder.favoritar.setImageResource(R.drawable.ic_heart_red);
        }else{
            holder.favoritar.setImageResource(R.drawable.ic_heart);
        }
        holder.valoracion.setNameForSmile(BaseRating.TERRIBLE, "TERRIBLE");
        holder.valoracion.setNameForSmile(BaseRating.BAD, "MAL");
        holder.valoracion.setNameForSmile(BaseRating.OKAY, "REGULAR");
        holder.valoracion.setNameForSmile(BaseRating.GOOD, "BIEN");
        holder.valoracion.setNameForSmile(BaseRating.GREAT, "EXCELENTE");
        holder.valoracion.setSelectedSmile(list_conductores.get(position).getValoracion()-1);
        //holder.valoracion.setNameForSmile(BaseRating.NONE, "NINGUNA");
        holder.valoracion.setShowLine(false);





    }

    @Override
    public int getItemCount() {
        return list_conductores.size();
    }


    public class View_Holder_Conductor extends RecyclerView.ViewHolder{

        public CircularImageView foto;
        public CircularImageView foto_vehc;
        public TextView nombre;
        public TextView distancia;
        public ImageButton favoritar;
        public LinearLayout detalles;
        public SmileRating valoracion;
        public Button solicitar, reservar;
        public PhotoView zoom;
        public CardView item_conductores;


        public View_Holder_Conductor(@NonNull final View itemView, final OnItemClickListener listener) {
            super(itemView);
            foto = itemView.findViewById(R.id.foto);
            foto_vehc = itemView.findViewById(R.id.foto_vehc);
            nombre = itemView.findViewById(R.id.nombre);
            distancia = itemView.findViewById(R.id.distancia);
            favoritar = itemView.findViewById(R.id.favoritar);
            detalles = itemView.findViewById(R.id.detalles);
            valoracion = itemView.findViewById(R.id.valoracion);
            solicitar = itemView.findViewById(R.id.solicitar);
            reservar = itemView.findViewById(R.id.reservar);
            item_conductores = itemView.findViewById(R.id.item_conductores);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            if (detalles.getVisibility() == View.GONE){
                                detalles.setVisibility(View.VISIBLE);
                            } else {
                                detalles.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            });

            reservar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            listener.reservar(position);
                        }
                    }

                }
            });
            favoritar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            if (!list_conductores.get(position).isFavorito()){
                                favoritar.setImageResource(R.drawable.ic_heart_red);
                            }else{
                                favoritar.setImageResource(R.drawable.ic_heart);
                            }
                            listener.onFavClick(position);
                        }
                    }

                }
            });

            foto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            listener.onFotoClick(position, view);
                        }
                    }

                }
            });

            foto_vehc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            listener.onFotoVehcClick(position,view);
                        }
                    }

                }
            });


        }

    }
}
