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
import com.find_carhelper.ui.activity.NewsActvity;
import com.find_carhelper.ui.adapter.MainFragmentAdapter;
import com.find_carhelper.ui.base.MVPBaseActivity;
import com.find_carhelper.ui.fragment.FragmentFactory;
import com.find_carhelper.utils.SharedPreferencesUtil;
import com.find_carhelper.utils.ToastUtil;
import com.find_carhelper.utils.Utils;
import com.find_carhelper.widgets.NoScrollViewPager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;

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
        //NavigationView navigationView = findViewById(R.id.navigation_view);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        mViewPager.setCurrentItem(0);
                        setTitleBar("保全车辆", "消息");
                        return true;
                    case R.id.navigation_work:
                        mViewPager.setCurrentItem(1);
                        setTitleBar("保全订单", "");
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

//        getSupportFragmentManager().beginTransaction().replace(R.id.navigation_view,
//                FragmentFactory.getInstance().getNavigationViewFragmentt()).commit();

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

                //drawerlayout.openDrawer(navigationView);
                finish();
            }
        });
       // Toast.makeText(MainActivity.this,"share = "+ SharedPreferencesUtil.getString(MainActivity.this,"ces"),Toast.LENGTH_SHORT).show();
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
        mViewPager.setOffscreenPageLimit(0);
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


    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            //result即为获取的SHA1值,如果最后面有冒号的话就去掉
            Log.i("abc", result.toString());
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
