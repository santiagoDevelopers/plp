package com.example.mapas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Respuesta extends AppCompatActivity {
    String tt;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respuesta);

        textView= findViewById(R.id.respuest);

        Bundle bundle= getIntent().getExtras();
        tt=bundle.getString("txt");

        textView.setText(tt);
    }
}
