package com.find_carhelper.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.find_carhelper.R;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.MainActivity;
import com.find_carhelper.ui.base.MVPBaseActivity;
import com.find_carhelper.utils.ToastUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;

public class GuideActivity extends MVPBaseActivity implements View.OnClickListener, OnBannerListener {
    private MZBannerView mMZBanner;
    private MyImageLoader mMyImageLoader;
    private ArrayList<Integer> imagePath;
    private ArrayList<String> imageTitle;

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
        mMZBanner = findViewById(R.id.banner);
        initImages();
        initImageView();
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
