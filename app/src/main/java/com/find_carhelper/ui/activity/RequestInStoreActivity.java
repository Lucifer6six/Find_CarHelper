package com.find_carhelper.ui.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.find_carhelper.R;
import com.find_carhelper.bean.ItemBean;
import com.find_carhelper.bean.photoBean;
import com.find_carhelper.http.Application;
import com.find_carhelper.http.Constants;
import com.find_carhelper.http.NetRequest;
import com.find_carhelper.ui.adapter.MyImageUploadAdapter;
import com.find_carhelper.ui.adapter.MyImageUploadAdapter2;
import com.find_carhelper.utils.CustomHelper;
import com.find_carhelper.utils.SharedPreferencesUtil;
import com.find_carhelper.widgets.MyDialog;
import com.find_carhelper.widgets.OnItemClickListeners;
import com.jph.takephoto.app.TakePhotoActivity;
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

public class RequestInStoreActivity extends TakePhotoActivity implements OnItemClickListeners {
    private RecyclerView recycleListView,recycleListView2;
    private MyImageUploadAdapter mMyImageUploadAdapter;
    private MyImageUploadAdapter2 mMyImageUploadAdapter2;
    private CustomHelper customHelper;
    private View commenView;
    private Button takePhoto;
    private Button selectPhoto;
    public int position;
    private List<photoBean> photoes;
    private List<photoBean> addPhotoes;
    private String orderCode;
    private String vin;
    public Button save, update;
    public EditText memo;
    public CommonTitleBar mTitleBar;
    public RelativeLayout addLayout;
    public ImageView photoView;
    public boolean isAddPhoto = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_in_store);
        orderCode = getIntent().getStringExtra("no");
        vin = getIntent().getStringExtra("vin");
        initViews();
        initData();
    }

    public void initData(){

        String url = Constants.SERVICE_NAME+Constants.QUERY_PHOTOS;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", Constants.ID);//
        params.put("accessToken", SharedPreferencesUtil.getString(getApplicationContext(), "token"));
        params.put("vin", vin);
        params.put("orderCode", orderCode);
        // ...
        NetRequest.getFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                // 请求成功的回调
                Log.e("TAG", result.toString());
                if (!TextUtils.isEmpty(result)) {
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(result);
                    if (jsonObject.getString("success").equals("true")) {
                        com.alibaba.fastjson.JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        photoes = JSON.parseArray(jsonObject1.getJSONArray("photoTypeList").toJSONString(), photoBean.class);
                        addPhotoes = JSON.parseArray(jsonObject1.getJSONArray("addedPhotoList").toJSONString(), photoBean.class);
                        mHandler.sendEmptyMessage(0);
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

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case 0:
                    // initImages(photoes.size());
                    initAdapter();
                    initAdapter2();
                    break;
                case 2:
                    int position = (int)msg.obj;
                    fotPostition( position);
                    break;
            }
        }
    };
    public void fotPostition(int position){
        this.position = position;
    }
    private void initViews() {
        //initImages();
        mTitleBar = findViewById(R.id.title_bar);
        addLayout = findViewById(R.id.addLayout);
        photoView = findViewById(R.id.photoView);
        commenView = LayoutInflater.from(this).inflate(R.layout.common_layout, null);
        takePhoto = commenView.findViewById(R.id.btnPickByTake);
        selectPhoto = commenView.findViewById(R.id.btnPickBySelect);
        customHelper = CustomHelper.of(commenView);
        recycleListView = findViewById(R.id.img_list);
        recycleListView2 = findViewById(R.id.img_list2);
        save = findViewById(R.id.save);//暂存
        update = findViewById(R.id.update);
        memo = findViewById(R.id.memo);
        save.setOnClickListener(v -> {
            commitAction("YES");
        });
        update.setOnClickListener(v -> {
            commitAction("NO");
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

    public void commitAction(String type) {
        String url = Constants.SERVICE_NAME+Constants.SAVE_LIBIARY;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", Constants.ID);//
        params.put("accessToken", SharedPreferencesUtil.getString(getApplicationContext(), "token"));
        params.put("vin", vin);
        params.put("orderCode", orderCode);
        params.put("staging", type);
        params.put("remark", memo.getText().toString());
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

    public void initImages(int size) {
        Log.e("TAG", "?????");
        for (int i = 0; i < size; i++) {
            ItemBean images = new ItemBean();
            images.setPosition(i);
            images.setPath("");
            images.setName(photoes.get(i).getName());
        }

    }

    private void initAdapter() {
        mMyImageUploadAdapter = new MyImageUploadAdapter(this, photoes);
        mMyImageUploadAdapter.setOnItemClickListeners(this);
        recycleListView.setLayoutManager(new GridLayoutManager(this, 2));
        recycleListView.setHasFixedSize(true);
        recycleListView.setAdapter(mMyImageUploadAdapter);
    }

    public void initAdapter2() {
        if(addPhotoes.size()>0){
            addLayout.setVisibility(View.VISIBLE);
        }else{
            addLayout.setVisibility(View.GONE);
        }

        mMyImageUploadAdapter2 = new MyImageUploadAdapter2(this, addPhotoes);
        mMyImageUploadAdapter2.setOnItemClickListeners(new OnItemClickListeners() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, Object data, int position) {
                Message msg = new Message();
                msg.obj = position;
                msg.what = 2;
                mHandler.sendMessage(msg);
                isAddPhoto = true;
                showSelectDialog();
            }
        });
        recycleListView2.setLayoutManager(new GridLayoutManager(this, 2));
        recycleListView2.setHasFixedSize(true);
        recycleListView2.setAdapter(mMyImageUploadAdapter2);
    }
    public void showSelectDialog(){
        MyDialog dialogHistory = new MyDialog(RequestInStoreActivity.this, R.style.dialog_theme_pick);
        dialogHistory.setTextTitle("请选择照片来源");
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

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);

        if (isAddPhoto) {
            isAddPhoto = false;
            addPhotoes.get(position).setPhotoUrl(result.getImage().getCompressPath());
            uploadImg(result.getImage().getCompressPath(), addPhotoes.get(position).getCode());
            mMyImageUploadAdapter2.notifyDataSetChanged();
            return;
        }

        if (photoes.size() > 0) {
            photoes.get(position).setPhotoUrl(result.getImage().getCompressPath());
            uploadImg(result.getImage().getCompressPath(), photoes.get(position).getCode());
            mMyImageUploadAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, Object data, int position) {
        //takePhoto.performClick();
        this.position = position;
        isAddPhoto = false;
        //customHelper.onClick(takePhoto, getTakePhoto());
        showSelectDialog();
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
                JSONObject myJson = new JSONObject(response);
                JSONObject jsonObject = myJson.getJSONObject("data");
                imageName = jsonObject.getString("name");
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
        String url = Constants.SERVICE_NAME+"/upload/vehicle/retrieve/garage/apply";
        Request request = new Request.Builder()
                .url(url)
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
}
