package com.example.mapas;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Tab2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Tab2 extends Fragment {

    boolean prosc = false;

    double[] idUsuario;
    double[] latitud;
    double[] longitud;
    double mlat, mlon;
    String[] nombres;
    String[] faceS;
    String[] imagen;
    int pos = 0;

    adapter2 adp;
    RecyclerView vista1;
    final ArrayList<Cardf> locations = new ArrayList<>();
    String title;
    int cant;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Context context;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Tab2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab2.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab2 newInstance(String param1, String param2) {
        Tab2 fragment = new Tab2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);

        mlat = ((MapsActivity) getActivity()).getLat();
        mlon = ((MapsActivity) getActivity()).getLon();
        vista1 = view.findViewById(R.id.choferes2);
        vista1.setHasFixedSize(true);
        vista1.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));


        adp = new adapter2(locations);

        vista1.setAdapter(adp);
        adp.setOnItemClickListener(new adapter2.OnItemCLickListener() {
            @Override
            public void OnItemClick(int position) {
                title = locations.get(position).getTitle();
                ((MapsActivity) getActivity()).mostrarChapa(title, 2);
                adp.notifyItemChanged(position);
            }
        });


        cantDrivers(5);

        return view;

    }


    public void cantDrivers(double radio) {
        final int[] p = {0};
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://168.254.1.104/Sitio/extraerCoorCerc.php" + "?radio=" + radio + "&tipoVehc=2", new com.android.volley.Response.Listener<JSONArray>() {
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


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://168.254.1.104/Sitio/extraerCoorCerc.php" + "?radio=" + radio + "&tipoVehc=2", new com.android.volley.Response.Listener<JSONArray>() {
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
            locations.add(new Cardf(imagen[i], faceS[i], String.valueOf(idUsuario[i]), nombres[i], distancia+" km" , (int)Math.round((distancia/ 13)*60)+" min"));
            adp.notifyDataSetChanged();
        }

    }

}
