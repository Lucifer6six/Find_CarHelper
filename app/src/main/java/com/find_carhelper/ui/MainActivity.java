package com.find_carhelper.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.find_carhelper.R;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.activity.BaoQuanActivity;
import com.find_carhelper.ui.activity.NewsActvity;
import com.find_carhelper.ui.adapter.MainFragmentAdapter;
import com.find_carhelper.ui.base.MVPBaseActivity;
import com.find_carhelper.ui.fragment.FragmentFactory;
import com.find_carhelper.utils.SharedPreferencesUtil;
import com.find_carhelper.utils.ToastUtil;
import com.find_carhelper.utils.Utils;
import com.find_carhelper.widgets.NoCacheViewPager;
import com.find_carhelper.widgets.NoScrollViewPager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends MVPBaseActivity {
    private NoCacheViewPager mViewPager;
    BottomNavigationView bottomNavigationView;

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
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        mViewPager.setCurrentItem(1);
                        //setTitleBar("保全车辆");
                        hideTitleBar();
                        return true;
                    case R.id.navigation_work:
                        mViewPager.setCurrentItem(3);
                        //setTitleBar("保全订单");
                        hideTitleBar();
                        return true;
                    case R.id.navigation_plan:
                        mViewPager.setCurrentItem(4);
                        hideTitleBar();
                        return true;
                    case R.id.navigation_main:
                        mViewPager.setCurrentItem(0);
                        hideTitleBar();
                        return true;
                    case R.id.navigation_find:
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

        //主页面右上角点击事件
        registerRightClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FragmentFactory.getInstance().getHomeFragment().pageChange();
                startActivity(new Intent(MainActivity.this, BaoQuanActivity.class));
            }
        });
        //主页面左上角点击事件
        registerLeftClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //drawerlayout.openDrawer(navigationView);
                finish();
            }
        });
        bottomNavigationView.findViewById(R.id.navigation_main).performClick();
    }

    /**
     * 初始化fragment
     */
    private void initFragments() {
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(FragmentFactory.getInstance().getMainPageFragment());
        fragmentList.add(FragmentFactory.getInstance().getHomeFragment());
        fragmentList.add(FragmentFactory.getInstance().getFindCarFragment());
        fragmentList.add(FragmentFactory.getInstance().getFaultRepairMainPageFragment());
        fragmentList.add(FragmentFactory.getInstance().getUserCenterFragment());
        //初始化viewPager适配器
        MainFragmentAdapter mainFragmentAdapter = new MainFragmentAdapter(getSupportFragmentManager(), fragmentList);
        //注入适配器
        mViewPager.setAdapter(mainFragmentAdapter);
        //设置Viewpager缓冲数量
        mViewPager.setOffscreenPageLimit(0);
    }

    private long mBackTime;

    @Override
    public void onBackPressed() {

        long time = SystemClock.elapsedRealtime();
        if (time - mBackTime > 2000) {
            ToastUtil.showLongToast(getApplicationContext(), "再按一次返回退出");
            mBackTime = time;
            return;
        }

        super.onBackPressed();
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

//    public void changePage(int index) {
//
//        switch (index) {
//
//            case 1:
//                bottomNavigationView.findViewById(R.id.navigation_home).performClick();
//                break;
//            case 2:
//                bottomNavigationView.findViewById(R.id.navigation_find).performClick();
//                break;
//
//        }
//
//    }
}
