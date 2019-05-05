package com.find_carhelper.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * Project：Fujian_PQHelper
 * Author：zj
 * Date：2019/4/1
 * Version: 3.0.0
 * Description：主页Fragment切换Adapter
 */
public class MainFragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragmentList;
    private FragmentManager fragmentManager;

    public MainFragmentAdapter(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        fragmentManager = fm;
        fragmentList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fragmentManager.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = fragmentList.get(position);
        fragmentManager.beginTransaction().hide(fragment).commit();
    }

}