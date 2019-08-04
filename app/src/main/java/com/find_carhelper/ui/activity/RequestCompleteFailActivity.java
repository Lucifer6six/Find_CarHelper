package com.find_carhelper.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import com.find_carhelper.http.Application;
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
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    private CommonTitleBar title_bar;
    public String imageName1, imageName2;
    public Button request_complete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_complete_fail);
        initViews();
        initData();
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);

        Bitmap bitmap = BitmapFactory.decodeFile(result.getImage().getCompressPath());
        String imgCode = imgFlag == 1 ? "CAR" : "VIN";
        uploadImg(result.getImage().getCompressPath(), imgCode);
        showImage(bitmap);

    }

    public void showImage(Bitmap bitmap) {
        switch (imgFlag) {
            case 1:
                photoView.setVisibility(View.VISIBLE);
                photoView.setImageBitmap(bitmap);
                break;
            case 2:
                photoView2.setVisibility(View.VISIBLE);
                photoView2.setImageBitmap(bitmap);
                break;

        }
    }

    private UpImgAsyncTask mbuttonAsyncTask;

    public void uploadImg(String url, String code) {

        mbuttonAsyncTask = new UpImgAsyncTask();
        mbuttonAsyncTask.execute(url, code);

    }

    private class UpImgAsyncTask extends AsyncTask<String, Object, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            String imageName = "";
            try {
                Log.e("TAG", "result = " + response);
                org.json.JSONObject myJson = new org.json.JSONObject(response);
                org.json.JSONObject jsonObject = myJson.getJSONObject("data");
                imageName = jsonObject.getString("name");
                if (imgFlag == 1) {
                    imageName1 = imageName;
                } else
                    imageName2 = imageName;
                //checkButtonStutus();
                if (myJson.getString("success").equals("true")) {
                    Toast.makeText(getApplicationContext(), "上传成功", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(getApplicationContext(), "上传失败", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... params) {
            File file = new File(params[0]);
            String re = "";
            try {
                re = uploadImage(file, params[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return re;
        }
    }

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
    private final OkHttpClient client = new OkHttpClient();

    public String uploadImage(File file, String code) throws Exception {

        //2.创建RequestBody
        RequestBody fileBody = RequestBody.create(MEDIA_TYPE_PNG, file);

        //3.构建MultipartBody
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "testImage.png", fileBody)
                .addFormDataPart("deviceId", Constants.ID)//MobileInfoUtil.getIMEI(getContext())
                .addFormDataPart("accessToken", SharedPreferencesUtil.getString(getApplicationContext(), "token"))
                .addFormDataPart("vin", vin)
                .addFormDataPart("orderCode", orderCode)
                .addFormDataPart("imgType", code)
                .build();

        //4.构建请求
        Request request = new Request.Builder()
                .url(Constants.SERVICE_NAME + "/upload/vehicle/find/order/finish/apply")
                .post(requestBody)
                .build();

        //5.发送请求
        Response response = client.newCall(request).execute();
        String re = response.toString();
        String header = response.header("accessToken");
        if (!TextUtils.isEmpty(header)) {
            SharedPreferencesUtil.putString(Application.getContext(), "token", header);
        }
        return response.body().string();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void initViews() {
        commenView = LayoutInflater.from(this).inflate(R.layout.common_layout, null);
        title_bar = findViewById(R.id.title_bar);
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
        request_complete = (Button) findViewById(R.id.request_complete);
        customHelper = CustomHelper.of(commenView);
        carImagLayout.setOnClickListener(this);
        carIdImgLayout.setOnClickListener(this);
        title_bar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                    finish();
                }
            }
        });
        request_complete.setOnClickListener(view -> {
            if (pin_edit.getText().length() > 0) {
                commitAction();
            }
        });
    }

    public void commitAction() {
        String url = Constants.SERVICE_NAME + Constants.FIND_CAR_COMPLETE;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", Constants.ID);//
        params.put("accessToken", SharedPreferencesUtil.getString(getApplicationContext(), "token"));
        params.put("vin", vin);
        params.put("orderCode", orderCode);
        params.put("positioningPin", pin_edit.getText().toString());
        params.put("vehiclePhoto", imageName1);
        params.put("vinPhoto", imageName2);
        // ...
        NetRequest.postFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                // 请求成功的回调
                Log.e("TAG", result.toString());
                if (!TextUtils.isEmpty(result)) {
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result);
                    if (jsonObject.getString("success").equals("true")) {
                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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

    public void setValue() {
        car_type.setText(findCarInfo.getVehicleModel());
        car_id.setText(findCarInfo.getLpn());
        reaseon.setText("驳回原因:" + findCarInfo.getReason());
        pin_edit.setText(findCarInfo.getPositioningPin());
        if (findCarInfo.getVehiclePhoto() != null) {
            ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(RequestCompleteFailActivity.this).writeDebugLogs().build();
            ImageLoader.getInstance().init(configuration);
            DisplayImageOptions mOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(false).cacheOnDisc(false)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
            photoView.setVisibility(View.VISIBLE);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(findCarInfo.getVehiclePhoto(), photoView);
            if (findCarInfo.getVinPhoto() != null) {
                imageLoader.displayImage(findCarInfo.getVinPhoto(), photoView2);
                photoView2.setVisibility(View.VISIBLE);
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
