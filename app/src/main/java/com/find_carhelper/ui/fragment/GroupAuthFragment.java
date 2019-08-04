package com.find_carhelper.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.find_carhelper.ui.activity.LoginActivity;
import com.find_carhelper.utils.CustomHelper;
import com.find_carhelper.utils.MobileInfoUtil;
import com.find_carhelper.utils.SharedPreferencesUtil;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoFragment;
import com.jph.takephoto.model.TResult;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.security.interfaces.RSAKey;
import java.util.HashMap;

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
    private EditText name,jcname,faren,sfz,tydm,address;
    private Button commitAction;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
    private final OkHttpClient client = new OkHttpClient();
    private String imgName;
    private boolean upload = false;
    public String businessName = "";
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
        name = contentView.findViewById(R.id.name);
        jcname = contentView.findViewById(R.id.id);
        faren = contentView.findViewById(R.id.faren);
        sfz = contentView.findViewById(R.id.faren_id);
        tydm = contentView.findViewById(R.id.tydm);
        address = contentView.findViewById(R.id.address);
        commitAction = contentView.findViewById(R.id.commit);
        commitAction.setOnClickListener(v -> {
            Log.e("TAG","commitAction");
            commitAction();
        });
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
    public void commitAction(){
        String groupName = name.getText().toString();
        String groupJc = jcname.getText().toString();
        String groupFr = faren.getText().toString();
        String frsfz = sfz.getText().toString();
        String groupDm = tydm.getText().toString();
        String companyAddress = address.getText().toString();
        if (!TextUtils.isEmpty(groupName)&&!TextUtils.isEmpty(groupJc)&&
                !TextUtils.isEmpty(groupFr)&&!TextUtils.isEmpty(frsfz)&&!TextUtils.isEmpty(groupDm)&&upload){


            String url = Constants.SERVICE_NAME+Constants.REGISTER_GROUP;
            HashMap<String, String> params = new HashMap<>();
            // 添加请求参数
            params.put("deviceId", Constants.ID);
            params.put("accessToken", SharedPreferencesUtil.getString(getContext(),"token"));
            params.put("companyName", groupName);
            params.put("companyShortName", groupJc);
            params.put("uscc", groupDm);
            params.put("legalRepresentative", groupFr);
            params.put("idCardNo", frsfz);
            params.put("businessLicenseImgUrl", businessName);
            params.put("companyAddress", companyAddress);
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
                            lockStatus();
                        }else{
                            Toast.makeText(getContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                        }
                            ;
                    }
                }

                @Override
                public void requestFailure(Request request, IOException e) {
                    // 请求失败的回调
                    Log.e("TAG",request.toString()+e.getMessage());
                }
            });
            Log.e("TAG","commitAction");
        }else {
            if (!upload){
                Toast.makeText(getContext(),"请上传营业执照以及填写完整信息",Toast.LENGTH_SHORT).show();
            }else
                Toast.makeText(getContext(),"请填写完整信息",Toast.LENGTH_SHORT).show();
        }


    }

    public void lockStatus(){

        name.setEnabled(false);
        jcname.setEnabled(false);
        faren.setEnabled(false);
        sfz.setEnabled(false);
        tydm.setEnabled(false);
        address.setEnabled(false);
        commitAction.setBackgroundColor(getResources().getColor(R.color.back_ground_gray));
        AuthActivity.GroupAuth = true;
        //if (AuthActivity.GroupAuth&&AuthActivity.IdentityAuth){
            getActivity().finish();
       // }
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
        imgName = result.getImage().getOriginalPath();
        mImageView.setImageBitmap(bitmap);

            new Thread(){
                @Override
                public void run() {
                    super.run();
                    try{
                       String response = uploadImage(Constants.ID,new File(result.getImage().getCompressPath()));
                        JSONObject  myJson = new JSONObject(response);
                        JSONObject jsonObject = myJson.getJSONObject("data");
                        businessName = jsonObject.getString("name");
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
    public String  uploadImage(String userName, File file) throws Exception{

        //2.创建RequestBody
        RequestBody fileBody = RequestBody.create(MEDIA_TYPE_PNG, file);

        //3.构建MultipartBody
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "testImage.png", fileBody)
                .addFormDataPart("deviceId", Constants.ID)
                .addFormDataPart("accessToken", SharedPreferencesUtil.getString(getContext(),"token"))
                .build();

        //4.构建请求
        Request request = new Request.Builder()
                .url(Constants.SERVICE_NAME+"/onstage/upload/company/businessLicense")
                .post(requestBody)
                .build();

        //5.发送请求
        Response response = client.newCall(request).execute();
        String re = response.body().string();
        String header = response.header("accessToken");
        if (!TextUtils.isEmpty(header)){
            SharedPreferencesUtil.putString(Application.getContext(),"token",header);
        }
        if (response.code() == 200){
            upload = true;
        }
        if (response.code() == 200) {
            return re;
        }
       return "";
    }

}
