package com.example.mapas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Sesion_Conductor extends AppCompatActivity {
    private String json;
    private CSesion sesion;
    private CircularImageView foto_perfil;
    private CircularImageView foto_vehc;
    private TextView usuario;
    private TextView contrasena;
    private TextView nombre;
    private TextView correo;
    private TextView tipo_vehc;
    int PETICION_PERMISO_LOCALIZACION;
    LocationManager locationManager;

    Coordenadas coordenadas = new Coordenadas(10, 2, "http://168.254.1.104/Sitio/insertarCoordenada.php",
            "http://168.254.1.104/Sitio/extraerEspeCoordenada.php", "http://168.254.1.104/Sitio/extraerCoorCerc.php", "http://168.254.1.104/Sitio/extraerDrivCoord.php", this);

    Switch s1;

    double longitudeNetwork, latitudeNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion__conductor);
        Crear_Sesion();
        foto_perfil = findViewById(R.id.foto_perfil);
        Picasso.get().load(sesion.get_Foto()).resize(500, 500).centerCrop().into(foto_perfil);
        foto_vehc = findViewById(R.id.foto_vehc);
        Picasso.get().load(sesion.get_foto_Vehc()).fit().into(foto_vehc);
        usuario = findViewById(R.id.usuario);
        usuario.setText("Usuario: " + sesion.get_Usuario());
        contrasena = findViewById(R.id.contrasena);
        contrasena.setText("Contraseña: " + sesion.get_Contrasena());
        nombre = findViewById(R.id.nombre);
        nombre.setText(String.valueOf(sesion.get_Id()));
        correo = findViewById(R.id.correo);
        correo.setText("Correo: " + sesion.get_Correo());
        tipo_vehc = findViewById(R.id.tipo_vehc);
        tipo_vehc.setText("Vehículo: " + sesion.get_tipo_Vehc());
        s1 = findViewById(R.id.switch1);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        coordenadas.setIdPropio(Integer.valueOf(sesion.get_Id()));

        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    ubica();
            }
        });


        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PETICION_PERMISO_LOCALIZACION);


    }

    public void ubica() {

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
                        coordenadas.insertarCoordenadas(location.getLatitude(),location.getLongitude());
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


    protected void Crear_Sesion() {
        Bundle bundle = this.getIntent().getExtras();
        json = bundle.getString("json");
        sesion = new CSesion(json);
    }

    public void mensajes(View view) {
        Intent intent = new Intent(getApplicationContext(), Mensajes.class);
        intent.putExtra("id", Integer.valueOf(sesion.get_Id()));
        startActivity(intent);

    }

    public class Coordenadas {
        int IdPropio;
        int tipoUsurario;
        int cantDrivers;
        String URL_subir;
        double[] idUsuario;
        double[] latitud;
        double[] longitud;
        double Olatitud;
        double Olongitud;
        String URL_bajarUn;
        String URL_bajarRadio;
        String URL_bajarAll;
        Context context;

        public Coordenadas(int idPropio, int tipoUsurario, String URL_subir, String URL_bajarUn, String URL_bajarRadio, String URL_bajarAll, Context context) {
            IdPropio = idPropio;
            this.tipoUsurario = tipoUsurario;
            this.URL_subir = URL_subir;
            this.URL_bajarUn = URL_bajarUn;
            this.URL_bajarRadio = URL_bajarRadio;
            this.URL_bajarAll = URL_bajarAll;
            this.context = context;
        }

        public void insertarCoordenadas(final double latitud, final double longitud) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_subir, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(context, "Subido correctamente", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parametros = new HashMap<String, String>();
                    parametros.put("idUsuario", String.valueOf(IdPropio));
                    parametros.put("tipoUsuario", String.valueOf(tipoUsurario));
                    parametros.put("latitud", String.valueOf(latitud));
                    parametros.put("longitud", String.valueOf(longitud));
                    return parametros;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);

        }

        public void extraerDrivCoordenadas(double radio) {
            idUsuario = new double[cantDrivers];
            latitud = new double[cantDrivers];
            longitud = new double[cantDrivers];

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_bajarRadio + "?radio=" + radio, new Response.Listener<JSONArray>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);
                            idUsuario[i] = jsonObject.getDouble("idUsuario");
                            latitud[i] = jsonObject.getDouble("latitud");
                            longitud[i] = jsonObject.getDouble("longitud");

                        } catch (JSONException e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(jsonArrayRequest);


        }

        public void extraerEspCoordenada(int idUsuario) {

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_bajarUn + "?idUsuario=" + idUsuario, new Response.Listener<JSONArray>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);
                            Olatitud = jsonObject.getDouble("latitud");
                            Olongitud = jsonObject.getDouble("longitud");

                        } catch (JSONException e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(jsonArrayRequest);


        }


        public double getOlatitud() {
            return Olatitud;
        }

        public double getOlongitud() {
            return Olongitud;
        }

        public void cantDrivers(double radio) {
            final int[] p = {0};
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_bajarRadio + "?radio=" + radio, new Response.Listener<JSONArray>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);
                            p[0]++;

                        } catch (JSONException e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    cantDrivers = p[0];

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(jsonArrayRequest);
        }

        public int getIdPropio() {
            return IdPropio;
        }

        public void setIdPropio(int idPropio) {
            IdPropio = idPropio;
        }

        public int getTipoUsurario() {
            return tipoUsurario;
        }

        public void setTipoUsurario(int tipoUsurario) {
            this.tipoUsurario = tipoUsurario;
        }

        public int getCantDrivers() {
            return cantDrivers;
        }

        public void setCantDrivers(int cantDrivers) {
            this.cantDrivers = cantDrivers;
        }

        public String getURL_subir() {
            return URL_subir;
        }

        public void setURL_subir(String URL_subir) {
            this.URL_subir = URL_subir;
        }

        public double[] getIdUsuario() {
            return idUsuario;
        }

        public void setIdUsuario(double[] idUsuario) {
            this.idUsuario = idUsuario;
        }

        public double[] getLatitud() {
            return latitud;
        }

        public void setLatitud(double[] latitud) {
            this.latitud = latitud;
        }

        public double[] getLongitud() {
            return longitud;
        }

        public void setLongitud(double[] longitud) {
            this.longitud = longitud;
        }

        public String getURL_bajarUn() {
            return URL_bajarUn;
        }

        public void setURL_bajarUn(String URL_bajarUn) {
            this.URL_bajarUn = URL_bajarUn;
        }

        public String getURL_bajarAll() {
            return URL_bajarAll;
        }

        public void setURL_bajarAll(String URL_bajarAll) {
            this.URL_bajarAll = URL_bajarAll;
        }

        public Context getContext() {
            return context;
        }

        public void setContext(Context context) {
            this.context = context;
        }
    }
}