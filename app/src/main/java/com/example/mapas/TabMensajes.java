package com.example.mapas;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabMensajes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabMensajes extends Fragment {


    int PETICION_PERMISO_LOCALIZACION;

    double latitudUs, longitudUs;

    RecyclerView vista1;
    adapter adp;
    int cantMen;
    int id;
    int IdPropio=14;
    String city;
    String direccionActual;
    String imagen = null;

    Handler handler = new Handler();

    TextView tv1, tv2;
    final ArrayList<Msg> locations = new ArrayList<>();

    String[] mensaje;
    String[] nombres;
    String[] foto;
    String[] fecha;
    int[] idMensaje;
    int[] tipo;
    int[] uOrigen;
    String mensj;
    int idP;
    String fechaR, origen, destino;
    boolean flag = true;
    Context context;
    double Olatitud, Olongitud;

    Bitmap[] caras;
    int aux = 0;
    int pos = 0;
    boolean proc = false;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TabMensajes(String name) {
        Bundle arg= new Bundle();
        arg.putString("name", name);
        this.setArguments(arg);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabMensajes.
     */
    /*// TODO: Rename and change types and number of parameters
    public static TabMensajes newInstance(String param1, String param2) {
        TabMensajes fragment = new TabMensajes();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

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
        View view = inflater.inflate(R.layout.fragment_tab_mensajes, container, false);

        vista1=view.findViewById(R.id.recicMensajes);
        vista1.setHasFixedSize(true);
        vista1.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        IdPropio=((SConductor) getActivity()).devolverIdPropio();

        adp = new adapter(locations);


        vista1.setAdapter(adp);


        adp.setOnItemClickListener(new adapter.OnItemCLickListener() {
            @Override
            public void OnItemClick(int position) {
                adp.notifyItemChanged(position);
            }

            @Override
            public void aceptar(int position) {
                id = Integer.valueOf(locations.get(position).getID());
                origen=String.valueOf(locations.get(position).getDirec());
                destino=String.valueOf(locations.get(position).getDire2());
                fechaR=String.valueOf(locations.get(position).getFecha());
                extraerEspCoordenada(id);
            }

            @Override
            public void rechazar(int position) {
                locations.remove(position);
                adp.notifyItemRemoved(position);
            }


        });

        preparetion();
        return view;
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

    public void extraerEspCoordenada(int idUsuario) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://168.254.1.104/Sitio/extraerEspeCoordenada.php?idUsuario=" + idUsuario, new Response.Listener<JSONArray>() {
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
                verMapa(Olatitud, Olongitud);


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

    void visuali() {
        ((SConductor) getActivity()).addCounter(2,String.valueOf(cantMen));
        for (int i = 0; i < cantMen; i++) {
            String texto = mensaje[i];
            int t = texto.length();
            int k = texto.indexOf("-");
            int l = texto.indexOf("(");
            int p = texto.indexOf("/");
            String d1 = texto.substring(0, k);
            String d2 = texto.substring(k + 2, l);
            latitudUs = Double.parseDouble(mensaje[i].substring(l + 1, p));
            longitudUs = Double.parseDouble(mensaje[i].substring(p + 1, t - 2));
            locations.add(new Msg(foto[i], String.valueOf(uOrigen[i]), d1, nombres[i], " "+d2,fecha[i]));
            leido(idMensaje[i]);
            adp.notifyDataSetChanged();
        }


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

    public void extraerMensaje(String tipe) {
        mensaje = new String[cantMen];
        nombres = new String[cantMen];
        foto = new String[cantMen];
        fecha = new String[cantMen];
        idMensaje = new int[cantMen];
        tipo = new int[cantMen];
        uOrigen = new int[cantMen];
        Toast.makeText(context,String.valueOf("cant"),Toast.LENGTH_LONG).show();

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
        StringRequest stringRequest = new StringRequest(Request.Method.POST,  "http://168.254.1.104/Sitio/cantMensajes.php?usuarioDestino=" + IdPropio + "&tipo=" + tipe, new Response.Listener<String>() {
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

    public void verMapa(double Olatitud, double Olongitud) {
        Intent intent = new Intent(context, MapsActivity2.class);
        intent.putExtra("id", id);
        intent.putExtra("idP", IdPropio);
        intent.putExtra("origen", origen);
        intent.putExtra("fecha", fechaR);
        intent.putExtra("destino", destino);
        intent.putExtra("xlatitud", Olatitud);
        intent.putExtra("xlongitud", Olongitud);
        intent.putExtra("latitud", latitudUs);
        intent.putExtra("longitud", longitudUs);
        startActivity(intent);

    }

}
