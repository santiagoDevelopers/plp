package com.example.mapas;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Solicitud extends AppCompatActivity {


    double[] idUsuario;
    double[] latitud;
    double[] longitud;
    int cant = 10;


    int ID = 10, idMensajeActual, value;
    String mensaje;
    double mlat, mlon, tlat, tlon;
    Button b1, b2;
    TextView textView, textView2;
    private final int TIEMPO = 1500;
    private final int TESPERA = 600;
    Handler handler = new Handler();
    String CHANNEL_ID = "1";
    int notificationId = 2;
    private static final String KEY_TEXT_REPLY = "key_text_reply";



    Comunication comunication = new Comunication(10, this, "http://168.254.1.104/Sitio/insertar.php", "http://168.254.1.104/Sitio/buscar.php","http://168.254.1.104/Sitio/cantMensajes.php");
    Coordenadas coordenadas = new Coordenadas(10, 1, "http://192.168.49.199/Sitio/insertarCoordenada.php",
            "http://192.168.49.199/Sitio/extraerEspeCoordenada.php", "http://192.168.49.199/Sitio/extraerCoorCerc.php","http://192.168.49.199/Sitio/extraerDrivCoord.php", this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud);


        b1 = findViewById(R.id.miUbicacion);
        b2 = findViewById(R.id.verMapa);
        textView = findViewById(R.id.tt);
        textView2 = findViewById(R.id.tr);
        Context context = getApplicationContext();






        //preparation();


        createNotificationChannel();
        //ejecutarTarea();
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        idMensajeActual = sharedPref.getInt("idMensaje", 0);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Comparta su ubicacion luego de aparecer en el mapa..", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Solicitud.this, MapsActivity.class);
                startActivity(intent);
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });


    }

    public void preparation() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {



                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                }, TESPERA);

                handler.postDelayed(this, 30000);
            }
        }, 1);


    }


    public void ejecutarTarea() {

        handler.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
            @Override
            public void run() {
                comunication.extraerMensaje();

                if (mensaje != null && value != idMensajeActual)
                    Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();

                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.commit();
                handler.postDelayed(this, TIEMPO);
            }
        }, TIEMPO);

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    public void notification() {
        Intent fullScreenIntent = new Intent(this, MapsActivity.class);
        fullScreenIntent.putExtra("txt", mensaje);
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0,
                fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        String replyLabel = getResources().getString(R.string.reply_label);
        RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel(replyLabel)
                .build();


        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(R.drawable.ic_launcher_foreground,
                        getString(R.string.label), fullScreenPendingIntent)
                        .build();

        NotificationCompat.Action action2 =
                new NotificationCompat.Action.Builder(R.drawable.ic_launcher_foreground,
                        getString(R.string.label2), fullScreenPendingIntent)
                        .build();


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(mensaje))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("My notification")
                .setPriority(5)
                .addAction(action)
                .addAction(action2)
                .setFullScreenIntent(fullScreenPendingIntent, true)
                .setAutoCancel(true)
                .setTimeoutAfter(5000)
                .setContentText("Hello World!");


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());


    }


    @SuppressLint("WrongConstant")
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_MAX;
            NotificationChannel channel;
            channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    ////////CLASE COMUNICATION////////////////////////////
    public class Comunication {

        int IdPropio;
        Context context;
        String URL_instertar;
        String URL_extraer;
        String URL_cantM;
        String[] mensaje;
        String imagen;
        int[] idMensaje;
        int[] tipo;
        int[] uOrigen;
        int cant;


        public Comunication(int idPropio, Context context, String URL_instertar, String URL_extraer, String URL_cantM) {
            IdPropio = idPropio;
            this.context = context;
            this.URL_instertar = URL_instertar;
            this.URL_extraer = URL_extraer;
            this.URL_cantM = URL_cantM;
        }

        public void insertarMensaje(final String usuarioDestino, final String mensaje, final String tipo) {
            StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, URL_instertar, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(context, "Registrado correctamente", Toast.LENGTH_SHORT).show();
                }
            }, new com.android.volley.Response.ErrorListener() {
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

        public void extraerMensaje() {
            mensaje = new String[cant];
            idMensaje = new int[cant];
            tipo = new int[cant];
            uOrigen = new int[cant];


            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_extraer + "?usuarioDestino=" + IdPropio + "", new com.android.volley.Response.Listener<JSONArray>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);
                            mensaje[i] = jsonObject.getString("mensaje");
                            tipo[i] = jsonObject.getInt("tipo");
                            idMensaje[i] = jsonObject.getInt("idMensaje");
                            uOrigen[i] = jsonObject.getInt("usuarioOrigen");
                        } catch (JSONException e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(jsonArrayRequest);

        }


        public void cantMensajes() {
            StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, URL_cantM + "?usuarioDestino=" + IdPropio, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    cant = Integer.valueOf(response);
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);

        }

        public void extraerImagen(String idUsuario) {

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://168.254.1.104/Sitio/extraerImagen.php?idUsuario=" + idUsuario, new com.android.volley.Response.Listener<JSONArray>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);
                            imagen = jsonObject.getString("foto");
                        } catch (JSONException e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                }
            }, new com.android.volley.Response.ErrorListener() {
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

        public void setMensaje(String[] mensaje) {
            this.mensaje = mensaje;
        }

        public void setImagen(String imagen) {
            this.imagen = imagen;
        }

        public int[] getIdMensaje() {
            return idMensaje;
        }

        public void setIdMensaje(int[] idMensaje) {
            this.idMensaje = idMensaje;
        }

        public int[] getTipo() {
            return tipo;
        }

        public void setTipo(int[] tipo) {
            this.tipo = tipo;
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


    /////CLASE COORDENADAS////////////

    public class Coordenadas {
        int IdPropio;
        int tipoUsurario;
        int cantDrivers;
        String URL_subir;
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

            StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, URL_subir, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(context, "Subido correctamente", Toast.LENGTH_SHORT).show();
                }
            }, new com.android.volley.Response.ErrorListener() {
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

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_bajarRadio + "?radio=" + radio, new com.android.volley.Response.Listener<JSONArray>() {
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
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(jsonArrayRequest);


        }

        public void extraerEspCoordenada(int idUsuario) {

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_bajarUn, new com.android.volley.Response.Listener<JSONArray>() {
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
            }, new com.android.volley.Response.ErrorListener() {
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
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_bajarRadio + "?radio=" + radio, new com.android.volley.Response.Listener<JSONArray>() {
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
                    extraerDrivCoordenadas(radio);

                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(jsonArrayRequest);
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


        public double[] getLatitud() {
            return latitud;
        }


        public double[] getLongitud() {
            return longitud;
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
