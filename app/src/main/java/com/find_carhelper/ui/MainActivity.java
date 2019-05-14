package com.find_carhelper.ui;

import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.find_carhelper.R;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.activity.NewsActvity;
import com.find_carhelper.ui.activity.TestActivity;
import com.find_carhelper.ui.adapter.MainFragmentAdapter;
import com.find_carhelper.ui.base.MVPBaseActivity;
import com.find_carhelper.ui.fragment.FragmentFactory;
import com.find_carhelper.utils.ToastUtil;
import com.find_carhelper.utils.Utils;
import com.find_carhelper.widgets.NoScrollViewPager;

import java.util.ArrayList;

public class MainActivity extends MVPBaseActivity{
    private NoScrollViewPager mViewPager;
    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        mViewPager = findViewById(R.id.vp_mian);
        DrawerLayout  drawerlayout = findViewById(R.id.drawerlayout_container);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        mViewPager.setCurrentItem(0);
                        setTitleBar("保全-车辆列表", "消息");
                        return true;
                    case R.id.navigation_work:
                        mViewPager.setCurrentItem(1);
                        setTitleBar("保全-订单列表", "");
                        return true;
                    case R.id.navigation_plan:
                        mViewPager.setCurrentItem(2);
                        hideTitleBar();
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });
        //调用上面的类进行设置
        Utils.disableShiftMode(bottomNavigationView);

        initFragments();

        getSupportFragmentManager().beginTransaction().replace(R.id.navigation_view,
                FragmentFactory.getInstance().getNavigationViewFragmentt()).commit();

        //主页面右上角点击事件
        registerRightClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FragmentFactory.getInstance().getHomeFragment().pageChange();
                startActivity(new Intent(MainActivity.this, NewsActvity.class));
            }
        });

        //主页面左上角点击事件
        registerLeftClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerlayout.openDrawer(navigationView);
            }
        });
        Log.e(TAG, "接单ed: " );
        //startActivity(new Intent(MainActivity.this, TestActivity.class));
    }

    /**
     * 初始化fragment
     */
    private void initFragments() {
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(FragmentFactory.getInstance().getHomeFragment());
        fragmentList.add(FragmentFactory.getInstance().getFaultRepairMainPageFragment());
        fragmentList.add(FragmentFactory.getInstance().getPlanRepairMainPageFragment());


        //初始化viewPager适配器
        MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), fragmentList);
        //注入适配器
        mViewPager.setAdapter(mainFragmentAdapter);
        //设置Viewpager缓冲数量
        mViewPager.setOffscreenPageLimit(2);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {
        Log.e(TAG, "bus: " + eventCenter.toString());
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }



}
