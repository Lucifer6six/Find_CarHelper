package com.find_carhelper.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.find_carhelper.R;
import com.find_carhelper.bean.FindCarCooperatingBean;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.http.Constants;
import com.find_carhelper.http.NetRequest;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.activity.OrdersInfoActivity;
import com.find_carhelper.ui.adapter.FindCarCompletingOrderAdapter;
import com.find_carhelper.ui.base.MVPBaseFragment;
import com.find_carhelper.utils.SharedPreferencesUtil;
import com.find_carhelper.widgets.OnItemClickListeners;
import com.wega.library.loadingDialog.LoadingDialog;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;


/**
 * 寻车订单-合作中
 */
public class FindCarOrdersCooptering extends MVPBaseFragment implements OnItemClickListeners, View.OnClickListener {
    private RecyclerView recycleListView;
    private FindCarCompletingOrderAdapter mListOrderAcceptAdapter;
    public List<FindCarCooperatingBean.data.carInfo> carBeans;
    public RelativeLayout no_auth_layout;
    public TextView no_data_tv;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_find_car_orders;
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
        no_auth_layout = mRootView.findViewById(R.id.no_auth_layout);
        no_data_tv = mRootView.findViewById(R.id.no_data_tv);
        initLoading();
    }

    public void initLoading() {

        LoadingDialog.Builder builder = new LoadingDialog.Builder(getContext());
        builder.setLoading_text("加载中...")
                .setFail_text("加载失败")
                .setSuccess_text("加载成功");
        //设置延时5000ms才消失,可以不设置默认1000ms
        //设置默认延时消失事件, 可以不设置默认不调用延时消失事件
    }

    private void initAdapter(List<FindCarCooperatingBean.data.carInfo> list) {
        mListOrderAcceptAdapter = new FindCarCompletingOrderAdapter(mContext, list);
        mListOrderAcceptAdapter.setOnItemClickListeners(this);
        recycleListView.setLayoutManager(new LinearLayoutManager(mContext));
        recycleListView.setHasFixedSize(true);
        recycleListView.setAdapter(mListOrderAcceptAdapter);

        mListOrderAcceptAdapter.setOnItemClickListener(new FindCarCompletingOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, FindCarCompletingOrderAdapter.ViewName viewName, int position) {

                if (viewName.equals(FindCarCompletingOrderAdapter.ViewName.ITEM)){

                    Intent intent = new Intent(getContext(), OrdersInfoActivity.class);
                    // intent.putExtra("vin", list.get(position).getVin());
                    // intent.putExtra("no", list.get(position).getOrderCode());
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(),"accepOrders",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onItemLongClick(View v) {

            }
        });
    }

    @Override
    protected void initData() {

    }

    public void getCarData() {
        String url = Constants.SERVICE_NAME + Constants.FIND_CAR_ORDERS;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", Constants.ID);//MobileInfoUtil.getIMEI(getContext())
        params.put("accessToken", SharedPreferencesUtil.getString(getContext(), "token"));
        params.put("status", "COOPERATING");
        params.put("pageNum", "0");
        params.put("pageSize", "100");
        // ...
        NetRequest.getFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                // 请求成功的回调
                Log.e("TAG", result.toString());
                no_auth_layout.setVisibility(View.INVISIBLE);
                if (!result.equals("401")) {
                    if (!TextUtils.isEmpty(result)) {
                        JSONObject jsonObject = JSON.parseObject(result);
                        if (jsonObject.getString("status") != null)
                            if (jsonObject.getString("status").equals("500") && jsonObject.getString("message").equals("W02000")) {
                                Toast.makeText(getContext(), "服务器错误", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        if (jsonObject.getString("success").equals("true")) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            Message msg = new Message();
                            carBeans = JSON.parseArray(jsonObject1.getJSONArray("list").toJSONString(), FindCarCooperatingBean.data.carInfo.class);
                            if (carBeans.size()>0){
                                msg.what = 0;
                            }else{
                                msg.what = 1;
                            }
                            mHandler.sendMessage(msg);
                        } else {
                            String msg = jsonObject.getString("message");
                            if (msg.contains("认证")) {
                                no_auth_layout.setVisibility(View.INVISIBLE);
                            }
                            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "服务器错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                // 请求失败的回调
                Log.e("TAG", request.toString() + e.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getCarData();
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, Object data, int position) {

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    no_data_tv.setVisibility(View.GONE);
                    Log.e("!@#", "size = " + carBeans.size());
                    if (carBeans != null) {
                        initAdapter(carBeans);
                    }
                    break;
                case 1:
                    no_data_tv.setVisibility(View.VISIBLE);
                    no_data_tv.setText("没有合作中的寻车订单~");
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
    }
}
