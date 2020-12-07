package com.example.mapas;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.Objects;

public class PageAdapterConductor extends FragmentStatePagerAdapter {

    int numTabs;
    Context context;

    public PageAdapterConductor(FragmentManager fm, int numTabs, Context context) {
        super(fm);
        this.numTabs = numTabs;
        this.context=context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TabGraficas tab2 = new TabGraficas("graficas");
                tab2.setContext(context);
                return tab2;
            case 1:
                TabMensajes tab1 = new TabMensajes("mensajes");
                tab1.setContext(context);
                return tab1;
            case 2:
                TabBooking tabBooking=new TabBooking("booking");
                tabBooking.setContext(context);
                return tabBooking;
            default:
                return null;

        }

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int getItemPosition(@NonNull Object object) {
        Fragment fragment=(Fragment) object;
        if(fragment.getArguments().getString("name").contentEquals("booking"))
            return POSITION_NONE;
        else
            return POSITION_UNCHANGED;
    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
