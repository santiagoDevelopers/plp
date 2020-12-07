package com.example.mapas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class Mensajes extends AppCompatActivity {

    int PETICION_PERMISO_LOCALIZACION;

    double latitudUs, longitudUs;

    RecyclerView vista1;
    adapter adp;
    int cantMen;
    int id;
    String city;
    String direccionActual;
    String imagen = null;

    Handler handler = new Handler();

    TextView tv1, tv2;
    final ArrayList<Msg> locations = new ArrayList<>();

    String[] mensaje;
    String[] nombres;
    String[] foto;
    int[] idMensaje;
    int[] tipo;
    int[] uOrigen;
    String mensj;
    int idP;
    String nombre;
    boolean flag = true;

    Bitmap[] caras;
    int aux = 0;
    int pos = 0;
    boolean proc = false;

    Comunication comunication = new Comunication(10, this, "http://168.254.1.104/Sitio/insertar.php", "http://168.254.1.104/Sitio/extraerMensaje.php", "http://168.254.1.104/Sitio/cantMensajes.php");
    Button b1, b2;
    Coordenadas coordenadas = new Coordenadas(10, 1, "http://168.254.1.104/Sitio/insertarCoordenada.php",
            "http://168.254.1.104/Sitio/extraerEspeCoordenada.php", "http://168.254.1.104/Sitio/extraerCoorCerc.php", "http://168.254.1.104/Sitio/extraerDrivCoord.php", this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajes);
        vista1 = findViewById(R.id.recic);
        //Bundle bundle = getIntent().getExtras();
        //idP = bundle.getInt("id");
        idP = 14;
        comunication.setIdPropio(idP);
        coordenadas.setIdPropio(idP);


        preparetion();


        Recycler();


        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PETICION_PERMISO_LOCALIZACION);


    }

    void miDireccion(double lat, double lon) {


        OkHttpClient client = new OkHttpClient();
        String url = "https://maps.google.com/?q=" + String.valueOf(lat) + "," + String.valueOf(lon);

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {
                final String myresponse = response.body().string();
                Mensajes.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int p = myresponse.indexOf(city);
                        String texto;

                        int s = 0;

                        for (int i = 1; i < 200; i++) {
                            texto = myresponse.substring(p - i, p);
                            if (texto.indexOf('"') != -1) {
                                s = i - 1;
                                break;
                            }
                        }
                        direccionActual = myresponse.substring(p - s, p - 2);


                    }
                });
            }
        });


    }

    public void preparetion() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                    comunication.cantMensajes("2");


                handler.postDelayed(this, 5000);
            }
        }, 1);
    }


    void visuali() {
        for (int i = 0; i < cantMen; i++) {
            String texto = mensaje[i];
            int t = texto.length();
            int k = texto.indexOf("-");
            int l = texto.indexOf("(");
            int p = texto.indexOf("/");
            String d1 = texto.substring(0, k);
            String d2 = texto.substring(k + 2, l);
            mensj = texto.substring(0, l);
            latitudUs = Double.parseDouble(mensaje[i].substring(l + 1, p));
            longitudUs = Double.parseDouble(mensaje[i].substring(p + 1, t - 2));
            locations.add(new Msg(foto[i], String.valueOf(uOrigen[i]), d1, nombres[i], " "+d2, ""));
            comunication.leido(idMensaje[i]);
            adp.notifyDataSetChanged();
        }


    }


    private void Recycler() {
        vista1.setHasFixedSize(true);
        vista1.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        adp = new adapter(locations);


        vista1.setAdapter(adp);


        adp.setOnItemClickListener(new adapter.OnItemCLickListener() {
            @Override
            public void OnItemClick(int position) {
                locations.get(position).changeTit("wait");
                adp.notifyItemChanged(position);
            }

            @Override
            public void aceptar(int position) {
                id = Integer.valueOf(locations.get(position).getID());

                coordenadas.extraerEspCoordenada(id);

            }

            @Override
            public void rechazar(int position) {

            }
        });


    }

    public void verMapa() {

        Intent intent = new Intent(getApplicationContext(), MapsActivity2.class);
        intent.putExtra("id", id);
        intent.putExtra("idP", idP);
        intent.putExtra("direccion", mensj);
        intent.putExtra("xlatitud", coordenadas.getOlatitud());
        intent.putExtra("xlongitud", coordenadas.getOlongitud());
        intent.putExtra("latitud", latitudUs);
        intent.putExtra("longitud", longitudUs);
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
                    verMapa();


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

    public class Comunication {

        int IdPropio;
        Context context;
        String URL_instertar;
        String URL_extraer;
        String URL_cantM;
        String imagen;
        int cant;


        public Comunication(int idPropio, Context context, String URL_instertar, String URL_extraer, String URL_cantM) {
            IdPropio = idPropio;
            this.context = context;
            this.URL_instertar = URL_instertar;
            this.URL_extraer = URL_extraer;
            this.URL_cantM = URL_cantM;
        }

        public void insertarMensaje(final String usuarioDestino, final String mensaje, final String tipo) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_instertar, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(context, "Registrado correctamente", Toast.LENGTH_SHORT).show();
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
                    parametros.put("mensaje", mensaje);
                    parametros.put("usuarioOrigen", String.valueOf(IdPropio));
                    parametros.put("usuarioDestino", usuarioDestino);
                    parametros.put("tipo", tipo);
                    return parametros;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        }

        public void extraerMensaje(String tipe) {
            mensaje = new String[cant];
            nombres = new String[cant];
            foto = new String[cant];
            idMensaje = new int[cant];
            tipo = new int[cant];
            uOrigen = new int[cant];
            Toast.makeText(getApplicationContext(),String.valueOf("cant"),Toast.LENGTH_LONG).show();

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_extraer + "?usuarioDestino=" + IdPropio + "&tipo=" + tipe, new Response.Listener<JSONArray>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);
                            mensaje[i] = jsonObject.getString("mensaje");
                            nombres[i] = jsonObject.getString("nombre");
                            foto[i] = jsonObject.getString("foto");
                            tipo[i] = jsonObject.getInt("tipo");
                            idMensaje[i] = jsonObject.getInt("idMensaje");
                            uOrigen[i] = jsonObject.getInt("usuarioOrigen");
                        } catch (JSONException e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    visuali();

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


        public void cantMensajes(String tipe) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_cantM + "?usuarioDestino=" + IdPropio + "&tipo=" + tipe, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    cant = Integer.valueOf(response);
                    cantMen = cant;
                    if (cant > 0) {
                   //  Toast.makeText(getApplicationContext(),String.valueOf(cant),Toast.LENGTH_LONG).show();
                        extraerMensaje("2");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);

        }


        public void leido(int id) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://168.254.1.104/Sitio/leido.php?idMensaje=" + id, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        }

        public void extraerImagen(String idUsuario) {

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://168.254.1.104/Sitio/extraerImagen.php?idUsuario=" + idUsuario, new Response.Listener<JSONArray>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);
                            imagen = jsonObject.getString("foto");
                            nombre = jsonObject.getString("nombre");
                        } catch (JSONException e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    Toast.makeText(getApplicationContext(), imagen, Toast.LENGTH_LONG).show();

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


        public void setIdPropio(int idPropio) {
            IdPropio = idPropio;
        }

        public void setURL_instertar(String URL_instertar) {
            this.URL_instertar = URL_instertar;
        }

        public void setURL_extraer(String URL_extraer) {
            this.URL_extraer = URL_extraer;
        }

        public int getIdPropio() {
            return IdPropio;
        }

        public Context getContext() {
            return context;
        }

        public String getURL_instertar() {
            return URL_instertar;
        }

        public String getURL_extraer() {
            return URL_extraer;
        }

        public String getImagen() {
            return imagen;
        }

        public void setContext(Context context) {
            this.context = context;
        }

        public String getURL_cantM() {
            return URL_cantM;
        }

        public void setURL_cantM(String URL_cantM) {
            this.URL_cantM = URL_cantM;
        }

        public String[] getMensaje() {
            return mensaje;
        }


        public void setImagen(String imagen) {
            this.imagen = imagen;
        }

        public int[] getIdMensaje() {
            return idMensaje;
        }


        public int[] getTipo() {
            return tipo;
        }


        public int getCant() {
            return cant;
        }

        public int[] getuOrigen() {
            return uOrigen;
        }

        public void setCant(int cant) {
            this.cant = cant;
        }
    }
}