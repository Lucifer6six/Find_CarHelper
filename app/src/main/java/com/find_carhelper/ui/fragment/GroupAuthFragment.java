package com.find_carhelper.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.find_carhelper.R;
import com.find_carhelper.utils.CustomHelper;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoFragment;
import com.jph.takephoto.model.TResult;

import java.io.File;
import java.security.interfaces.RSAKey;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class GroupAuthFragment extends TakePhotoFragment {
    private ImageView mImageView;
    private CustomHelper customHelper;
    private View commenView,contentView;
    private Button takePhoto;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
    private final OkHttpClient client = new OkHttpClient();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       contentView =  LayoutInflater.from(getActivity()).inflate(R.layout.fragment_group_auth, null);

       initView();
       return contentView;
    }
    public void initView(){
        commenView = LayoutInflater.from(getContext()).inflate(R.layout.common_layout,null);
        customHelper = CustomHelper.of(commenView);
        takePhoto = commenView.findViewById(R.id.btnPickByTake);
        mImageView = contentView.findViewById(R.id.img1);
        mImageView.setOnClickListener(v -> {
            takePhoto();
        });
    }
    public void takePhoto(){
        customHelper.onClick(takePhoto,getTakePhoto());
    }
    public GroupAuthFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public TakePhoto getTakePhoto() {
        return super.getTakePhoto();
    }

    @Override
    public void takeSuccess(TResult result){
        super.takeSuccess(result);
        Bitmap bitmap = BitmapFactory.decodeFile(result.getImage().getCompressPath());
        mImageView.setImageBitmap(bitmap);

            new Thread(){
                @Override
                public void run() {
                    super.run();
                    try{
                        uploadImage("1231412123123",new File(result.getImage().getCompressPath()));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }.start();


    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }
    public void uploadImage(String userName, File file) throws Exception{

        //2.创建RequestBody
        RequestBody fileBody = RequestBody.create(MEDIA_TYPE_PNG, file);

        //3.构建MultipartBody
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "testImage.png", fileBody)
                .addFormDataPart("deviceId", userName)
                .build();

        //4.构建请求
        Request request = new Request.Builder()
                .url("http://39.100.119.162:9090/onstage/upload/company/businessLicense")
                .post(requestBody)
                .build();

        //5.发送请求
        Response response = client.newCall(request).execute();
        String re = response.toString();
        Toast.makeText(getContext(),re,Toast.LENGTH_LONG).show();
    }
}
