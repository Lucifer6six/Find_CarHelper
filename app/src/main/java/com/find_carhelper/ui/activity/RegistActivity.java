package com.find_carhelper.ui.activity;

import android.content.Intent;
import android.view.View;

import com.find_carhelper.R;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.base.MVPBaseActivity;

public class RegistActivity extends MVPBaseActivity implements View.OnClickListener{

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
        return R.layout.activity_regist;
    }

    @Override
    protected void initViews() {
        findViewById(R.id.login_tv).setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_tv:
                startActivity(new Intent(RegistActivity.this,LoginActivity.class));
                break;

        }
    }
}
