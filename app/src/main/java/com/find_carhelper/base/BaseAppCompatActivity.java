package com.find_carhelper.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.utils.BaseAppManager;
import com.find_carhelper.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


/**
 * 项目通用父类
 *
 * @author
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity {

    /**
     * 上下文对象
     */
    protected Context mContext = null;

    /**
     * 管理RxJava2生命周期
     */
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    /**
     * 是否绑定EventBus
     *
     * @return
     */
    protected abstract boolean isBindEventBusHere();

    /**
     * 设置布局ID
     *
     * @return
     */
    protected abstract int getContentViewLayoutID();

    /**
     * 初始化布局以及监听事件
     */
    protected abstract void initViews();

    /**
     * 初始化业务操作
     */
    protected abstract void initData();

    /**
     * EventBus 回调方法
     *
     * @param eventCenter
     */
    protected abstract void onEventComing(EventCenter eventCenter);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置透明状态栏
        //fullScreen(BaseAppCompatActivity.this);
        // 竖屏锁定
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mContext = this;

        BaseAppManager.getInstance().addActivity(this);
        //是否绑定了EventBus
        if (isBindEventBusHere()) {
            EventBus.getDefault().register(this);
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }
        initViews();
        initData();
    }

    /**
     * 通过设置全屏，设置状态栏透明
     *
     * @param activity
     */
    private void fullScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以正常设置
                //window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
                //attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }

    protected void addCompositeDisposable(Disposable disposable) {
        if (disposable != null) {
            compositeDisposable.add(disposable);
        }
    }

    /**
     * 停止RxJava2事件订阅
     *
     * @param disposable
     */
    protected void stopCompositeDisposable(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }


    /**
     * 当前Activity Or Fragment 注册 如有EventBus回调 则会回调到此方法
     *
     * @param eventCenter
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventCenter eventCenter) {
        if (null != eventCenter) {
            onEventComing(eventCenter);
        }
    }

    @Override
    public void finish() {
        super.finish();
        BaseAppManager.getInstance().removeActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 如果当前Activity注册了EventBus 则取消注册
        if (isBindEventBusHere()) {
            EventBus.getDefault().unregister(this);
        }
        //  解除RxJava2的订阅
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    /**
     * Toast 消息
     * @param message message
     */
    protected void showToast(String message) {
        ToastUtil.showShotToast(mContext.getApplicationContext(), message);
    }

    protected void showToast(int msgId) {
        ToastUtil.showShotToast(mContext.getApplicationContext(), msgId);
    }
}