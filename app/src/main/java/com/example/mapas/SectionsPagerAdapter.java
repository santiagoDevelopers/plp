package com.example.mapas;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private boolean flag_cercanos = true;
    private boolean flag_favs = true;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new Cond_Cercanos_Fragment("Cercanos");
                break;
            case 1:
                fragment = new Cond_Favs_Fragment("Favoritos");
                break;
        }
        return fragment;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {

        Fragment fragment = (Fragment)object;

        if(fragment.getArguments().getString("name").contentEquals("Cercanos")){
            if (flag_cercanos)
                return POSITION_NONE;
            else
                return POSITION_UNCHANGED;
        }else{
            if (flag_favs)
                return POSITION_NONE;
            else
                return POSITION_UNCHANGED;
        }


    }

    public void notifyItemChanged(boolean flag_cercanos, boolean flag_favs){
        this.flag_cercanos = flag_cercanos;
        this.flag_favs = flag_favs;

    }
}
