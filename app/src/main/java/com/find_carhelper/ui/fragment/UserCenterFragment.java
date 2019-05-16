package com.find_carhelper.ui.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RelativeLayout;

import com.find_carhelper.R;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.activity.EditPswActivity;
import com.find_carhelper.ui.activity.MyTeamActivity;
import com.find_carhelper.ui.activity.NewsActvity;
import com.find_carhelper.ui.base.MVPBaseFragment;



public class UserCenterFragment extends MVPBaseFragment implements View.OnClickListener {

    private RelativeLayout pswLayout,newsLayout,myTeamLayout;

    public static Fragment newInstance() {
       UserCenterFragment fragment = new UserCenterFragment();
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
        return R.layout.fragment_user_center;
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
        pswLayout = mRootView.findViewById(R.id.edit_psw);
        newsLayout = mRootView.findViewById(R.id.news_center);
        myTeamLayout = mRootView.findViewById(R.id.team);
        myTeamLayout.setOnClickListener(this);
        pswLayout.setOnClickListener(this);
        newsLayout.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.edit_psw:

                startActivity(new Intent(getContext(), EditPswActivity.class));

                break;

            case R.id.news_center:
                startActivity(new Intent(getContext(), NewsActvity.class));
                break;

            case R.id.team:
                startActivity(new Intent(getContext(), MyTeamActivity.class));
                break;

        }
    }
}
