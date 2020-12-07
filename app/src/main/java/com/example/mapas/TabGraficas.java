package com.example.mapas;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabGraficas#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabGraficas extends Fragment {
    Context context;
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
    boolean first = true;

    LineChart chartDinero;
    LineChart chartDistancia;
    LineChart chartViajes;
    String idPropio = "14";
    CircularImageView face, vehc;
    ViewPager viewPager;
    CustomMarkerView mv;
    Paint paint = new Paint();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TabGraficas(String name) {
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
     * @return A new instance of fragment TabGraficas.
     */
    // TODO: Rename and change types and number of parameters
    /*public static TabGraficas newInstance(String param1, String param2) {
        TabGraficas fragment = new TabGraficas("graficas");
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
        View view = inflater.inflate(R.layout.fragment_tab_graficas, container, false);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mv = new CustomMarkerView(getActivity().getApplicationContext(), R.layout.marker, displayMetrics.widthPixels - 50);
        chartDinero = view.findViewById(R.id.chartPrecio);
        chartDistancia = view.findViewById(R.id.chartDistancia);
        chartViajes = view.findViewById(R.id.chartViajes);
       // Toast.makeText(context, "graf", Toast.LENGTH_LONG).show();
        prepararCharts(chartDinero);
        prepararCharts(chartDistancia);
        prepararCharts(chartViajes);
        cantidadFilas("14");
        return view;
    }

    void prepararCharts(LineChart chartDinero) {
        chartDinero.setDragEnabled(true);
        chartDinero.setScaleEnabled(true);
        chartDinero.getAxisLeft().setEnabled(true);
        chartDinero.getAxisLeft().setDrawGridLines(false);
        chartDinero.getAxisRight().setEnabled(false);
        chartDinero.getXAxis().setEnabled(true);
        chartDinero.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chartDinero.getXAxis().setDrawGridLines(false);
        chartDinero.getDescription().setEnabled(false);


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    LineData datosChart(LineDataSet lineDataSet) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            lineDataSet.setColor(getActivity().getColor(R.color.orange));
        }
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        lineDataSet.setDrawFilled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            lineDataSet.setFillDrawable((getActivity()).getDrawable(R.drawable.fade_orange));
        }
        lineDataSet.setHighLightColor(getActivity().getColor(R.color.gray));
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        //lineDataSet.setDrawVerticalHighlightIndicator(false);
        iLineDataSets.add(lineDataSet);
        LineData data = new LineData(iLineDataSets);
        return data;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    int obtenerDia(String fecha) {
            if (!Objects.equals(fecha, null)){
                String dia = fecha.substring(8, 10);
                return Integer.valueOf(dia);}
            else
                return 0;

    }


    void cantidadFilas(String idUsuario) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://168.254.1.104/Sitio/extraerEstadisticas.php?idUsuario=" + idUsuario, new com.android.volley.Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
            @Override
            public void onResponse(JSONArray response) {


                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        cantFilas++;

                    } catch (JSONException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }


                if (cantFilas > 0)
                    extraerEstadisticas(idPropio);
                else
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


    void extraerEstadisticas(String idUsuario) {
        cantFilas++;

        ubicacionCliente = new String[cantFilas];
        destinoCliente = new String[cantFilas];
        fecha = new String[cantFilas];
        idCliente = new int[cantFilas];
        precio = new double[cantFilas];
        distanciaViaje = new double[cantFilas];


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://168.254.1.104/Sitio/extraerEstadisticas.php?idUsuario=" + idUsuario, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;

                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        ubicacionCliente[i] = jsonObject.getString("ubicacionCliente");
                        destinoCliente[i] = jsonObject.getString("destinoCliente");
                        fecha[i] = jsonObject.getString("fecha");
                        idCliente[i] = jsonObject.getInt("idCliente");
                        precio[i] = jsonObject.getDouble("precio");
                        distanciaViaje[i] = jsonObject.getDouble("distanciaViaje");
                    } catch (JSONException e) {
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                organizar();


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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void organizar() {
        newSize = 1;
        int next = 0;

        for (int i = 0; i < cantFilas; i++)
            if (i != 0 && obtenerDia(fecha[i]) != obtenerDia(fecha[i - 1]))
                newSize++;


        newfecha = new int[newSize];
        cantidadViajes = new int[newSize];
        newprecio = new double[newSize];
        newdistanciaViaje = new double[newSize];

        double promedioPrecio = 0;
        double promedioDistancia = 0;
        int cant;
        int newPos = -1;

        for (int i = 0; i < cantFilas; i++) {
            first = true;
            newPos++;
            cant = 1;
            promedioPrecio = precio[i];
            promedioDistancia = distanciaViaje[i];
            for (int j = i + 1; j < cantFilas ; j++) {
                if (Objects.equals(fecha[i], "0000-00-00 00")) {
                    newPos--;
                    break;
                } else {
                    if (first) {
                        newprecio[newPos] = promedioPrecio;
                        newdistanciaViaje[newPos] = promedioDistancia;
                        cantidadViajes[newPos] += 1;
                        first = false;
                        newfecha[newPos] = obtenerDia(fecha[i]);
                    }

                }
                if (obtenerDia(fecha[i]) == obtenerDia(fecha[j])) {
                    cant++;
                    promedioPrecio += precio[j];
                    promedioDistancia += distanciaViaje[j];
                    newprecio[newPos] = promedioPrecio;
                    cantidadViajes[newPos] += 1;
                    newdistanciaViaje[newPos] = promedioDistancia;
                    newfecha[newPos] = obtenerDia(fecha[i]);
                    fecha[j] = "0000-00-00 00";
                }
            }


        }

       // Toast.makeText(context, String.valueOf(newPos) + String.valueOf(newSize) + String.valueOf(newfecha[0]) + String.valueOf(newfecha[1]) + String.valueOf(newfecha[2]) + String.valueOf(newfecha[3]), Toast.LENGTH_LONG).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            graficaDinero();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    void graficaDinero() {
        ArrayList<Entry> entries1 = new ArrayList<>();
        ArrayList<Entry> entries2 = new ArrayList<>();
        ArrayList<Entry> entries3 = new ArrayList<>();
        for (int i = 0; i < newSize - 1; i++) {
            entries1.add(new Entry(newfecha[i], (float) newprecio[i]));
        }
        for (int i = 0; i < newSize - 1; i++) {
            entries2.add(new Entry(newfecha[i], (float) newdistanciaViaje[i]));
        }
        for (int i = 0; i < newSize - 1; i++) {
            entries3.add(new Entry(newfecha[i], (float) cantidadViajes[i]));
        }


        LineDataSet lineDataSet = new LineDataSet(entries1, "Moneda Nacional");
        LineDataSet lineDataSet2 = new LineDataSet(entries2, "Kilometros");
        LineDataSet lineDataSet3 = new LineDataSet(entries3, "Viajes");


        Easing.EasingFunction animacion = Easing.Linear;
        int time = 1000;
        chartDinero.setMarker(mv);
        chartDinero.setData(datosChart(lineDataSet));
        chartDinero.notifyDataSetChanged();
        chartDinero.animateY(time, animacion);
        chartDinero.invalidate();

        chartDistancia.setMarker(mv);
        chartDistancia.setData(datosChart(lineDataSet2));
        chartDistancia.notifyDataSetChanged();
        chartDistancia.animateY(time, animacion);
        chartDistancia.invalidate();

        chartViajes.setMarker(mv);
        chartViajes.setData(datosChart(lineDataSet3));
        chartViajes.notifyDataSetChanged();
        chartViajes.animateY(time, animacion);
        chartViajes.invalidate();

    }

}
