package com.find_carhelper.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.find_carhelper.R;
import com.find_carhelper.bean.CarBean;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.base.MVPBaseActivity;

import java.nio.file.attribute.UserDefinedFileAttributeView;

import cc.ibooker.zcountdownviewlib.CountDownView;

public class OrderDetailActivity extends MVPBaseActivity implements View.OnClickListener {
    public Button request_in_store;
    public CarBean carBean;
    TextView car_id_, car_type, money, car_no_, area_,
            key_, master_, location_method_, location_name_,
            car_name_, location_pin_, memo,accept_orders_time;
    CountDownView countdownView;
    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initViews() {

        request_in_store = findViewById(R.id.request_in_store);
        accept_orders_time = findViewById(R.id.accept_orders_time);
        countdownView = findViewById(R.id.countdownView);
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
        request_in_store.setOnClickListener(this);
        carBean = (CarBean) getIntent().getSerializableExtra("obj");
        if (carBean != null) {
            setValue();
        }
        registerLeftClickEvent(v -> finish());
    }

    @Override
    protected void initData() {
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
       // location_pin_.setText(carBean.getPositioningPin());
        memo.setText(carBean.getRemark());
        accept_orders_time.setText(carBean.getOrderTime());
        if (carBean.getCountdown() != null) {
            initCountDown1(countdownView, Integer.parseInt(carBean.getCountdown()));
        }
    }
    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }
    public void initCountDown1(CountDownView countDownView,int seconds){
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
                        //Toast.makeText(mContext, "倒计时结束", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(mContext, RequestInStoreActivity.class);
        intent.putExtra("no", carBean.getOrderCode());
        intent.putExtra("vin", carBean.getVin());
        mContext.startActivity(intent);
    }
}
