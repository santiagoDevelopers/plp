package com.example.mapas;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.percentlayout.widget.PercentRelativeLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioFragment extends Fragment {

    private static TextView ubicacionCalle;
    private static TextView ubicacionCity;
    private ArrayList<Lug_Fav> lug_fav;
    private RecyclerView recyl_lug_fav;
    private PercentRelativeLayout layout_foto_perfil;
    private CircularImageView foto_perfil;
    private FloatingActionButton conductores;
    private PercentRelativeLayout backg_inicio;
    private PercentRelativeLayout layout_add;
    private CircularImageView edit;
    private CircularImageView delet;
    private CircularImageView add_lug;
    private EditText titl_lug;
    private String lugar_edit;
    private String lugar_delet;
    private RelativeLayout go_text;
    private int pos_edit;
    private int pos_delet;
    private String old_lug = "";
    private float Xpos;
    private static ImageView searchingMyLocation;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InicioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InicioFragment newInstance(String param1, String param2) {
        InicioFragment fragment = new InicioFragment();
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        ((MainActivity) getActivity()).LayoutCond(View.VISIBLE);
        ((MainActivity) getActivity()).topBarColor(getResources().getColor(R.color.naranja));
        ubicacionCalle = view.findViewById(R.id.ubicacionCalle);
        ubicacionCity = view.findViewById(R.id.ubicacionCity);
        backg_inicio = view.findViewById(R.id.backg_inicio);
        foto_perfil = view.findViewById(R.id.foto_perfil);
        layout_add = view.findViewById(R.id.layout_add);
        titl_lug = view.findViewById(R.id.titl_lug);
        add_lug = view.findViewById(R.id.add_lug);
        edit = view.findViewById(R.id.edit);
        delet = view.findViewById(R.id.delet);
        go_text = view.findViewById(R.id.go_text);
        searchingMyLocation = view.findViewById(R.id.searchingLocation);
        lug_fav = new ArrayList<>();
        recyl_lug_fav = view.findViewById(R.id.recyl_lug_fav);
        recyl_lug_fav.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        load_lug_Fav();
        final Adaptador_Lug_Fav adapt_lug_fav = new Adaptador_Lug_Fav(lug_fav, getContext());
        recyl_lug_fav.setAdapter(adapt_lug_fav);
        recyl_lug_fav.setHorizontalFadingEdgeEnabled(true);

        adapt_lug_fav.setOnItemClickListener(new Adaptador_Lug_Fav.OnItemClickListener() {


            @Override
            public void EditEvent(int position, View view) {
                ocultar_btns(false);
                edit_delet(position, view);
            }

            @Override
            public void MapaEvent(int position, View view) {
                click_Recyl(position, view);

            }

            @Override
            public void DownTouch(int position, View view) {
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                Xpos = (float) (location[0] - view.getWidth() / 4.0);
                ocultar_btns(true);
            }


        });


        foto_perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "";
                float[] coords = ((MainActivity) getActivity()).getLocationView(view);
                ((MainActivity) getActivity()).zoomFoto(coords[0], coords[1], url);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos_edit < 3) {
                    Intent intent = new Intent(getContext(), Add_Fav_Map_Actv.class);
                    intent.putExtra("titl_lug", lugar_edit);
                    startActivity(intent);
                    ocultar_btns(true);

                } else {
                    if (pos_edit == lug_fav.size() - 1) {
                        old_lug = "";
                    } else {
                        old_lug = lug_fav.get(pos_edit).getLugar();
                    }
                    ocultar_btns(false);
                    btnsAnimation(layout_add, Xpos, View.VISIBLE, 250, false);

                }

            }

        });

        delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).delet_lug_Fav(lug_fav.get(pos_delet).getLugar());
                lug_fav.remove(pos_delet);
                adapt_lug_fav.notifyItemRemoved(pos_delet);
                ocultar_btns(true);

            }
        });

        add_lug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lugar = titl_lug.getText().toString();
                if (!lugar.isEmpty()) {
                    if (old_lug.isEmpty()) {
                        ((MainActivity) getActivity()).add_titl_lug_Fav(lugar);
                    } else {
                        ((MainActivity) getActivity()).edit_titl_lug_Fav(old_lug, lugar);
                    }

                    Intent intent = new Intent(getContext(), Add_Fav_Map_Actv.class);
                    intent.putExtra("titl_lug", lugar);
                    startActivity(intent);
                    ocultar_btns(true);
                }

            }
        });


        backg_inicio.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ocultar_btns(true);
                return false;
            }
        });


        return view;
    }


    //Lugares Favoritos---------------------------------------------------------------
    private void load_lug_Fav() {
        lug_fav = ((MainActivity) getActivity()).get_list_lug_Fav();
    }

    public static void insertarUbicacion(String calle, String city) {
        searchingMyLocation.setImageResource(R.drawable.ic_my_location);
        ubicacionCalle.setText(calle);
        ubicacionCity.setText(city);
    }

    private void ocultar_btns(boolean textVisib) {

        btnsAnimation(edit, 0, View.GONE, 200, true);
        btnsAnimation(delet, 0, View.GONE, 200, true);
        btnsAnimation(layout_add, 0, View.GONE, 200, false);

        if (textVisib) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    go_text.setVisibility(View.VISIBLE);
                }
            }, 200);
        } else
            go_text.setVisibility(View.GONE);


    }
//Hacer un metodo general para inciar un activity
    public void click_Recyl(int position, View view) {
        if (position == 0) {
            Intent intent = new Intent((MainActivity) getActivity(), MapsActivity.class);
            intent.putExtra("titl_lug", "none");
            intent.putExtra("direccion", "none");
            intent.putExtra("fase", 1);
            intent.putExtra("driver", 0);
            intent.putExtra("mlatitud", ((MainActivity) getActivity()).getLatitudeNetwork());
            intent.putExtra("mlongitud", ((MainActivity) getActivity()).getLongitudeNetwork());
            intent.putExtra("nombreDriver", "NADIE");
            intent.putExtra("id", 20);
            startActivity(intent);
        } else {
            if (position != lug_fav.size() - 1) {
                String direccion = ((MainActivity) getActivity()).get_lug_Fav(lug_fav.get(position).getLugar());

                if (direccion.isEmpty()) {
                    Intent intent = new Intent(getContext(), MapsActivity.class);
                    intent.putExtra("titl_lug", lug_fav.get(position).getLugar());
                    intent.putExtra("direccion", "none");
                    intent.putExtra("fase", 2);
                    intent.putExtra("driver", 0);
                    intent.putExtra("mlatitud", 1);
                    intent.putExtra("mlongitud", 1);
                    intent.putExtra("nombreDriver", "NADIE");
                    intent.putExtra("id", 20);
                    startActivity(intent);
                } else {

                    Intent intent = new Intent(getContext(), MapsActivity.class);
                    intent.putExtra("titl_lug", lug_fav.get(position).getLugar());
                    intent.putExtra("direccion", direccion);
                    intent.putExtra("fase", 3);
                    intent.putExtra("driver", 0);
                    intent.putExtra("mlatitud", 1);
                    intent.putExtra("mlongitud", 1);
                    intent.putExtra("nombreDriver", "NADIE");
                    intent.putExtra("id", 20);
                    startActivity(intent);
                }
            } else {
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                float x = (float) (location[0] - view.getWidth() / 4.0);
                btnsAnimation(layout_add, x, View.VISIBLE, 250, false);

            }
        }
    }

    public void edit_delet(int position, View view) {
        pos_edit = position;
        pos_delet = position;
        lugar_edit = lug_fav.get(position).getLugar();
        lugar_delet = lug_fav.get(position).getLugar();

        int width = view.getWidth();
        int screenX = ((MainActivity) getActivity()).getDimScreen()[0];
        float editW = (float) (screenX * 0.11);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        float editX = (float) (location[0] + width / 2.0 - editW / 1.3);
        float deletX = (float) (editX + 1.1 * editW);

        if (position != lug_fav.size() - 1) {
            if (position < 3) {
                btnsAnimation(edit, editX, View.VISIBLE, 150, true);

            } else {
                btnsAnimation(edit, editX, View.VISIBLE, 150, true);
                btnsAnimation(delet, deletX, View.VISIBLE, 200, true);

            }

        }


    }

    public void btnsAnimation(final View view, float posX, int visibility, int duration, boolean booth_axes) {

        if (visibility == View.VISIBLE) {
            if (booth_axes) {
                view.setX(posX);
                view.setAlpha(0f);
                view.setScaleX(0f);
                view.setScaleY(0f);
                view.animate()
                        .alpha(1f)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(duration)
                        .setListener(null);
                view.setVisibility(View.VISIBLE);

            } else {
                view.setX(posX);
                view.setAlpha(0f);
                view.setScaleX(0f);
                view.animate()
                        .alpha(1f)
                        .scaleX(1f)
                        .setDuration(duration)
                        .setListener(null);
                view.setVisibility(View.VISIBLE);
            }

        } else {
            if (booth_axes) {
                view.animate()
                        .alpha(0f)
                        .scaleX(0f)
                        .scaleY(0f)
                        .setDuration(duration)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                view.setVisibility(View.GONE);
                            }
                        });
            } else {
                view.animate()
                        .alpha(0f)
                        .scaleX(0f)
                        .setDuration(duration)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                view.setVisibility(View.GONE);
                            }
                        });

            }

        }


    }

}