package com.example.mapas;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

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

public class Coordenadas {
    int IdPropio;
    int tipoUsurario;
    int cantDrivers;
    String URL_subir;
    double[] idUsuario;
    double[] latitud;
    double[] longitud;
    String URL_bajarUn;
    String URL_bajarAll;
    Context context;

    public Coordenadas(int idPropio, int tipoUsurario, String URL_subir, String URL_bajarUn, String URL_bajarAll, Context context) {
        IdPropio = idPropio;
        this.tipoUsurario = tipoUsurario;
        this.URL_subir = URL_subir;
        this.URL_bajarUn = URL_bajarUn;
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

    public void extraerDrivCoordenadas() {
         idUsuario = new double[cantDrivers];
        latitud = new double[cantDrivers];
        longitud = new double[cantDrivers];

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_bajarAll, new Response.Listener<JSONArray>() {
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


    public void cantDrivers() {
        final int[] p = {0};
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_bajarAll, new Response.Listener<JSONArray>() {
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
                cantDrivers=p[0];

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
