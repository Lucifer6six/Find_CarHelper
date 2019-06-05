package com.find_carhelper.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.find_carhelper.R;
import com.find_carhelper.http.Constants;
import com.find_carhelper.utils.CustomHelper;
import com.find_carhelper.utils.MobileInfoUtil;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoFragment;
import com.jph.takephoto.model.TResult;

import java.io.Console;
import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class IdentityAuthFragment extends TakePhotoFragment implements View.OnClickListener {
    private ImageView mImageView1,mImageView2,mImageView3;
    private CustomHelper customHelper;
    private View commenView,contentView;
    private Button takePhoto;
    private EditText name,jcname,faren,sfz,tydm;
    private Button commitAction;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
    public String imageType1 = "ID_CARD_OBVERSE";
    public String imageType2 = "ID_CARD_REVERSE";
    public String imageTYpe3 = "ID_CARD_HOLD";
    private final OkHttpClient client = new OkHttpClient();
    private String token;
    private String imgName;
    private int takePhotoFlag = 0;
    private UpImgAsyncTask mbuttonAsyncTask;
    public IdentityAuthFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        Bitmap bitmap = BitmapFactory.decodeFile(result.getImage().getCompressPath());
        imgName = result.getImage().getOriginalPath();
        switch (takePhotoFlag){
            case 1:
                mImageView1.setImageBitmap(bitmap);
                uploadImg(result.getImage().getCompressPath());
                break;
            case 2:
                mImageView1.setImageBitmap(bitmap);
                uploadImg(result.getImage().getCompressPath());
                break;
            case 3:
                mImageView1.setImageBitmap(bitmap);
                uploadImg(result.getImage().getCompressPath());
                break;
        }
    }
    public void uploadImage(File file) throws Exception{

        //2.创建RequestBody
        RequestBody fileBody = RequestBody.create(MEDIA_TYPE_PNG, file);

        //3.构建MultipartBody
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "testImage.png", fileBody)
                .addFormDataPart("deviceId", MobileInfoUtil.getIMEI(getContext()))
                .addFormDataPart("accessToken", Constants.TOKEN)
                .build();

        //4.构建请求
        Request request = new Request.Builder()
                .url("http://39.100.119.162:9090/onstage/upload/person/idcard")
                .post(requestBody)
                .build();

        //5.发送请求
        Response response = client.newCall(request).execute();
        String re = response.toString();
        Log.e("hhh",re);
        Toast.makeText(getContext(),re,Toast.LENGTH_LONG).show();
    }

    public void uploadImg(String url){

        File file = new File(url);
        mbuttonAsyncTask = new UpImgAsyncTask();
        mbuttonAsyncTask.execute(file);

    }
    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView =  LayoutInflater.from(getActivity()).inflate(R.layout.fragment_identity_auth, null);
        initView();
        return contentView;
    }
    public void initView(){
        commenView = LayoutInflater.from(getContext()).inflate(R.layout.common_layout,null);
        customHelper = CustomHelper.of(commenView);
        takePhoto = commenView.findViewById(R.id.btnPickByTake);
        mImageView1 = contentView.findViewById(R.id.img1);
        mImageView2 = contentView.findViewById(R.id.img2);
        mImageView3 = contentView.findViewById(R.id.img3);
        mImageView1.setOnClickListener(this);
        mImageView2.setOnClickListener(this);
        mImageView3.setOnClickListener(this);
    }
    public void takePhoto(){
        customHelper.onClick(takePhoto,getTakePhoto());
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img1:
                takePhotoFlag = 1;
                takePhoto();
                break;
            case R.id.img2:
                takePhotoFlag = 2;
                takePhoto();
                break;
            case R.id.img3:
                takePhotoFlag = 3;
                takePhoto();
                break;
        }
    }
    @Override
    public TakePhoto getTakePhoto() {
        return super.getTakePhoto();
    }

    private class UpImgAsyncTask extends AsyncTask<File,Object,Integer> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Integer doInBackground(File... params) {
            //uploadImage();
            return null;
        }
    }
}
