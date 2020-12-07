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

public class Comunication {

    int IdPropio;
    Context context;
    String URL_instertar;
    String URL_extraer;
    String mensaje=null;
    int idMensaje;
    int idMensajeActual;

    public Comunication(int idPropio, Context context, String URL_instertar, String URL_extraer) {
        IdPropio = idPropio;
        this.context = context;
        this.URL_instertar = URL_instertar;
        this.URL_extraer = URL_extraer;
    }


    public void insertarMensaje(final String usuarioDestino, final String mensaje) {
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
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    public void extraerMensaje() {

        final String[] salida = new String[1];
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_extraer + "?usuarioDestino=" + IdPropio + "", new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        salida[0] = jsonObject.getString("mensaje");
                        idMensaje = jsonObject.getInt("idMensaje");
                    } catch (JSONException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }if (idMensaje != idMensajeActual) {
                    idMensajeActual = idMensaje;
                    mensaje=salida[0];}
                else
                    mensaje=null;

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

    public String getMensaje() {
        return mensaje;
    }

    public int getIdMensaje() {
        return idMensaje;
    }

    public int getIdMensajeActual() {
        return idMensajeActual;
    }
}
