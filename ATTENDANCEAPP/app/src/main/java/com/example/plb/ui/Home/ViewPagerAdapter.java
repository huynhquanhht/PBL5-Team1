package com.example.plb.ui.Home;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior, Context context) {
        super(fm, behavior);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TodayFragment();
            case 1:
                return new OverallFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        String title = "";

        switch (position) {
            case 0:
                title = "Today";
                break;
            case 1:
                title = "Overall";
                break;

        }

        return title;
    }

}
