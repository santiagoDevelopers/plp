package com.example.mapas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Add_Fav_Map_Actv extends AppCompatActivity {
    private EditText direccion;
    private Button add;
    private String titl_lug;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__fav__map);
        direccion = findViewById(R.id.fav_location);
        add = findViewById(R.id.add);
        Bundle bundle = this.getIntent().getExtras();
        titl_lug = bundle.getString("titl_lug");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addedit_lug_Fav(titl_lug, direccion.getText().toString());
            }
        });


    }
    public void addedit_lug_Fav(String lugar, String direccion){

        SharedPreferences pref = getSharedPreferences("pref_lug_fav", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(lugar, direccion);
        editor.apply();

    }
}