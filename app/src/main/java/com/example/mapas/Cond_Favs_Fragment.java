package com.example.mapas;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.kishandonga.csbx.CustomSnackbar;

import java.util.ArrayList;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link //Cond_Favs_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Cond_Favs_Fragment extends Fragment {

    private ArrayList<Conductores> cond_favoritos;
    private RecyclerView recyl_cond_favoritos;
    private Adaptador_Cond adaptador_cond;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Cond_Favs_Fragment(String name) {
        Bundle args = new Bundle();
        args.putString("name", name);
        this.setArguments(args);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment Cond_Favs_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    /*public static Cond_Favs_Fragment newInstance(String param1, String param2) {
        Cond_Favs_Fragment fragment = new Cond_Favs_Fragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cond__favs, container, false);
        cond_favoritos = new ArrayList<Conductores>();
        //cargar_Fav();
        recyl_cond_favoritos = view.findViewById(R.id.recyl_cond_favoritos);
        recyl_cond_favoritos.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        adaptador_cond = new Adaptador_Cond(cond_favoritos, getContext());
        adaptador_cond.setOnItemClickListener(new Adaptador_Cond.OnItemClickListener() {

            @Override
            public void onFavClick(final int position) {
                ((MainActivity)getActivity()).setFlagConds(true, false);
                final int id = cond_favoritos.get(position).getId();
                cond_favoritos.remove(position);
                adaptador_cond.notifyItemRemoved(position);
                ((MainActivity)getActivity()).delet_Fav(id+"");
                ((MainActivity)getActivity()).hideSnackbar();
                CustomSnackbar snackbar = ((MainActivity)getActivity()).getSnackbarAction();
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
                        ((MainActivity)getActivity()).setFlagConds(true, true);
                        ((MainActivity)getActivity()).añadir_Fav(id+"");

                        return null;
                    }
                });
                snackbar.show();

            }

            @Override
            public void reservar(int position) {
                Intent intent = new Intent((MainActivity) getActivity(),MapsActivity.class);
                intent.putExtra("titl_lug", "none");
                intent.putExtra("direccion", "none");
                intent.putExtra("fase",7);
                intent.putExtra("driver", cond_favoritos.get(position).getId());
                intent.putExtra("mlatitud", ((MainActivity) getActivity()).getLatitudeNetwork());
                intent.putExtra("mlongitud", ((MainActivity) getActivity()).getLongitudeNetwork());
                intent.putExtra("nombreDriver", cond_favoritos.get(position).getNombre());
                intent.putExtra("id",14);
                startActivity(intent);
            }

            @Override
            public void onFotoClick(int position, View view) {
                String url = cond_favoritos.get(position).getFoto();
                float [] coords = ((MainActivity)getActivity()).getLocationView(view) ;
                ((MainActivity)getActivity()).zoomFoto(coords[0], coords[1], url);
            }

            @Override
            public void onFotoVehcClick(int position, View view) {
                String url = cond_favoritos.get(position).getFoto_vehc();
                float [] coords = ((MainActivity)getActivity()).getLocationView(view) ;
                ((MainActivity)getActivity()).zoomFoto(coords[0], coords[1], url);
            }

        });
        recyl_cond_favoritos.setAdapter(adaptador_cond);

        return view;
    }



    private void cargar_Fav() {
        cond_favoritos = ((MainActivity)getActivity()).getFavs();

    }
}