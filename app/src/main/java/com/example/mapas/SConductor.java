package com.example.mapas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.PaintKt;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.service.autofill.Dataset;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.db.williamchart.Painter;
import com.db.williamchart.animation.ChartAnimation;
import com.db.williamchart.data.DataPoint;
import com.db.williamchart.data.Frame;
import com.db.williamchart.data.Label;
import com.db.williamchart.extensions.CanvasExtKt;
import com.db.williamchart.renderer.BarChartRenderer;
import com.db.williamchart.view.BarChartView;
import com.db.williamchart.view.HorizontalBarChartView;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.maps.model.Circle;
import com.google.android.material.tabs.TabLayout;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.nex3z.notificationbadge.NotificationBadge;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import kotlin.jvm.functions.Function0;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class SConductor extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    int cantFilas;
    int[] idCliente;
    String[] ubicacionCliente;
    String[] destinoCliente;
    double[] distanciaViaje;
    double[] newdistanciaViaje;
    double[] precio;
    double[] newprecio;
    String[] fecha;
    int[] newfecha;
    int[] cantidadViajes;
    int newSize;
    int cantUsuarios;
    boolean first = true;
    String[] ciudades = new String[58];
    double longitudeNetwork, latitudeNetwork;
    int PETICION_PERMISO_LOCALIZACION;
    LocationManager locationManager;
    int cantMen = 0;
    String[] mensaje;
    String[] nombres;
    String[] foto;
    int[] idMensaje;
    int[] tipo;
    int[] uOrigen;
    String mensj;
    int idP;
    String fechaR, origen, destino;
    boolean flag = true;
    Context context;
    double Olatitud, Olongitud;
    int IdPropio = 14;
    LineChart chartDinero;
    LineChart chartDistancia;
    LineChart chartViajes;
    String idPropio = "14";
    CircularImageView face, vehc;
    ViewPager viewPager;
    CustomMarkerView mv;
    Paint paint = new Paint();
    TextView usuariosOnline, ubicacion;
    Handler handler = new Handler();
    NotificationBadge notificationBadge;
    ImageView menu;
    ArrayList<Reservas> reservas = new ArrayList<>();
    MeowBottomNavigation bottomNavigation;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_s_conductor);
        face = findViewById(R.id.faceImage);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mv = new CustomMarkerView(getApplicationContext(), R.layout.marker, displayMetrics.widthPixels - 50);
        Picasso.get().load("http://168.254.1.104/Sitio//fotos/11.png").resize(500, 500).centerCrop().into(face);
      /*TabLayout tabLayout = findViewById(R.id.tablayoutC);
        tabLayout.addTab(tabLayout.newTab().setText("Motos"));
        tabLayout.addTab(tabLayout.newTab().setText("autos"));
        tabLayout.addTab(tabLayout.newTab().setText("reservas"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);*/
        viewPager = findViewById(R.id.pagerC);
        // reservas.add(new Reservas("http://168.254.1.104/Sitio//fotos/11.png", "10/08/2020 12:30 pm", "Thalia Sanchez", "Cornelio Robert, Santiago de Cuba", "Cuqui Bosch, Santiago de Cuba"));
        // reservas.add(new Reservas("http://168.254.1.104/Sitio//fotos/10.png", "10/08/2020 12:30 pm", "Yilian Nieves", "Cornelio Robert, Santiago de Cuba", "Cuqui Bosch, Santiago de Cuba"));
        PageAdapterConductor pageAdapter = new PageAdapterConductor(getSupportFragmentManager(), 3, this);
        viewPager.setAdapter(pageAdapter);
        chartDinero = findViewById(R.id.chartPrecio);
        chartDistancia = findViewById(R.id.chartDistancia);
        chartViajes = findViewById(R.id.chartViajes);
        menu = findViewById(R.id.menu);
        usuariosOnline = findViewById(R.id.cantidadUsuarios);
        ubicacion = findViewById(R.id.ubicacion);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        bottomNavigation = findViewById(R.id.bottomNav);
        viewPager.setOffscreenPageLimit(3);

        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic__87__chart));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.solicitudes));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.calendar_black));
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                // your codes
                viewPager.setCurrentItem(item.getId() - 1);
            }
        });


        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                switch (item.getId()) {
                    case 1:
                        viewPager.setCurrentItem(0);
                        break;
                    case 2:
                        viewPager.setCurrentItem(1);
                        break;
                    case 3:
                        viewPager.setCurrentItem(2);
                        break;
                }
                // your codes
            }
        });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                // your codes
            }
        });
        bottomNavigation.show(1, true);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigation.show(1, true);
                        break;
                    case 1:
                        bottomNavigation.show(2, true);
                        break;
                    case 2:
                        bottomNavigation.show(3, true);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        // prueba();

        cantUsuariosOnline("1");
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PETICION_PERMISO_LOCALIZACION);

        toggleNetworkUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewPager.getAdapter().notifyDataSetChanged();
    }

    public void menuC(View view) {
        PopupMenu menu = new PopupMenu(SConductor.this, view);
        menu.getMenuInflater().inflate(R.menu.menu_conductores, menu.getMenu());

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return true;
            }
        });

        menu.show();

    }
    int devolverIdPropio() {
        return Integer.valueOf(idPropio);
    }


    void addCounter(int id, String value) {
        bottomNavigation.setCount(id, value);
    }


    ArrayList<Reservas> arreglo() {

        return reservas;
    }


    void clearCounter(int id) {
        bottomNavigation.clearCount(id);
    }

    public void cantUsuariosOnline(String tipe) {
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, "http://168.254.1.104/Sitio/cantUsuarios.php?tipoUsuario=" + tipe, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                cantUsuarios = Integer.valueOf(response);
                if (cantUsuarios > 0) {
                    //Toast.makeText(getApplicationContext(), String.valueOf(cantUsuarios), Toast.LENGTH_SHORT).show();
                    usuariosOnline.setText(String.valueOf(cantUsuarios));
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    public void obtenerDireccionCompleta(double lat, double lon) {

        ciudades = new String[]{"La Habana", "Santiago de Cuba", "Camagüey", "Holguín", "Guantánamo", "Santa Clara", "Las Tunas", "Bayamo", "Cienfuegos", "Pinar del Río", "Matanzas"
                , "Ciego de Ávila", "Sancti Spíritus", "Manzanillo", "Cárdenas", "Palma Soriano", "Moa", "Morón", "Florida", "Contramaestre", "Artemisa", "Nueva Gerona", "Trinidad"
                , "Colón", "Baracoa", "Güines", "Placetas", "Nuevitas", "Sagua la Grande", "San José de las Lajas", "Banes", "San Luis", "Puerto Padre", "San Antonio de los Baños"
                , "Caibarién", "Cabaiguán", "Mayarí", "San Cristóbal", "Vertientes", "Jagüey Grande", "Consolación del Sur", "Jovellanos", "Amancio", "Güira de Melena", "Cumanayagua"
                , "Jatibonico", "Niquero", "San Germán", "Sagua de Tánamo", "Bauta", "La Maya", "Guanajay", "Colombia", "Jiguaní", "Manicaragua", "Camajuaní", "Guisa", "Jobabo"};

        OkHttpClient client = new OkHttpClient();
        String url = "https://maps.google.com/?q=" + String.valueOf(lat) + "," + String.valueOf(lon);

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {
                try {
                    final String myresponse = response.body().string();
                    SConductor.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String city = null;
                            for (int i = 0; i < 58; i++) {
                                if (myresponse.indexOf(ciudades[i]) != -1) {
                                    city = ciudades[i];
                                }
                            }
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
                            ubicacion.setText(String.valueOf(myresponse.substring(p - s, p - 2)) + ", " + city);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public void toggleNetworkUpdates() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 6 * 1000, 1, locationListenerNetwork);


    }

    public void preparetion() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                cantMensajes("2");


                handler.postDelayed(this, 5000);
            }
        }, 1);
    }


    public void leido(int id) {
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, "http://168.254.1.104/Sitio/leido.php?idMensaje=" + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void extraerMensaje(String tipe) {
        mensaje = new String[cantMen];
        nombres = new String[cantMen];
        foto = new String[cantMen];
        fecha = new String[cantMen];
        idMensaje = new int[cantMen];
        tipo = new int[cantMen];
        uOrigen = new int[cantMen];
        Toast.makeText(context, String.valueOf("cant"), Toast.LENGTH_LONG).show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://168.254.1.104/Sitio/extraerMensaje.php?usuarioDestino=" + IdPropio + "&tipo=" + tipe, new Response.Listener<JSONArray>() {
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
                        fecha[i] = jsonObject.getString("fecha");
                        tipo[i] = jsonObject.getInt("tipo");
                        idMensaje[i] = jsonObject.getInt("idMensaje");
                        uOrigen[i] = jsonObject.getInt("usuarioOrigen");
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

    public void cantMensajes(String tipe) {
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, "http://168.254.1.104/Sitio/cantMensajes.php?usuarioDestino=" + IdPropio + "&tipo=" + tipe, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                cantMen = Integer.valueOf(response);
                if (cantMen > 0) {
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

    private final LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeNetwork = location.getLongitude();
            latitudeNetwork = location.getLatitude();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //if(10 == location.getAccuracy())
                    Toast.makeText(getApplicationContext(), String.valueOf(location.getAccuracy()), Toast.LENGTH_LONG).show();
                    //obtenerDireccionCompleta(latitudeNetwork, longitudeNetwork);
                    //ubicacion.setText(String.valueOf(latitudeNetwork)+String.valueOf(longitudeNetwork));
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

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }
}
