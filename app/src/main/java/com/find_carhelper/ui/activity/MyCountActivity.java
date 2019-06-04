package com.find_carhelper.ui.activity;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.find_carhelper.R;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.adapter.MyTeamAdapter;
import com.find_carhelper.ui.base.MVPBaseActivity;
import com.find_carhelper.widgets.OnItemClickListeners;

public class MyCountActivity extends MVPBaseActivity implements OnItemClickListeners {
    private RecyclerView recycleListView;
    private MyTeamAdapter mListOrderAcceptAdapter;
    private ImageView withdraw;
    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_my_count;
    }

    @Override
    protected void initViews() {
        recycleListView = findViewById(R.id.list_money);
        withdraw = findViewById(R.id.withdraw);
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyCountActivity.this,WithDrawActivity.class));
            }
        });
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
