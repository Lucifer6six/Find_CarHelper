package com.find_carhelper.ui.activity;

import com.find_carhelper.R;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.base.MVPBaseActivity;

public class OrderDetailActivity extends MVPBaseActivity {

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
