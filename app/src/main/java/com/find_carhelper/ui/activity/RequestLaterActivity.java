package com.find_carhelper.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.find_carhelper.R;
import com.find_carhelper.bean.ItemBean;
import com.find_carhelper.bean.photoBean;
import com.find_carhelper.http.Application;
import com.find_carhelper.http.Constants;
import com.find_carhelper.http.NetRequest;
import com.find_carhelper.ui.adapter.MyImageUploadAdapter;
import com.find_carhelper.utils.CustomHelper;
import com.find_carhelper.utils.MobileInfoUtil;
import com.find_carhelper.utils.SharedPreferencesUtil;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestLaterActivity extends TakePhotoActivity {
    private CustomHelper customHelper;
    private View commenView;
    private Button takePhoto;
    private int position;
    ArrayList<TImage> images;
    private List<ItemBean> list;
    private List<String> updateList;
    private CardView tekeImage;
    private ImageView photoView;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
    private final OkHttpClient client = new OkHttpClient();
    public Button commitAction;
    public EditText request_hour,late_reason;
    public String vin;
    public String orderCode;
    public CommonTitleBar mTitleBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_later);
        vin = getIntent().getStringExtra("vin");
        orderCode = getIntent().getStringExtra("no");
        initViews();
        getConfigInfo();
    }
    public void getConfigInfo(){
            String url = Constants.SERVICE_NAME+Constants.GET_CONFIG;
            HashMap<String, String> params = new HashMap<>();
            // 添加请求参数
            params.put("deviceId",Constants.ID);//
            params.put("accessToken",SharedPreferencesUtil.getString(getApplicationContext(),"token"));
            // ...
            NetRequest.getFormRequest(url, params, new NetRequest.DataCallBack() {
                @Override
                public void requestSuccess(String result) throws Exception {
                    // 请求成功的回调
                    Log.e("TAG",result.toString());
                    if (!TextUtils.isEmpty(result)){
                        com.alibaba.fastjson.JSONObject jsonObject =  JSON.parseObject(result);
                        if (jsonObject.getString("success").equals("true")){
                           String hour = jsonObject.getString("data");
                            Log.e("TAG","list size = "+ hour);
                            request_hour.setHint("请输入1到"+hour+"的整数");
                        }else{
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void requestFailure(Request request, IOException e) {
                    // 请求失败的回调
                    Log.e("TAG",request.toString()+e.getMessage());
                }
            });

    }

    private void initViews() {
        mTitleBar = findViewById(R.id.title_bar);
        list = new ArrayList<ItemBean>();
        tekeImage = findViewById(R.id.takePic);
        photoView = findViewById(R.id.photoView);
        commitAction = findViewById(R.id.commit);
        request_hour = findViewById(R.id.request_hour);
        late_reason = findViewById(R.id.memo);
        commenView = LayoutInflater.from(this).inflate(R.layout.common_layout,null);
        takePhoto = commenView.findViewById(R.id.btnPickByTake);
        customHelper = CustomHelper.of(commenView);
        tekeImage.setOnClickListener(view -> {
            customHelper.onClick(takePhoto,getTakePhoto());
        });
        commitAction.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(request_hour.getText())&&!TextUtils.isEmpty(late_reason.getText()))
                commitAction();
            else
                Toast.makeText(getApplicationContext(),"请填写完整信息",Toast.LENGTH_SHORT).show();
        });

        mTitleBar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                    finish();
                }
            }
        });

    }

    public void commitAction(){
        String url = Constants.SERVICE_NAME+Constants.REQUEST_LATER;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId",Constants.ID);//
        params.put("accessToken",SharedPreferencesUtil.getString(getApplicationContext(),"token"));
        params.put("orderCode", orderCode);
        params.put("vin", vin);
        params.put("applyReason", late_reason.getText().toString());
        params.put("applyAddHour", request_hour.getText().toString());
        // ...
        NetRequest.postFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                // 请求成功的回调
                Log.e("TAG",result.toString());
                if (!TextUtils.isEmpty(result)){
                    com.alibaba.fastjson.JSONObject jsonObject =  JSON.parseObject(result);
                    if (jsonObject.getString("success").equals("true")){
                        Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                // 请求失败的回调
                Log.e("TAG",request.toString()+e.getMessage());
            }
        });

    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        String path= result.getImage().getCompressPath();
        File file = new File(path);
        if (file.exists()){

            Bitmap bitmap = BitmapFactory.decodeFile(path);
            photoView.setImageBitmap(bitmap);
            photoView.setVisibility(View.VISIBLE);

            new Thread(){
                @Override
                public void run() {
                    super.run();
                    try{
                        String response = uploadImage(Constants.ID,new File(result.getImage().getCompressPath()));
                        JSONObject myJson = new JSONObject(response);
                        if (myJson.getString("success").equals("true"))
                            mhandler.sendEmptyMessage(0);
                        else
                            mhandler.sendEmptyMessage(1);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }.start();
        }

    }
    public boolean isUpload = false;
    public Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    isUpload = true;
                    Toast.makeText(getApplicationContext(),"处理成功",Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(getApplicationContext(),"处理失败,请重试",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    public String  uploadImage(String userName, File file) throws Exception{

        //2.创建RequestBody
        RequestBody fileBody = RequestBody.create(MEDIA_TYPE_PNG, file);

        //3.构建MultipartBody
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "testImage.png", fileBody)
                .addFormDataPart("deviceId", userName)
                .addFormDataPart("vin", vin)
                .addFormDataPart("orderCode", orderCode)
                .addFormDataPart("accessToken", SharedPreferencesUtil.getString(getApplicationContext(),"token"))
                .build();

        //4.构建请求
        Request request = new Request.Builder()
                .url(Constants.SERVICE_NAME+"/upload/vehicle/retrieve/defer/apply")
                .post(requestBody)
                .build();

        //5.发送请求
        Response response = client.newCall(request).execute();
        String re = response.body().string();
        String header = response.header("accessToken");
        if (!TextUtils.isEmpty(header)){
            SharedPreferencesUtil.putString(Application.getContext(),"token",header);
        }
        if (response.code() == 200) {
            return re;
        }
        return "";
    }
}
