package com.find_carhelper.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.find_carhelper.R;
import com.find_carhelper.bean.CarBean;
import com.find_carhelper.bean.FindCarListBean;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.http.Constants;
import com.find_carhelper.http.NetRequest;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.activity.AuthActivity;
import com.find_carhelper.ui.activity.FindCarOrdersActivity;
import com.find_carhelper.ui.activity.ReUploadImageActivity;
import com.find_carhelper.ui.adapter.FindCarListAdapter;
import com.find_carhelper.ui.adapter.LoadMoreWrapper;
import com.find_carhelper.ui.base.MVPBaseFragment;
import com.find_carhelper.ui.listener.EndlessRecyclerOnScrollListener;
import com.find_carhelper.utils.SharedPreferencesUtil;
import com.find_carhelper.utils.ToastUtil;
import com.find_carhelper.widgets.MarkerOrderPopWindow;
import com.find_carhelper.widgets.OnItemClickListeners;
import com.find_carhelper.widgets.ToolDateSelectorPopWindow;
import com.wega.library.loadingDialog.LoadingDialog;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;


/**
 * 寻车
 */
public class FindCarFragment extends MVPBaseFragment implements OnItemClickListeners, View.OnClickListener {
    private RecyclerView recycleListView;
    private FindCarListAdapter mListOrderAcceptAdapter;
    public FindCarListBean carBeans;
    public FindCarListBean carListBean;
    public RelativeLayout no_auth_layout;
    public RelativeLayout takePhoto, scanPhoto, ImgPhoto;
    LoadMoreWrapper loadMoreWrapper;
    public ImageView find_car;
    public boolean loadMoreFlag = true;
    public int pageNum = 1;
    public TextView order_no;
    public EditText searchView;
    public RelativeLayout iconSearch;
    public String orderNum;
    private Context context;
    public static Fragment newInstance() {
        FindCarFragment fragment = new FindCarFragment();
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
        return R.layout.fragment_find_car;
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
        pageNum = 1;
        getCarData();
    }

    @Override
    protected void onUserInvisible() {
    }

    @Override
    protected void initViews() {
        context =getContext();
        carListBean = new FindCarListBean();
        recycleListView = mRootView.findViewById(R.id.list_orders);
        no_auth_layout = mRootView.findViewById(R.id.no_auth_layout);
        takePhoto = mRootView.findViewById(R.id.take_photo);
        scanPhoto = mRootView.findViewById(R.id.scan);
        ImgPhoto = mRootView.findViewById(R.id.image);
        find_car = mRootView.findViewById(R.id.find_car);
        order_no = mRootView.findViewById(R.id.order_no);
        searchView = mRootView.findViewById(R.id.search_edit);
        iconSearch = mRootView.findViewById(R.id.search_icon);
        find_car.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), FindCarOrdersActivity.class));
        });
        takePhoto.setOnClickListener(this);
        scanPhoto.setOnClickListener(this);
        ImgPhoto.setOnClickListener(this);
        mRootView.findViewById(R.id.auth_action).setOnClickListener(view -> {
            startActivity(new Intent(getContext(), AuthActivity.class));
        });
        iconSearch.setOnClickListener(view -> {
            if (searchView.getText().length() > 6) {
                getSearchCarData(searchView.getText().toString());
            } else {
                ToastUtil.makeShortText("车牌输入不合法", getContext());
            }

        });
        initLoading();
    }

    public void initLoading() {

        LoadingDialog.Builder builder = new LoadingDialog.Builder(getContext());
        builder.setLoading_text("加载中...")
                .setFail_text("加载失败")
                .setSuccess_text("加载成功");
        //设置延时5000ms才消失,可以不设置默认1000ms
    }

    private void initAdapter(List<FindCarListBean.data.carInfo> list) {
        mListOrderAcceptAdapter = new FindCarListAdapter(mContext, list);
        mListOrderAcceptAdapter.setOnItemClickListeners(this);
        loadMoreWrapper = new LoadMoreWrapper(mListOrderAcceptAdapter);
        recycleListView.setLayoutManager(new LinearLayoutManager(mContext));
        recycleListView.setHasFixedSize(true);
        recycleListView.setAdapter(loadMoreWrapper);
        recycleListView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                getCarData();
            }
        });
        mListOrderAcceptAdapter.setOnItemClickListener(new FindCarListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, FindCarListAdapter.ViewName viewName, int position) {
                if (viewName.equals(FindCarListAdapter.ViewName.PRACTISE))
                new ToolDateSelectorPopWindow(getContext(), new MarkerOrderPopWindow.getdata() {
                    @Override
                    public void getdatas(String str) {
                        if (str.equals("1")) {
                            if (!list.get(position).getStatus().equals("已被抢"))
                                acceptOrderAction(list.get(position).getVin());
                            else
                                ToastUtil.makeShortText("该订单已被抢", getContext());
                        }
                    }
                }).showPopupWindow();
            }

            @Override
            public void onItemLongClick(View v) {

            }
        });
    }
    public void acceptOrderAction(String vin) {
        Log.e("失败的", "position == " + vin);
        String url = Constants.SERVICE_NAME + Constants.FIND_CAR_ACCEPT_ORDER;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", Constants.ID);//MobileInfoUtil.getIMEI(getContext())
        params.put("accessToken", SharedPreferencesUtil.getString(getContext(), "token"));
        params.put("vin", vin);
        // ...
        NetRequest.postFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                // 请求成功的回调
                Log.e("acceptOrderAction", result.toString());
                if (!result.equals("401")) {
                    if (!TextUtils.isEmpty(result)) {
                        JSONObject jsonObject = JSON.parseObject(result);
                        if (jsonObject.getString("success").equals("true")) {
                            ToastUtil.makeLongText("接单成功",mContext);
                            pageNum = 1;
                            getCarData();
                            orderNum =""+(Integer.parseInt(orderNum) + 1);
                            order_no.setText(orderNum);
                        } else {
                            Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    }
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
    protected void initData() {

    }

    public void getSearchCarData(String lpn) {
        String url = Constants.SERVICE_NAME + Constants.FIND_CAR;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", Constants.ID);//MobileInfoUtil.getIMEI(getContext())
        params.put("accessToken", SharedPreferencesUtil.getString(getContext(), "token"));
        params.put("pageNum", "" + pageNum);
        params.put("pageSize", "10");
        params.put("searchMethod", "SEARCH");
        params.put("lpn", lpn);
        params.put("locale",Constants.Province+Constants.City+Constants.District);
        params.put("longitude",Constants.Longitude);
        params.put("latitude",Constants.Latitude);
        // ...
        NetRequest.postFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                // 请求成功的回调
                Log.e("TAG + 个体Car == " + pageNum, result.toString());
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
                            Message msg = new Message();
                            carBeans = JSON.parseObject(jsonObject.toJSONString(), FindCarListBean.class);
                            msg.what = 3;
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

    public void getCarData() {
        String url = Constants.SERVICE_NAME + Constants.FIND_CAR_LIST;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", Constants.ID);//MobileInfoUtil.getIMEI(getContext())
        params.put("accessToken", SharedPreferencesUtil.getString(context, "token"));
        params.put("pageNum", "" + pageNum);
        params.put("pageSize", "10");
        // ...
        NetRequest.getFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                // 请求成功的回调
                Log.e("TAG + 个体Car == " + pageNum, result.toString());
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
                            Message msg = new Message();
                            carBeans = JSON.parseObject(jsonObject.toJSONString(), FindCarListBean.class);
                            if (pageNum > 1) {
                                msg.what = 1;
                            } else
                                msg.what = 0;

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
                    if (carBeans != null) {
                        carListBean = carBeans;
                        initAdapter(carListBean.getData().getList());
                        orderNum = carBeans.getData().getTotal();
                        order_no.setText(orderNum);
                    }
                    break;
                case 1:
                    if (carBeans != null) {
                        if (carBeans.getData().getList().size() > 0 && loadMoreFlag) {
                            for (int i = 0; i < carBeans.getData().getList().size(); i++) {
                                carListBean.getData().getList().add(carBeans.getData().getList().get(i));
                            }
                            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);
                            if (carBeans.getData().isLastPage.equals("true")) {
                                loadMoreFlag = false;
                            }
                        } else
                            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                    }
                    break;
                case 3:
                    if (carBeans != null)
                        if (mListOrderAcceptAdapter != null) {
                            mListOrderAcceptAdapter.notifyDataSetChanged();
                        }

                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        Toast.makeText(getContext(), "功能尚未开放，敬请期待", Toast.LENGTH_SHORT).show();
    }
}
