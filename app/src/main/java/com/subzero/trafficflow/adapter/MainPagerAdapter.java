package com.subzero.trafficflow.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @author luo
 *
 */
public abstract class MainPagerAdapter extends FragmentPagerAdapter {

    public   Fragment[] fragments;

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public abstract Fragment[] setFragments(Fragment... fragments);

    public abstract String[] setTabArrays(String... tabArrays);
    @Override
    public Fragment getItem(int position) {
        return setFragments()[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return setTabArrays()[position];
    }

    @Override
    public int getCount() {
        return setTabArrays().length;
    }
}
