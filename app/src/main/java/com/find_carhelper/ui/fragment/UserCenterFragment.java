package com.find_carhelper.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.LocationSource;
import com.find_carhelper.R;
import com.find_carhelper.bean.UserBean;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.http.Constants;
import com.find_carhelper.http.NetRequest;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.MainActivity;
import com.find_carhelper.ui.activity.AuthActivity;
import com.find_carhelper.ui.activity.AuthFailActivity;
import com.find_carhelper.ui.activity.BaoQuanActivity;
import com.find_carhelper.ui.activity.EditPswActivity;
import com.find_carhelper.ui.activity.FindCarOrdersActivity;
import com.find_carhelper.ui.activity.LoginActivity;
import com.find_carhelper.ui.activity.MyCountActivity;
import com.find_carhelper.ui.activity.MyTeamActivity;
import com.find_carhelper.ui.activity.NewsActvity;
import com.find_carhelper.ui.base.MVPBaseFragment;
import com.find_carhelper.utils.SharedPreferencesUtil;
import com.find_carhelper.widgets.MarkerOrderPopWindow;
import com.find_carhelper.widgets.SelectPopWindow;
import com.find_carhelper.widgets.ToolDateSelectorPopWindow;
import com.google.gson.Gson;
import com.wega.library.loadingDialog.LoadingDialog;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

public class UserCenterFragment extends MVPBaseFragment implements View.OnClickListener, LocationSource {
    private AMapLocationClient mLocationClient;
    private RelativeLayout pswLayout, newsLayout, myTeamLayout, protocalLayout, acountLayout, updateLayout, quite, baoquanLayout, findCarLayout;
    private AMapLocationClientOption mLocationOption;
    private String TAG = "UserCenterFragment";
    private TextView name, nickName, status;
    private ImageView auth_stutes;
    private UserBean userBean;
    private TextView auth_fail;
    private LinearLayout countLayout;
    boolean fistLoad = false;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    Log.i(TAG, "当前定位结果来源-----" + amapLocation.getLocationType());//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    Log.i(TAG, "纬度 ----------------" + amapLocation.getLatitude());//获取纬度
                    Log.i(TAG, "经度-----------------" + amapLocation.getLongitude());//获取经度
                    Log.i(TAG, "精度信息-------------" + amapLocation.getAccuracy());//获取精度信息
                    Log.i(TAG, "地址-----------------" + amapLocation.getAddress());//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    Log.i(TAG, "国家信息-------------" + amapLocation.getCountry());//国家信息
                    Log.i(TAG, "省信息---------------" + amapLocation.getProvince());//省信息
                    Toast.makeText(getContext(), amapLocation.getProvince() + amapLocation.getCity(), Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "城市信息-------------" + amapLocation.getCity());//城市信息
                    Log.i(TAG, "城区信息-------------" + amapLocation.getDistrict());//城区信息
                    Log.i(TAG, "街道信息-------------" + amapLocation.getStreet());//街道信息
                    Log.i(TAG, "街道门牌号信息-------" + amapLocation.getStreetNum());//街道门牌号信息
                    Log.i(TAG, "城市编码-------------" + amapLocation.getCityCode());//城市编码
                    Log.i(TAG, "地区编码-------------" + amapLocation.getAdCode());//地区编码
                    Log.i(TAG, "当前定位点的信息-----" + amapLocation.getAoiName());//获取当前定位点的AOI信息
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };


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
        fistLoad = true;
    }

    @Override
    protected void onUserVisible() {
        if (!fistLoad) {
            getData();
            fistLoad = false;
        }
    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void initViews() {
        pswLayout = mRootView.findViewById(R.id.edit_psw);
        newsLayout = mRootView.findViewById(R.id.news_center);
        countLayout = mRootView.findViewById(R.id.about_me);
        myTeamLayout = mRootView.findViewById(R.id.team);
        myTeamLayout.setOnClickListener(this);
        protocalLayout = mRootView.findViewById(R.id.protocal);
        acountLayout = mRootView.findViewById(R.id.acount);
        updateLayout = mRootView.findViewById(R.id.update);
        baoquanLayout = mRootView.findViewById(R.id.baoquan_layout);
        findCarLayout = mRootView.findViewById(R.id.xunche_layout);
        quite = mRootView.findViewById(R.id.quite);
        name = mRootView.findViewById(R.id.nick_name);
        nickName = mRootView.findViewById(R.id.little_nick_name);
        nickName.setOnClickListener(this);
        auth_stutes = mRootView.findViewById(R.id.auth_stutes);
        auth_stutes.setOnClickListener(this);
        auth_fail = mRootView.findViewById(R.id.auth_fail_);
        updateLayout.setOnClickListener(this);
        pswLayout.setOnClickListener(this);
        newsLayout.setOnClickListener(this);
        protocalLayout.setOnClickListener(this);
        acountLayout.setOnClickListener(this);
        quite.setOnClickListener(this);
        baoquanLayout.setOnClickListener(this);
        findCarLayout.setOnClickListener(this);
        auth_fail.setOnClickListener(this);
//        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {//未开启定位权限
//            //开启定位权限,200是标识码
//            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 200);
//        } else {
//            startLocaion();//开始定位
//        }
        auth_stutes.setOnClickListener(this);
        initLoading();
    }

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

    @Override
    protected void initData() {
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            initDatas();
        }
    };

    public void initDatas() {
        if (userBean != null) {
            Constants.isLogin = true;
            auth_fail.setVisibility(View.INVISIBLE);
            name.setText(userBean.getCompanyName());
            nickName.setText(userBean.getNickname());
            if (userBean.getStatus().equals("AUTH_SUCCESS")) {
                auth_stutes.setImageDrawable(getResources().getDrawable(R.mipmap.mine_btn_rz2));
            } else if (userBean.getStatus().equals("AUTH_FAILURE")) {
                auth_fail.setVisibility(View.VISIBLE);
                auth_stutes.setImageDrawable(getResources().getDrawable(R.mipmap.mine_btn_rz3));
            } else if (userBean.getStatus().equals("IN_AUTH")) {
                auth_stutes.setImageDrawable(getResources().getDrawable(R.mipmap.mine_btn_rz3));
            } else
                auth_stutes.setImageDrawable(getResources().getDrawable(R.mipmap.mine_btn_rz1));
            Constants.phoneNo = userBean.getPhoneNo();
            if (userBean.getRole().equals("COMPANY")) {
                //myTeamLayout.setVisibility(View.VISIBLE);
                //acountLayout.setVisibility(View.VISIBLE);
                countLayout.setVisibility(View.VISIBLE);
            } else {
                countLayout.setVisibility(View.GONE);
                //myTeamLayout.setVisibility(View.GONE);
                // acountLayout.setVisibility(View.GONE);
            }

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    public void getData() {
        loadingDialog.loading();
        String url = Constants.SERVICE_NAME + Constants.MY_INFO;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", Constants.ID);//MobileInfoUtil.getIMEI(getContext())
        params.put("accessToken", SharedPreferencesUtil.getString(getContext(), "token"));//MobileInfoUtil.getIMEI(getContext())

        // ...
        NetRequest.getFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                // 请求成功的回调
                loadingDialog.cancel();
                Log.e("TAG", result.toString());
                if (!TextUtils.isEmpty(result)) {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = "";
                    if (jsonObject.has("status")) {
                        status = jsonObject.getString("status");
                    }
                    if (status.equals("200") || jsonObject.getString("code").equals("I00000")) {
                        if (jsonObject.getString("success").equals("true")) {
                            // Toast.makeText(getContext(), "查询成功", Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            Gson gson = new Gson();
                            userBean = gson.fromJson(jsonObject1.getJSONObject("user").toString(), UserBean.class);
                            handler.sendEmptyMessage(0);
                        } else {
                            //startIntent();
                            Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (status.equals("401") || status.equals("500")) {
                            Toast.makeText(getContext(), "请重新登录", Toast.LENGTH_SHORT).show();
                            startIntent();
                        } else {
                            Toast.makeText(getContext(), "系统错误", Toast.LENGTH_SHORT).show();
                        }

                    }
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


    public void startIntent() {

        startActivity(new Intent(getContext(), LoginActivity.class));

    }

    @Override
    public void onClick(View view) {
        if (Constants.isLogin)
            switch (view.getId()) {

                case R.id.edit_psw:

                    startActivity(new Intent(getContext(), EditPswActivity.class));

                    break;

                case R.id.news_center:
                    startActivity(new Intent(getContext(), NewsActvity.class));
                    break;

                case R.id.team:
                    startActivity(new Intent(getContext(), MyTeamActivity.class));
                    break;

                case R.id.protocal:
                    //startActivity(new Intent(getContext(), RequestLaterActivity.class));
                    break;
                case R.id.acount:
                    startActivity(new Intent(getContext(), MyCountActivity.class));
                    break;
                case R.id.update:
                    // startActivity(new Intent(getContext(), RequestInStoreActivity.class));
                    break;
                case R.id.auth_stutes:

                    if (userBean != null) {
                        if (userBean.getStatus().equals("AUTH_SUCCESS") || userBean.getStatus().equals("IN_AUTH")) {

                        } else {
                            startActivity(new Intent(getContext(), AuthActivity.class));
                        }
                    }

                    break;
                case R.id.quite:

                    new SelectPopWindow(getContext(), new MarkerOrderPopWindow.getdata() {
                        @Override
                        public void getdatas(String str) {
                            if (str.equals("1")) {
                                quite();
                            }
                        }
                    }).showPopupWindow();

                    break;
                case R.id.baoquan_layout:
                    startActivity(new Intent(getContext(), BaoQuanActivity.class));
                    break;
                case R.id.xunche_layout:
                    startActivity(new Intent(getActivity(), FindCarOrdersActivity.class));
                    break;
                case R.id.auth_fail_:
                    Intent intent = new Intent(getContext(), AuthFailActivity.class);
                    if (userBean.getRole().equals("COMPANY")) {
                        intent.putExtra("type", "company");
                    } else
                        intent.putExtra("type", "personal");
                    startActivity(intent);
                    break;
            }
        else
            startIntent();
    }

    /**
     * 退出
     */
    public void quite() {

        SharedPreferencesUtil.putString(getContext(), "token", "");

        startActivity(new Intent(getContext(), LoginActivity.class));
    }

    public void startLocaion() {
        Log.e(TAG, "开始定位");
        mLocationClient = new AMapLocationClient(getContext());
        mLocationClient.setLocationListener(mLocationListener);
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }
}
