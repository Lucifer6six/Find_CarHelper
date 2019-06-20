package com.find_carhelper.ui.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.find_carhelper.R;
import com.find_carhelper.http.Application;
import com.find_carhelper.http.Constants;
import com.find_carhelper.http.NetRequest;
import com.find_carhelper.ui.activity.AuthActivity;
import com.find_carhelper.utils.CustomHelper;
import com.find_carhelper.utils.MobileInfoUtil;
import com.find_carhelper.utils.SharedPreferencesUtil;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoFragment;
import com.jph.takephoto.model.TResult;

import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

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
    private EditText name,id;
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
    private String imageType;
    private String imageName1,imageName2,imageName3;
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
                imageType = imageType1;
                uploadImg(result.getImage().getCompressPath());
                break;
            case 2:
                mImageView2.setImageBitmap(bitmap);
                imageType = imageType2;
                uploadImg(result.getImage().getCompressPath());
                break;
            case 3:
                mImageView3.setImageBitmap(bitmap);
                imageType = imageTYpe3;
                uploadImg(result.getImage().getCompressPath());
                break;
        }
    }
    public String uploadImage(File file) throws Exception{
        //2.创建RequestBody
        RequestBody fileBody = RequestBody.create(MEDIA_TYPE_PNG, file);
        //3.构建MultipartBody
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "testImage.png", fileBody)
                .addFormDataPart("deviceId", Constants.ID)//MobileInfoUtil.getIMEI(getContext())
                .addFormDataPart("accessToken", SharedPreferencesUtil.getString(getContext(),"token"))
                .addFormDataPart("imgType", imageType)
                .build();
        //4.构建请求
        Request request = new Request.Builder()
                .url("http://39.100.119.162:9090/onstage/upload/person/idcard")
                .post(requestBody)
                .build();
        //5.发送请求
        Response response = client.newCall(request).execute();
        String header = response.header("accessToken");
        if (!TextUtils.isEmpty(header)){
            SharedPreferencesUtil.putString(Application.getContext(),"token",header);
        }
        return response.body().string();
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
        name = contentView.findViewById(R.id.name);
        id = contentView.findViewById(R.id.id);
        mImageView1 = contentView.findViewById(R.id.img1);
        mImageView2 = contentView.findViewById(R.id.img2);
        mImageView3 = contentView.findViewById(R.id.img3);
        commitAction = contentView.findViewById(R.id.commit);
        mImageView1.setOnClickListener(this);
        mImageView2.setOnClickListener(this);
        mImageView3.setOnClickListener(this);
        commitAction.setOnClickListener(this);
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
            case R.id.commit:
                commitAction();
                break;
        }
    }

    public void commitAction(){
        String realName = name.getText().toString();
        String sid = id.getText().toString();

        if (!TextUtils.isEmpty(realName)&&!TextUtils.isEmpty(sid)
                    &&!TextUtils.isEmpty(imageName1)&&!TextUtils.isEmpty(imageName2)
                        &&!TextUtils.isEmpty(imageName3)){
            String url = Constants.REGISTER_Identity;
            HashMap<String, String> params = new HashMap<>();
            // 添加请求参数
            params.put("deviceId", Constants.ID);//MobileInfoUtil.getIMEI(getContext())
            params.put("accessToken", SharedPreferencesUtil.getString(getContext(),"token"));
            params.put("realName", realName);
            params.put("idCardNo", sid);
            params.put("idCardObverseImgUrl", imageName1);
            params.put("idCardReverseImgUrl", imageName2);
            params.put("idCardHoldImgUrl", imageName3);
            // ...
            NetRequest.postFormRequest(url, params, new NetRequest.DataCallBack() {
                @Override
                public void requestSuccess(String result) throws Exception {
                    // 请求成功的回调
                    Log.e("TAG",result.toString());
                    if (!TextUtils.isEmpty(result)){
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.getString("success").equals("true")){
                            Toast.makeText(getContext(),"提交成功",Toast.LENGTH_SHORT).show();
                            LockStatus();
                        }else{
                            Toast.makeText(getContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void requestFailure(Request request, IOException e) {
                    // 请求失败的回调
                    Log.e("TAG",request.toString()+e.getMessage());
                }
            });
        }else
            Toast.makeText(getContext(),"请填写完整信息或上传全部身份证",Toast.LENGTH_SHORT).show();

    }

    public void LockStatus(){

        name.setEnabled(false);
        id.setEnabled(false);
        commitAction.setBackgroundColor(getResources().getColor(R.color.back_ground_gray));
        AuthActivity.IdentityAuth = true;
        //if (AuthActivity.GroupAuth&&AuthActivity.IdentityAuth){
            getActivity().finish();
    //    }
    }

    @Override
    public TakePhoto getTakePhoto() {
        return super.getTakePhoto();
    }

    private class UpImgAsyncTask extends AsyncTask<File,Object,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            String imageName = "";
            try{
                Log.e("TAG","result = "+response);
            JSONObject  myJson = new JSONObject(response);
            JSONObject jsonObject = myJson.getJSONObject("data");
            imageName = jsonObject.getString("name");
            if (myJson.getString("success").equals("true")){
                Toast.makeText(getContext(),"上传成功",Toast.LENGTH_LONG).show();
                switch (takePhotoFlag){
                    case 1:
                        imageName1 = imageName;
                        break;
                    case 2:
                        imageName2 = imageName;
                        break;
                    case 3:
                        imageName3 = imageName;
                        break;
                }

            }else
                Toast.makeText(getContext(),"上传失败",Toast.LENGTH_LONG).show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(File... params) {
            String re = "";
            try{
               re =  uploadImage(params[0]);
            }catch (Exception e){
                e.printStackTrace();
            }
            return re;
        }
    }
}
