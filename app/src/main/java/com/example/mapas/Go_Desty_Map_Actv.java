package com.example.mapas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Go_Desty_Map_Actv extends AppCompatActivity {
    private String direcccion;
    private TextView text_lugar;
    private TextView text_direccion;
    private String titl_lugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go__desty__map);
        text_lugar = findViewById(R.id.fav_name);
        text_direccion = findViewById(R.id.direccion);
        Bundle bundle = this.getIntent().getExtras();
        titl_lugar = bundle.getString("titl_lug");
        direcccion = bundle.getString("direccion");
        text_lugar.setText(titl_lugar);
        text_direccion.setText(direcccion);
    }
}