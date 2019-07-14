package com.find_carhelper.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.find_carhelper.R;
import com.find_carhelper.bean.CarBean;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.http.Constants;
import com.find_carhelper.http.NetRequest;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.activity.AuthActivity;
import com.find_carhelper.ui.activity.RequestInStoreActivity;
import com.find_carhelper.ui.activity.RequestLaterActivity;
import com.find_carhelper.ui.adapter.MyCooperationOrderAdapter;
import com.find_carhelper.ui.adapter.MyPhbAdapter;
import com.find_carhelper.ui.base.MVPBaseFragment;
import com.find_carhelper.utils.SharedPreferencesUtil;
import com.find_carhelper.widgets.OnItemClickListeners;
import com.wega.library.loadingDialog.LoadingDialog;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;


/**
 * 合作中
 */
public class PaiHangBangFragment extends MVPBaseFragment  implements OnItemClickListeners {
    private RecyclerView recycleListView;
    private MyPhbAdapter mListOrderAcceptAdapter;
    public LoadingDialog loadingDialog;
    public static Fragment newInstance() {
       PaiHangBangFragment fragment = new PaiHangBangFragment();
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
        initAdapter(null);
       // initLoading();
    }
    private void initAdapter(List<CarBean> list){
        mListOrderAcceptAdapter = new MyPhbAdapter(list,mContext);
        mListOrderAcceptAdapter.setOnItemClickListeners(this);
        recycleListView.setLayoutManager(new LinearLayoutManager(mContext));
        recycleListView.setHasFixedSize(true);
        recycleListView.setAdapter(mListOrderAcceptAdapter);
    }
    public void initLoading(){

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
