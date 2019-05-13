package com.find_carhelper.ui.fragment;

import android.support.v4.app.Fragment;

import com.find_carhelper.R;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.base.MVPBaseFragment;


/**
 * 路径分析
 */
public class TogetherNewsFragment extends MVPBaseFragment {

    public static Fragment newInstance() {
       TogetherNewsFragment fragment = new TogetherNewsFragment();
        return fragment;
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_together_news;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }
}
