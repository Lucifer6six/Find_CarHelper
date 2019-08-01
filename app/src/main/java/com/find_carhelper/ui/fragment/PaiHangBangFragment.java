package com.find_carhelper.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.find_carhelper.R;
import com.find_carhelper.bean.CarBean;
import com.find_carhelper.bean.ListBean;
import com.find_carhelper.bean.MainPageDataBean;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.adapter.MyPhbAdapter;
import com.find_carhelper.ui.base.MVPBaseFragment;
import com.find_carhelper.widgets.OnItemClickListeners;
import com.wega.library.loadingDialog.LoadingDialog;

import java.util.List;


/**
 * 合作中
 */
public class PaiHangBangFragment extends MVPBaseFragment implements OnItemClickListeners {
    private RecyclerView recycleListView;
    private MyPhbAdapter mListOrderAcceptAdapter;
    public LoadingDialog loadingDialog;
    public RelativeLayout no_data_layout;
    public List<MainPageDataBean.ListBean> listBeans;
    public int type = 0;

    public static Fragment newInstance(int type) {
        PaiHangBangFragment fragment = new PaiHangBangFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
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
        return R.layout.fragment_paihang;
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
        type = getArguments().getInt("type");
        recycleListView = mRootView.findViewById(R.id.list_orders);
        no_data_layout = mRootView.findViewById(R.id.no_data_layout);
        listBeans = getListBeans(type);
        if (listBeans != null && listBeans.size() > 0) {
            initAdapter();
            no_data_layout.setVisibility(View.GONE);
        } else {
            no_data_layout.setVisibility(View.VISIBLE);
        }

    }

    public List<MainPageDataBean.ListBean> getListBeans(int type) {
        switch (type) {

            case 1:
                listBeans = MainPageFragment.verListt;
                break;
            case 2:
                listBeans = MainPageFragment.findList;
                break;
            case 3:
                listBeans = MainPageFragment.timeList;
                break;


        }
        return listBeans;
    }

    private void initAdapter() {


        mListOrderAcceptAdapter = new MyPhbAdapter(listBeans, mContext, type);
        mListOrderAcceptAdapter.setOnItemClickListeners(this);
        recycleListView.setLayoutManager(new LinearLayoutManager(mContext));
        recycleListView.setHasFixedSize(true);
        recycleListView.setAdapter(mListOrderAcceptAdapter);
    }

    public void initLoading() {

        LoadingDialog.Builder builder = new LoadingDialog.Builder(getContext());
        builder.setLoading_text("加载中...")
                .setFail_text("加载失败")
                .setSuccess_text("加载成功");
        //设置延时5000ms才消失,可以不设置默认1000ms
        //设置默认延时消失事件, 可以不设置默认不调用延时消失事件

        loadingDialog = builder.create();

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, Object data, int position) {

    }
}
