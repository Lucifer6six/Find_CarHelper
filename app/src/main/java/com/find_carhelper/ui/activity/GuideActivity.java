package com.find_carhelper.ui.activity;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.gesture.GestureUtils;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.find_carhelper.R;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.http.Constants;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.MainActivity;
import com.find_carhelper.ui.base.MVPBaseActivity;
import com.find_carhelper.utils.Logger;
import com.find_carhelper.utils.PermissionChecker;
import com.find_carhelper.utils.SharedPreferencesUtil;
import com.find_carhelper.utils.ToastUtil;
import com.find_carhelper.widgets.CountDownTextView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cc.ibooker.zcountdownviewlib.CountDownView;

public class GuideActivity extends MVPBaseActivity implements View.OnClickListener, OnBannerListener {
    private MZBannerView mMZBanner;
    private MyImageLoader mMyImageLoader;
    private ArrayList<Integer> imagePath;
    private ArrayList<String> imageTitle;
    private CountDownTextView mCountDownTextView;
    CountDownView countdownView;
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMZBanner.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMZBanner.start();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initViews() {

        findViewById(R.id.image_bao).setOnClickListener(this);
        mCountDownTextView = findViewById(R.id.tvCountDown);
        countdownView = findViewById(R.id.countdownView);
        mMZBanner = findViewById(R.id.banner);
        initImages();
        initImageView();
        int checkCoarseFine = ContextCompat.checkSelfPermission(GuideActivity.this, Manifest.permission.READ_PHONE_STATE);
        if (checkCoarseFine == PackageManager.PERMISSION_GRANTED ) {
            initDeviceID();
        } else {//没有权限
            ActivityCompat.requestPermissions(GuideActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_FINE_LOCATION}, 1);//申请授权
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] ==PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted 授予权限
                    //处理授权之后逻辑
                    initDeviceID();
                } else {
                    // Permission Denied 权限被拒绝
                    Toast.makeText(GuideActivity.this,"权限被禁用",Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    private void initDeviceID() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "无法获取设备信息", Toast.LENGTH_SHORT);
        }else {
            TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(mContext.TELEPHONY_SERVICE);
            String imei = telephonyManager.getDeviceId();
            Constants.ID = imei;
            Log.e("TAG", "imei = "+imei);
        }
    }
    private void initPermission() {
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (!permissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toArray(new String[permissionList.size()]), 1);
        } else {}
    }

    @Override
    protected void initData() {

    }

    public void initImages(){
        imagePath = new ArrayList<>();
        imageTitle = new ArrayList<>();
        imagePath.add(R.mipmap.main_image);
        imagePath.add(R.mipmap.main_image);
        imagePath.add(R.mipmap.main_image);
        imageTitle.add("");
        imageTitle.add("");
        imageTitle.add("");
    }

    private void initImageView(){
        // 设置数据
        mMZBanner.setPages(imagePath, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
    }
    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.image_bao:

                startActivity(new Intent(GuideActivity.this, MainActivity.class));

                break;

        }
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
    public void OnBannerClick(int position) {

    }

    /**
     * 图片加载类
     */
    private class MyImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context.getApplicationContext())
                    .load(path)
                    .into(imageView);
        }
    }
    public static class BannerViewHolder implements MZViewHolder<Integer> {
        private ImageView mImageView;
        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item,null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data) {
            // 数据绑定
            //mImageView.setImageResource(data);
            mImageView.setBackground(context.getResources().getDrawable(data));
        }
    }
}
