package com.example.mapas;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter {

    int numTabs;
    Context context;

    public PageAdapter(FragmentManager fm, int numTabs, Context context) {
        super(fm);
        this.numTabs = numTabs;
        this.context=context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Tab1 tab1 = new Tab1();
                tab1.setContext(context);
                return tab1;
            case 1:
                Tab2 tab2 = new Tab2();
                tab2.setContext(context);
                return tab2;
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return numTabs;
    }
}
