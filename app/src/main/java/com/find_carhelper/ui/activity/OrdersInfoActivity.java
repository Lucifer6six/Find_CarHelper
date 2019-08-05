package com.find_carhelper.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.find_carhelper.R;
import com.find_carhelper.bean.FindCarInfo;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.http.Constants;
import com.find_carhelper.http.NetRequest;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.base.MVPBaseActivity;
import com.find_carhelper.utils.SharedPreferencesUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class OrdersInfoActivity extends MVPBaseActivity implements View.OnClickListener {
    public Button request_complete;
    public String vin;
    public String orderCode;
    public LinearLayout imgLayout;
    public List<String> list;
    DisplayImageOptions mOptions;
    RelativeLayout noDataLayout;
    FindCarInfo findCarInfo;
    public TextView accept_orders_time, car_type, car_id, car_no, address_tips, money;

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_orders_info;
    }

    @Override
    protected void initViews() {
        imgLayout = findViewById(R.id.imgLayout);
        accept_orders_time = findViewById(R.id.accept_orders_time);
        car_type = findViewById(R.id.car_type);
        car_id = findViewById(R.id.car_id);
        car_no = findViewById(R.id.car_no);
        address_tips = findViewById(R.id.address_tips);
        money = findViewById(R.id.money);
        noDataLayout = findViewById(R.id.no_data_layout);

        request_complete = findViewById(R.id.request_complete);
        request_complete.setOnClickListener(view -> {

            if (findCarInfo != null) {
                Intent intent = new Intent(OrdersInfoActivity.this, RequesCompleteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("obj", findCarInfo);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        registerLeftClickEvent(view -> finish());
    }

    @Override
    protected void initData() {
        findCarInfo = getIntent().getParcelableExtra("obj");
        if (findCarInfo != null) {
            vin = findCarInfo.getVin();
            orderCode = findCarInfo.getOrderCode();
            getData();

            car_type.setText(findCarInfo.getVehicleModel());
            car_id.setText(findCarInfo.getLpn());
            address_tips.setText(findCarInfo.getRegion() + "/" + findCarInfo.getPartya());
            money.setText(findCarInfo.getRewardAmount());
            car_no.setText("车架号:  " + findCarInfo.getVin());
            accept_orders_time.setText("接单时间:   " + findCarInfo.getOrderTime());
        }


    }

    public void getData() {
        String url = Constants.SERVICE_NAME + Constants.FIND_CAR_OWNERS;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", Constants.ID);//MobileInfoUtil.getIMEI(getContext())
        params.put("accessToken", SharedPreferencesUtil.getString(getApplicationContext(), "token"));
        params.put("vin", vin);
        params.put("orderCode", orderCode);
        // ...
        NetRequest.getFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                // 请求成功的回调
                Log.e("TAG", result.toString());
                if (!result.equals("401")) {
                    if (!TextUtils.isEmpty(result)) {
                        JSONObject jsonObject = JSON.parseObject(result);
                        if (jsonObject.getString("status") != null)
                            if (jsonObject.getString("status").equals("500") && jsonObject.getString("message").equals("W02000")) {
                                Toast.makeText(getApplicationContext(), "服务器错误", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        if (jsonObject.getString("success").equals("true")) {
                            list = JSON.parseArray(jsonObject.getJSONArray("data").toJSONString(), String.class);
                            handler.sendEmptyMessage(0);
                        } else {

                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "服务器错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                // 请求失败的回调
                Log.e("TAG", request.toString() + e.getMessage());
            }
        });
    }


    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case 0:

                    if (list != null) {

                        setValue();

                    }

                    break;

            }

        }
    };
    public String url;

    public void setValue() {

        if (list.size() > 0) {
            ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(OrdersInfoActivity.this).writeDebugLogs().build();
            ImageLoader.getInstance().init(configuration);
            mOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(false).cacheOnDisc(false)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();

            for (int i = 0; i < list.size(); i++) {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_find_order_detial_img, null);

                ImageView imageView = view.findViewById(R.id.photoView);
                imageView.setScaleType(ImageView.ScaleType.CENTER);
                ImageLoader imageLoader = ImageLoader.getInstance();
                url = list.get(i);
                imageLoader.displayImage(list.get(i), imageView);
                view.setOnClickListener(this);
                imgLayout.addView(view);
            }
        }else{
            noDataLayout.setVisibility(View.VISIBLE);
        }


    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {

        for (int i = 0; i < imgLayout.getChildCount(); i++) {
            LinearLayout imageView = (LinearLayout) imgLayout.getChildAt(i);
            if (view == imageView) {
                Intent intent = new Intent(OrdersInfoActivity.this, ShowImageActivity.class);
                intent.putExtra("url", list.get(i));
                startActivity(intent);
            }
        }
    }
}
