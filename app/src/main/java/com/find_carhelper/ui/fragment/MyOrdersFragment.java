package com.find_carhelper.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.find_carhelper.R;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.adapter.FaultRepairPagerAdapter;
import com.find_carhelper.ui.base.MVPBaseFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * 路径分析
 */
public class MyOrdersFragment extends MVPBaseFragment {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private String[] titles;
    private List<Fragment> fragments;
    private FaultRepairPagerAdapter mainPagerAdapter;
    public static Fragment newInstance() {
       MyOrdersFragment fragment = new MyOrdersFragment();
        return fragment;
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_my_orders;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void initViews() {
        mTabLayout = mRootView.findViewById(R.id.tool_tab);
        mViewPager = mRootView.findViewById(R.id.view_pager);
        titles = getResources().getStringArray(R.array.orders_tab);
        CooperatingFragment cooperatingFragment = new CooperatingFragment();
        AlreadyCompleteFragment alreadyCompleteFragment = new AlreadyCompleteFragment();
        fragments = new ArrayList<>();
        fragments.add(cooperatingFragment);
        fragments.add(alreadyCompleteFragment);
        mainPagerAdapter = new FaultRepairPagerAdapter(getChildFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mainPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void initData() {

    }
}
