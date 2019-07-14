package com.find_carhelper.ui.activity;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.find_carhelper.R;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.http.Constants;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.MainActivity;
import com.find_carhelper.ui.base.MVPBaseActivity;


public class SplashActivity extends MVPBaseActivity {


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_splash;
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
            finish();
        }
    };
    @Override
    protected void initViews() {
        handler.sendEmptyMessageDelayed(0,3000);
    }

    @Override
    protected void initData() {
        if (Constants.DEBUG){
            Constants.SERVICE_NAME = Constants.DEBUG_SERVICE_NAME;
        }else
            Constants.SERVICE_NAME = Constants.REAL_SERVICE_NAME;
    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }
}
