package com.find_carhelper.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.find_carhelper.R;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.base.MVPBaseActivity;

public class OrdersInfoActivity extends MVPBaseActivity {
    public Button request_complete;

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

    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
