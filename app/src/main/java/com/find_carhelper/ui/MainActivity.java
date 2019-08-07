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
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.find_carhelper.R;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.http.Constants;
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
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    Log.i(TAG, "当前定位结果来源-----" + amapLocation.getLocationType());//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    Log.i(TAG, "纬度 ----------------" + amapLocation.getLatitude());//获取纬度
                    Log.i(TAG, "经度-----------------" + amapLocation.getLongitude());//获取经度
                    Constants.Latitude = ""+amapLocation.getLatitude();
                    Constants.Longitude = ""+amapLocation.getLongitude();
                    Log.i(TAG, "精度信息-------------" + amapLocation.getAccuracy());//获取精度信息
                    Log.i(TAG, "地址-----------------" + amapLocation.getAddress());//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    Log.i(TAG, "国家信息-------------" + amapLocation.getCountry());//国家信息
                    Log.i(TAG, "省信息---------------" + amapLocation.getProvince());//省信息
                    Constants.Province = ""+amapLocation.getProvince();
                    //Toast.makeText(MainActivity.this, amapLocation.getProvince() + amapLocation.getCity(), Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "城市信息-------------" + amapLocation.getCity());//城市信息
                    Constants.City = ""+amapLocation.getCity();
                    Log.i(TAG, "城区信息-------------" + amapLocation.getDistrict());//城区信息
                    Constants.District = ""+amapLocation.getDistrict();
                    Log.i(TAG, "街道信息-------------" + amapLocation.getStreet());//街道信息
                    Log.i(TAG, "街道门牌号信息-------" + amapLocation.getStreetNum());//街道门牌号信息
                    Log.i(TAG, "城市编码-------------" + amapLocation.getCityCode());//城市编码
                    Log.i(TAG, "地区编码-------------" + amapLocation.getAdCode());//地区编码
                    Log.i(TAG, "当前定位点的信息-----" + amapLocation.getAoiName());//获取当前定位点的AOI信息
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };
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
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {//未开启定位权限
            //开启定位权限,200是标识码
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
        } else {
            startLocaion();//开始定位
        }

    }
    public void startLocaion() {
        Log.e(TAG, "开始定位");
        mLocationClient = new AMapLocationClient(MainActivity.this);
        mLocationClient.setLocationListener(mLocationListener);
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
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
