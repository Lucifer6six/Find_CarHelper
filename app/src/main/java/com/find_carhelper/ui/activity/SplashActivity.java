package com.find_carhelper.ui.activity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

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
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }
    };
    @Override
    protected void initViews() {

        int checkCoarseFine = ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.READ_PHONE_STATE);
        if (checkCoarseFine == PackageManager.PERMISSION_GRANTED ) {
            initDeviceID();
        } else {//没有权限
            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_FINE_LOCATION}, 1);//申请授权
        }
        handler.sendEmptyMessageDelayed(0,3000);
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
