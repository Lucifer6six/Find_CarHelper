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
       // count();
        //initCountDownView();
       // SharedPreferencesUtil.putString(GuideActivity.this,"ces","123");
       // PermissionChecker.getInstance().requestReadPhoneState(this);
        int checkCoarseFine = ContextCompat.checkSelfPermission(GuideActivity.this, Manifest.permission.READ_PHONE_STATE);
        if (checkCoarseFine == PackageManager.PERMISSION_GRANTED ) {
            //Toast.makeText(GuideActivity.this,"already",Toast.LENGTH_SHORT).show();
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
    private void initCountDownView(){
        // 基本属性设置
        countdownView.setCountTime(3663) // 设置倒计时时间戳
                .setHourTvTextColorHex("#f2692e")
                .setHourTvGravity(CountDownView.CountDownViewGravity.GRAVITY_CENTER)
                .setHourTvTextSize(21)
                .setHourTvBackgroundColorHex("#FFFFFF")
                .setHourColonTvSize(18, 0)
                .setHourColonTvTextColorHex("#FFFFFF")
                .setHourColonTvGravity(CountDownView.CountDownViewGravity.GRAVITY_CENTER)
                .setHourColonTvTextSize(21)

                .setMinuteTvTextColorHex("#f2692e")
                .setMinuteTvTextSize(21)
                .setMinuteTvBackgroundColorHex("#FFFFFF")
                .setMinuteColonTvSize(18, 0)
                .setMinuteColonTvTextColorHex("#FFFFFF")
                .setMinuteColonTvTextSize(21)

                .setSecondTvTextColorHex("#f2692e")
                .setSecondTvBackgroundColorHex("#FFFFFF")
                .setSecondTvTextSize(21)

//      .setTimeTvWH(18, 40)
//      .setColonTvSize(30)

                // 开启倒计时
                .startCountDown()

                // 设置倒计时结束监听
                .setCountDownEndListener(new CountDownView.CountDownEndListener() {
                    @Override
                    public void onCountDownEnd() {
                        Toast.makeText(GuideActivity.this, "倒计时结束", Toast.LENGTH_SHORT).show();
                    }
                })
        ;


    }

    private void count() {
        mCountDownTextView
                .setNormalText("获取验证码")
                .setCountDownText("重新获取(", "s)")
                .setCloseKeepCountDown(false)//关闭页面保持倒计时开关
                .setCountDownClickable(false)//倒计时期间点击事件是否生效开关
                .setShowFormatTime(false)//是否格式化时间
                .setIntervalUnit(TimeUnit.SECONDS)
                .setOnCountDownStartListener(new CountDownTextView.OnCountDownStartListener() {
                    @Override
                    public void onStart() {
                        Toast.makeText(GuideActivity.this, "开始计时", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnCountDownTickListener(new CountDownTextView.OnCountDownTickListener() {
                    @Override
                    public void onTick(long untilFinished) {
                        Log.e("------", "onTick: " + untilFinished);
                    }
                })
                .setOnCountDownFinishListener(new CountDownTextView.OnCountDownFinishListener() {
                    @Override
                    public void onFinish() {
                        Toast.makeText(GuideActivity.this, "倒计时完毕", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(GuideActivity.this, "短信已发送", Toast.LENGTH_SHORT).show();
                        mCountDownTextView.startCountDown(100);
                    }
                });
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
//    private void initImageView(){
//
//        mMyImageLoader = new MyImageLoader();
//        mBanner = findViewById(R.id.banner);
//        //设置样式，里面有很多种样式可以自己都看看效果
//        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
//        //设置图片加载器
//        mBanner.setImageLoader(mMyImageLoader);
//        //设置轮播的动画效果,里面有很多种特效,可以都看看效果。
//        mBanner.setBannerAnimation(Transformer.Default);
//        //轮播图片的文字
//        mBanner.setBannerTitles(imageTitle);
//        //设置轮播间隔时间
//        mBanner.setDelayTime(3000);
//        //设置是否为自动轮播，默认是true
//        mBanner.isAutoPlay(true);
//        //设置指示器的位置，小点点，居中显示
//        mBanner.setIndicatorGravity(BannerConfig.RIGHT);
//        //设置图片加载地址
//        mBanner.setImages(imagePath)
//                //轮播图的监听
//                .setOnBannerListener(this)
//                //开始调用的方法，启动轮播图。
//                .start();
//
//    }
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
