package com.example.mapas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amalbit.trail.Route;
import com.amalbit.trail.RouteOverlayView;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.ncorti.slidetoact.SlideToActView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    int PETICION_PERMISO_LOCALIZACION;
    RouteOverlayView rutas;
    Handler handler = new Handler();


    double glat, glon, xlat, xlon;
    int id;

    String[] mensaje;
    int[] idMensaje;
    int[] tipo;
    int[] uOrigen;
    int cantMens;
    int idP = 14;
    String distanciaViaje, precioViaje;

    LinearLayout lRespo;
    TextView textp, tdir, name;
    ImageView im2;
    RelativeLayout l1;
    EditText et1;
    LinearLayout l2;
    ProgressBar p1;
    SlideToActView sd1;
    CircularImageView c1;
    boolean flag = true;
    String nombren, imagenn;
    float dista;
    Button b4;
    float xd, x20p, lockRight, ancho;
    boolean posicionLateral = false;
    String origen, destino, fecha;

    LinearLayout layoutButtons;
    TextView distancia, miDir, CDir, respuesta;

    String[] ciudades = new String[58];
    String city;
    String direccionActual;
    LinearLayout lc, lperma;
    Button m1, m2;
    View b = null;
    List<LatLng> Route = new ArrayList<>();
    RelativeLayout slideL, dirLay;
    ImageView left, right;
    Button cancelB;
    TextView fechaM, distanciaRuta;
    Comunication comunication = new Comunication(10, this, "http://168.254.1.104/Sitio/insertar.php", "http://168.254.1.104/Sitio/extraerMensaje.php", "http://168.254.1.104/Sitio/cantMensajes.php");

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        rutas = findViewById(R.id.routeo);
        l1 = findViewById(R.id.solicitud2);
        et1 = findViewById(R.id.precio);
        lRespo = findViewById(R.id.respons);
        textp = findViewById(R.id.tetPrecio);
        im2 = findViewById(R.id.veh2);
        l2 = findViewById(R.id.topview);
        p1 = findViewById(R.id.prog);
        sd1 = findViewById(R.id.slide);
        c1 = findViewById(R.id.cardIm);
        name = findViewById(R.id.nombre);
        distancia = findViewById(R.id.distance);
        miDir = findViewById(R.id.miDir);
        CDir = findViewById(R.id.textDir);
        lperma = findViewById(R.id.topReq);
        layoutButtons = findViewById(R.id.layoutButtons);
        slideL = findViewById(R.id.slideLayout);
        dirLay = findViewById(R.id.direccionsL);
        left = findViewById(R.id.iLi);
        right = findViewById(R.id.iRi);
        cancelB = findViewById(R.id.canButt);
        respuesta = findViewById(R.id.respuesta);
        fechaM = findViewById(R.id.fecha);
        distanciaRuta = findViewById(R.id.distanciaRuta);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("id");
        idP = bundle.getInt("idP");
        origen = bundle.getString("origen");
        destino = bundle.getString("destino");
        xlat = bundle.getDouble("xlatitud");
        fecha = bundle.getString("fecha");
        xlon = bundle.getDouble("xlongitud");
        glat = bundle.getDouble("latitud");
        glon = bundle.getDouble("longitud");

        comunication.extraerImagen(String.valueOf(id));

        comunication.setIdPropio(idP);

        sd1.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView view) {
                respuesta(view);
            }
        });

        Resources resources = getResources();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        ancho = displayMetrics.widthPixels;
        lockRight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, resources.getDisplayMetrics());
        x20p = lockRight;
        lockRight = (float) (ancho * 0.83);

        slideL.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                xd = event.getRawX();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        lperma.setX(xd - lperma.getWidth());
                        break;

                    case MotionEvent.ACTION_UP:
                        animarLateral(posicionLateral, lperma);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        lperma.setBackground(getDrawable(R.drawable.box5));
                        dirLay.setBackground(getDrawable(R.drawable.box5));
                        cancelB.setBackground(getDrawable(R.drawable.box2));
                        break;

                    default:
                        return true;

                }
                return true;

            }
        });

        extraer();
        asignar();
    }

    public double distancia(double mlat, double mlon, double tlat, double tlon) {
        Location location = new Location("localizacion 1");
        location.setLatitude(mlat);  //latitud
        location.setLongitude(mlon); //longitud
        Location location2 = new Location("localizacion 2");
        location2.setLatitude(tlat);  //latitud
        location2.setLongitude(tlon); //longitud
        double distance = location.distanceTo(location2);
        return reducirDecimal(distance / 1000, 2);
    }

    private void animarLateral(boolean mostrar, LinearLayout layoutAnimado) {
        ObjectAnimator objectAnimator;
        if (posicionLateral)
            posicionLateral = false;
        else
            posicionLateral = true;

        if (mostrar) {
            left.setVisibility(View.VISIBLE);
            right.setVisibility(View.GONE);
            objectAnimator = ObjectAnimator.ofFloat(layoutAnimado, "X", x20p);
            objectAnimator.setDuration(300);
            objectAnimator.start();

        } else {
            left.setVisibility(View.GONE);
            right.setVisibility(View.VISIBLE);
            objectAnimator = ObjectAnimator.ofFloat(layoutAnimado, "X", -lockRight);
            objectAnimator.setDuration(300);
            objectAnimator.start();
            handler.postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void run() {
                    layoutAnimado.setBackground(getDrawable(R.drawable.tbox));
                    dirLay.setBackground(getDrawable(R.drawable.tbox));
                    cancelB.setBackground(getDrawable(R.drawable.tbox));
                }
            }, 250);


        }

    }

    public static Double reducirDecimal(double n, double nd) {
        return Math.round(n * Math.pow(10, nd)) / Math.pow(10, nd);
    }

    public void ponerPrecio(View view) {
        l1.setVisibility(View.VISIBLE);
        layoutButtons.setVisibility(View.GONE);

    }

    void asignar() {

        distanciaViaje = String.valueOf(distancia(xlat, xlon, glat, glon));
        distancia.setText("Distancia del trayecto: " + distanciaViaje + " km");

        miDir.setText(origen);
        CDir.setText(destino);
        if (java.util.Objects.equals(fecha, "")) {
            fechaM.setText(" ¡AHORA!");
        } else if (java.util.Objects.equals(fecha, "  "))
            fechaM.setText(" ¡AHORA!");
        else
            fechaM.setText(String.valueOf(fecha));


    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setMyLocationEnabled(true);

        MarkerOptions markerOptions = new MarkerOptions();
        MarkerOptions markerOptions1 = new MarkerOptions();
        LatLng latLng;
        LatLng latLng1;

        Route.add(new LatLng(xlat, xlon));
        Route.add(puntoMedio(xlat, xlon, glat, glon));
        Route.add(new LatLng(glat, glon));

        latLng = new LatLng(xlat, xlon);
        latLng1 = new LatLng(glat, glon);
        markerOptions.position(latLng);
        markerOptions1.position(latLng1);
        mMap.addMarker(markerOptions);
        mMap.addMarker(markerOptions1);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));


        mMap.setOnCameraMoveListener(() ->
        {
            rutas.onCameraMove(googleMap.getProjection(), googleMap.getCameraPosition());
        });


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                drawRoutes();
            }
        }, 2500);

        // Add a marker in Sydney and move the camera

    }

    public void moneda(View view) {

        String text = et1.getText().toString();
        if (text.indexOf("MN") == -1 && text.indexOf("Mn") == -1 && text.indexOf("mN") == -1 && text.indexOf("mn") == -1 &&
                text.indexOf("cuc") == -1 && text.indexOf("cuC") == -1 && text.indexOf("cCc") == -1 && text.indexOf("cUC") == -1 &&
                text.indexOf("Cuc") == -1 && text.indexOf("CuC") == -1 && text.indexOf("CUc") == -1 && text.indexOf("CUC") == -1) {
            sd1.setLocked(true);
            new AlertDialog.Builder(MapsActivity2.this)
                    .setMessage("Introduzca la moneda (cuc o mn)")
                    .setCancelable(false)
                    .setNegativeButton(
                            "Entendido", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
        } else if (sd1.isLocked())
            sd1.setLocked(false);

    }


    void extraer() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (flag)
                    comunication.cantMensajes("1");

                handler.postDelayed(this, 7000);
            }
        }, 1);


    }

    public void mostrar() {

        lRespo.setVisibility(View.VISIBLE);

        if (Objects.equal(mensaje[0], " A ")) {
            respuesta.setText("El cliente ha aceptado");
            if (!Objects.equal(fecha, " ¡AHORA!")) {
                TabBooking.addReserva(imagenn, obtenerFecha(fecha), nombren, origen, destino, fecha.substring(11));
            }
            comunication.insertarEstadistica();
        } else
            respuesta.setText("El cliente ha rechazado ");
        comunication.leido(idMensaje[0]);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                lRespo.setVisibility(View.GONE);
                flag = true;
            }
        }, 5000);


    }

    String dayOfWeek(int D, int M, int A) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(A, M - 1, D);
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                return "Lun.";
            case Calendar.TUESDAY:
                return "Mar.";
            case Calendar.WEDNESDAY:
                return "Mié.";
            case Calendar.THURSDAY:
                return "Jue.";
            case Calendar.FRIDAY:
                return "Vie.";
            case Calendar.SATURDAY:
                return "Sáb.";
            case Calendar.SUNDAY:
                return "Dom.";
            default:
                return null;

        }
    }

    String month(int M) {
        switch (M) {
            case 1:
                return "Ene.";
            case 2:
                return "Feb.";
            case 3:
                return "Mar.";
            case 4:
                return "Abr.";
            case 5:
                return "May.";
            case 6:
                return "Jun.";
            case 7:
                return "Jul.";
            case 8:
                return "Ago.";
            case 9:
                return "Sept.";
            case 10:
                return "Oct.";
            case 11:
                return "Nov.";
            case 12:
                return "Dic.";
            default:
                return null;

        }
    }

    int obtenerDia(String fecha) {
        int D = Integer.valueOf(fecha.substring(0, 2));
        return D;
    }

    int obtenerMes(String fecha) {
        int D = Integer.valueOf(fecha.substring(3, 5));
        return D;
    }

    int obtenerAño(String fecha) {
        int D = Integer.valueOf(fecha.substring(6, 10));
        return D;
    }

    String obtenerFecha(String data) {
        String fecha = data.substring(0, 10);
        return dayOfWeek(obtenerDia(fecha), obtenerMes(fecha), obtenerAño(fecha)) + " " + obtenerDia(fecha) + " " + month(obtenerMes(fecha)) + " " + obtenerAño(fecha);
    }

    public LatLng puntoMedio(double lat1, double lon1, double lat2, double lon2) {
        double latm, lonm;
        LatLng latLng;
        latm = (lat1 + lat2) / 2;
        lonm = (lon1 + lon2) / 2;
        latLng = new LatLng(latm, lonm);
        return latLng;
    }


    private void drawRoutes() {

        com.amalbit.trail.Route arcOverlayPolyline = new Route.Builder(rutas)
                .setRouteType(RouteOverlayView.RouteType.ARC)
                .setCameraPosition(mMap.getCameraPosition())
                .setProjection(mMap.getProjection())
                .setLatLngs(Route)
                .setBottomLayerColor(Color.GRAY)
                .setTopLayerColor(Color.BLACK)
                .setRouteShadowColor(Color.GRAY)
                .create();

    }

    public void Cancelar(View view) {
        comunication.insertarMensaje(String.valueOf(id), "123", "1");
        super.onBackPressed();
    }

    public void respuesta(View view) {
        precioViaje = et1.getText().toString();
        comunication.insertarMensaje(String.valueOf(id), precioViaje, "1");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                l1.setVisibility(View.GONE);
                sd1.resetSlider();
            }
        }, 1120);

        InputMethodManager im = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void Minim(View view) {
        lperma.setVisibility(View.GONE);
    }

    public void Maxim(View view) {
        lperma.setVisibility(View.VISIBLE);
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
                    parametros.put("fecha", "null");
                    return parametros;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        }

        public void insertarEstadistica() {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://168.254.1.104/Sitio/insertarEstad.php", new Response.Listener<String>() {
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
                    parametros.put("idUsuario", String.valueOf(IdPropio));
                    parametros.put("idCliente", String.valueOf(id));
                    parametros.put("ubicacionCliente", origen);
                    parametros.put("destinoCliente", destino);
                    parametros.put("distanciaViaje", distanciaViaje);
                    parametros.put("precio", precioViaje);
                    return parametros;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        }

        public void extraerMensaje(String tipe) {
            mensaje = new String[cant];
            idMensaje = new int[cant];
            tipo = new int[cant];
            uOrigen = new int[cant];


            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_extraer + "?usuarioDestino=" + IdPropio + "&tipo=" + tipe, new Response.Listener<JSONArray>() {
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

                    mostrar();


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

        public void cantMensajes(String tipe) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_cantM + "?usuarioDestino=" + IdPropio + "&tipo=" + tipe, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    cant = Integer.valueOf(response);
                    cantMens = cant;
                    if (cant > 0) {
                        Toast.makeText(context, String.valueOf(cant), Toast.LENGTH_LONG).show();
                        extraerMensaje("1");
                        flag = false;
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

        public void extraerImagen(String idUsuario) {

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://168.254.1.104/Sitio/extraerImagen.php?idUsuario=" + idUsuario, new Response.Listener<JSONArray>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);
                            imagenn = jsonObject.getString("foto");
                            nombren = jsonObject.getString("nombre");
                        } catch (JSONException e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    Picasso.get().load(imagenn).resize(500, 500).centerCrop().into(c1);
                    name.setText(nombren);

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


    }


}
