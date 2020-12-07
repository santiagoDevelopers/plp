package com.example.mapas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.percentlayout.widget.PercentRelativeLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.makeramen.roundedimageview.RoundedImageView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adaptador_Lug_Fav extends RecyclerView.Adapter<Adaptador_Lug_Fav.View_Holder_Lug_Fav> {

    private ArrayList<Lug_Fav>list_lug_fav;
    private OnItemClickListener listener;
    private Context context;


    public interface OnItemClickListener{
        void EditEvent(int position, View view);
        void MapaEvent(int position, View view);
        void DownTouch(int position, View view);

    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public Adaptador_Lug_Fav(ArrayList<Lug_Fav> list_lug_fav, Context context) {
        this.list_lug_fav = list_lug_fav;
        this.context = context;
    }

    @NonNull
    @Override
    public View_Holder_Lug_Fav onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_lug_fav, null, false);
        return new View_Holder_Lug_Fav(view, this.listener);
    }

    @Override
    public void onBindViewHolder(@NonNull View_Holder_Lug_Fav holder, int position) {

        holder.lug_fav.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale_anim));
        holder.back_rounded.setImageResource(list_lug_fav.get(position).getBackground());
        holder.icono.setAnimation(AnimationUtils.loadAnimation(context, R.anim.transt_anim_y));
        holder.lugar.setAnimation(AnimationUtils.loadAnimation(context, R.anim.transt_anim_x));
        holder.icono.setImageResource(list_lug_fav.get(position).getIcono());
        holder.lugar.setText(list_lug_fav.get(position).getLugar());

    }

    @Override
    public int getItemCount() {
        return list_lug_fav.size();
    }

    public class View_Holder_Lug_Fav extends RecyclerView.ViewHolder {
        CircularImageView icono;
        TextView lugar;
        CardView lug_fav;
        RoundedImageView back_rounded;




        @SuppressLint("ClickableViewAccessibility")
        public View_Holder_Lug_Fav(@NonNull final View itemView, final Adaptador_Lug_Fav.OnItemClickListener listener) {
            super(itemView);
            //context = itemView.getContext();
            icono = itemView.findViewById(R.id.img_icon);
            lugar = itemView.findViewById(R.id.lugar);
            lug_fav = itemView.findViewById(R.id.lug_fav);
            back_rounded = itemView.findViewById(R.id.back_rounded);


            lug_fav.setOnTouchListener(new OnEventTouchListener(itemView.getContext()){

                @Override
                public void onClick() {
                    super.onClick();
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            listener.MapaEvent(position, itemView);
                            //Toast.makeText(itemView.getContext(), "click", Toast.LENGTH_SHORT).show();

                        }
                    }
                }

                @Override
                public void onDoubleClick() {
                    super.onDoubleClick();
                    // your on onDoubleClick here
                }

                @Override
                public void onLongClick() {
                    super.onLongClick();
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            listener.EditEvent(position, itemView);
                            //Toast.makeText(itemView.getContext(), "long", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onDownTouch() {
                    super.onDownTouch();
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            lug_fav.setCardElevation(3f);
                            listener.DownTouch(position, itemView);
                            //Toast.makeText(itemView.getContext(), "down", Toast.LENGTH_SHORT).show();

                        }
                    }
                }

                @Override
                public void onUpTouch() {
                    super.onUpTouch();
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            lug_fav.setCardElevation(20f);
                        }
                    }
                }

                @Override
                public void onMoveTouch() {
                    super.onMoveTouch();
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            lug_fav.setCardElevation(20f);
                            //Toast.makeText(itemView.getContext(), "move", Toast.LENGTH_SHORT).show();
                        }
                    }
                }



            });
        }

    }
}
