package com.find_carhelper.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.find_carhelper.R;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.base.MVPBaseActivity;
import com.find_carhelper.ui.fragment.GroupAuthFailFragment;
import com.find_carhelper.ui.fragment.IdentityAuthFailFragment;
import com.find_carhelper.ui.fragment.IdentityAuthFragment;

public class AuthFailActivity extends MVPBaseActivity {

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_auth_fail;
    }

    @Override
    protected void initViews() {
        IdentityAuthFailFragment identityAuthFailFragment = new IdentityAuthFailFragment();
        GroupAuthFailFragment groupAuthFailFragment = new GroupAuthFailFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.container,identityAuthFailFragment).commit();
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
