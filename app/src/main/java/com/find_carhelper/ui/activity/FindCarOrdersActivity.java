package com.find_carhelper.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;

import com.find_carhelper.R;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.adapter.FaultRepairPagerAdapter;
import com.find_carhelper.ui.base.MVPBaseActivity;
import com.find_carhelper.ui.fragment.AlreadyCompleteFragment;
import com.find_carhelper.ui.fragment.CooperatingFragment;
import com.find_carhelper.ui.fragment.FindCarOrdersComplete;
import com.find_carhelper.ui.fragment.FindCarOrdersCooptering;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * 寻车订单
 */
public class FindCarOrdersActivity extends MVPBaseActivity {
    private ViewPager mViewPager;
    private String[] titles;
    private List<Fragment> fragments;
    private FaultRepairPagerAdapter mainPagerAdapter;
    private SmartTabLayout mSmartTablayout;


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_find_car_;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }


    @Override
    protected void initViews() {
        mSmartTablayout = findViewById(R.id.smart_tablayout);
        mViewPager = findViewById(R.id.view_pager);
        titles = getResources().getStringArray(R.array.orders_tab);
        FindCarOrdersCooptering cooperatingFragment = new FindCarOrdersCooptering();
        FindCarOrdersComplete alreadyCompleteFragment = new FindCarOrdersComplete();
        fragments = new ArrayList<>();
        fragments.add(cooperatingFragment);
        fragments.add(alreadyCompleteFragment);
        mainPagerAdapter = new FaultRepairPagerAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mainPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mSmartTablayout.setViewPager(mViewPager);
    }


    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
