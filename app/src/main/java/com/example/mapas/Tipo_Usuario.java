package com.example.mapas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Tipo_Usuario extends AppCompatActivity {
    private Button conductor;
    private Button usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo__usuario);
        usuario = findViewById(R.id.bt_usuario);
        conductor = findViewById(R.id.bt_conductor);

        usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Tipo_Usuario.this, Cuenta_Usuario.class);
                startActivity(intent1);
            }
        });





    }

    public void conductor_cuenta(View view) {
        Intent intent = new Intent(this, Conductor_Cuenta.class);
        startActivity(intent);
    }
}