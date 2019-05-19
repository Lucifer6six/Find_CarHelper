package com.find_carhelper.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.find_carhelper.R;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.adapter.MyTeamAdapter;
import com.find_carhelper.ui.base.MVPBaseActivity;
import com.find_carhelper.widgets.OnItemClickListeners;

public class MyTeamActivity extends MVPBaseActivity implements OnItemClickListeners {
    private RecyclerView recycleListView;
    private MyTeamAdapter mListOrderAcceptAdapter;
    private Button inviteBtn;
    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_my_team;
    }

    @Override
    protected void initViews() {
        recycleListView = findViewById(R.id.list_teams);
        inviteBtn = findViewById(R.id.invite);
        inviteBtn.setOnClickListener(view -> startActivity(new Intent(MyTeamActivity.this,InviteFriendsActivity.class)));
        initAdapter();
    }

    private void initAdapter(){
        mListOrderAcceptAdapter = new MyTeamAdapter(mContext);
        mListOrderAcceptAdapter.setOnItemClickListeners(this);
        recycleListView.setLayoutManager(new LinearLayoutManager(mContext));
        recycleListView.setHasFixedSize(true);
        recycleListView.setAdapter(mListOrderAcceptAdapter);
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
    public void onItemClick(RecyclerView.ViewHolder viewHolder, Object data, int position) {

    }
}
