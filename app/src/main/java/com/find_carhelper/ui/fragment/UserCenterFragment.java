package com.find_carhelper.ui.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.LocationSource;
import com.find_carhelper.R;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.activity.EditPswActivity;
import com.find_carhelper.ui.activity.LoginActivity;
import com.find_carhelper.ui.activity.MyCountActivity;
import com.find_carhelper.ui.activity.MyTeamActivity;
import com.find_carhelper.ui.activity.NewsActvity;
import com.find_carhelper.ui.activity.RequestInStoreActivity;
import com.find_carhelper.ui.base.MVPBaseFragment;

public class UserCenterFragment extends MVPBaseFragment implements View.OnClickListener, LocationSource {
    private AMapLocationClient mLocationClient;
    private RelativeLayout pswLayout,newsLayout,myTeamLayout,protocalLayout,acountLayout,updateLayout;
    private AMapLocationClientOption mLocationOption;
    private String TAG = "UserCenterFragment";
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
                    Toast.makeText(getContext(),"省信息---------------" + amapLocation.getCity(),Toast.LENGTH_SHORT).show();
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

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void initViews() {
        pswLayout = mRootView.findViewById(R.id.edit_psw);
        newsLayout = mRootView.findViewById(R.id.news_center);
        myTeamLayout = mRootView.findViewById(R.id.team);
        myTeamLayout.setOnClickListener(this);
        protocalLayout = mRootView.findViewById(R.id.protocal);
        acountLayout = mRootView.findViewById(R.id.acount);
        updateLayout = mRootView.findViewById(R.id.update);
        updateLayout.setOnClickListener(this);
        pswLayout.setOnClickListener(this);
        newsLayout.setOnClickListener(this);
        protocalLayout.setOnClickListener(this);
        acountLayout.setOnClickListener(this);
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){//未开启定位权限
            //开启定位权限,200是标识码
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},200);
        }else{
            startLocaion();//开始定位
            Toast.makeText(getContext(),"已开启定位权限",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.edit_psw:

                startActivity(new Intent(getContext(), EditPswActivity.class));
                //

                break;

            case R.id.news_center:
                startActivity(new Intent(getContext(), NewsActvity.class));
                break;

            case R.id.team:
                startActivity(new Intent(getContext(), MyTeamActivity.class));
                break;

            case R.id.protocal:
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
            case R.id.acount:
                startActivity(new Intent(getContext(), MyCountActivity.class));
                break;
            case R.id.update:
                startActivity(new Intent(getContext(), RequestInStoreActivity.class));
                break;
        }
    }
    public void startLocaion(){
        Log.e(TAG,"开始定位");
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
