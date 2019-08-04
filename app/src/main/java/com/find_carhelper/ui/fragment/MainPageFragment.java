package com.find_carhelper.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.find_carhelper.R;
import com.find_carhelper.bean.MainPageDataBean;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.http.Constants;
import com.find_carhelper.http.NetRequest;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.MainActivity;
import com.find_carhelper.ui.activity.BaoQuanActivity;
import com.find_carhelper.ui.activity.FindCarOrdersActivity;
import com.find_carhelper.ui.activity.LoginActivity;
import com.find_carhelper.ui.activity.NewsActvity;
import com.find_carhelper.ui.adapter.FaultRepairPagerAdapter;
import com.find_carhelper.ui.base.MVPBaseFragment;
import com.find_carhelper.utils.SharedPreferencesUtil;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.sunfusheng.marqueeview.MarqueeView;
import com.wega.library.loadingDialog.LoadingDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;


public class MainPageFragment extends MVPBaseFragment implements View.OnClickListener {
    private ViewPager mViewPager;
    private String[] titles;
    private List<Fragment> fragments;
    private FaultRepairPagerAdapter mainPagerAdapter;
    private SmartTabLayout mSmartTablayout;
    private SwipeRefreshLayout refreshLayout;
    private MarqueeView mMarqueeView;
    private MainPageDataBean mainPageDataBean;
    private TextView findingCarTv, findedCarTv, arriveCarTv, arrivedCarTv, clueCarTv, mounthAdd;
    private RelativeLayout find_car_layout, baoquan_layout, news_layout;
    private MainActivity mainActivity;
    public static List<MainPageDataBean.ListBean> findList;
    public static List<MainPageDataBean.ListBean> verListt;
    public static List<MainPageDataBean.ListBean> timeList;
    public static Fragment newInstance() {
        MainPageFragment fragment = new MainPageFragment();
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
        return R.layout.fragment_main_page;
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
        mainActivity = (MainActivity) getActivity();
        mSmartTablayout = (SmartTabLayout) mRootView.findViewById(R.id.smart_tablayout);
        // mTabLayout = mRootView.findViewById(R.id.tool_tab);
        mViewPager = mRootView.findViewById(R.id.view_pager);
        mMarqueeView = mRootView.findViewById(R.id.marqueeView);
        find_car_layout = mRootView.findViewById(R.id.find_car_layout);
        find_car_layout.setOnClickListener(this);
        baoquan_layout = mRootView.findViewById(R.id.baoquan_layout);
        baoquan_layout.setOnClickListener(this);
        news_layout = mRootView.findViewById(R.id.news_layout);
        news_layout.setOnClickListener(this);
        findingCarTv = mRootView.findViewById(R.id.finding_amount);
        findedCarTv = mRootView.findViewById(R.id.finded_amount);
        arriveCarTv = mRootView.findViewById(R.id.awoing_amount);
        arrivedCarTv = mRootView.findViewById(R.id.awoed_amount);
        clueCarTv = mRootView.findViewById(R.id.add_value_tv);
        mounthAdd = mRootView.findViewById(R.id.add);

        refreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.swipe_id);
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadingDialog.loading();
                getData();
            }
        });
        initNews(null);
    }

    public void initNews(List<String> strings) {

        List<String> info = new ArrayList<>();
        mMarqueeView.startWithList(strings);
        mMarqueeView.startWithList(info, R.anim.anim_bottom_in, R.anim.anim_top_out);
        initLoading();
    }


    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    public void getData() {

        String url = Constants.SERVICE_NAME + Constants.MAIN_PAGE_DATE;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", Constants.ID);//
        params.put("accessToken", SharedPreferencesUtil.getString(getContext(), "token"));
        // ...
        NetRequest.getFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                // 请求成功的回调
                loadingDialog.cancel();
                Log.e("TAG", result.toString() + url);
                if (!TextUtils.isEmpty(result) && !result.equals("401")) {
                    JSONObject jsonObject = JSON.parseObject(result);
                    if (jsonObject.getString("status") != null)
                        if (jsonObject.getString("status").equals("500") && jsonObject.getString("message").equals("W02000")) {
                            startIntent();
                            return;
                        }
                    if (jsonObject.getString("success").equals("true")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        mainPageDataBean = JSON.parseObject(jsonObject1.toJSONString(), MainPageDataBean.class);
                        mHandler.sendEmptyMessage(0);
                    } else {
                        Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (result.equals("401")) {
                        startIntent();
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

    public void startIntent() {
        startActivity(new Intent(getContext(), LoginActivity.class));
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (mainPageDataBean != null) {
                        setValue();
                    }
                    initList();
                    refreshLayout.setRefreshing(false);

                    break;
            }
        }
    };
    LoadingDialog loadingDialog;
    public void initLoading() {

        LoadingDialog.Builder builder = new LoadingDialog.Builder(getActivity());
        builder.setLoading_text("加载中...")
                .setFail_text("加载失败")
                .setSuccess_text("加载成功");
        //设置延时5000ms才消失,可以不设置默认1000ms
        //设置默认延时消失事件, 可以不设置默认不调用延时消失事件

        loadingDialog = builder.create();

    }
    public void initList() {
        MainPageFragment.findList = mainPageDataBean.getFindList();
        MainPageFragment.timeList = mainPageDataBean.getTimeList();
        MainPageFragment.verListt = mainPageDataBean.getRetrieveList();
        titles = getResources().getStringArray(R.array.list_tab);
        Fragment cooperatingFragment = PaiHangBangFragment.newInstance(1);
        Fragment alreadyCompleteFragment = PaiHangBangFragment.newInstance(2);
        Fragment paiHangBangFragment = PaiHangBangFragment.newInstance(3);
        fragments = new ArrayList<>();
        fragments.add(cooperatingFragment);
        fragments.add(alreadyCompleteFragment);
        fragments.add(paiHangBangFragment);
        mainPagerAdapter = new FaultRepairPagerAdapter(getChildFragmentManager(), fragments, titles);
        mViewPager.setAdapter(mainPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mSmartTablayout.setViewPager(mViewPager);

    }

    public void setValue() {

        findingCarTv.setText(mainPageDataBean.getFinding());
        findedCarTv.setText(mainPageDataBean.getFound());
        arriveCarTv.setText(mainPageDataBean.getRetrieving());
        arrivedCarTv.setText(mainPageDataBean.getRetrieved());
        clueCarTv.setText(mainPageDataBean.getTotal());
        mounthAdd.setText("本日新增  " + mainPageDataBean.getToday());

        if (mainPageDataBean.getNewsList().size() > 0) {
            initNews(mainPageDataBean.getNewsList());
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.news_layout:

                startActivity(new Intent(getContext(), NewsActvity.class));

                break;
            case R.id.find_car_layout:
                startActivity(new Intent(getActivity(), FindCarOrdersActivity.class));
                break;
            case R.id.baoquan_layout:
                startActivity(new Intent(getContext(), BaoQuanActivity.class));
                break;

        }
    }
}
