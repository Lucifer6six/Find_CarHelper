package com.find_carhelper.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.find_carhelper.R;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.base.MVPBaseActivity;

public class OrderDetailActivity extends MVPBaseActivity implements View.OnClickListener {
    public Button request_in_store;

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
        request_in_store.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        startActivity(new Intent(OrderDetailActivity.this, RequestInStoreActivity.class));
    }
}
