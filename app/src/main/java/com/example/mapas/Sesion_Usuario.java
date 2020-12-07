package com.example.mapas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;


public class Sesion_Usuario extends AppCompatActivity {

    private String json;
    private CSesion sesion;
    private CircularImageView foto_perfil;
    private TextView usuario;
    private TextView contrasena;
    private TextView nombre;
    private TextView correo;
    int PETICION_PERMISO_LOCALIZACION;
    LocationManager locationManager;

    double longitudeNetwork, latitudeNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion__usuario);
        Crear_Sesion();
        foto_perfil = findViewById(R.id.foto_perfil);
        Picasso.get().load(sesion.get_Foto()).resize(500, 500).centerCrop().into(foto_perfil);
        usuario = findViewById(R.id.usuario);
        usuario.setText("Usuario: " + sesion.get_Usuario());
        contrasena = findViewById(R.id.contrasena);
        contrasena.setText("Contraseña: " + sesion.get_Contrasena());
        nombre = findViewById(R.id.nombre);
        nombre.setText("Nombre: " + sesion.get_Nombre());
        correo = findViewById(R.id.correo);
        correo.setText("Correo: " + sesion.get_Correo());
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PETICION_PERMISO_LOCALIZACION);


    }

    public void ubica(View view) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 20 * 1000, 10, locationListenerNetwork);
        Toast.makeText(this, "Network provider started running", Toast.LENGTH_LONG).show();


    }


    private final LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeNetwork = location.getLongitude();
            latitudeNetwork = location.getLatitude();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    usuario.setText(longitudeNetwork + "");
                    nombre.setText(latitudeNetwork + "");
                    Toast.makeText(Sesion_Usuario.this, "Network Provider update", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {

        }
        @Override
        public void onProviderDisabled(String s) {

        }
    };

    protected void Crear_Sesion(){
        Bundle bundle = this.getIntent().getExtras();
        json = bundle.getString("json");
        sesion = new CSesion(json);
    }

    public void mapa(View view){
        Intent intent=new Intent(getApplicationContext(),MapsActivity.class);
        intent.putExtra("id", Integer.valueOf(sesion.get_Id()));
        startActivity(intent);
    }
}