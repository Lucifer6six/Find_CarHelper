package com.find_carhelper.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.find_carhelper.R;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.base.MVPBaseActivity;
import com.find_carhelper.ui.fragment.MyOrdersFragment;

public class BaoQuanActivity extends MVPBaseActivity {

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_bao_quan;
    }

    @Override
    protected void initViews() {
        getSupportFragmentManager()    //
                .beginTransaction()
                .add(R.id.content, new MyOrdersFragment())   // 此处的R.id.fragment_container是要盛放fragment的父容器
                .commit();

        registerLeftClickEvent(view -> finish());


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
