package com.example.mapas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AppComponentFactory;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.view.animation.AnimationUtils;

import com.airbnb.lottie.LottieAnimationView;
import com.amalbit.trail.Route;
import com.amalbit.trail.RouteOverlayView;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.wrappers.PackageManagerWrapper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.ncorti.slidetoact.SlideToActView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.CharacterData;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    int PETICION_PERMISO_LLAMADA;
    String phoneNumber = "54079796";
    CardView searchButt;
    String[] mensaje;
    String[] nombress;
    String[] imagenes;
    int[] idMensaje;
    String[] tipo;
    int[] uOrigen;
    double[] idUsuario;
    double[] latitud;
    double[] longitud;
    int cant, fase, driver;
    double radio = 5, latBookOrig, lonBookOrig, lonBookDest, latBookDest;
    boolean first = true, fromBooking, fromBookingOrigen = false, fromBookingDestino = false;
    private LottieAnimationView sendMes;
    DatePicker datePicker;
    TimePicker timePicker;
    final ArrayList<Cardf> locations = new ArrayList<>();
    final ArrayList<Direcc> locations2 = new ArrayList<>();
    final ArrayList<BusquedaReciente> listBusquedas = new ArrayList<>();
    private static String TAG = "OverlayRouteActivity";


    private MapStyleOptions mapStyle;

    private List<LatLng> mRoute;

    private RouteOverlayView mRouteOverlayView;

    String title, choferReserva, nombreChoferReserva = "Juan Daniel Nieves";
    String direccion;
    Button b1;
    RelativeLayout r1;
    LinearLayout l1;
    RelativeLayout r2;
    private static int Timer = 1750;
    private final int TESPERA = 600;
    LinearLayout l2, l3;
    CircularImageView im1, im3;
    private GoogleMap mMap;
    TextView tv1, tv2;
    EditText et1;
    Marker marcador;
    double mlat, mlon, tlat, tlon;
    int PETICION_PERMISO_LOCALIZACION;
    Animation an1;
    RelativeLayout relativeLayout;
    Handler handler = new Handler();
    Button b2, b3;
    Marker marker1 = null;

    boolean flag = true;
    RouteOverlayView rutas;
    Bitmap imagen = null;
    RelativeLayout lRespo, Reserva;
    TextView textp;
    ImageView im2;
    RelativeLayout p1;
    int idP;
    String nombres;
    int n = 0;

    RecyclerView vista1;
    RecyclerView vista2;
    RecyclerView busqedas;
    adapter2 adp;
    adapter3 adp2;
    adapterBusquedas adapterBusquedas;
    int id;
    Bitmap[] caras;
    String faceS;

    RelativeLayout notificacionRespuestas;
    String[] ciudades = new String[58];
    String city;

    List<LatLng> Route = new ArrayList<>();

    int cantMens;
    String direccionActual;
    LinearLayout l5;
    int aux = 0;
    int pos = 0;
    Bitmap face = null;
    LinearLayout topR;
    LinearLayout coincidencias;
    LinearLayout road;
    RelativeLayout esp;
    TextView nombre;
    boolean prosc = false;
    boolean search = false;
    String tit;
    TextView distancia, mDireccion, DDirec;
    TextView distancia2, mDireccion2, DDirec2;
    TextView rNombre;
    HorizontalScrollView scrollView;
    double distance;
    SlideToActView sd1;
    LinearLayout barra;
    View b;
    int UltimaFase;
    int FaseAnterior;

    String miUbicacion = "Cornelio Robert, Santiago de Cuba";
    LinearLayout rechazo;
    String texto;
    int markadores;

    String[] Coords = new String[50];

    adapter4 adp3;
    RecyclerView vista3;
    final ArrayList<RespConduct> locations3 = new ArrayList<>();


    int cont = 0;
    int y;
    int h;
    int posMensaje = 0;
    String s2;

    int aux2 = 0;
    int vuelta = 0;

    boolean flagCoinc = true;
    boolean vez = true;
    boolean DireccionExacta = false;
    boolean cancel = false;
    boolean flag2 = true;

    ImageView V;

    TextView cantR;

    ViewPager viewPager;
    LinearLayout espButtons, hideLayout;
    float anchoPantalla;

    boolean rechazarTodos = false;
    boolean tracking = false;
    boolean adding = false;
    double distanciaTotal;
    Marker trackMarker;
    int vehiculo;
    float y20p, y50p, yd, xd, x20p;
    float alto, ancho;
    float posAnt, top, lockRight;
    boolean posicion = false;
    Resources resources;
    RelativeLayout relativeBuscar;
    ImageView iDown, iUp;
    RelativeLayout sLayout;
    ImageView iLeft, iRight, iFechaHora;
    boolean posicionLateral = false;
    LinearLayout direccLayout, establecerLugFav;
    RelativeLayout icSolic;
    CircularImageView cantSol;
    ImageButton tVehc;
    RelativeLayout deleteLay;
    boolean showDlete = true;
    EditText et2, et3;
    Button btnReserva;
    TextView textReserva, direccionEstabl;
    RelativeLayout destinoReserva, fechaReserva;
    String titlLugar, direccionLugar;
    private ImageView iconEditTextBusar;

    private Button clearHistorial;

    ScrollView scrollView1;
    RelativeLayout hideSearchDir;
    Comunication comunication = new Comunication(20, this, "http://168.254.1.104/Sitio/insertar.php", "http://168.254.1.104/Sitio/extraerMensaje.php", "http://168.254.1.104/Sitio/cantMensajes.php");
    Coordenadas coordenadas = new Coordenadas(20, 1, "http://168.254.1.104/Sitio/insertarCoordenada.php",
            "http://168.254.1.104/Sitio/extraerEspeCoordenada.php", "http://168.254.1.104/Sitio/extraerCoorCercAll.php", "http://168.254.1.104/Sitio/extraerDrivCoord.php", this);

    public MapsActivity() {
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        r1 = findViewById(R.id.openn);
        et1 = findViewById(R.id.dir);
        tv1 = findViewById(R.id.tv1);
        l2 = findViewById(R.id.topview);
        DDirec = findViewById(R.id.textDir);
        DDirec2 = findViewById(R.id.textDir2);
        r2 = findViewById(R.id.box2);
        b2 = findViewById(R.id.motoMasCercana);
        rutas = findViewById(R.id.routeo);
        im1 = findViewById(R.id.veh);
        im3 = findViewById(R.id.foto_vehc);
        l3 = findViewById(R.id.chapa);
        lRespo = findViewById(R.id.respons);
        textp = findViewById(R.id.tetPrecio);
        im2 = findViewById(R.id.veh2);
        p1 = findViewById(R.id.prog);
        vista2 = findViewById(R.id.concidd);
        vista3 = findViewById(R.id.respCond);
        l5 = findViewById(R.id.requests);
        b3 = findViewById(R.id.solc);
        nombre = findViewById(R.id.name);
        distancia = findViewById(R.id.distance);
        distancia2 = findViewById(R.id.distance2);
        mDireccion = findViewById(R.id.miDir);
        mDireccion2 = findViewById(R.id.miDir2);
        topR = findViewById(R.id.topReq);
        esp = findViewById(R.id.esperando);
        rNombre = findViewById(R.id.rNombre);
        coincidencias = findViewById(R.id.coincidencias);
        road = findViewById(R.id.camino);
        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Motos"));
        tabLayout.addTab(tabLayout.newTab().setText("autos"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = findViewById(R.id.pager);
        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), this);
        viewPager.setAdapter(pageAdapter);
        V = findViewById(R.id.vehiculo);
        iconEditTextBusar = findViewById(R.id.icono2);
        searchButt = findViewById(R.id.felcha);
        espButtons = findViewById(R.id.espVehc);
        rechazo = findViewById(R.id.solicitudRechazada);
        notificacionRespuestas = findViewById(R.id.cantRespuestas);
        cantR = findViewById(R.id.cantidadResp);
        hideLayout = findViewById(R.id.hideLayout);
        hideSearchDir = findViewById(R.id.hideDown);
        relativeBuscar = findViewById(R.id.editBuscar);
        iDown = findViewById(R.id.down);
        iUp = findViewById(R.id.up);
        iRight = findViewById(R.id.iconRigh);
        iLeft = findViewById(R.id.iconLeft);
        sLayout = findViewById(R.id.ssLayout);
        direccLayout = findViewById(R.id.direccLayout);
        icSolic = findViewById(R.id.icSolicitud);
        tVehc = findViewById(R.id.vehSolic);
        cantSol = findViewById(R.id.cantSolicitudes);
        et3 = findViewById(R.id.direccionOrigenReserva);
        et2 = findViewById(R.id.direccionDestinoReserva);
        textReserva = findViewById(R.id.fechaReserva);
        btnReserva = findViewById(R.id.btnReserva);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        iFechaHora = findViewById(R.id.iReservaFecha);
        destinoReserva = findViewById(R.id.Reserva);
        fechaReserva = findViewById(R.id.Reserva2);
        busqedas = findViewById(R.id.busqquedasRecientes);
        clearHistorial = findViewById(R.id.clearHistorial);
        sendMes = findViewById(R.id.sendMesj);
        establecerLugFav = findViewById(R.id.layLugFav);
        direccionEstabl=findViewById(R.id.direccionEstablecida);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_motorcycle_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_directions_car_24dp);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


       
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                int i = tab.getPosition();
                switch (i) {
                    case 0:
                        tab.setIcon(R.drawable.ic_motorcycle_black_24dp);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.ic_directions_car_black_24dp);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int i = tab.getPosition();
                switch (i) {
                    case 0:
                        tab.setIcon(R.drawable.ic_motorcycle_24dp);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.ic_directions_car_24dp);
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        searchButt.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(30);

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            searchButt.setCardElevation(0);
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            searchButt.setCardBackgroundColor(getColor(R.color.orangeP));
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        searchButt.setCardElevation(14f);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            searchButt.setCardBackgroundColor(getColor(R.color.orange));
                        }
                        sendDir(b);
                        break;

                    default:
                        return true;

                }
                return true;
            }
        });
        resources = getResources();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        alto = displayMetrics.heightPixels;
        ancho = displayMetrics.widthPixels;
        y20p = (float) (alto * 0.958);
        //  x20p = (float) (ancho * 0.959);


        lockRight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, resources.getDisplayMetrics());
        x20p = lockRight;
        lockRight = (float) (ancho * 0.83);
        Toast.makeText(getApplicationContext(), String.valueOf(lockRight), Toast.LENGTH_SHORT).show();

        hideSearchDir.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                yd = event.getRawY();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:

                        if (yd < y20p && yd > top)
                            hideLayout.setY(yd);
                        break;

                    case MotionEvent.ACTION_UP:
                        animar(posicion, hideLayout);
                        break;

                    default:
                        return true;

                }
                return true;
            }
        });

        sLayout.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                xd = event.getRawX();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:

                        if ((xd - topR.getWidth()) < x20p && (xd - topR.getWidth()) > -lockRight)
                            topR.setX(xd - topR.getWidth());
                        break;

                    case MotionEvent.ACTION_UP:
                        animarLateral(posicionLateral, topR, sLayout);
                        break;

                    case MotionEvent.ACTION_DOWN:
                        topR.setBackground(getDrawable(R.drawable.box5));
                        direccLayout.setBackground(getDrawable(R.drawable.box5));
                        break;

                    default:
                        return true;

                }
                return true;
            }
        });


        tVehc.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Fase10(v);
                mostrar();
                animarX(icSolic, -250);
                top = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 355f, resources.getDisplayMetrics());
                top = alto - top;
                lRespo.setVisibility(View.VISIBLE);

            }
        });



/*
        et1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                posAnt=hideLayout.getHeight();

                Toast.makeText(getApplicationContext(),String.valueOf(hideLayout.getHeight()),Toast.LENGTH_SHORT).show();
                if(hasFocus){
                ObjectAnimator objectAnimator;
                objectAnimator = ObjectAnimator.ofFloat(hideLayout,"Y",alto-r2.getHeight()-200);
                objectAnimator.setDuration(300);
                objectAnimator.start();}
                else{
                    ObjectAnimator objectAnimator;
                    objectAnimator = ObjectAnimator.ofFloat(hideLayout,"Y",alto);
                    objectAnimator.setDuration(300);
                    objectAnimator.start();}


            }
        });
*/
        sendMes.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                sendMes.setVisibility(View.GONE);
                fechaReserva.setVisibility(View.GONE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


        Bundle bundle = getIntent().getExtras();
        titlLugar = bundle.getString("titl_lug");
        direccionLugar = bundle.getString("direccion");
        idP = bundle.getInt("id");
        fase = bundle.getInt("fase");
        mlat = bundle.getDouble("mlatitud");
        mlon = bundle.getDouble("mlongitud");
        choferReserva = String.valueOf(bundle.getInt("driver"));
        nombreChoferReserva = bundle.getString("nombreDriver");
        comunication.setIdPropio(idP);
        coordenadas.setIdPropio(idP);


        asignarMiUbicacion(b);
        preparation();
        Recycler2();
        Recycler();
        RecyclerBusquedasRecientes();
        extraer();

        switch (fase) {
            case 1://Normal
                Fase1(b);
                fromBooking = false;
                adding = false;
                break;
            case 7:
                Fase7(b);// Booking
                fromBooking = true;
                adding = false;
                break;
            case 2:
                Fase2(b);//
                fromBooking = false;
                adding = true;
                break;
            case 3:
                Fase3(b);
                DDirec.setText(direccionLugar);
                fromBooking = false;
                adding = false;
                break;
            default:
                break;
        }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        LocationServices.getFusedLocationProviderClient(this)
                .getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    mlat = location.getLatitude();
                    mlon = location.getLongitude();

                    //obtenerCiudad(mlat, mlon);
                    //INSERTAR LAS COORDENADAS AQUIII
                    coordenadas.insertarCoordenadas(mlat, mlon);

                }
            }
        });

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CALL_PHONE}, PETICION_PERMISO_LLAMADA
        );
    }

    public void asignarMiUbicacion(View view) {
        et3.setText(miUbicacion);
    }

    private void animar(boolean mostrar, LinearLayout layoutAnimado) {
        ObjectAnimator objectAnimator;
        if (posicion)
            posicion = false;
        else
            posicion = true;

        if (mostrar) {
            iDown.setVisibility(View.VISIBLE);
            iUp.setVisibility(View.GONE);
            objectAnimator = ObjectAnimator.ofFloat(layoutAnimado, "Y", top);
            objectAnimator.setDuration(300);
            objectAnimator.start();

        } else {
            iDown.setVisibility(View.GONE);
            iUp.setVisibility(View.VISIBLE);
            objectAnimator = ObjectAnimator.ofFloat(layoutAnimado, "Y", y20p);
            objectAnimator.setDuration(300);
            objectAnimator.start();

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void animarLateral(boolean mostrar, LinearLayout layoutAnimado, RelativeLayout lanim) {
        ObjectAnimator objectAnimator;
        if (posicionLateral)
            posicionLateral = false;
        else
            posicionLateral = true;

        if (mostrar) {
            iLeft.setVisibility(View.VISIBLE);
            iRight.setVisibility(View.GONE);
            objectAnimator = ObjectAnimator.ofFloat(layoutAnimado, "X", x20p);
            objectAnimator.setDuration(300);
            objectAnimator.start();

        } else {
            iLeft.setVisibility(View.GONE);
            iRight.setVisibility(View.VISIBLE);
            objectAnimator = ObjectAnimator.ofFloat(layoutAnimado, "X", -lockRight);
            objectAnimator.setDuration(300);
            objectAnimator.start();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    layoutAnimado.setBackground(getDrawable(R.drawable.tbox));
                    direccLayout.setBackground(getDrawable(R.drawable.tbox));
                }
            }, 250);


        }

    }


    public void Cancel(View view) {
        cancel = true;
        vez = true;
        direccion = "";
        p1.setVisibility(View.GONE);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                cancel = false;
            }
        }, 2500);
    }

    //Minimizado
    public void Fase0(View view) {
        view.setVisibility(View.GONE);
    }


    //Buscar Direccion
    public void Fase1(View view) {
        FaseAnterior = UltimaFase;
        UltimaFase = 1;
        if (r1.getVisibility() == View.GONE)
            r1.setVisibility(View.VISIBLE);
        if (destinoReserva.getVisibility() == View.VISIBLE)
            destinoReserva.setVisibility(View.GONE);
        if (fechaReserva.getVisibility() == View.VISIBLE)
            fechaReserva.setVisibility(View.GONE);
        if (l2.getVisibility() == View.VISIBLE)
            l2.setVisibility(View.GONE);
        if (l3.getVisibility() == View.VISIBLE)
            l3.setVisibility(View.GONE);
        if (l5.getVisibility() == View.VISIBLE)
            l5.setVisibility(View.GONE);
        if (r2.getVisibility() == View.VISIBLE)
            r2.setVisibility(View.GONE);
        if (topR.getVisibility() == View.VISIBLE)
            topR.setVisibility(View.GONE);
        if (coincidencias.getVisibility() == View.VISIBLE)
            coincidencias.setVisibility(View.GONE);
        if (establecerLugFav.getVisibility() == View.VISIBLE)
            establecerLugFav.setVisibility(View.GONE);

    }


    //Escribir Dir
    public void Fase2(View view) {
        if (Objects.equals(titlLugar, "Casa")) {
            et1.setHint("Direccion de su hogar");
            iconEditTextBusar.setImageResource(R.drawable.ic_home2);
        } else if (Objects.equals(titlLugar, "Trabajo")) {
            et1.setHint("Direccion de su trabajo");
            iconEditTextBusar.setImageResource(R.drawable.ic_trabajo2);
        } else if (Objects.equals(titlLugar, "Escuela")) {
            et1.setHint("Direccion de su escuela");
            iconEditTextBusar.setImageResource(R.drawable.ic_escuela2);
        } else {
            et1.setHint("Direccion, lugar, etc...");
            iconEditTextBusar.setImageResource(R.drawable.ic_location_on_black_24dp);
        }
        cargarHistorial();
        top = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 275f, resources.getDisplayMetrics());
        top = alto - top;
        FaseAnterior = UltimaFase;
        UltimaFase = 2;
        rutas.removeRoutes();
        et1.getText().clear();
        if (r1.getVisibility() == View.VISIBLE)
            r1.setVisibility(View.GONE);
        if (destinoReserva.getVisibility() == View.VISIBLE)
            destinoReserva.setVisibility(View.GONE);
        if (fechaReserva.getVisibility() == View.VISIBLE)
            fechaReserva.setVisibility(View.GONE);
        if (l2.getVisibility() == View.VISIBLE)
            l2.setVisibility(View.GONE);
        if (listBusquedas.size() == 0)
            clearHistorial.setVisibility(View.GONE);
        else
            clearHistorial.setVisibility(View.VISIBLE);
        if (l3.getVisibility() == View.VISIBLE)
            l3.setVisibility(View.GONE);
        if (l5.getVisibility() == View.VISIBLE)
            l5.setVisibility(View.GONE);
        if (r2.getVisibility() == View.GONE)
            r2.setVisibility(View.VISIBLE);
        if (topR.getVisibility() == View.VISIBLE)
            topR.setVisibility(View.GONE);
        if (coincidencias.getVisibility() == View.VISIBLE)
            coincidencias.setVisibility(View.GONE);
        if (establecerLugFav.getVisibility() == View.VISIBLE)
            establecerLugFav.setVisibility(View.GONE);

    }

    //Ver ruta
    public void Fase3(View view) {
        top = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 222f, resources.getDisplayMetrics());
        top = alto - top;

        FaseAnterior = UltimaFase;
        UltimaFase = 3;
        if (establecerLugFav.getVisibility() == View.VISIBLE)
            establecerLugFav.setVisibility(View.GONE);
        if (r1.getVisibility() == View.VISIBLE)
            r1.setVisibility(View.GONE);
        if (destinoReserva.getVisibility() == View.VISIBLE)
            destinoReserva.setVisibility(View.GONE);
        if (fechaReserva.getVisibility() == View.VISIBLE)
            fechaReserva.setVisibility(View.GONE);
        if (l2.getVisibility() == View.GONE && !adding)
            l2.setVisibility(View.VISIBLE);
        else {
            establecerLugFav.setVisibility(View.VISIBLE);
        }
        if (l3.getVisibility() == View.VISIBLE)
            l3.setVisibility(View.GONE);
        if (l5.getVisibility() == View.VISIBLE)
            l5.setVisibility(View.GONE);
        if (r2.getVisibility() == View.VISIBLE)
            r2.setVisibility(View.GONE);
        if (topR.getVisibility() == View.VISIBLE)
            topR.setVisibility(View.GONE);
        if (coincidencias.getVisibility() == View.VISIBLE)
            coincidencias.setVisibility(View.GONE);
    }

    //Buscar Chofer
    public void Fase4(View view) {
        top = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 510f, resources.getDisplayMetrics());
        top = alto - top;
        FaseAnterior = UltimaFase;
        UltimaFase = 4;
        if (r1.getVisibility() == View.VISIBLE)
            r1.setVisibility(View.GONE);
        if (destinoReserva.getVisibility() == View.VISIBLE)
            destinoReserva.setVisibility(View.GONE);
        if (fechaReserva.getVisibility() == View.VISIBLE)
            fechaReserva.setVisibility(View.GONE);
        if (l2.getVisibility() == View.VISIBLE)
            l2.setVisibility(View.GONE);
        if (l3.getVisibility() == View.VISIBLE)
            l3.setVisibility(View.GONE);
        if (l5.getVisibility() == View.GONE)
            l5.setVisibility(View.VISIBLE);
        if (r2.getVisibility() == View.VISIBLE)
            r2.setVisibility(View.GONE);
        if (topR.getVisibility() == View.GONE)
            topR.setVisibility(View.VISIBLE);
        if (coincidencias.getVisibility() == View.VISIBLE)
            coincidencias.setVisibility(View.GONE);
        if (b3.getVisibility() == View.GONE) {
            b3.setVisibility(View.VISIBLE);
            ObjectAnimator animator = ObjectAnimator.ofFloat(b3, "alpha", 1.0f);
            animator.start();
        }
        if (esp.getVisibility() == View.VISIBLE)
            esp.setVisibility(View.GONE);
        if (establecerLugFav.getVisibility() == View.VISIBLE)
            establecerLugFav.setVisibility(View.GONE);
    }

    //ver chofer
    public void Fase5(View view) {
        top = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 190f, resources.getDisplayMetrics());
        top = alto - top;
        FaseAnterior = UltimaFase;
        UltimaFase = 5;
        if (r1.getVisibility() == View.VISIBLE)
            r1.setVisibility(View.GONE);
        if (l2.getVisibility() == View.VISIBLE)
            l2.setVisibility(View.GONE);
        if (destinoReserva.getVisibility() == View.VISIBLE)
            destinoReserva.setVisibility(View.GONE);
        if (fechaReserva.getVisibility() == View.VISIBLE)
            fechaReserva.setVisibility(View.GONE);
        if (l3.getVisibility() == View.GONE)
            l3.setVisibility(View.VISIBLE);
        if (l5.getVisibility() == View.VISIBLE)
            l5.setVisibility(View.GONE);
        if (r2.getVisibility() == View.VISIBLE)
            r2.setVisibility(View.GONE);
        if (topR.getVisibility() == View.GONE)
            topR.setVisibility(View.VISIBLE);
        if (coincidencias.getVisibility() == View.VISIBLE)
            coincidencias.setVisibility(View.GONE);
        if (establecerLugFav.getVisibility() == View.VISIBLE)
            establecerLugFav.setVisibility(View.GONE);
    }

    //ver Coincidencias
    public void Fase6(View view) {
        top = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 270f, resources.getDisplayMetrics());
        top = alto - top;
        FaseAnterior = UltimaFase;
        UltimaFase = 6;
        if (r1.getVisibility() == View.VISIBLE)
            r1.setVisibility(View.GONE);
        if (fromBooking) {
            if (destinoReserva.getVisibility() == View.VISIBLE)
                destinoReserva.setVisibility(View.GONE);
        }
        if (fechaReserva.getVisibility() == View.VISIBLE)
            fechaReserva.setVisibility(View.GONE);
        if (l2.getVisibility() == View.VISIBLE)
            l2.setVisibility(View.GONE);
        if (l3.getVisibility() == View.VISIBLE)
            l3.setVisibility(View.GONE);
        if (l5.getVisibility() == View.VISIBLE)
            l5.setVisibility(View.GONE);
        if (r2.getVisibility() == View.VISIBLE)
            r2.setVisibility(View.GONE);
        if (topR.getVisibility() == View.VISIBLE)
            topR.setVisibility(View.GONE);
        if (coincidencias.getVisibility() == View.GONE)
            coincidencias.setVisibility(View.VISIBLE);
        if (establecerLugFav.getVisibility() == View.VISIBLE)
            establecerLugFav.setVisibility(View.GONE);
    }

    //BOOKING
    public void Fase7(View view) {
        FaseAnterior = UltimaFase;
        UltimaFase = 7;
        if (destinoReserva.getVisibility() == View.GONE)
            destinoReserva.setVisibility(View.VISIBLE);
        if (fechaReserva.getVisibility() == View.GONE)
            fechaReserva.setVisibility(View.VISIBLE);
        if (r1.getVisibility() == View.VISIBLE)
            r1.setVisibility(View.GONE);
        if (l2.getVisibility() == View.VISIBLE)
            l2.setVisibility(View.GONE);
        if (l3.getVisibility() == View.VISIBLE)
            l3.setVisibility(View.GONE);
        if (l5.getVisibility() == View.VISIBLE)
            l5.setVisibility(View.GONE);
        if (r2.getVisibility() == View.VISIBLE)
            r2.setVisibility(View.GONE);
        if (topR.getVisibility() == View.VISIBLE)
            topR.setVisibility(View.GONE);
        if (coincidencias.getVisibility() == View.VISIBLE)
            coincidencias.setVisibility(View.GONE);
        if (establecerLugFav.getVisibility() == View.VISIBLE)
            establecerLugFav.setVisibility(View.GONE);

    }

    //todo cerrado
    public void Fase10(View view) {
        FaseAnterior = UltimaFase;
        UltimaFase = 10;
        if (destinoReserva.getVisibility() == View.VISIBLE)
            destinoReserva.setVisibility(View.GONE);
        if (fechaReserva.getVisibility() == View.VISIBLE)
            fechaReserva.setVisibility(View.GONE);
        if (r1.getVisibility() == View.VISIBLE)
            r1.setVisibility(View.GONE);
        if (l2.getVisibility() == View.VISIBLE)
            l2.setVisibility(View.GONE);
        if (l3.getVisibility() == View.VISIBLE)
            l3.setVisibility(View.GONE);
        if (l5.getVisibility() == View.VISIBLE)
            l5.setVisibility(View.GONE);
        if (r2.getVisibility() == View.VISIBLE)
            r2.setVisibility(View.GONE);
        if (topR.getVisibility() == View.VISIBLE)
            topR.setVisibility(View.GONE);
        if (coincidencias.getVisibility() == View.VISIBLE)
            coincidencias.setVisibility(View.GONE);
        if (establecerLugFav.getVisibility() == View.VISIBLE)
            establecerLugFav.setVisibility(View.GONE);
    }

    int isInStringArray(String[] array, String val) {
        if (array == null)
            return -2;
        else {

            for (int i = 0; i < array.length; i++) {
                if (Objects.equals(array[i], val)) {
                    return i;
                }
            }
        }
        return -1;


    }


    public void addBusqueda(String lugar, String lat, String longi) {
        SharedPreferences pref = getSharedPreferences("historial", Context.MODE_PRIVATE);
        String[] titulos = (pref.getString("places", "")).trim().split("ª·");
        String[] latitudes = (pref.getString("latitud", "")).trim().split("ª·");
        String[] longitudes = (pref.getString("longitud", "")).trim().split("ª·");
        SharedPreferences.Editor editor = pref.edit();

        if (isInStringArray(titulos, lugar) == -1) {
            editor.putString("places", pref.getString("places", "") + "ª·" + lugar);
            editor.putString("latitud", pref.getString("latitud", "") + "ª·" + lat);
            editor.putString("longitud", pref.getString("longitud", "") + "ª·" + longi);
            editor.apply();
        }


    }

    public void cargarHistorial() {
        listBusquedas.clear();
        SharedPreferences pref = getSharedPreferences("historial", Context.MODE_PRIVATE);
        String[] titulos = (pref.getString("places", "")).trim().split("ª·");
        String[] latitudes = (pref.getString("latitud", "")).trim().split("ª·");
        String[] longitudes = (pref.getString("longitud", "")).trim().split("ª·");

        if (titulos.length > 1)
            for (int i = 1; i < titulos.length; i++) {
                //Toast.makeText(getApplicationContext(),  longitudes[1], Toast.LENGTH_LONG).show();
                listBusquedas.add(new BusquedaReciente(titulos[i], Double.valueOf(latitudes[i]), Double.valueOf(longitudes[i])));
            }

        adapterBusquedas.notifyDataSetChanged();

    }

    public void eliminarBusqueda(String titl, String lat, String longi) {
        String titulos;
        String latitudes;
        String longitudes;
        SharedPreferences pref = getSharedPreferences("historial", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        titulos = pref.getString("places", "").replace("ª·" + titl, "");
        latitudes = pref.getString("latitud", "").replace("ª·" + lat, "");
        longitudes = pref.getString("longitud", "").replace("ª·" + longi, "");
        editor.putString("places", titulos);
        editor.putString("latitud", latitudes);
        editor.putString("longitud", longitudes);
        editor.apply();

    }

    public void borrarHistorial(View v) {
        clearHistorial.setVisibility(View.GONE);
        SharedPreferences pref = getSharedPreferences("historial", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("places");
        editor.remove("latitud");
        editor.remove("longitud");
        editor.apply();
        listBusquedas.clear();
        adapterBusquedas.notifyDataSetChanged();
    }


    public void seguirConductor(double latActual, double lonActual) {

        double actual = distancia(mlat, mlon, latActual, lonActual);
        actual = distanciaTotal - reducirDecimal(actual, 2);
        Toast.makeText(getApplicationContext(), actual + " de " + distanciaTotal, Toast.LENGTH_LONG).show();
        float move = (float) (((actual) * anchoPantalla) / distanciaTotal);
        V.setTranslationX(move);
        if (distanciaTotal - actual < 0.005)
            tracking = false;


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void mostrarCamino(String conductor) {

        int c = posMensajeIn(conductor);
        Picasso.get().load(imagenes[c]).resize(500, 500).centerCrop().into(im2);
        rNombre.setText(nombress[c]);

        top = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200f, resources.getDisplayMetrics());
        top = alto - top;
        int o = repr(conductor);
        tracking = true;
        double total = distancia(mlat, mlon, latitud[o], longitud[o]);
        distanciaTotal = reducirDecimal(total, 2);
        handler.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                if (tracking) {
                    coordenadas.extraerEspCoordenada(conductor);
                }
                handler.postDelayed(this, 900);
            }
        }, 0);


        if (Objects.equals(tipo[c], "Moto"))
            V.setImageResource(R.drawable.ic_motorcycle_black_24dp);
        else
            V.setImageResource(R.drawable.ic_directions_car_black_24dp);
        road.setVisibility(View.VISIBLE);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float b = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 154f, resources.getDisplayMetrics());
        anchoPantalla = displayMetrics.widthPixels;
        anchoPantalla -= b;

    }


    public void buscarChoferes(View view) {
        distancia2.setText("Tu destino esta a: " + reducirDecimal(distance, 2) + " km");
        mDireccion2.setText(direccionActual + ", " + city);
        DDirec2.setText(direccion + ", " + city);
        Fase4(b);

    }


    private void Recycler2() {
        vista2.setHasFixedSize(true);
        vista2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        adp2 = new adapter3(locations2);

        vista2.setAdapter(adp2);
        adp2.setOnItemClickListener(new adapter3.OnItemCLickListener() {
            @Override
            public void OnItemClick(int position) {

                if (fromBooking) {
                    fechaReserva.setVisibility(View.VISIBLE);
                    destinoReserva.setVisibility(View.VISIBLE);
                    if (fromBookingOrigen)
                        mostrarOrigenMapaReserva(locations2.get(position).getLatitud(), locations2.get(position).getLongitud());
                    else
                        mostrarDestinoMapaReserva(locations2.get(position).getLatitud(), locations2.get(position).getLongitud());
                } else
                    mostrarEnMapa(locations2.get(position).getLatitud(), locations2.get(position).getLongitud(), true, "");
                adp2.notifyItemChanged(position);
            }

        });


    }

    private void Recycler() {
        vista3.setHasFixedSize(true);
        vista3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        adp3 = new adapter4(locations3);


        vista3.setAdapter(adp3);
        adp3.setOnSlideCompleteListener(new adapter4.OnSlideCompleteListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void OnSlideComplete(int position) {
                aceptar(locations3.get(position).getTitle(), position);
            }

        });

        adp3.setOnItemClickListener(new adapter4.OnItemCLickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void OnItemClick(int position) {
                rechazar(locations3.get(position).getTitle(), position);
            }
        });


    }

    private void RecyclerBusquedasRecientes() {
        busqedas.setHasFixedSize(true);
        busqedas.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        adapterBusquedas = new adapterBusquedas(listBusquedas);

        busqedas.setAdapter(adapterBusquedas);

        adapterBusquedas.setOnItemClickListener(new com.example.mapas.adapterBusquedas.OnItemCLickListener() {
            @Override
            public void OnItemClick(int position) {
                mostrarEnMapa(listBusquedas.get(position).getLatitud(), listBusquedas.get(position).getLongitud(), false, listBusquedas.get(position).getBusqueda());
            }

            @Override
            public void Delete(int position) {
                eliminarBusqueda(listBusquedas.get(position).getBusqueda(), String.valueOf(listBusquedas.get(position).getLatitud()), String.valueOf(listBusquedas.get(position).getLongitud()));
                listBusquedas.remove(position);
                Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_LONG).show();
                adapterBusquedas.notifyItemRemoved(position);
            }
        });

    }

    @Override
    public void onBackPressed() {
        switch (UltimaFase) {
            case 0:
                Maxim(b);
                break;
            case 1:
                super.onBackPressed();
                break;
            case 2:
                if (posicion)
                    animar(true, hideLayout);
                else
                    Fase1(b);

                break;
            case 3:
                marcador.remove();
                if (FaseAnterior == 6) {
                    if (posicion)
                        animar(true, hideLayout);
                    else
                        Fase6(b);
                    rutas.removeRoutes();
                } else if (posicion)
                    animar(true, hideLayout);
                else
                    Fase2(b);
                break;
            case 4:
                if (posicion)
                    animar(true, hideLayout);
                else
                    Fase3(b);
                break;
            case 5:
                if (posicion)
                    animar(true, hideLayout);
                else
                    Fase4(b);
                break;
            case 6:
                if (posicion)
                    animar(true, hideLayout);
                else {
                    if (fromBooking)
                        Fase7(b);
                    else
                        Fase2(b);
                }
                break;
            default:
                if (posicion)
                    animar(true, hideLayout);
                else
                    super.onBackPressed();
                break;
        }
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
                MapsActivity.this.runOnUiThread(new Runnable() {
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

    public void call(View view) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
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

    String obtenerFecha(int D, int M, int A) {
        return dayOfWeek(D, M, A) + " " + D + " " + month(M) + " " + A;
    }

    String putCero(int x) {
        if (x < 10)
            return String.valueOf(0) + x;
        else
            return String.valueOf(x);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void Reserva(View view) {
        if (first) {
            textReserva.setText("Hora");
            btnReserva.setText("Enviar Solicitud");
            datePicker.setVisibility(View.GONE);
            timePicker.setVisibility(View.VISIBLE);
            iFechaHora.setImageResource(R.drawable.clock_orange);
            first = false;
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    new AlertDialog.Builder(MapsActivity.this)
                            .setMessage("Desea enviar una solicitud a " + nombreChoferReserva +
                                    " para el " + obtenerFecha(datePicker.getDayOfMonth(), datePicker.getMonth() + 1, datePicker.getYear()) + " a las " + timePicker.getHour() + ":" + timePicker.getMinute() + " " + AM_PM(timePicker.getHour()))
                            .setCancelable(false)
                            .setPositiveButton(
                                    "Enviar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            sendMes.setVisibility(View.VISIBLE);
                                            sendMes.playAnimation();
                                            comunication.insertarMensaje(choferReserva, et3.getText().toString() + "->" + et2.getText().toString() + "(" + latBookDest + "/" + lonBookDest + ")", "2", putCero(datePicker.getDayOfMonth()) + "/" + putCero(datePicker.getMonth() + 1) + "/" + datePicker.getYear() + " " + timePicker.getHour() + ":" + timePicker.getMinute() + " " + AM_PM(timePicker.getHour()));
                                            dialog.cancel();
                                        }
                                    })
                            .setNegativeButton(
                                    "Cancelar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    }
                            ).show();
                }
            }
        }

    }

    String AM_PM(int hour) {
        if (hour < 12)
            return "AM";
        else
            return "PM";

    }

    void extraer() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                comunication.cantMensajes("1");

                handler.postDelayed(this, 6000);
            }
        }, 1);

    }

    public void obtenerCiudad(double lat, double lon) {
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
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                final String myresponse = response.body().string();
                MapsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 58; i++) {
                            if (myresponse.indexOf(ciudades[i]) != -1) {
                                city = ciudades[i];
                            }
                        }
                        miDireccion(lat, lon);
                    }
                });
            }
        });

    }

    public void mostrarChapa(String i, int v) {
        Toast.makeText(getApplicationContext(), i, Toast.LENGTH_LONG).show();
        vehiculo = v;

        title = i;
        int o = repr(i);

        LatLng latLng1 = new LatLng(latitud[o], longitud[o]);
        Fase5(b);
        comunication.extraerImagen(i);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 16));
    }

    public int MotoMasCercana() {
        int moto = -1;
        if (cant > 0) {
            double menor = 100000000;
            for (int i = 0; i < cant; i++) {
                Location location = new Location("localizacion 1");
                location.setLatitude(mlat);  //latitud
                location.setLongitude(mlon); //longitud
                Location location2 = new Location("localizacion 2");
                location2.setLatitude(latitud[i]);  //latitud
                location2.setLongitude(longitud[i]); //longitud
                double distance = location.distanceTo(location2);
                if (distance < menor) {
                    menor = distance;
                    moto = i;
                }
            }
        }
        return moto;

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void mostrar() {

        top = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 290f, resources.getDisplayMetrics());
        top = alto - top;

        for (int i = 0; i < cantMens; i++) {
            if (!Objects.equals(mensaje[i], " 123 ")) {
                //Toast.makeText(getApplicationContext(), mensaje[posMensaje], Toast.LENGTH_LONG).show();
                locations3.add(new RespConduct(imagenes[i], nombress[i], mensaje[i], "5 min", String.valueOf(uOrigen[i])));
                adp3.notifyDataSetChanged();
                comunication.leido(idMensaje[i]);
            }
        }


    }

    public int posMensajeIn(String cond) {

        for (int i = 0; i < tipo.length; i++) {
            if (idUsuario[i] == Double.valueOf(cond))
                return i;

        }
        return 0;
    }

    public void preparation() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                coordenadas.cantDrivers(radio);
                handler.postDelayed(this, 10000);
            }
        }, 1);
    }

    public void solicitar(View view) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(b3, "alpha", 0.0f);
        animator.setDuration(150);
        animator.start();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                b3.setVisibility(View.GONE);
                esp.setVisibility(View.VISIBLE);
            }
        }, 150);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                new AlertDialog.Builder(MapsActivity.this)
                        .setMessage("EL conductor le enviará el precio de su viaje, mientras puede solicitar otros conductores")
                        .setCancelable(false)
                        .setNegativeButton(
                                "Entendido", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Fase3(b);
                                        dialog.cancel();
                                    }
                                }).show();


            }
        }, 2900);

        String v1 = String.valueOf(tlat);
        String v2 = String.valueOf(tlon);
        comunication.insertarMensaje(title, mDireccion.getText().toString() + "->" + DDirec.getText().toString() + "(" + v1 + "/" + v2 + ")", "2", "null");


    }

    public void ObtenerMotoMasCercana(View view) {
        int h = MotoMasCercana();
        if (h != -1) {
            b2.setVisibility(View.GONE);
            p1.setVisibility(View.VISIBLE);
            comunication.extraerImagen(String.valueOf(h));
        }

    }

    public void escribirDir(View view) {
        r1.setVisibility(View.GONE);
        l2.setVisibility(View.GONE);
        r2.setVisibility(View.VISIBLE);
        rutas.removeRoutes();
        et1.requestFocus();
        et1.getText().clear();

    }

    public void Cancelar(View view) {

        aux++;
        if (r1.getVisibility() == View.GONE)
            r1.setVisibility(View.VISIBLE);
        if (barra.getVisibility() == View.VISIBLE)
            barra.setVisibility(View.GONE);
        if (l5.getVisibility() == View.VISIBLE) {
            topR.setVisibility(View.GONE);
            l5.setVisibility(View.GONE);
        }

        marcador.remove();
        r1.setVisibility(View.VISIBLE);
        rutas.removeRoutes();
        l2.setVisibility(View.GONE);


    }

    public void direccionOrigen(View view) {
        fromBookingOrigen = true;
        String D = et3.getText().toString();
        limpiarRecycler();
        mMap.clear();
        p1.setVisibility(View.VISIBLE);
        if (vez)
            direccion = D;
        Toast.makeText(getApplicationContext(), direccion, Toast.LENGTH_LONG).show();
        OkHttpClient client = new OkHttpClient();
        String url = "https://maps.google.com/?q=" + direccion + ", " + city;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    if (!cancel) {
                        final String myresponse = response.body().string();
                        MapsActivity.this.runOnUiThread(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void run() {
                                int t;
                                String clave = remplazo(" Santiago de Cuba", " ", "+");
                                int a = myresponse.indexOf(clave);
                                int v = myresponse.indexOf("/@");
                                if (a != -1 && v != -1) {
                                    for (int i = 1; i < clave.length() + 20; i++) {
                                        String texto2 = myresponse.substring(v - i, v);
                                        if (texto2.indexOf(clave) != -1) {
                                            DireccionExacta = true;
                                            break;
                                        }
                                    }

                                    if (DireccionExacta) {
                                        DireccionExacta = false;
                                        String texto = myresponse.substring(v + 2);
                                        t = texto.length();
                                        int y = texto.indexOf(",");
                                        String s1 = texto.substring(0, y);
                                        texto = texto.substring(y + 1, t);
                                        y = texto.indexOf(",");
                                        String s2 = texto.substring(0, y);
                                        tlat = Double.valueOf(s1);
                                        tlon = Double.valueOf(s2);
                                        mostrarOrigenMapaReserva(tlat, tlon);
                                    } else
                                        Coincidencias(myresponse);
                                } else
                                    Coincidencias(myresponse);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        mMap.setOnCameraMoveListener(() -> {
                    rutas.onCameraMove(mMap.getProjection(), mMap.getCameraPosition());
                }
        );

    }

    public void sendDir(View view) {
        String D = "";
        if (!et1.getText().toString().trim().isEmpty())
            D = et1.getText().toString();
        else if (!et2.getText().toString().trim().isEmpty())
            D = et2.getText().toString();
        limpiarRecycler();
        mMap.clear();
        p1.setVisibility(View.VISIBLE);
        if (vez)
            direccion = D;
        Toast.makeText(getApplicationContext(), direccion, Toast.LENGTH_LONG).show();
        OkHttpClient client = new OkHttpClient();

        city="Santiago de Cuba";//PRUEBAS ESPAÑAAAAAAAAAAAAAAAAAA

        String url = "https://maps.google.com/?q=" + direccion + ", " + city;

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    if (!cancel) {
                        final String myresponse = response.body().string();
                        MapsActivity.this.runOnUiThread(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void run() {
                                int t;
                                String clave = remplazo(" Santiago de Cuba", " ", "+");
                                int a = myresponse.indexOf(clave);
                                int v = myresponse.indexOf("/@");
                                if (a != -1 && v != -1) {
                                    for (int i = 1; i < clave.length() + 20; i++) {
                                        String texto2 = myresponse.substring(v - i, v);
                                        if (texto2.indexOf(clave) != -1) {
                                            DireccionExacta = true;
                                            break;
                                        }
                                    }

                                    if (DireccionExacta) {
                                        DireccionExacta = false;
                                        String texto = myresponse.substring(v + 2);
                                        t = texto.length();
                                        int y = texto.indexOf(",");
                                        String s1 = texto.substring(0, y);
                                        texto = texto.substring(y + 1, t);
                                        y = texto.indexOf(",");
                                        String s2 = texto.substring(0, y);
                                        tlat = Double.valueOf(s1);
                                        tlon = Double.valueOf(s2);
                                        if (fromBooking)
                                            mostrarDestinoMapaReserva(tlat, tlon);
                                        else
                                            mostrarEnMapa(tlat, tlon, true, "");
                                    } else
                                        Coincidencias(myresponse);
                                } else
                                    Coincidencias(myresponse);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        mMap.setOnCameraMoveListener(() -> {
                    rutas.onCameraMove(mMap.getProjection(), mMap.getCameraPosition());
                }
        );

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void Coincidencias(String myresponse) {
        int t = myresponse.length();
        int m = myresponse.indexOf("markers");
        Toast.makeText(getApplicationContext(), String.valueOf(m), Toast.LENGTH_LONG).show();
        if (m != -1) {
            texto = myresponse.substring(m + 8, t);
            extraerInfo(texto, "%", "&");
            if (!vez) {
                vez = true;
                organizar();
            } else if (vez) {
                flagCoinc = true;
                modificarDir();
            }
        } else {
            if (vez) {
                flagCoinc = false;
                modificarDir();
            } else {
                vez = true;
                Toast.makeText(getApplicationContext(), "No se encuentra la direccion", Toast.LENGTH_SHORT).show();
                p1.setVisibility(View.GONE);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void modificarDir() {
        int yMin = direccion.indexOf(" y ");
        int yMay = direccion.indexOf(" Y ");
        if (yMin != -1 || yMay != -1) {
            String p = direccion.substring(0, (-1 * yMay * yMin));
            direccion = direccion.substring((-1 * yMay * yMin) + 3);
            direccion += " y " + p;
            vez = false;
            aux2 = vuelta;
            vuelta = 0;
            sendDir(b);
        } else {
            int l = direccion.indexOf(" ");
            if (l != -1) {
                String aux = direccion.substring(0, l);
                direccion = direccion.substring(l + 1);
                direccion += " " + aux;
                vez = false;
                aux2 = vuelta;
                vuelta = 0;
                sendDir(b);
            } else if (flagCoinc) {
                flagCoinc = false;
                Toast.makeText(getApplicationContext(), "esperand", Toast.LENGTH_LONG).show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        organizar();
                    }
                }, 2000);

            } else {
                p1.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "No se encuentra la direccion", Toast.LENGTH_LONG).show();
            }
        }
    }

    void limpiarRecycler() {
        locations2.clear();
        adp2.notifyDataSetChanged();
    }

    void MostrarCoinc() {
        Fase6(b);
        if (fromBooking) {
            fechaReserva.setVisibility(View.GONE);
        }
        LatLng latLng = new LatLng(Double.valueOf(Coords[0]), Double.valueOf(Coords[1]));
        p1.setVisibility(View.GONE);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        vuelta = 0;
        cont = 0;
        Coords = new String[50];


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void organizar() {
        MarkerOptions markerOptions = new MarkerOptions();
        LatLng latLng;

        int tamaño = vuelta + aux2 + 1;
        for (int r = 0; r < tamaño; r++) {
            for (int o = r + 1; o < tamaño; o++) {
                if (Objects.equals(Coords[r * 2], (Coords[o * 2])) && Objects.equals(Coords[r * 2 + 1], (Coords[o * 2 + 1]))) {
                    Coords[o * 2] = null;
                    Coords[o * 2 + 1] = null;
                }
            }
        }
        for (int j = 0; j < tamaño; j++) {
            if (!Objects.equals(Coords[j * 2], null) && !Objects.equals(Coords[j * 2 + 1], null)) {
                latLng = new LatLng(Double.valueOf(Coords[j * 2]), Double.valueOf(Coords[j * 2 + 1]));
                obtenerDireccionCompleta(Double.valueOf(Coords[j * 2]), Double.valueOf(Coords[j * 2 + 1]), "D" + j, direccion);
                markerOptions.position(latLng);
                markerOptions.title("D" + j);
                mMap.addMarker(markerOptions);
            }
        }
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(et1.getWindowToken(), 0);

        MostrarCoinc();

    }

    public void mostrarCoincidencias(String direccion, String distancia, String title,
                                     double lat, double lon) {

        locations2.add(new Direcc(R.drawable.ic_pin_drop_black_24dp, direccion, distancia, title, lat, lon));
        adp2.notifyDataSetChanged();


    }

    public void obtenerDireccionCompleta(double lat, double lon, String tite2, String D) {
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
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    final String myresponse = response.body().string();
                    MapsActivity.this.runOnUiThread(new Runnable() {
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
                            if (p - s < p - 2) {
                                String direccionActual = myresponse.substring(p - s, p - 2);
                                mostrarCoincidencias(direccionActual + ", " + city, "2 km", tite2, lat, lon);
                            } else {
                                mostrarCoincidencias(D + ", " + city, "2 km", tite2, lat, lon);
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public double distancia(double mlat, double mlon, double tlat, double tlon) {
        Location location = new Location("localizacion 1");
        location.setLatitude(mlat);  //latitud
        location.setLongitude(mlon); //longitud
        Location location2 = new Location("localizacion 2");
        location2.setLatitude(tlat);  //latitud
        location2.setLongitude(tlon); //longitud
        distance = location.distanceTo(location2);
        return reducirDecimal(distance / 1000, 2);
    }

    public double getLat() {
        return mlat;
    }

    public double getLon() {
        return mlon;
    }

    void mostrarOrigenMapaReserva(double tlat, double tlon) {
        latBookOrig = tlat;
        lonBookOrig = tlon;
        vez = true;
        LatLng latLng1 = new LatLng(tlat, tlon);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng1);
        //  markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_person_pin_out));
        marcador = mMap.addMarker(markerOptions);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 16));
        p1.setVisibility(View.GONE);
        fromBookingOrigen = false;

    }

    public void mostrarDestinoMapaReserva(double tlat, double tlon) {
        latBookDest = tlat;
        lonBookDest = tlon;
        vez = true;
        LatLng latLng1 = new LatLng(tlat, tlon);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng1);
        marcador = mMap.addMarker(markerOptions);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 16));


        Route.clear();
        Route.add(new LatLng(latBookOrig, lonBookOrig));
        Route.add(puntoMedio(latBookOrig, lonBookOrig, latBookDest, lonBookDest));
        Route.add(new LatLng(latBookDest, lonBookDest));

        drawRoutes();

        distance = distancia(latBookOrig, lonBookOrig, latBookDest, lonBookDest);
        //distancia.setText("Tu destino esta a: " + reducirDecimal(distance, 2) + " km");
        //mDireccion.setText(direccionActual + ", " + city);
        // DDirec.setText(direccion + ", " + city);
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(et1.getWindowToken(), 0);
        //Fase3(b);
        p1.setVisibility(View.GONE);


    }

    public void mostrarEnMapa(double tlat, double tlon, boolean addHistorial, String save) {
        vez = true;
        LatLng latLng1 = new LatLng(tlat, tlon);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng1);
        marcador = mMap.addMarker(markerOptions);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 16));


        Route.add(new LatLng(mlat, mlon));
        Route.add(puntoMedio(mlat, mlon, tlat, tlon));
        Route.add(new LatLng(tlat, tlon));

        drawRoutes();

        distance = distancia(mlat, mlon, tlat, tlon);
        distancia.setText("Tu destino esta a: " + reducirDecimal(distance, 2) + " km");
        if (addHistorial) {
            mDireccion.setText(direccionActual + ", " + city);
            DDirec.setText(direccion + ", " + city);
            addBusqueda(direccion + ", " + city, String.valueOf(tlat), String.valueOf(tlon));
        } else {
            DDirec.setText(save);
        }
        InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(et1.getWindowToken(), 0);


        direccionEstabl.setText(direccion+", "+city);
        Fase3(b);
        p1.setVisibility(View.GONE);


    }

    public void addedit_lug_Fav(View view){

        SharedPreferences pref = getSharedPreferences("pref_lug_fav", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(titlLugar, direccion);
        editor.apply();

    }
    public void extraerInfo(String texto, String char1, String char2) {
        if (vuelta > 0) {
            y = texto.indexOf("%");
            s2 = texto.substring(0, y);
            Coords[cont * 2 + 1] = s2;
            cont++;
            texto = texto.substring(y + 3);
        }
        int h = texto.indexOf(char1);
        String s1 = texto.substring(0, h);
        Coords[cont * 2] = s1;
        texto = texto.substring(h + 3);
        h = texto.indexOf(char2);
        s2 = texto.substring(0, h);
        if (!isNumber(s2)) {
            vuelta++;
            extraerInfo(texto, "%", "&");

        } else {
            Coords[cont * 2 + 1] = s2;
        }
    }

    public boolean isNumber(String in) {
        String perimitidos = "-+1234567890.,";
        for (int i = 0; i < in.length(); i++)
            if (perimitidos.indexOf(in.substring(i, i + 1)) == -1) {
                return false;
            }

        return true;
    }

    public String remplazo(String entrada, String Char, String newChar) {
        String salida = "";
        for (int i = 0; i < entrada.length(); i++) {
            if (entrada.substring(i, i + 1).indexOf(Char) == -1) {
                salida = salida + entrada.substring(i, i + 1);
            } else
                salida = salida + newChar;
        }
        return salida;
    }

    public static Double reducirDecimal(double n, double nd) {
        return Math.round(n * Math.pow(10, nd)) / Math.pow(10, nd);
    }

    public void addMarkers() {

        MarkerOptions markerOptions = new MarkerOptions();
        LatLng latLng;


        for (int i = 0; i < cant; i++) {
            latLng = new LatLng(latitud[i], longitud[i]);
            markerOptions.position(latLng);
            markerOptions.title(String.valueOf(idUsuario[i]));
            mMap.addMarker(markerOptions);
        }

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        LatLng latLng = new LatLng(mlat, mlon);


        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (mlat != 1)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

        mMap.setMyLocationEnabled(true);


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.getTitle().indexOf("D") == -1) {

                    Fase5(b);
                    p1.setVisibility(View.VISIBLE);
                    title = marker.getTitle();
                    comunication.extraerImagen(title);
                } else {
                    rutas.removeRoutes();
                    if (fromBooking)
                        if (fromBookingOrigen)
                            mostrarOrigenMapaReserva(marker.getPosition().latitude, marker.getPosition().longitude);
                        else
                            mostrarDestinoMapaReserva(marker.getPosition().latitude, marker.getPosition().longitude);
                    else
                        mostrarEnMapa(marker.getPosition().latitude, marker.getPosition().longitude, true, "");
                }

                return false;
            }
        });


        mMap.setOnCameraMoveListener(() ->
        {
            rutas.onCameraMove(googleMap.getProjection(), googleMap.getCameraPosition());
        });

/*
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {
                if (r2.getVisibility() == View.VISIBLE)
                    r1.setVisibility(View.VISIBLE);

                if (l2.getVisibility() == View.VISIBLE)
                    Minim(b);
                lRespo.setVisibility(View.GONE);
                r2.setVisibility(View.GONE);


            }
        });*/

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

        Route arcOverlayPolyline = new Route.Builder(rutas)
                .setRouteType(RouteOverlayView.RouteType.ARC)
                .setCameraPosition(mMap.getCameraPosition())
                .setProjection(mMap.getProjection())
                .setLatLngs(Route)
                .setBottomLayerColor(Color.GRAY)
                .setTopLayerColor(Color.BLACK)
                .setRouteShadowColor(Color.GRAY)
                .create();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void aceptar(String title, int k) {


        comunication.insertarMensaje(title, "A", "1", "null");

        // Toast.makeText(getApplicationContext(), String.valueOf(locations3.size()), Toast.LENGTH_LONG).show();
        for (int i = 0; i < locations3.size(); i++) {
            if (!Objects.equals(locations3.get(i).getTitle(), title))
                comunication.insertarMensaje(locations3.get(i).getTitle(), "rechazo", "1", "null");
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                locations3.clear();
                adp3.notifyDataSetChanged();
                lRespo.setVisibility(View.GONE);
                if (fase == 1) {
                    mostrarCamino(title);
                } else
                    Toast.makeText(getApplicationContext(), "Reservado!!!!!!!!", Toast.LENGTH_LONG).show();

            }
        }, 150);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void rechazar(String title, int pos) {

        comunication.insertarMensaje(title, "rechazo", "1", "null");
        locations3.remove(pos);
        adp3.notifyDataSetChanged();

    }

    public int repr(String j) {

        for (int i = 0; i < cant; i++) {
            if (idUsuario[i] == Double.valueOf(j)) {
                return i;
            }
        }
        return 0;
    }

    public void Minim(View view) {

        l2.setVisibility(View.GONE);
        barra.setVisibility(View.VISIBLE);
    }

    public void Maxim(View view) {
        switch (UltimaFase) {
            case 1:
                Fase1(b);
                break;
            case 2:
                Fase2(b);
                break;
            case 3:
                Fase3(b);
                break;
            case 4:
                Fase4(b);
                break;
            case 5:
                Fase5(b);
                break;
        }


    }

    public void CambiarDir(View view) {
        marcador.remove();
        et1.setText("");
        rutas.removeRoutes();
        Fase2(view);

    }

    public void CambioChof(View view) {
        l3.setVisibility(View.GONE);
        l5.setVisibility(View.VISIBLE);
        if (topR.getVisibility() == View.GONE)
            topR.setVisibility(View.VISIBLE);
    }

    public void solicitudRechazada() {
        Fase10(b);
        comunication.leido(idMensaje[posMensaje]);
        posMensaje++;
        rechazo.setVisibility(View.VISIBLE);
        handler.postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                rechazo.setVisibility(View.GONE);
                if (posMensaje < cantMens) {
                    mostrar();
                } else {
                    Fase3(b);
                    flag = true;
                    posMensaje = 0;
                }
            }
        }, 2500);


    }

    public void animarX(Object object, float val) {
        @SuppressLint("ObjectAnimatorBinding") ObjectAnimator animator = ObjectAnimator.ofFloat(object, "X", val);
        animator.setDuration(300);
        animator.start();

    }

    public void BackToDate(View view) {
        textReserva.setText("Fecha");
        btnReserva.setText("Siguiente");
        datePicker.setVisibility(View.VISIBLE);
        timePicker.setVisibility(View.GONE);
        iFechaHora.setImageResource(R.drawable.calendar_orange);
        first = true;
    }


    ////////CLASE COMUNICATION////////////////////////////
    public class Comunication {

        int IdPropio;
        Context context;
        String URL_instertar;
        String URL_extraer;
        String URL_cantM;
        String imagen;
        int cantM;


        public Comunication(int idPropio, Context context, String URL_instertar, String URL_extraer, String URL_cantM) {
            IdPropio = idPropio;
            this.context = context;
            this.URL_instertar = URL_instertar;
            this.URL_extraer = URL_extraer;
            this.URL_cantM = URL_cantM;
        }

        public void insertarMensaje(final String usuarioDestino, final String mensaje, final String tipo, String fecha) {
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
                    parametros.put("fecha", fecha);
                    return parametros;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        }

        public void leido(int id) {
            StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, "http://168.254.1.104/Sitio/leido.php?idMensaje=" + id, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

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

        public void extraerMensaje(String tipe) {
            mensaje = new String[cantM];
            nombress = new String[cantM];
            imagenes = new String[cantM];
            idMensaje = new int[cantM];
            tipo = new String[cantM];
            uOrigen = new int[cantM];


            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_extraer + "?usuarioDestino=" + IdPropio + "&tipo=" + tipe, new com.android.volley.Response.Listener<JSONArray>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);

                            mensaje[i] = jsonObject.getString("mensaje");
                            nombress[i] = jsonObject.getString("nombre");
                            imagenes[i] = jsonObject.getString("foto_vehc");
                            tipo[i] = jsonObject.getString("tipo_vehc");
                            idMensaje[i] = jsonObject.getInt("idMensaje");
                            uOrigen[i] = jsonObject.getInt("usuarioOrigen");
                        } catch (JSONException e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    Toast.makeText(context, tipo[0], Toast.LENGTH_SHORT).show();
                    if (Objects.equals(tipo[0], "Moto")) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            tVehc.setImageDrawable(getDrawable(R.drawable.ic_motorcycle_black_24dp));
                        }
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        tVehc.setImageDrawable(getDrawable(R.drawable.ic_directions_car_black_24dp));
                    }

                    animarX(icSolic, -82);
                    Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(600);


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


        public void cantMensajes(String tipe) {
            StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, URL_cantM + "?usuarioDestino=" + IdPropio + "&tipo=" + tipe, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    cantM = Integer.valueOf(response);
                    cantMens = cantM;
                    if (cantM > 0) {
                        Toast.makeText(getApplicationContext(), String.valueOf(cantMens), Toast.LENGTH_SHORT).show();
                        extraerMensaje("1");
                    }
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


        public void extraerImagenC(String idUsuario) {

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://168.254.1.104/Sitio/extraerImagen.php?idUsuario=" + idUsuario, new com.android.volley.Response.Listener<JSONArray>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);
                            imagen = jsonObject.getString("foto_vehc");
                            nombres = jsonObject.getString("nombre");
                        } catch (JSONException e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    imagen.replace("localhost", "168.254.1.104");

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

        public void extraerImagen(String idUsuario) {


            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://168.254.1.104/Sitio/extraerImagen.php?idUsuario=" + idUsuario, new com.android.volley.Response.Listener<JSONArray>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);
                            imagen = jsonObject.getString("foto_vehc");
                            nombres = jsonObject.getString("nombre");
                            faceS = jsonObject.getString("foto");
                            phoneNumber = jsonObject.getString("numero");
                        } catch (JSONException e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    imagen.replace("localhost", "168.254.1.104");
                    Picasso.get().load(imagen).resize(500, 500).centerCrop().into(im1);
                    Picasso.get().load(faceS).resize(500, 500).centerCrop().into(im3);
                    p1.setVisibility(View.GONE);
                    nombre.setText(nombres);


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


        public void setImagen(String imagen) {
            this.imagen = imagen;
        }

        public int[] getIdMensaje() {
            return idMensaje;
        }


        public int getCant() {
            return cant;
        }

        public int[] getuOrigen() {
            return uOrigen;
        }

        public void setCant(int cant) {
            this.cantM = cant;
        }
    }


    /////CLASE COORDENADAS////////////////////////////////

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
                    addMarkers();


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

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public void extraerEspCoordenada(String idUsuario) {
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_bajarUn + "?idUsuario=" + idUsuario, new com.android.volley.Response.Listener<JSONArray>() {
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
                    Toast.makeText(getApplicationContext(), Olatitud + " : " + Olongitud, Toast.LENGTH_LONG).show();

                    if (tracking) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        LatLng latLng = new LatLng(Olatitud, Olongitud);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                        seguirConductor(Olatitud, Olongitud);
                        markerOptions.position(latLng);
                        trackMarker = mMap.addMarker(markerOptions);
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
                    cant = cantDrivers;
                    if (cantDrivers > 0)
                        extraerDrivCoordenadas(radio);
                    else
                        Toast.makeText(getApplicationContext(), "No hay chóferes cercanos en este momento...", Toast.LENGTH_LONG).show();

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

