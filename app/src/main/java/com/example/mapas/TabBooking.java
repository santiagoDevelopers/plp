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
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TabBooking#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabBooking extends Fragment {

    RecyclerView lista;
    Context context;
    adapterReservas adp;
    static ArrayList<Reservas> locations = new ArrayList<>();
    ArrayList<Reservas> locations2 = new ArrayList<>();
    Handler handler=new Handler();

    String name="booking";

    MaterialCalendarView calendarView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TabBooking(String name) {
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
     * @return A new instance of fragment TabBooking.
     */
    // TODO: Rename and change types and number of parameters
   /* public static TabBooking newInstance(String param1, String param2) {
        TabBooking fragment = new TabBooking();
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

    public String getFragmentName(){
        return name;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_booking, container, false);
        calendarView = view.findViewById(R.id.calendarView);
        calendarView.setDateSelected(CalendarDay.today(), true);
        calendarView.setDateSelected(CalendarDay.from(2020, 8, 11), true);

        lista = view.findViewById(R.id.reservas);
        lista.setHasFixedSize(true);
        lista.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));


        adp = new adapterReservas(locations);


        lista.setAdapter(adp);



        adp.setOnItemClickListener(new adapterReservas.OnItemCLickListener() {
            @Override
            public void OnItemClick(int position) {
                //locations.get(position).changeTit("wait");
                adp.notifyItemChanged(position);
            }

        });
        addBook();

        return view;

    }

    void addBook() {

               locations2 = ((SConductor)(getActivity())).arreglo();
               for (int i=0; i<locations2.size();i++){
                   locations.add(locations2.get(i));
               }
                ((SConductor) getActivity()).addCounter(3, String.valueOf(locations.size()));
                adp.notifyDataSetChanged();


    }
    public static int addReserva(String imagen, String fecha, String nombre, String origen, String destino, String hora) {
        locations.add(new Reservas(imagen, fecha, nombre, origen, destino, hora));
        return locations.size();
    }
}
