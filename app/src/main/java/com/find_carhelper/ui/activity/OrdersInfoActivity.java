package com.find_carhelper.ui.activity;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.find_carhelper.R;
import com.find_carhelper.bean.FindCarInfo;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.http.Constants;
import com.find_carhelper.http.NetRequest;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.base.MVPBaseActivity;
import com.find_carhelper.utils.SharedPreferencesUtil;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

public class OrdersInfoActivity extends MVPBaseActivity {
    public Button request_complete;
    public String vin;
    public String orderCode;
    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_orders_info;
    }

    @Override
    protected void initViews() {

        request_complete = findViewById(R.id.request_complete);
        request_complete.setOnClickListener(view -> startActivity(new Intent(OrdersInfoActivity.this, RequesCompleteActivity.class)));
    }

    @Override
    protected void initData() {
        vin = getIntent().getStringExtra("vin");
        orderCode = getIntent().getStringExtra("no");
        getData();
    }

    public void getData(){
        String url = Constants.SERVICE_NAME + Constants.FIND_CAR_OWNERS;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", Constants.ID);//MobileInfoUtil.getIMEI(getContext())
        params.put("accessToken", SharedPreferencesUtil.getString(getApplicationContext(), "token"));
        params.put("vin", vin);
        params.put("orderCode", orderCode);
        // ...
        NetRequest.getFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                // 请求成功的回调
                Log.e("TAG", result.toString());
                if (!result.equals("401")) {
                    if (!TextUtils.isEmpty(result)) {
                        JSONObject jsonObject = JSON.parseObject(result);
                        if (jsonObject.getString("status") != null)
                            if (jsonObject.getString("status").equals("500") && jsonObject.getString("message").equals("W02000")) {
                                Toast.makeText(getApplicationContext(), "服务器错误", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        if (jsonObject.getString("success").equals("true")) {

                        } else {

                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "服务器错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                // 请求失败的回调
                Log.e("TAG", request.toString() + e.getMessage());
            }
        });
    }
    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
