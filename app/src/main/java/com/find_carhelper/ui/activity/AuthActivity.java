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
import com.find_carhelper.ui.fragment.GroupAuthFragment;
import com.find_carhelper.ui.fragment.IdentityAuthFragment;
import com.find_carhelper.ui.fragment.SystemNewsFragment;
import com.find_carhelper.ui.fragment.TogetherNewsFragment;

import java.util.ArrayList;
import java.util.List;

public class AuthActivity extends MVPBaseActivity {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private String[] titles;
    private List<Fragment> fragments;
    private FaultRepairPagerAdapter mainPagerAdapter;
    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_auth;
    }

    @Override
    protected void initViews() {
        mTabLayout = findViewById(R.id.tool_tab);
        mViewPager = findViewById(R.id.view_pager);
        titles = getResources().getStringArray(R.array.plan_repair_main_tab);
        GroupAuthFragment groupAuthFragment = new GroupAuthFragment();
        IdentityAuthFragment identityAuthFragment = new IdentityAuthFragment();
        fragments = new ArrayList<>();
        fragments.add(groupAuthFragment);
        fragments.add(identityAuthFragment);
        mainPagerAdapter = new FaultRepairPagerAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mainPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);

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


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
