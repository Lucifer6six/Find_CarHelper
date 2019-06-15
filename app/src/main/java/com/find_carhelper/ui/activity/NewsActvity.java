package com.find_carhelper.ui.activity;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.find_carhelper.R;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.adapter.FaultRepairPagerAdapter;
import com.find_carhelper.ui.base.MVPBaseActivity;
import com.find_carhelper.ui.fragment.SystemNewsFragment;
import com.find_carhelper.ui.fragment.TogetherNewsFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import java.util.ArrayList;
import java.util.List;

public class NewsActvity extends MVPBaseActivity {
    //private TabLayout mTabLayout;
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
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_news_actvity;
    }

    @Override
    protected void initViews() {
        mSmartTablayout = (SmartTabLayout) findViewById(R.id.smart_tablayout);
        //mTabLayout = findViewById(R.id.tool_tab);
        mViewPager = findViewById(R.id.view_pager);
        titles = getResources().getStringArray(R.array.plan_repair_main_tab);
        SystemNewsFragment systemNewsFragment = new SystemNewsFragment();
        TogetherNewsFragment togetherNewsFragment = new TogetherNewsFragment();
        fragments = new ArrayList<>();
        fragments.add(systemNewsFragment);
        fragments.add(togetherNewsFragment);
        mainPagerAdapter = new FaultRepairPagerAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mainPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
       // mTabLayout.setupWithViewPager(mViewPager);
        mSmartTablayout.setViewPager(mViewPager);
        registerLeftClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }
}
