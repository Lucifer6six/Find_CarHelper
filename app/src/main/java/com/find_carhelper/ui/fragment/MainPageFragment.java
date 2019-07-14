package com.find_carhelper.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.find_carhelper.R;
import com.find_carhelper.bean.CarBean;
import com.find_carhelper.bean.CardBean;
import com.find_carhelper.bean.CityBean;
import com.find_carhelper.bean.JsonBean;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.http.Application;
import com.find_carhelper.http.Constants;
import com.find_carhelper.http.NetRequest;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.activity.LoginActivity;
import com.find_carhelper.ui.adapter.FaultRepairPagerAdapter;
import com.find_carhelper.ui.adapter.ListOrderAcceptAdapter;
import com.find_carhelper.ui.base.MVPBaseFragment;
import com.find_carhelper.utils.GetJsonDataUtil;
import com.find_carhelper.utils.SharedPreferencesUtil;
import com.find_carhelper.widgets.MarkerOrderPopWindow;
import com.find_carhelper.widgets.ToolDateSelectorPopWindow;
import com.google.gson.Gson;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.wega.library.loadingDialog.LoadingDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;


public class MainPageFragment extends MVPBaseFragment {
    private ViewPager mViewPager;
    private String[] titles;
    private List<Fragment> fragments;
    private FaultRepairPagerAdapter mainPagerAdapter;
    private SmartTabLayout mSmartTablayout;
    private SwipeRefreshLayout refreshLayout;
    public static Fragment newInstance() {
       MainPageFragment fragment = new MainPageFragment();
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
        return R.layout.fragment_main_page;
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
        mSmartTablayout = (SmartTabLayout)mRootView.findViewById(R.id.smart_tablayout);
        // mTabLayout = mRootView.findViewById(R.id.tool_tab);
        mViewPager = mRootView.findViewById(R.id.view_pager);
        titles = getResources().getStringArray(R.array.list_tab);
        PaiHangBangFragment cooperatingFragment = new PaiHangBangFragment();
        PaiHangBangFragment alreadyCompleteFragment = new PaiHangBangFragment();
        PaiHangBangFragment paiHangBangFragment = new PaiHangBangFragment();
        fragments = new ArrayList<>();
        fragments.add(cooperatingFragment);
        fragments.add(alreadyCompleteFragment);
        fragments.add(paiHangBangFragment);
        mainPagerAdapter = new FaultRepairPagerAdapter(getChildFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mainPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mSmartTablayout.setViewPager(mViewPager);
        refreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.swipe_id);
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });

    }


    public void initLoading(){

    }


    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();

    }

}
