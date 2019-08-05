package com.find_carhelper.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.find_carhelper.R;
import com.find_carhelper.bean.CarBean;
import com.find_carhelper.bean.CardBean;
import com.find_carhelper.bean.CityBean;
import com.find_carhelper.bean.JsonBean;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.http.Application;
import com.find_carhelper.http.Constants;
import com.find_carhelper.http.NetRequest;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.activity.BaoQuanActivity;
import com.find_carhelper.ui.activity.CarDetailActivity;
import com.find_carhelper.ui.activity.LoginActivity;
import com.find_carhelper.ui.adapter.ListOrderAcceptAdapter;
import com.find_carhelper.ui.adapter.LoadMoreWrapper;
import com.find_carhelper.ui.base.MVPBaseFragment;
import com.find_carhelper.ui.listener.EndlessRecyclerOnScrollListener;
import com.find_carhelper.utils.GetJsonDataUtil;
import com.find_carhelper.utils.SharedPreferencesUtil;
import com.find_carhelper.utils.ToastUtil;
import com.find_carhelper.widgets.MarkerOrderPopWindow;
import com.find_carhelper.widgets.ToolDateSelectorPopWindow;
import com.google.gson.Gson;
import com.wega.library.loadingDialog.LoadingDialog;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;


public class AcceptOrderFragment extends MVPBaseFragment {

    private RelativeLayout areaSelectedLayout, timeSelectedLayout;
    private TextView areaSelectedTv, timeSelectedTv, order_no;

    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<CityBean>> options2Items = new ArrayList<>();
    private RecyclerView recycleListView;
    private ListOrderAcceptAdapter mListOrderAcceptAdapter;
    private List<JsonBean> options1Items_ = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items_ = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items_ = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<CityBean>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    public String queryCode = "";
    public String queryCode1 = "";
    public String minMoney = "";
    public String maxMoney = "";
    private static boolean isLoaded = false;
    public LoadingDialog loadingDialog;
    public List<CarBean> carBeans;
    public List<CarBean> carBeanList;
    LoadMoreWrapper loadMoreWrapper;
    public boolean loadMoreFlag = true;
    public int pageNum = 1;
    public String isLasePage;
    public ImageView imageOrders;
    public String orderNum;
    public boolean alreadyInit = false;

    public static Fragment newInstance() {
        AcceptOrderFragment fragment = new AcceptOrderFragment();
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
        return R.layout.fragment_accept_order;
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
        Log.e("tag", "onUserVisible");
        minMoney = "";
        maxMoney = "";
        queryCode = "";
        queryCode1 = "";
        areaSelectedTv.setText("全部区域");
        timeSelectedTv.setText("全部赏金");
        loadingDialog.loading();
        getProvinceData();
        getCarData();
    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void initViews() {
        carBeanList = new ArrayList<CarBean>();
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        areaSelectedLayout = mRootView.findViewById(R.id.area_layout);
        timeSelectedLayout = mRootView.findViewById(R.id.time_layout);
        recycleListView = mRootView.findViewById(R.id.list_orders);
        areaSelectedTv = mRootView.findViewById(R.id.areaTv);
        timeSelectedTv = mRootView.findViewById(R.id.timeTv);
        imageOrders = mRootView.findViewById(R.id.imageOrders);
        order_no = mRootView.findViewById(R.id.order_no);
        areaSelectedLayout.setOnClickListener(view -> {
            if (isLoaded) {
                showAreaPickView();
            } else {
                Toast.makeText(getContext(), "Please waiting until the data is parsed", Toast.LENGTH_SHORT).show();
            }


        });
        timeSelectedLayout.setOnClickListener(view -> {
            showTimePickView();

        });
        imageOrders.setOnClickListener(view -> {

            startActivity(new Intent(getContext(), BaoQuanActivity.class));

        });
        // initAdapter();
        initLoading();
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

    private void initAdapter(List<CarBean> list) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setId(i);
        }
        mListOrderAcceptAdapter = new ListOrderAcceptAdapter(mContext, list);
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
        mListOrderAcceptAdapter.setOnItemClickListener(new ListOrderAcceptAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View v, ListOrderAcceptAdapter.ViewName viewName, int position) {
                //  Toast.makeText(getContext(),"position ="+position,Toast.LENGTH_SHORT).show();

                if (viewName == ListOrderAcceptAdapter.ViewName.ORDERS) {
                    new ToolDateSelectorPopWindow(getContext(), new MarkerOrderPopWindow.getdata() {
                        @Override
                        public void getdatas(String str) {
                            if (str.equals("1")) {
                                if (!list.get(position).getStatus().equals("已被抢"))
                                    acceptOrderAction(position);
                                else
                                    ToastUtil.makeShortText("该订单已被抢", getContext());

                            }
                        }
                    }).showPopupWindow();
                } else if (viewName == ListOrderAcceptAdapter.ViewName.ITEM) {

                    if (Constants.canOrder) {
                        Intent intent = new Intent(getContext(), CarDetailActivity.class);
                        intent.putExtra("obj", list.get(position));
//                        Bundle bundle = new Bundle();
//                        bundle.("obj", list.get(position));
//                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onItemLongClick(View v) {

            }
        });

    }

    public void acceptOrderAction(int position) {
        Log.e("失败的", "position == " + position);
        String url = Constants.SERVICE_NAME + Constants.ACCEPT_ORDER;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", Constants.ID);//MobileInfoUtil.getIMEI(getContext())
        params.put("accessToken", SharedPreferencesUtil.getString(getContext(), "token"));
        params.put("vin", carBeans.get(position).getVin());
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
                            String countDown = jsonObject.getString("data");
                           // carBeans.get(position).setCountdown(countDown);
                            //mListOrderAcceptAdapter.notifyItemChanged(position);
                            getCarData();
                            orderNum =""+(Integer.parseInt(orderNum) + 1);
                            order_no.setText(orderNum);
                            loadingDialog.cancel();
                        } else {
                            loadingDialog.cancel();
                            Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    loadingDialog.cancel();
                    startIntent();
                }
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                // 请求失败的回调
                loadingDialog.cancel();
                Log.e("TAG", request.toString() + e.getMessage());
            }
        });
    }

    private void showAreaPickView() {

        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2).getName() : "";

                String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3).getName() : "";

                String tx = opt1tx + "-" + opt2tx;
                if (opt1tx.equals("全部") && opt2tx.equals("全部")) {
                    tx = "全部区域";
                }
                if (!opt2tx.equals("全部")) {
                    if (!TextUtils.isEmpty(opt2tx)) {
                        queryCode = options2Items.get(options1).get(options2).getCode();
                    }
                } else {
                    queryCode1 = options1Items.get(options1).getCode();
                }
                //Toast.makeText(getContext(), tx, Toast.LENGTH_SHORT).show();
                getCarData();
                areaSelectedTv.setText(tx);
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items);//三级选择器
        pvOptions.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("AcceptOrderFragemtn", "destory");
        queryCode = "";
        queryCode1 = "";
        minMoney = "";
        maxMoney = "";
    }

    private ArrayList<CardBean> cardItem = new ArrayList<>();
    private OptionsPickerView pvCustomOptions;

    private void showTimePickView() {


        OptionsPickerView pvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items_.size() > 0 ?
                        options1Items_.get(options1).getPickerViewText() : "";

                String opt2tx = options2Items_.size() > 0
                        && options2Items_.get(options1).size() > 0 ?
                        options2Items_.get(options1).get(options2) : "";


                String tx = opt1tx + " ~ " + opt2tx;
                if (opt1tx.equals("全部赏金")) {
                    tx = "全部赏金";
                }
                timeSelectedTv.setText(tx);
                minMoney = opt1tx;
                maxMoney = opt2tx;
                if (minMoney.equals("全部赏金")) {
                    minMoney = "";
                    maxMoney = "";

                }
                getCarData();
            }
        })

                .setTitleText("赏金选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items_, options2Items_);//三级选择器
        pvOptions.show();
    }


    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("tag", "onResume");
        String token = SharedPreferencesUtil.getString(getContext(), "token");
        if (!TextUtils.isEmpty(token)) {
            loadingDialog.loading();
            getProvinceData();
            getCarData();
        } else {
            Toast.makeText(getContext(), "请重新登录", Toast.LENGTH_SHORT).show();
            startIntent();
        }

    }

    public void getCarData() {
        String url = Constants.SERVICE_NAME + Constants.GET_CARS;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", Constants.ID);//MobileInfoUtil.getIMEI(getContext())
        params.put("accessToken", SharedPreferencesUtil.getString(Application.getContext(), "token"));
        params.put("provinceCode", queryCode1);
        params.put("cityCode", queryCode);
        params.put("minAmount", minMoney);
        params.put("maxAmount", maxMoney);
        params.put("pageNum", "" + pageNum);
        params.put("pageSize", "10");
        // ...
        NetRequest.getFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                // 请求成功的回调
                loadingDialog.cancel();
                Log.e("getCarData", result.toString());
                if (!result.equals("401")) {
                    if (!TextUtils.isEmpty(result)) {
                        JSONObject jsonObject = JSON.parseObject(result);
                        if (jsonObject.getString("status") != null)
                            if (jsonObject.getString("status").equals("500") && jsonObject.getString("message").equals("W02000")) {
                                startIntent();
                                return;
                            }
                        if (jsonObject.getString("success").equals("true")) {
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            Message msg = new Message();
                            carBeans = JSON.parseArray(jsonObject1.getJSONArray("list").toJSONString(), CarBean.class);

                            if (jsonObject1.getString("isLastPage").equals("true")) {
                                loadMoreFlag = false;
                            }

                            if (pageNum > 1) {
                                msg.what = 6;
                            } else
                                msg.what = 5;
                            mHandler.sendMessage(msg);
                        } else {
                            Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    loadingDialog.cancel();
                    startIntent();
                }
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                // 请求失败的回调
                loadingDialog.cancel();
                Log.e("TAG", request.toString() + e.getMessage());
            }
        });
    }

    boolean flag = false;

    public void startIntent() {
        if (!flag) {
            startActivity(new Intent(getContext(), LoginActivity.class));
            flag = true;
        }
    }

    public void getProvinceData() {
        String url = Constants.SERVICE_NAME + Constants.GET_AREAA;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", Constants.ID);//MobileInfoUtil.getIMEI(getContext())
        params.put("accessToken", SharedPreferencesUtil.getString(Application.getContext(), "token"));
        // ...
        NetRequest.getFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                // 请求成功的回调
                loadingDialog.cancel();
                Log.e("AcceptOrder", result.toString());
                if (!result.equals("401")) {
                    JSONObject jsonObject = JSON.parseObject(result);
                    if (jsonObject.getString("status") != null)
                        if (jsonObject.getString("status").equals("500") && jsonObject.getString("message").equals("W02000")) {
                            startIntent();
                            return;
                        }
                    if (jsonObject.getString("success").equals("true")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        if (jsonObject1.getString("canOrder").equals("true")) {
                            Constants.canOrder = true;
                        } else {
                            Constants.canOrder = false;
                        }
                        orderNum = jsonObject1.getString("orderCount");
                        Message msg = new Message();
                        msg.obj = jsonObject1.getJSONArray("provinceList").toString();
                        msg.what = 4;
                        mHandler.sendMessage(msg);
                    } else {
                        Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    startIntent();
                }
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                // 请求失败的回调
                Log.e("AcceptOrderFrament失败的", request.toString() + e.getMessage());
            }
        });
    }

    private void initJsonData(String result) {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         * */
        // String JsonData = new GetJsonDataUtil().getJson(result);//获取assets目录下的json文件数据
        String JsonDatas = new GetJsonDataUtil().getJson(getContext(), "money.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(result);//用Gson 转成实体
        ArrayList<JsonBean> jsonBeans = parseData(JsonDatas);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<CityBean> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<CityBean>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                CityBean cityBean = new CityBean();
                cityBean.setName(jsonBean.get(i).getCityList().get(c).getName());
                cityBean.setCode(jsonBean.get(i).getCityList().get(c).getCode());
                cityList.add(cityBean);//添加城市

                //  ArrayList<CityBean> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                // city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                //  province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }
//-----------------------------------------------------------
        options1Items_ = jsonBeans;

        for (int i = 0; i < jsonBeans.size(); i++) {//遍历省份
            ArrayList<String> cityList_ = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList_ = new ArrayList<>();//该省的所有地区列表（第三极）
            if (jsonBeans.get(i).getCityList() != null)
                for (int c = 0; c < jsonBeans.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                    String cityName = jsonBeans.get(i).getCityList().get(c).getName();
                    cityList_.add(cityName);//添加城市
                    //              ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

//                city_AreaList.addAll(jsonBeans.get(i).getCityList().get(c).getArea());
//                province_AreaList_.add(city_AreaList);//添加该省所有地区数据
                }

            /**
             * 添加城市数据
             */
            options2Items_.add(cityList_);
            //  options3Items_.add(province_AreaList_);

        }
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了

                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                //initJsonData("");
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    Toast.makeText(getContext(), "Parse Failed", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    String result = (String) msg.obj;
                    initJsonData(result);
                    if (!TextUtils.isEmpty(orderNum)) {
                        initCountView();//初始化角标
                    }
                    break;
                case 5:
                    loadingDialog.cancel();
                    Log.e("!@#", "size = " + carBeans.size());
                    if (carBeans.size() == 0) {
                        Toast.makeText(getContext(), "没有查询到该条件下的车辆", Toast.LENGTH_SHORT).show();
                    }
                    if (carBeans != null) {
                        carBeanList = carBeans;
                        initAdapter(carBeanList);
                    }
                    break;
                case 6:
                    if (carBeans != null) {
                        if (carBeans.size() > 0 && loadMoreFlag) {
                            for (int i = 0; i < carBeans.size(); i++) {
                                carBeanList.add(carBeans.get(i));
                            }
                            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_COMPLETE);

                        } else
                            loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING_END);
                    }
                    break;
            }
        }
    };

    /*初始化角标*/
    public void initCountView() {
        Log.e("TAG", "count = " + orderNum);
        order_no.setText(orderNum);
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = JSON.parseArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.size(); i++) {
                JsonBean entity = gson.fromJson(data.get(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }


}
