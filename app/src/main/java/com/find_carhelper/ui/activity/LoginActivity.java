package com.find_carhelper.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.find_carhelper.R;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.base.MVPBaseActivity;

public class LoginActivity extends MVPBaseActivity implements View.OnClickListener{
    private TextView registTv;
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
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
            registTv = findViewById(R.id.regist_tv);
            registTv.setOnClickListener(this);
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
            case R.id.regist_tv:
                startActivity(new Intent(LoginActivity.this,RegistActivity.class));
                break;
        }
    }
}
