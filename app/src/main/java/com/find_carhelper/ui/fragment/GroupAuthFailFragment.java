package com.find_carhelper.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.TextView;
import android.widget.Toast;

import com.find_carhelper.R;
import com.find_carhelper.bean.AuthBean;
import com.find_carhelper.bean.GroupAuthBean;
import com.find_carhelper.bean.UserBean;
import com.find_carhelper.http.Application;
import com.find_carhelper.http.Constants;
import com.find_carhelper.http.NetRequest;
import com.find_carhelper.ui.activity.AuthActivity;
import com.find_carhelper.utils.CustomHelper;
import com.find_carhelper.utils.SharedPreferencesUtil;
import com.find_carhelper.utils.ToastUtil;
import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoFragment;
import com.jph.takephoto.model.TResult;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

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


public class GroupAuthFailFragment extends TakePhotoFragment {
    private ImageView mImageView;
    private CustomHelper customHelper;
    private View commenView, contentView;
    private Button takePhoto;
    private EditText name, jcname, faren, sfz, tydm, address;
    private Button commitAction;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
    private final OkHttpClient client = new OkHttpClient();
    private String imgName;
    private boolean upload = false;
    public String businessName = "";
    public String reason;
    public GroupAuthBean userBean;
    public TextView auth_fail_tips1;
    public Context context;
    public CommonTitleBar title_bar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_group_auth_fail, null);
        context = getContext();
        initView();
        return contentView;
    }


    public void getData() {
        String url = Constants.SERVICE_NAME + Constants.AUTH_FAILED;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", Constants.ID);//MobileInfoUtil.getIMEI(getContext())
        params.put("accessToken", SharedPreferencesUtil.getString(getContext(), "token"));//MobileInfoUtil.getIMEI(getContext())

        // ...
        NetRequest.getFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                // 请求成功的回调
                Log.e("TAG", result.toString());
                if (!TextUtils.isEmpty(result)) {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = "";
                    if (jsonObject.has("status")) {
                        status = jsonObject.getString("status");
                    }
                    if (status.equals("200") || jsonObject.getString("code").equals("I00000")) {
                        if (jsonObject.getString("success").equals("true")) {
                            // Toast.makeText(getContext(), "查询成功", Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                            Gson gson = new Gson();
                            reason = jsonObject1.getString("reason");
                            userBean = gson.fromJson(jsonObject1.getJSONObject("member").toString(), GroupAuthBean.class);
                            handler.sendEmptyMessage(0);
                        } else {
                            //startIntent();
                            Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        if (status.equals("401") || status.equals("500")) {
                            Toast.makeText(getContext(), "请重新登录", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "系统错误", Toast.LENGTH_SHORT).show();
                        }

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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    initDatas();
                    break;
                case 1:
                    ToastUtil.makeLongText("上传成功",context);
                    break;
                case 2:
                    ToastUtil.makeLongText("上传失败",context);
                    break;
            }

        }
    };

    public void initDatas() {
        name.setText(userBean.getCompanyName());
        jcname.setText(userBean.getCompanyShortName());
        address.setText(userBean.getCompanyAddress());
        faren.setText(userBean.getLegalRepresentative());
        sfz.setText(userBean.getIdCardNo());
        tydm.setText(userBean.getUscc());
        auth_fail_tips1.setText(reason);
        if (userBean.getBusinessLicenseImgUrl() != null) {
            ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context).writeDebugLogs().build();
            ImageLoader.getInstance().init(configuration);
            DisplayImageOptions mOptions = new DisplayImageOptions.Builder()
                    .cacheInMemory(false).cacheOnDisc(false)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(userBean.getBusinessLicenseImgUrl(), mImageView);

        }
    }

    public void initView() {
        commenView = LayoutInflater.from(getContext()).inflate(R.layout.common_layout, null);
        customHelper = CustomHelper.of(commenView);
        takePhoto = commenView.findViewById(R.id.btnPickByTake);
        mImageView = contentView.findViewById(R.id.img1);
        title_bar = contentView.findViewById(R.id.title_bar);
        name = contentView.findViewById(R.id.name);
        jcname = contentView.findViewById(R.id.id);
        faren = contentView.findViewById(R.id.faren);
        sfz = contentView.findViewById(R.id.faren_id);
        tydm = contentView.findViewById(R.id.tydm);
        address = contentView.findViewById(R.id.address);
        auth_fail_tips1 = contentView.findViewById(R.id.auth_fail_tips1);
        commitAction = contentView.findViewById(R.id.commit);
        title_bar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                    getActivity().finish();
                }
            }
        });
        commitAction.setOnClickListener(v -> {
            commitAction();
        });
        mImageView.setOnClickListener(v -> {
            takePhoto();
        });
        getData();
    }

    public void takePhoto() {
        customHelper.onClick(takePhoto, getTakePhoto());
    }

    public GroupAuthFailFragment() {
        super();
    }

    public void commitAction() {
        String groupName = name.getText().toString();
        String groupJc = jcname.getText().toString();
        String groupFr = faren.getText().toString();
        String frsfz = sfz.getText().toString();
        String groupDm = tydm.getText().toString();
        String companyAddress = address.getText().toString();
        if (!TextUtils.isEmpty(groupName) && !TextUtils.isEmpty(groupJc) &&
                !TextUtils.isEmpty(groupFr) && !TextUtils.isEmpty(frsfz) && !TextUtils.isEmpty(groupDm) && upload) {
            String url = Constants.SERVICE_NAME + Constants.RE_AUTH_COMPANY;
            HashMap<String, String> params = new HashMap<>();
            // 添加请求参数
            params.put("deviceId", Constants.ID);
            params.put("accessToken", SharedPreferencesUtil.getString(getContext(), "token"));
            params.put("companyName", groupName);
            params.put("companyShortName", groupJc);
            params.put("uscc", groupDm);
            params.put("legalRepresentative", groupFr);
            params.put("idCardNo", frsfz);
            params.put("businessLicenseImgUrl", businessName);
            params.put("companyAddress", companyAddress);
            params.put("memberCode", userBean.getMemberCode());
            // ...
            NetRequest.postFormRequest(url, params, new NetRequest.DataCallBack() {
                @Override
                public void requestSuccess(String result) throws Exception {
                    // 请求成功的回调
                    Log.e("TAG", result.toString());
                    if (!TextUtils.isEmpty(result)) {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.getString("success").equals("true")) {
                            Toast.makeText(getContext(), "提交成功", Toast.LENGTH_SHORT).show();
                            lockStatus();
                        } else {
                            Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                @Override
                public void requestFailure(Request request, IOException e) {
                    // 请求失败的回调
                    Log.e("TAG", request.toString() + e.getMessage());
                }
            });
            Log.e("TAG", "commitAction");
        } else {
            if (!upload) {
                Toast.makeText(getContext(), "请上传营业执照以及填写完整信息", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getContext(), "请填写完整信息", Toast.LENGTH_SHORT).show();
        }
    }

    public void lockStatus() {
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
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        Bitmap bitmap = BitmapFactory.decodeFile(result.getImage().getCompressPath());
        imgName = result.getImage().getOriginalPath();
        mImageView.setImageBitmap(bitmap);

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    String response = uploadImage(Constants.ID, new File(result.getImage().getCompressPath()));
                    JSONObject myJson = new JSONObject(response);
                    JSONObject jsonObject = myJson.getJSONObject("data");
                    businessName = jsonObject.getString("name");
                } catch (Exception e) {
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

    public String uploadImage(String userName, File file) throws Exception {

        //2.创建RequestBody
        RequestBody fileBody = RequestBody.create(MEDIA_TYPE_PNG, file);

        //3.构建MultipartBody
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "testImage.png", fileBody)
                .addFormDataPart("deviceId", Constants.ID)
                .addFormDataPart("accessToken", SharedPreferencesUtil.getString(getContext(), "token"))
                .build();

        //4.构建请求
        Request request = new Request.Builder()
                .url(Constants.SERVICE_NAME + "/upload/company/businessLicense")
                .post(requestBody)
                .build();

        //5.发送请求
        Response response = client.newCall(request).execute();
        String re = response.body().string();
        String header = response.header("accessToken");
        if (!TextUtils.isEmpty(header)) {
            SharedPreferencesUtil.putString(Application.getContext(), "token", header);
        }
        if (response.code() == 200) {
            upload = true;
            handler.sendEmptyMessage(1);
        }else {
            handler.sendEmptyMessage(2);
        }
        if (response.code() == 200) {
            return re;
        }
        return "";
    }
}
