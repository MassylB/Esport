package com.example.esport.ui.main;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.esport.Fragmentajd;
import com.example.esport.Fragmentdemain;
import com.example.esport.Fragmenthier;
import com.example.esport.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[] {R.string.tab_text_1, R.string.tab_text_2,R.string.tab_text_3};
    private final Context mContext;
    private final List<String> listajd;
    private final List<String> listhier;
    private final List<String> listdemain;


    public SectionsPagerAdapter(Context context, FragmentManager fm,List<String> listajd,List<String> listhier,List<String> listdemain){
        super(fm);
        mContext = context;
        this.listajd = listajd;
        this.listhier = listhier;
        this.listdemain = listdemain;


    }
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Fragment fragment = null;
        switch(position) {
            case 0:
                fragment = new Fragmenthier(listhier);
                break;
            case 1:
                fragment = new Fragmentajd(listajd);
                break;
            case 2:
                fragment = new Fragmentdemain(listdemain);
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }
}