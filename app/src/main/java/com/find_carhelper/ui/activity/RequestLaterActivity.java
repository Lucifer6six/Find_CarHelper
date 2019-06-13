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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.find_carhelper.R;
import com.find_carhelper.bean.ItemBean;
import com.find_carhelper.ui.adapter.MyImageUploadAdapter;
import com.find_carhelper.utils.CustomHelper;
import com.find_carhelper.utils.MobileInfoUtil;
import com.find_carhelper.utils.SharedPreferencesUtil;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_later);
        initViews();
    }


    private void initViews() {
        list = new ArrayList<ItemBean>();
        tekeImage = findViewById(R.id.takePic);
        photoView = findViewById(R.id.photoView);
        commenView = LayoutInflater.from(this).inflate(R.layout.common_layout,null);
        takePhoto = commenView.findViewById(R.id.btnPickByTake);
        customHelper = CustomHelper.of(commenView);
        tekeImage.setOnClickListener(view -> {
            customHelper.onClick(takePhoto,getTakePhoto());
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
                        String response = uploadImage(MobileInfoUtil.getIMEI(RequestLaterActivity.this),new File(result.getImage().getCompressPath()));
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
    public Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
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
                .addFormDataPart("deviceId", MobileInfoUtil.getIMEI(getApplicationContext()))
                .addFormDataPart("accessToken", SharedPreferencesUtil.getString(getApplicationContext(),"token"))
                .build();

        //4.构建请求
        Request request = new Request.Builder()
                .url("http://39.100.119.162:9090/onstage/upload/company/businessLicense")
                .post(requestBody)
                .build();

        //5.发送请求
        Response response = client.newCall(request).execute();
        String re = response.body().string();
        Log.e("hhh",re);
        if (response.code() == 200) {
            return re;
        }
        return "";
    }
}
