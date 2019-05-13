package com.find_carhelper.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


/**
 * author Mzy
 * date 2019/3/20
 */
public class FaultRepairPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;
    private String[] tabs;

    public FaultRepairPagerAdapter(FragmentManager fm, List<Fragment> list, String[] tabs) {
        super(fm);
        this.mFragmentList = list;
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
