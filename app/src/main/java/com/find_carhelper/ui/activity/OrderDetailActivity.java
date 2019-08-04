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

public class OrderDetailActivity extends MVPBaseActivity implements View.OnClickListener {
    public Button request_in_store;
    public CarBean carBean;
    TextView car_id_, car_type, money, car_no_, area_, key_, master_, location_method_, location_name_, car_name_, location_pin_, memo;

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
    }

    @Override
    protected void initData() {
        carBean = (CarBean) getIntent().getSerializableExtra("obj");
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
//        if (carBean.getCountdown() != null) {
//            initCountDown(countdownView, Integer.parseInt(carBean.getCountdown()));
//        }
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
        startActivity(new Intent(OrderDetailActivity.this, RequestInStoreActivity.class));
    }
}
