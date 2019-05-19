package com.find_carhelper.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.find_carhelper.R;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.adapter.ListOrderAcceptAdapter;
import com.find_carhelper.ui.adapter.MyCooperationOrderAdapter;
import com.find_carhelper.ui.base.MVPBaseFragment;
import com.find_carhelper.widgets.OnItemClickListeners;


/**
 * 合作中
 */
public class CooperatingFragment extends MVPBaseFragment  implements OnItemClickListeners {
    private RecyclerView recycleListView;
    private MyCooperationOrderAdapter mListOrderAcceptAdapter;
    public static Fragment newInstance() {
       CooperatingFragment fragment = new CooperatingFragment();
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
        return R.layout.fragment_cooperating;
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
        recycleListView = mRootView.findViewById(R.id.list_orders);
        initAdapter();
    }
    private void initAdapter(){
        mListOrderAcceptAdapter = new MyCooperationOrderAdapter(mContext);
        mListOrderAcceptAdapter.setOnItemClickListeners(this);
        recycleListView.setLayoutManager(new LinearLayoutManager(mContext));
        recycleListView.setHasFixedSize(true);
        recycleListView.setAdapter(mListOrderAcceptAdapter);
    }
    @Override
    protected void initData() {

    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, Object data, int position) {

    }
}
