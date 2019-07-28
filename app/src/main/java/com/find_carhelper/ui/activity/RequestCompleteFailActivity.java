package com.find_carhelper.ui.activity;

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
import android.widget.EditText;
import android.widget.ImageView;
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
import com.find_carhelper.utils.CustomHelper;
import com.find_carhelper.utils.SharedPreferencesUtil;
import com.find_carhelper.widgets.MyDialog;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TResult;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

public class RequestCompleteFailActivity extends TakePhotoActivity implements View.OnClickListener {
    public String vin;
    public String orderCode;
    public FindCarInfo findCarInfo;
    public TextView car_type, car_id, reaseon;
    public EditText pin_edit;
    private ImageView photoView, photoView2;
    private RelativeLayout carImagLayout, carIdImgLayout;
    private View commenView;
    private Button takePhoto;
    private Button selectPhoto;
    private CustomHelper customHelper;
    private int imgFlag = 1;
    DisplayImageOptions mOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_complete_fail);
        initViews();
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    public void initViews() {
        commenView = LayoutInflater.from(this).inflate(R.layout.common_layout, null);
        takePhoto = commenView.findViewById(R.id.btnPickByTake);
        selectPhoto = commenView.findViewById(R.id.btnPickBySelect);
        carIdImgLayout = findViewById(R.id.car_id_layout);//photoview2 id照片
        carImagLayout = findViewById(R.id.car_img_layout);//photoview1
        photoView = findViewById(R.id.photoView);
        photoView2 = findViewById(R.id.photoView2);
        car_type = findViewById(R.id.car_type);
        car_id = findViewById(R.id.car_id);
        reaseon = findViewById(R.id.reaseon);
        pin_edit = (EditText) findViewById(R.id.pin_edit);
        customHelper = CustomHelper.of(commenView);
        carImagLayout.setOnClickListener(this);
        carIdImgLayout.setOnClickListener(this);
    }

    public void setValue() {
        car_type.setText(findCarInfo.getVehicleModel());
        car_id.setText(findCarInfo.getLpn());
        reaseon.setText("驳回原因:"+findCarInfo.getReason());
        pin_edit.setText(findCarInfo.getPositioningPin());
        if (findCarInfo.getVehiclePhoto() != null) {
            ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(RequestCompleteFailActivity.this).writeDebugLogs().build();
            ImageLoader.getInstance().init(configuration);
            mOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(false).cacheOnDisc(false)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
            photoView.setVisibility(View.VISIBLE);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(findCarInfo.getVehiclePhoto(), photoView);
            if (findCarInfo.getVinPhoto() != null) {
                imageLoader.displayImage(findCarInfo.getVinPhoto(), photoView2);
            }
        }
    }

    public void initData() {
        //"orderCode": "XC201907261859077678", "vin": "LFMBEC4DE0228807"　测试
        vin = getIntent().getStringExtra("vin");
        orderCode = getIntent().getStringExtra("no");
       // orderCode = "XC201907261859077678";
       // vin = "LFMBEC4DE0228807";
        getData();
    }

    public void getData() {
        String url = Constants.SERVICE_NAME + Constants.FIND_CAR_FAILED;
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
                                Toast.makeText(getApplicationContext(), "重新登录", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        if (jsonObject.getString("success").equals("true")) {

                            findCarInfo = JSON.parseObject(jsonObject.getJSONObject("data").toJSONString(), FindCarInfo.class);
                            handler.sendEmptyMessage(0);
                        } else {

                            Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "重新登录", Toast.LENGTH_SHORT).show();
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

                    if (findCarInfo != null) {

                        setValue();

                    }

                    break;

            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.car_id_layout:
                imgFlag = 2;
                showSelectDialog();
                break;
            case R.id.car_img_layout:
                imgFlag = 1;
                showSelectDialog();
                break;
        }
    }

    public void showSelectDialog() {
        MyDialog dialogHistory = new MyDialog(RequestCompleteFailActivity.this, R.style.dialog_theme_pick);
        dialogHistory.setTextTitle("确定清除历史搜索缓存");
        dialogHistory.setOnDialogClickListener(new MyDialog.onDialogListener() {

            @Override
            public void onquxiao() {
                //拍照
                customHelper.onClick(takePhoto, getTakePhoto());
            }

            @Override
            public void onqueding() {
                // 从相册选择
                customHelper.onClick(selectPhoto, getTakePhoto());
            }
        });
        dialogHistory.show();
    }
}
