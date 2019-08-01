package com.find_carhelper.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.find_carhelper.R;
import com.find_carhelper.bean.CarBean;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.http.Constants;
import com.find_carhelper.http.NetRequest;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.base.MVPBaseActivity;
import com.find_carhelper.utils.SharedPreferencesUtil;
import com.find_carhelper.utils.ToastUtil;

import java.io.IOException;
import java.util.HashMap;

import cc.ibooker.zcountdownviewlib.CountDownView;
import okhttp3.Request;

public class CarDetailActivity extends MVPBaseActivity implements View.OnClickListener {
    public Button request_in_store;
    public CarBean carBean;
    TextView car_id_, car_type, money, car_no_, area_, key_, master_, location_method_, location_name_, car_name_, location_pin_, memo;
    CountDownView countdownView;

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_car_detail;
    }

    @Override
    protected void initViews() {

        request_in_store = findViewById(R.id.request_in_store);
        car_id_ = findViewById(R.id.car_id_);
        car_type = findViewById(R.id.car_type);
        car_no_ = findViewById(R.id.car_no_);
        money = findViewById(R.id.money);
        area_ = findViewById(R.id.area_);
        key_ = findViewById(R.id.key_);
        master_ = findViewById(R.id.master_);
        location_method_ = findViewById(R.id.location_method_);
        location_name_ = findViewById(R.id.location_name_);
        car_name_ = findViewById(R.id.car_name_);
        location_pin_ = findViewById(R.id.location_pin_);
        memo = findViewById(R.id.memo);
        countdownView = findViewById(R.id.countdownView);
        request_in_store.setOnClickListener(this);
        registerLeftClickEvent(view -> finish());

    }

    @Override
    protected void initData() {
        carBean = getIntent().getParcelableExtra("obj");
        if (carBean != null) {
            setValue();
        }
    }

    public void setValue() {
        car_type.setText(carBean.getVehicleModel());
        money.setText(carBean.getRewardAmount());
        car_id_.setText(carBean.getLpn());
        car_no_.setText(carBean.getVin());
        area_.setText(carBean.getRegion());
        key_.setText(carBean.hasKey);
        master_.setText(carBean.getPartya());
        location_method_.setText(carBean.getPositioningMethod());
        location_name_.setText(carBean.getPositioningName());
        car_name_.setText(carBean.getLesseeInfo());
        location_pin_.setText(carBean.getPositioningPin());
        memo.setText(carBean.getRemark());
        if (!TextUtils.isEmpty(carBean.getCountdown()) && !carBean.getCountdown().equals("0")) {
            initCountDown(countdownView, Integer.parseInt(carBean.getCountdown()));
            countdownView.setVisibility(View.VISIBLE);
        }
    }

    public void initCountDown(CountDownView countDownView, int seconds) {
        // 基本属性设置
        countDownView.setCountTime(seconds) // 设置倒计时时间戳
                .setHourTvTextColorHex("#f2692e")
                .setHourTvGravity(CountDownView.CountDownViewGravity.GRAVITY_CENTER)
                .setHourTvTextSize(16)
                .setHourTvBackgroundColorHex("#fef0ea")
                .setHourColonTvSize(18, 0)
                .setHourColonTvTextColorHex("#f2692e")
                .setHourColonTvGravity(CountDownView.CountDownViewGravity.GRAVITY_CENTER)
                .setHourColonTvTextSize(21)

                .setMinuteTvTextColorHex("#f2692e")
                .setMinuteTvGravity(CountDownView.CountDownViewGravity.GRAVITY_CENTER)
                .setMinuteTvTextSize(16)
                .setMinuteTvBackgroundColorHex("#fef0ea")
                .setMinuteColonTvSize(18, 0)
                .setMinuteColonTvTextColorHex("#f2692e")
                .setMinuteColonTvGravity(CountDownView.CountDownViewGravity.GRAVITY_CENTER)
                .setMinuteColonTvTextSize(21)

                .setSecondTvTextColorHex("#f2692e")
                .setSecondTvGravity(CountDownView.CountDownViewGravity.GRAVITY_CENTER)
                .setSecondTvBackgroundColorHex("#fef0ea")
                .setSecondTvTextSize(16)
                // 开启倒计时
                .startCountDown()
                // 设置倒计时结束监听
                .setCountDownEndListener(new CountDownView.CountDownEndListener() {
                    @Override
                    public void onCountDownEnd() {
                        Toast.makeText(mContext, "倒计时结束", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onClick(View view) {
        //startActivity(new Intent(CarDetailActivity.this, RequestInStoreActivity.class));
        if (!carBean.getStatus().equals("已被抢"))
            acceptOrderAction();
        else
            ToastUtil.makeShortText("该订单已被抢",getApplicationContext());
    }

    public void acceptOrderAction() {
        String url = Constants.SERVICE_NAME + Constants.ACCEPT_ORDER;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", Constants.ID);//MobileInfoUtil.getIMEI(getContext())
        params.put("accessToken", SharedPreferencesUtil.getString(getApplicationContext(), "token"));
        params.put("vin", carBean.getVin());
        // ...
        NetRequest.postFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                // 请求成功的回调
                Log.e("acceptOrderAction", result.toString());
                if (!result.equals("401")) {
                    if (!TextUtils.isEmpty(result)) {
                        JSONObject jsonObject = JSON.parseObject(result);
                        if (jsonObject.getString("success").equals("true")) {
                            String countDown = jsonObject.getString("data");
                            carBean.setCountdown(countDown);
                            if (!TextUtils.isEmpty(countDown))
                                initCountDown(countdownView, Integer.parseInt(countDown));
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    startIntent();
                }
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                // 请求失败的回调
                Log.e("TAG", request.toString() + e.getMessage());
            }
        });
    }


    boolean flag = false;

    public void startIntent() {
        if (!flag) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            flag = true;
        }
    }
}
