package com.example.mapas;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.kishandonga.csbx.CustomSnackbar;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link //Cond_Cercanos_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Cond_Cercanos_Fragment extends Fragment {

    private ArrayList<Conductores> cond_cercanos;
    private RecyclerView recyl_cond_cercanos;
    private Adaptador_Cond adaptador_cond;
    ScrollView scrollView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;

    public Cond_Cercanos_Fragment(String name) {
        Bundle args = new Bundle();
        args.putString("name", name);
        this.setArguments(args);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param //param1 Parameter 1.
     * @return A new instance of fragment Cond_Cercanos_Fragment.
     */
    // TODO: Rename and change types and number of parameters
   /* public static Cond_Cercanos_Fragment newInstance(String param1 ) {
        Cond_Cercanos_Fragment fragment = new Cond_Cercanos_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cond__cercanos, container, false);
        recyl_cond_cercanos = view.findViewById(R.id.recyl_cond_cercanos);
        scrollView = view.findViewById(R.id.scroll);
        recyl_cond_cercanos.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        cond_cercanos = new ArrayList<Conductores>();
        //cargar_Cercanos();
        list_cond_cercanos();
        adaptador_cond = new Adaptador_Cond(cond_cercanos, getContext());
        adaptador_cond.setOnItemClickListener(new Adaptador_Cond.OnItemClickListener() {

            @Override
            public void onFavClick(final int position) {
                ((MainActivity) getActivity()).setFlagConds(false, true);
                final int id = cond_cercanos.get(position).getId();
                if (!cond_cercanos.get(position).isFavorito()) {
                    cond_cercanos.get(position).setFavorito(true);
                    ((MainActivity) getActivity()).añadir_Fav(id + "");
                    ((MainActivity) getActivity()).Msg("CONDUCTOR AÑADIDO CON ÉXITO!");

                } else {
                    cond_cercanos.get(position).setFavorito(false);
                    ((MainActivity) getActivity()).delet_Fav(id + "");
                    ((MainActivity) getActivity()).hideSnackbar();
                    CustomSnackbar snackbar = ((MainActivity) getActivity()).getSnackbarAction();
                    snackbar.message("Ha eliminado un conductor de su lista de favoritos!");
                    snackbar.textTypeface(Typeface.SANS_SERIF);
                    snackbar.textColorRes(R.color.light);
                    snackbar.actionTextColorRes(R.color.colorPrimary);
                    snackbar.backgroundColorRes(R.color.gray);
                    snackbar.padding(35);
                    snackbar.cornerRadius(15);
                    snackbar.duration(5000);
                    snackbar.withAction("Deshacer", new Function1<Snackbar, Unit>() {
                        @Override
                        public Unit invoke(Snackbar snackbar) {
                            ((MainActivity) getActivity()).añadir_Fav(id + "");
                            cond_cercanos.get(position).setFavorito(true);
                            adaptador_cond.notifyItemChanged(position);
                            return null;
                        }
                    });
                    snackbar.show();
                }

            }

            @Override
            public void reservar(int position) {
                Intent intent = new Intent((MainActivity) getActivity(), MapsActivity.class);
                intent.putExtra("titl_lug", "none");
                intent.putExtra("direccion", "none");
                intent.putExtra("fase", 7);
                intent.putExtra("driver", cond_cercanos.get(position).getId());
                intent.putExtra("mlatitud", ((MainActivity) getActivity()).getLatitudeNetwork());
                intent.putExtra("mlongitud", ((MainActivity) getActivity()).getLongitudeNetwork());
                intent.putExtra("nombreDriver", cond_cercanos.get(position).getNombre());
                intent.putExtra("id", 20);
                startActivity(intent);
            }

            @Override
            public void onFotoClick(int position, View view) {
                String url = cond_cercanos.get(position).getFoto();
                float[] coords = ((MainActivity) getActivity()).getLocationView(view);
                ((MainActivity) getActivity()).zoomFoto(coords[0], coords[1], url);


            }

            @Override
            public void onFotoVehcClick(int position, View view) {

                String url = cond_cercanos.get(position).getFoto_vehc();
                float[] coords = ((MainActivity) getActivity()).getLocationView(view);
                ((MainActivity) getActivity()).zoomFoto(coords[0], coords[1], url);


            }


        });
        recyl_cond_cercanos.setAdapter(adaptador_cond);

        return view;
    }

    private void cargar_Cercanos() {
        cond_cercanos = ((MainActivity) getActivity()).getCercanos();
    }

    private void list_cond_cercanos() {

        cond_cercanos.add(new Conductores(14, "http://192.168.0.130:8080/cuber//fotos/69.png", "Angel Claveria Mendez", (float) 9.4, "http://192.168.0.130:8080/cuber//fotos_vehc/69.png", 4, true, "carro"));
        cond_cercanos.add(new Conductores(14, "http://192.168.0.130:8080/cuber//fotos/67.png", "Juan Daniel Nieves Simon", (float) 3.1, "http://192.168.0.130:8080/cuber//fotos_vehc/67.png", 2, false, "moto"));
        cond_cercanos.add(new Conductores(14, "http://192.168.0.130:8080/cuber//fotos/69.png", "Angel Claveria Mendez", (float) 9.4, "http://192.168.0.130:8080/cuber//fotos_vehc/69.png", 4, true, "carro"));
        cond_cercanos.add(new Conductores(14, "http://192.168.0.130:8080/cuber//fotos/67.png", "Juan Daniel Nieves Simon", (float) 3.1, "http://192.168.0.130:8080/cuber//fotos_vehc/67.png", 2, false, "moto"));
        cond_cercanos.add(new Conductores(14, "http://192.168.0.130:8080/cuber//fotos/69.png", "Angel Claveria Mendez", (float) 9.4, "http://192.168.0.130:8080/cuber//fotos_vehc/69.png", 4, true, "carro"));
        cond_cercanos.add(new Conductores(14, "http://192.168.0.130:8080/cuber//fotos/67.png", "Juan Daniel Nieves Simon", (float) 3.1, "http://192.168.0.130:8080/cuber//fotos_vehc/67.png", 2, false, "moto"));


    }
}