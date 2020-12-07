package com.example.mapas;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tab1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab1 extends Fragment {

    boolean prosc = false;

    double[] idUsuario;
    double[] latitud;
    double[] longitud;
    double mlat, mlon;
    String[] nombres;
    String[] faceS;
    String[] imagen;
    View b;
    int pos = 0;

    adapter2 adp;
    RecyclerView vista1;
    final ArrayList<Cardf> locations = new ArrayList<>();
    String title;
    int cant;


    public static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Context context;

    private String mParam1;
    private String mParam2;


    public Tab1() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Tab1 newInstance(String param1, String param2) {
        Tab1 fragment = new Tab1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }


    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_tab1, container, false);

        mlat = ((MapsActivity) getActivity()).getLat();
        mlon = ((MapsActivity) getActivity()).getLon();
        vista1 = view.findViewById(R.id.choferes);

        vista1.setHasFixedSize(true);
        vista1.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));


        adp = new adapter2(locations);

        vista1.setAdapter(adp);
        adp.setOnItemClickListener(new adapter2.OnItemCLickListener() {
            @Override
            public void OnItemClick(int position) {
                title = locations.get(position).getTitle();
                ((MapsActivity) getActivity()).mostrarChapa(title, 1);
                adp.notifyItemChanged(position);
            }
        });


        cantDrivers(5);

        return view;

    }


    public void cantDrivers(double radio) {
        final int[] p = {0};
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://168.254.1.104/Sitio/extraerCoorCerc.php" + "?radio=" + radio + "&tipoVehc=1", new com.android.volley.Response.Listener<JSONArray>() {
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
                cant = p[0];

                if (cant > 0) {
                    //Toast.makeText(context, String.valueOf(cant)+" ciriasr", Toast.LENGTH_SHORT).show();
                    extraerDrivCoordenadas(radio);
                } else
                    Toast.makeText(context, "No hay chóferes cercanos en este momento...", Toast.LENGTH_LONG).show();

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

    public void extraerDrivCoordenadas(double radio) {
        idUsuario = new double[cant];
        latitud = new double[cant];
        longitud = new double[cant];
        imagen = new String[cant];
        nombres = new String[cant];
        faceS = new String[cant];


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://168.254.1.104/Sitio/extraerCoorCerc.php" + "?radio=" + radio + "&tipoVehc=1", new com.android.volley.Response.Listener<JSONArray>() {
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
                        imagen[i] = jsonObject.getString("foto_vehc");
                        nombres[i] = jsonObject.getString("nombre");
                        faceS[i] = jsonObject.getString("foto");

                    } catch (JSONException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                Toast.makeText(context, "premostrar", Toast.LENGTH_LONG).show();
                mostrarChofer();


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

    public void mostrarChofer() {
double distancia;
        for (int i = 0; i < cant; i++) {
            distancia=((MapsActivity) getActivity()).distancia(mlat, mlon,latitud[i], longitud[i]);
            locations.add(new Cardf(imagen[i], faceS[i], String.valueOf(idUsuario[i]), nombres[i], distancia + " km", (int)Math.round((distancia/ 13)*60)+" min"));
            adp.notifyDataSetChanged();
        }

    }

}
