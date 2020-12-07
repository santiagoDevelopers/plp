package com.example.mapas;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.percentlayout.widget.PercentRelativeLayout;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.util.TypedValue;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.github.chrisbanes.photoview.PhotoView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.snackbar.SnackbarContentLayout;
import com.google.android.material.tabs.TabLayout;
import com.kishandonga.csbx.CustomSnackbar;
import com.mikhaellopez.circularimageview.CircularImageView;

import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener, NavigationView.OnNavigationItemSelectedListener {
    private String json;
    String[] ciudades = new String[58];
    private CSesion sesion;
    private String keyname_temp, data_temp;
    private ProgressBar progress;
    private CustomSnackbar snackbar;
    private CustomSnackbar snackbar_action;
    private Handler handler = new Handler();
    private Uri foto_uri = null;
    private DrawerLayout drawer_layout;
    private NavigationView nav_view;
    private NavController nav_control;
    private int item_menu = R.id.nav_inicio;
    private PercentRelativeLayout content;
    private PhotoView foto_zoom;
    private float xfoto;
    private float yfoto;
    private ArrayList<Lug_Fav> list_lug_fav;
    private String[] titl_lug_fav;
    private int[] drawable_lug_fav;
    private TabLayout tabs;
    private ViewPager viewPager;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private boolean flag_cercanos = true;
    private boolean flag_favs = true;
    private LinearLayout layout_cond;
    private boolean ordenar = true;
    private ArrayList<Conductores> cond_cercanos;
    private ArrayList<Conductores> cond_favs;
    private String Url = "http://168.254.1.104/Sitio/";
    private CircularImageView btn_tipo_vehc;
    private TextView cant_cond;
    private String tipo_vehc = "Carro";
    private RelativeLayout layout_topbar;
    private RelativeLayout desplazar;
    private boolean flag_swipe = false;
    private Resources resources;
    private float alto;
    private float ybottom;
    private float ytop;
    private float ytouch;
    LocationManager locationManager;
    double longitudeNetwork = 1, latitudeNetwork = 1;
    int PETICION_PERMISO_LOCALIZACION;
    int PETICION_PERMISO_LLAMADA;
    String ubicacionActual;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main2);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.viewPag_cond);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tab_conductores);
        tabs.setupWithViewPager(viewPager);
        progress = findViewById(R.id.progress);
        layout_topbar = findViewById(R.id.layout_topbar);
        layout_cond = findViewById(R.id.layout_cond);
        foto_zoom = findViewById(R.id.foto_zoom);
        drawer_layout = findViewById(R.id.drawer_layout);
        nav_view = findViewById(R.id.navigation_view);
        content = findViewById(R.id.content);
        nav_view.bringToFront();
        animation_NavView();
        nav_control = Navigation.findNavController(this, R.id.navHostFragment);
        NavigationUI.setupWithNavController(nav_view, nav_control);
        nav_view.setNavigationItemSelectedListener(this);
        //crear_Sesion();
        //cargar_Datos();
        btn_tipo_vehc = findViewById(R.id.tipo_vehc);
        cant_cond = findViewById(R.id.cant_cond);
        snackbar = new CustomSnackbar(MainActivity.this);
        snackbar_action = new CustomSnackbar(MainActivity.this);
        // cargar_Favs();


        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    cant_cond.setText(getCantCercanos() + " conductores");
                } else {
                    cant_cond.setText(getCantFavs() + " conductores");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        foto_zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                foto_zoom.animate()
                        .translationX(xfoto)
                        .translationY(yfoto)
                        .alpha(0f)
                        .scaleX(0f)
                        .scaleY(0f)
                        .setDuration(400)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                foto_zoom.setVisibility(View.GONE);
                            }
                        });
            }
        });

        desplazar = findViewById(R.id.desplazar);
        resources = getResources();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        alto = displayMetrics.heightPixels;
        boolean hasMenuKey = ViewConfiguration.get(getApplicationContext()).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

        if (!hasMenuKey && !hasBackKey) {
            ybottom = (float) (alto * 0.915);
        } else
            ybottom = (float) (alto * 0.89);
        //ytop = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 670f, resources.getDisplayMetrics());
        ytop = (float) (alto * 0.066);
        desplazar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        swipeCond(ytouch, event.getRawY() - 180);
                        ytouch = event.getRawY() - 180;
                        if (ytouch < ybottom && ytouch > ytop)
                            layout_cond.setY(ytouch);
                        break;

                    case MotionEvent.ACTION_UP:
                        animar(layout_cond);
                        break;

                    default:
                        return true;

                }
                return true;
            }
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PETICION_PERMISO_LOCALIZACION);


        toggleNetworkUpdates();
    }

    public void toggleNetworkUpdates() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 6 * 1000, 1, locationListenerNetwork);


    }

    private final LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            latitudeNetwork = location.getLatitude();
            longitudeNetwork = location.getLongitude();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //if(10 == location.getAccuracy())
                    // Toast.makeText(getApplicationContext(), String.valueOf(location.getAccuracy()), Toast.LENGTH_LONG).show();
                    // obtenerDireccionCompleta(latitudeNetwork, longitudeNetwork);
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

    public void obtenerDireccionCompleta(double lat, double lon) {
        ciudades = new String[]{"La Habana", "Santiago de Cuba", "Camagüey", "Holguín", "Guantánamo", "Santa Clara", "Las Tunas", "Bayamo", "Cienfuegos", "Pinar del Río", "Matanzas"
                , "Ciego de Ávila", "Sancti Spíritus", "Manzanillo", "Cárdenas", "Palma Soriano", "Moa", "Morón", "Florida", "Contramaestre", "Artemisa", "Nueva Gerona", "Trinidad"
                , "Colón", "Baracoa", "Güines", "Placetas", "Nuevitas", "Sagua la Grande", "San José de las Lajas", "Banes", "San Luis", "Puerto Padre", "San Antonio de los Baños"
                , "Caibarién", "Cabaiguán", "Mayarí", "San Cristóbal", "Vertientes", "Jagüey Grande", "Consolación del Sur", "Jovellanos", "Amancio", "Güira de Melena", "Cumanayagua"
                , "Jatibonico", "Niquero", "San Germán", "Sagua de Tánamo", "Bauta", "La Maya", "Guanajay", "Colombia", "Jiguaní", "Manicaragua", "Camajuaní", "Guisa", "Jobabo"};

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
                try {
                    final String myresponse = response.body().string();
                    MainActivity.this.runOnUiThread(new Runnable() {
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
                            ubicacionActual = myresponse.substring(p - s, p - 2) + ", ";
                            InicioFragment.insertarUbicacion(ubicacionActual, city);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    @Override
    protected void onStart() {
        progress.setVisibility(View.VISIBLE);
        //find_cercanos.run();
        cargar_titl_lug_Fav();
        cargar_drawb_lug_Fav();
        cargar_list_lug_Fav();
        refresh_TabsHeader();
        super.onStart();

    }

    @Override
    protected void onPause() {
        nav_view.setCheckedItem(item_menu);
        super.onPause();
    }

    @Override
    protected void onStop() {
        handler.removeCallbacks(find_cercanos);
        super.onStop();
    }

    private Runnable find_cercanos = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, 30000);
            cargar_Cercanos();
        }
    };

    /*
        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {

            if (nav_view.getCheckedItem().getItemId() == R.id.nav_inicio ){
                if (keyCode == KeyEvent.KEYCODE_BACK){
                    dialogCloseApp();
                    return true;
                }
            }

            return super.onKeyDown(keyCode, event);
        }
    */
    @Override
    public void onBackPressed() {

        if (drawer_layout.isDrawerVisible(GravityCompat.START))
            drawer_layout.closeDrawer(GravityCompat.START);
        else {
            if (flag_swipe) {
                flag_swipe = false;
                animar(layout_cond);
            } else if (nav_view.getCheckedItem().getItemId() == R.id.nav_inicio)
                dialogCloseApp();
            else
                super.onBackPressed();
        }
        item_menu = nav_view.getCheckedItem().getItemId();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                foto_uri = result.getUri();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception ex = result.getError();
                Toast.makeText(MainActivity.this, "posible error is:" + ex, Toast.LENGTH_SHORT).show();
            }
        }
    }
    //Cargar Datos Sesion///////////////////////////////////////////////////////////////////////////

    private void crear_Sesion() {
        Bundle bundle = this.getIntent().getExtras();
        json = bundle.getString("json");
        sesion = new CSesion(json);
    }

    private void cargar_Ubicacion() {
    }

    private void cargar_Imagenes() {
        Picasso.get().load(sesion.get_Foto()).into(foto_zoom);

    }


    public void cerrar_Sesion() {
        SharedPreferences pref = getSharedPreferences("pref_sesion", Context.MODE_PRIVATE);
        pref.edit().clear().commit();
        /*Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);*/
        finish();
    }


    //Fuciones Complementarias//////////////////////////////////////////////////////////////////////
    private String nameCapitalize(String name) {

        String[] array = name.toLowerCase().split(" ");
        String new_name = "";

        for (int i = 0; i < array.length; i++)
            new_name = new_name + " " + array[i].substring(0, 1).toUpperCase() + array[i].substring(1);

        return new_name.trim();
    }

    public String getURL() {
        return Url;
    }

    public void setURL(String URL) {
        Url = URL;
    }

    public Uri getURI() {
        return foto_uri;
    }

    public void setURI(Uri uri) {
        foto_uri = uri;
    }

    public void setFlagConds(boolean flag_cercanos, boolean flag_favs) {
        this.flag_cercanos = flag_cercanos;
        this.flag_favs = flag_favs;
    }

    public boolean Conexion() {
        ConnectivityManager conect = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = conect.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = conect.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())) {
            return true;
        } else
            return false;

    }


    public CustomSnackbar getSnackbarAction() {
        return snackbar_action;
    }

    public void hideSnackbar() {
        snackbar.dismiss();
        snackbar_action.dismiss();

    }

    public void Msg(String msg) {
        hideSnackbar();
        snackbar.message(msg);
        snackbar.textTypeface(Typeface.SANS_SERIF);
        snackbar.textColorRes(R.color.colorPrimary);
        snackbar.backgroundColorRes(R.color.grayb);
        snackbar.padding(35);
        snackbar.cornerRadius(15);
        snackbar.duration(Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public void msgError(String error, final int type) {
        int duration = 8000;
        hideSnackbar();
        if (!Conexion()) {
            snackbar_action.message("SIN CONEXIÓN!");
            snackbar_action.textTypeface(Typeface.SANS_SERIF);
            snackbar_action.textColorRes(R.color.light);
            snackbar_action.actionTextColorRes(R.color.colorPrimary);
            snackbar_action.backgroundColorRes(R.color.grayb);
            snackbar_action.padding(35);
            snackbar_action.cornerRadius(15);
            snackbar_action.duration(duration);
            snackbar_action.withAction("Conectar", new Function1<Snackbar, Unit>() {
                @Override
                public Unit invoke(Snackbar snackbar) {
                    startActivity(new Intent(Settings.ACTION_DATA_USAGE_SETTINGS));
                    return null;
                }
            });
            snackbar_action.show();

        } else if (error.contains("TimeoutError")) {

            snackbar_action.message("ERROR AL CONECTAR!");
            snackbar_action.textTypeface(Typeface.SANS_SERIF);
            snackbar_action.textColorRes(R.color.light);
            snackbar_action.actionTextColorRes(R.color.colorPrimary);
            snackbar_action.backgroundColorRes(R.color.gray);
            snackbar_action.padding(35);
            snackbar_action.cornerRadius(15);
            snackbar_action.duration(duration);
            snackbar_action.withAction("Reintentar", new Function1<Snackbar, Unit>() {
                @Override
                public Unit invoke(Snackbar snackbar) {
                    progress.setVisibility(View.VISIBLE);
                    if (type == 0) {
                        if (cond_favs.size() != 0)
                            cargar_Cercanos();
                        else
                            cargar_Conds();

                    } else
                        setInfo(Url + "set_Info.php", keyname_temp, data_temp);

                    return null;
                }
            });
            snackbar_action.show();

        } else {

            snackbar_action.message("ERROR INESPERADO!");
            snackbar_action.textTypeface(Typeface.SANS_SERIF);
            snackbar_action.textColorRes(R.color.light);
            snackbar_action.actionTextColorRes(R.color.colorPrimary);
            snackbar_action.backgroundColorRes(R.color.gray);
            snackbar_action.padding(35);
            snackbar_action.cornerRadius(15);
            snackbar_action.duration(duration);
            snackbar_action.withAction("Reintentar", new Function1<Snackbar, Unit>() {
                @Override
                public Unit invoke(Snackbar snackbar) {
                    progress.setVisibility(View.VISIBLE);
                    if (type == 0) {
                        if (cond_favs.size() != 0)
                            cargar_Cercanos();
                        else
                            cargar_Conds();

                    } else
                        setInfo(Url + "set_Info.php", keyname_temp, data_temp);
                    return null;
                }
            });
            snackbar_action.show();


        }


    }

    private void dialogCloseSesion() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Estas seguro de que quieres Cerrar Sesión?")
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cerrar_Sesion();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nav_view.setCheckedItem(item_menu);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public double getLatitudeNetwork() {
        return latitudeNetwork;
    }

    public double getLongitudeNetwork() {
        return longitudeNetwork;
    }

    private void dialogCloseApp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Estas seguro de que quieres salir de la aplicación?")
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public void topBarColor(int color) {
        layout_topbar.setBackgroundColor(color);
    }

    public void LayoutCond(int visibility) {
        layout_cond.setVisibility(visibility);
        flag_swipe = false;
        animar(layout_cond);
    }

    public void zoomFoto(float x, float y, String url) {

        Picasso.get().load(url).into(foto_zoom);
        xfoto = x;
        yfoto = y;
        foto_zoom.setX(xfoto);
        foto_zoom.setY(yfoto);
        foto_zoom.setAlpha(0f);
        foto_zoom.setScaleX(0f);
        foto_zoom.setScaleY(0f);
        foto_zoom.animate()
                .translationX(0f)
                .translationY(0f)
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .setDuration(400)
                .setListener(null);
        foto_zoom.setVisibility(View.VISIBLE);
    }

    public int[] getDimScreen() {

        int[] dims = new int[2];
        dims[0] = getWindowManager().getDefaultDisplay().getWidth();
        dims[1] = getWindowManager().getDefaultDisplay().getHeight();

        return dims;
    }

    public float[] getLocationView(View view) {

        int width = view.getWidth();
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int[] dims = getDimScreen();
        float[] coords = new float[2];
        coords[0] = (float) (location[0] + width / 2.0 - dims[0] / 2.0);
        coords[1] = (float) (location[1] - dims[1] / 2.0);

        return coords;
    }


    //Navigation View///////////////////////////////////////////////////////////////////////////////
    private void animation_NavView() {
        drawer_layout.setScrimColor(getResources().getColor(R.color.colorPrimary));
        drawer_layout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - 0.7f);
                final float offsetScale = 1 - diffScaledOffset;
                content.setScaleX(offsetScale);
                content.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = content.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                content.setTranslationX(xTranslation);
            }
        });
    }

    public void clickMenu(View view) {
        if (drawer_layout.isDrawerVisible(GravityCompat.START))
            drawer_layout.closeDrawer(GravityCompat.START);
        else drawer_layout.openDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_inicio: {
                nav_control.navigate(R.id.nav_inicio);
                item_menu = R.id.nav_inicio;
                break;
            }
            case R.id.nav_perfil: {
                nav_control.navigate(R.id.nav_perfil);
                item_menu = R.id.nav_perfil;
                break;
            }
           /* case R.id.nav_notificacion: {
                item_menu = nav_view.getCheckedItem();
                nav_control.navigate(R.id.nav_notificacion);
                break;
            }
            case R.id.nav_ajustes: {
                item_menu = nav_view.getCheckedItem();
                nav_control.navigate(R.id.nav_ajustes);
                break;
            }*/
            case R.id.compartir_app: {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
                String aux = "Descarga la app\n";
                aux = aux + "https://play.google.com/store/apps/" + getBaseContext().getPackageName();
                intent.putExtra(Intent.EXTRA_TEXT, aux);
                startActivity(intent);
                break;
            }
            case R.id.cerrar_sesion: {
                dialogCloseSesion();
                break;
            }
            default: {
                break;
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START);
        return true;

    }

    //FRAGMENTOS////////////////////////////////////////////////////////////////////////////////////

    /*********************************************INICIO*******************************************/

    //Lugares Favoritos-----------------------------------------------------------------------------
    public void cargar_titl_lug_Fav() {
        SharedPreferences pref = getSharedPreferences("pref_lug_fav", Context.MODE_PRIVATE);
        String[] titulos_predt = {"Mapa", "Casa", "Trabajo", "Escuela"};
        String[] titulos = (pref.getString("titulos", "")).trim().split(" ");
        int length1 = titulos_predt.length;
        int length2 = 0;
        if (!titulos[0].isEmpty()) {
            length2 = titulos.length;
        }
        titl_lug_fav = new String[length1 + length2 + 1];
        System.arraycopy(titulos_predt, 0, titl_lug_fav, 0, length1);
        System.arraycopy(titulos, 0, titl_lug_fav, length1, length2);
        titl_lug_fav[titl_lug_fav.length - 1] = "Añadir";

    }

    public void cargar_drawb_lug_Fav() {
        drawable_lug_fav = new int[titl_lug_fav.length];
        drawable_lug_fav[0] = R.drawable.ic_search;
        drawable_lug_fav[1] = R.drawable.ic_home;
        drawable_lug_fav[2] = R.drawable.ic_work;
        drawable_lug_fav[3] = R.drawable.ic_school;
        for (int i = 4; i < titl_lug_fav.length - 1; i++) {
            drawable_lug_fav[i] = R.drawable.ic_pin_location;
        }
        drawable_lug_fav[titl_lug_fav.length - 1] = R.drawable.ic_add_location;
    }

    public void cargar_list_lug_Fav() {
        list_lug_fav = new ArrayList<Lug_Fav>();
        int background = R.drawable.mapa;
        for (int i = 0; i < titl_lug_fav.length; i++) {
            if (i == 1)
                background = R.color.transp;
            list_lug_fav.add(new Lug_Fav(drawable_lug_fav[i], titl_lug_fav[i], background));
        }
    }

    public ArrayList<Lug_Fav> get_list_lug_Fav() {
        return list_lug_fav;
    }

    public String get_lug_Fav(String lugar) {

        SharedPreferences pref = getSharedPreferences("pref_lug_fav", Context.MODE_PRIVATE);
        return pref.getString(lugar, "");
    }

    public void add_titl_lug_Fav(String lugar) {
        SharedPreferences pref = getSharedPreferences("pref_lug_fav", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("titulos", pref.getString("titulos", "") + " " + lugar);
        editor.apply();
    }

    public void edit_titl_lug_Fav(String lug_old, String lug_new) {
        SharedPreferences pref = getSharedPreferences("pref_lug_fav", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        String titulos = Objects.requireNonNull(pref.getString("titulos", "")).replace(" " + lug_old, " " + lug_new);
        editor.putString("titulos", titulos);
        editor.apply();

    }

    public void delet_lug_Fav(String titl) {
        String titulos;
        SharedPreferences pref = getSharedPreferences("pref_lug_fav", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        titulos = pref.getString("titulos", "").replace(" " + titl, "");
        editor.putString("titulos", titulos);
        editor.remove(titl);
        editor.apply();


    }

    //Conductores-----------------------------------------------------------------------------------

    public void actualizar() {
        sectionsPagerAdapter.notifyItemChanged(flag_cercanos, flag_favs);
        sectionsPagerAdapter.notifyDataSetChanged();
        refresh_TabsHeader();
    }

    public ArrayList<Conductores> getFavs() {
        ArrayList<Conductores> array = new ArrayList<>();
        for (int i = 0; i < cond_favs.size(); i++) {
            if (cond_favs.get(i).getTipo_vehc().contentEquals(tipo_vehc)) {
                array.add(cond_favs.get(i));
            }
        }
        return array;
    }

    public ArrayList<Conductores> getCercanos() {
        ArrayList<Conductores> array = new ArrayList<>();
        for (int i = 0; i < cond_cercanos.size(); i++) {
            if (cond_cercanos.get(i).getTipo_vehc().contentEquals(tipo_vehc)) {
                array.add(cond_cercanos.get(i));
            }
        }
        return array;
    }

    public int getCantFavs() {
        return getFavs().size();
    }

    public int getCantCercanos() {
        return getCercanos().size();
    }

    public String favs_Ids() {
        SharedPreferences pref = getSharedPreferences("pref_cond_fav", Context.MODE_PRIVATE);
        return pref.getString("ids", "");
    }

    public String cercanos_Ids() {
        return "67 69";
    }

    private void cargar_Favs() {
        cond_favs = new ArrayList<Conductores>();
        buscar_Conds(Url + "buscar_cond.php", favs_Ids(), true);
    }

    private void cargar_Cercanos() {
        cond_cercanos = new ArrayList<Conductores>();
        buscar_Conds(Url + "buscar_cond.php", cercanos_Ids(), false);
    }

    private void cargar_Conds() {
        cargar_Favs();
        cargar_Cercanos();

    }

    public void buscar_Conds(String URL, final String ids, final boolean tipo_cond) {
        StringRequest string_request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    JSONObject jresponse = null;
                    try {
                        jresponse = new JSONObject(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    JSONArray json = jresponse.optJSONArray("conductor");
                    Conductores conductor = new Conductores();
                    try {
                        for (int i = 0; i < json.length(); i++) {
                            conductor = new Conductores();
                            JSONObject jsonObject = null;
                            jsonObject = json.getJSONObject(i);

                            conductor.setId(jsonObject.optInt("id"));
                            conductor.setFoto(jsonObject.getString("foto"));
                            conductor.setFoto_vehc(jsonObject.getString("foto_vehc"));
                            conductor.setNombre(nameCapitalize(jsonObject.optString("nombre")));
                            conductor.setTipo_vehc(jsonObject.optString("tipo_vehc"));
                            conductor.setDistancia(2.1f);
                            conductor.setValoracion(3);
                            if (favs_Ids().contains("" + conductor.getId())) {
                                conductor.setFavorito(true);
                            } else {
                                conductor.setFavorito(false);
                            }

                            if (tipo_cond) {
                                cond_favs.add(conductor);
                            } else {
                                cond_cercanos.add(conductor);
                            }

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();

                        if (!tipo_cond) {
                            progress.setVisibility(View.GONE);
                            msgError("Error inesperado", 0);
                        }
                    }

                }
                actualizar();
                if (!tipo_cond) {
                    progress.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (!tipo_cond) {
                    progress.setVisibility(View.GONE);
                    msgError(error.toString(), 0);
                }


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("ids", ids);
                return parameters;
            }
        };
        RequestQueue request_queue = Volley.newRequestQueue(this);
        request_queue.add(string_request);
    }

    public void añadir_Fav(String id) {
        String favs = favs_Ids() + " " + id;
        SharedPreferences pref = getSharedPreferences("pref_cond_fav", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("ids", favs);
        editor.apply();

        if (flag_cercanos) {
            for (int i = 0; i < cond_cercanos.size(); i++) {
                if (id.contentEquals("" + cond_cercanos.get(i).getId())) {
                    cond_cercanos.get(i).setFavorito(true);
                    break;
                }

            }
        }
        buscar_Conds(Url + "buscar_cond.php", id, true);

    }

    public void delet_Fav(String id) {
        String favs;
        SharedPreferences pref = getSharedPreferences("pref_cond_fav", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        favs = pref.getString("ids", "").replace(" " + id, "");
        editor.putString("ids", favs);
        editor.apply();

        for (int i = 0; i < cond_favs.size(); i++) {
            if (id.contentEquals("" + cond_favs.get(i).getId())) {
                cond_favs.remove(i);
                break;
            }

        }
        for (int i = 0; i < cond_cercanos.size(); i++) {
            if (id.contentEquals("" + cond_cercanos.get(i).getId())) {
                cond_cercanos.get(i).setFavorito(false);
                break;
            }

        }

        actualizar();

    }

    public void Ordenar(View view) {
        PopupMenu menu = new PopupMenu(this, view);
        menu.setOnMenuItemClickListener(this);
        menu.inflate(R.menu.ordenar);
        MenuItem ord_dist = menu.getMenu().getItem(1);
        MenuItem ord_val = menu.getMenu().getItem(2);
        if (ordenar) {
            ord_dist.setChecked(true);
        } else {
            ord_val.setChecked(true);
        }
        menu.show();

    }


    public void Tipo_Vehc(View view) {

        if (tipo_vehc.contentEquals("Carro")) {
            btn_tipo_vehc.setImageResource(R.drawable.ic_car);
            tipo_vehc = "Moto";

        } else {
            btn_tipo_vehc.setImageResource(R.drawable.ic_motor);
            tipo_vehc = "Carro";
        }
        sectionsPagerAdapter.notifyItemChanged(true, true);
        sectionsPagerAdapter.notifyDataSetChanged();
        refresh_TabsHeader();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        menuItem.setChecked(true);
        int pos = tabs.getSelectedTabPosition();
        switch (menuItem.getItemId()) {
            case R.id.ord_dist:
                ordenar = true;
                actualizar();
                return true;
            case R.id.ord_val:
                ordenar = false;
                actualizar();
                return true;

        }
        return false;
    }

    public void refresh_TabsHeader() {
        tabs.getTabAt(0).setText("Cercanos");
        tabs.getTabAt(1).setText("Favoritos");

        if (tipo_vehc.contentEquals("Carro")) {
            tabs.getTabAt(0).setIcon(R.drawable.ic_car);
            tabs.getTabAt(1).setIcon(R.drawable.ic_car);

        } else {
            tabs.getTabAt(0).setIcon(R.drawable.ic_motor);
            tabs.getTabAt(1).setIcon(R.drawable.ic_motor);

        }

    }

    private void swipeCond(float old_Y, float new_Y) {
        float dy = old_Y - new_Y;
        if (dy > 0)
            flag_swipe = true;
        else if (dy == 0)
            flag_swipe = !flag_swipe;
        else
            flag_swipe = false;

    }

    private void animar(LinearLayout layoutAnimado) {
        ObjectAnimator objectAnimator;

        if (flag_swipe) {
            objectAnimator = ObjectAnimator.ofFloat(layoutAnimado, "Y", ytop);
            objectAnimator.setDuration(300);
            objectAnimator.start();

        } else {
            objectAnimator = ObjectAnimator.ofFloat(layoutAnimado, "Y", ybottom);
            objectAnimator.setDuration(300);
            objectAnimator.start();

        }

    }

    /*********************************************PERFIL*******************************************/
    public String getId() {
        return sesion.get_Id();
    }

    public String getFoto() {
        return sesion.get_Foto();
    }

    public String getName() {
        return nameCapitalize(sesion.get_Nombre());
    }

    public String getUser() {
        return sesion.get_Usuario();
    }

    public String getPswd() {
        return sesion.get_Contrasena();
    }

    public String getEmail() {
        return sesion.get_Correo();
    }

    public int getPhone() {
        return 1;
    }

    public void setInfo(String URL, final String keyname, final String newdata) {
        progress.setVisibility(View.VISIBLE);
        snackbar.dismiss();
        keyname_temp = keyname;
        data_temp = newdata;

        StringRequest string_request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (!response.isEmpty()) {
                    progress.setVisibility(View.GONE);
                    Msg(response);

                } else {
                    //animation
                    Msg("GUARDADO CON ÉXITO!");
                    progress.setVisibility(View.GONE);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progress.setVisibility(View.GONE);
                msgError(error.toString(), 1);

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("id", "67");//getId();
                parameters.put("keyname", keyname);
                parameters.put("newdata", newdata);

                return parameters;
            }
        };
        RequestQueue request_queue = Volley.newRequestQueue(this);
        request_queue.add(string_request);
    }


}