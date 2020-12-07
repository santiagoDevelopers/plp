package com.example.mapas;

import android.os.Build;
import android.os.CountDownTimer;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.ncorti.slidetoact.SlideToActView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class adapter4 extends RecyclerView.Adapter<adapter4.ViewHolder> {

    ArrayList<RespConduct> locations;
    private OnItemCLickListener mListener;
    private OnSlideCompleteListener sListener;
    boolean flag = true;
    int contador = 0;

    public interface OnItemCLickListener {
        void OnItemClick(int position);

    }

    public interface OnSlideCompleteListener {
        void OnSlideComplete(int position);
    }

    public void setOnSlideCompleteListener(OnSlideCompleteListener listener) {
        sListener = listener;
    }

    public void setOnItemClickListener(OnItemCLickListener listener) {

        mListener = listener;
    }


    public adapter4(ArrayList<RespConduct> locations) {
        this.locations = locations;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.design4, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view, mListener, sListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        RespConduct helper = locations.get(i);
        Picasso.get().load(locations.get(i).getVehcImg()).resize(500, 500).centerCrop().into(viewHolder.img);
        viewHolder.tit.setText(helper.getTitle());
        viewHolder.name.setText(helper.getNombr());
        viewHolder.dista.setText(helper.getTiempo());
        viewHolder.precio.setText(helper.getPrecio());


    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout l1;
        CircularImageView img;
        TextView tit, precio, dista, name, cuentaR;
        SlideToActView sd1;
        Button cancelar;
        CountDownTimer countDownTimer;

        public ViewHolder(@NonNull View itemView, final OnItemCLickListener listener, OnSlideCompleteListener slideCompleteListener) {
            super(itemView);

            img = itemView.findViewById(R.id.veh2);
            tit = itemView.findViewById(R.id.Ttile);
            precio = itemView.findViewById(R.id.tetPrecio);
            name = itemView.findViewById(R.id.rNombre);
            dista = itemView.findViewById(R.id.tiempo);
            sd1 = itemView.findViewById(R.id.acceptar);
            cancelar = itemView.findViewById(R.id.cancelB);
            cuentaR = itemView.findViewById(R.id.cuentaR);
            l1=itemView.findViewById(R.id.respons);

            countDownTimer = new CountDownTimer(10000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                //  cuentaR.setText(String.format(Locale.getDefault(), "%d seg", millisUntilFinished / 1000L));
                }

                @Override
                public void onFinish() {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        cuentaR.setText("Tiempo excedido");
                        sd1.setLocked(true);
                    }
                }

            }.start();


            cancelar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                           l1.setLayoutParams(new FrameLayout.LayoutParams(0,0));
                         //   listener.OnItemClick(position);
                        }
                    }

                }
            });

            sd1.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onSlideComplete(SlideToActView view) {
                    if (slideCompleteListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            slideCompleteListener.OnSlideComplete(position);
                        }
                    }

                    sd1.resetSlider();
                }
            });

        }

    }

}