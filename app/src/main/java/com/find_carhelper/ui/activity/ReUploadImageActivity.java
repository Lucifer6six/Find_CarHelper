package com.find_carhelper.ui.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.find_carhelper.R;
import com.find_carhelper.bean.ReasonBean;
import com.find_carhelper.http.Constants;
import com.find_carhelper.http.NetRequest;
import com.find_carhelper.ui.adapter.ReUploadAdapter;
import com.find_carhelper.utils.CustomHelper;
import com.find_carhelper.utils.SharedPreferencesUtil;
import com.find_carhelper.widgets.ShowImgPopWindow;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TResult;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import it.sephiroth.android.library.easing.Linear;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ReUploadImageActivity extends TakePhotoActivity {
    private RecyclerView recycleListView, recycleListView2;
    private ReUploadAdapter mListOrderAcceptAdapter;
    private CustomHelper customHelper;
    private View commenView;
    private Button takePhoto;
    private String orderCode;
    private String vin;
    private List<ReasonBean> photoes;
    private List<ReasonBean> photoes2;
    private String title;
    private String time;
    private TextView titleView;
    private TextView timeView;
    private LinearLayout addLayout;
    private Button commit;
    public int positions;
    public int imagNo = 0;
    public CommonTitleBar mTitleBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_upload_image);
        orderCode = getIntent().getStringExtra("no");
        vin = getIntent().getStringExtra("vin");
        initView();
        initData();
    }

    public void initData() {

        String url = Constants.SERVICE_NAME+Constants.GET_RE_CONFIG;
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
                        photoes = JSON.parseArray(jsonObject1.getJSONArray("rejectList").toJSONString(), ReasonBean.class);
                        //photoes2 = JSON.parseArray(jsonObject1.getJSONArray("addList").toJSONString(), ReasonBean.class);
                        time = jsonObject1.getString("submitTime");
                        title = jsonObject1.getString("vehicleModel");
                        //Toast.makeText(getApplicationContext(), "size=" + photoes.size(), Toast.LENGTH_SHORT).show();
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
                    titleView.setText(title);
                    timeView.setText("提交时间" + time);
                    initAdapter();
                    //initAdapter2();
                    break;
            }

        }
    };

    public void initView() {
        mTitleBar = findViewById(R.id.title_bar);
        recycleListView = findViewById(R.id.list_orders);
        recycleListView2 = findViewById(R.id.list_orders2);
        titleView = findViewById(R.id.title);
        timeView = findViewById(R.id.time);
        addLayout = findViewById(R.id.addLayout);
        commit = findViewById(R.id.commit);
        commenView = LayoutInflater.from(this).inflate(R.layout.common_layout, null);
        takePhoto = commenView.findViewById(R.id.btnPickByTake);
        customHelper = CustomHelper.of(commenView);
        commit.setOnClickListener(view -> {
            commitAction();
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

    public void commitAction() {
        String url = Constants.SERVICE_NAME+Constants.RE_COMMIT;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", Constants.ID);//
        params.put("accessToken", SharedPreferencesUtil.getString(getApplicationContext(), "token"));
        params.put("vin", vin);
        params.put("orderCode", orderCode);
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

    private void initAdapter() {
        mListOrderAcceptAdapter = new ReUploadAdapter(ReUploadImageActivity.this, photoes);
        // mListOrderAcceptAdapter.setOnItemClickListeners(this);
        recycleListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycleListView.setHasFixedSize(true);
        recycleListView.setAdapter(mListOrderAcceptAdapter);
        mListOrderAcceptAdapter.setOnItemClickListener(new ReUploadAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, ReUploadAdapter.ViewName viewName, int position) {
                positions = position;
                switch (v.getId()) {
                    case R.id.btnPickByTake:
                        imagNo = 1;
                        customHelper.onClick(takePhoto, getTakePhoto());
                        break;

                    case R.id.btnPickByTake2:
                        imagNo = 2;
                        customHelper.onClick(takePhoto, getTakePhoto());
                        break;
                    case R.id.check_photo:
                            new ShowImgPopWindow(ReUploadImageActivity.this,photoes.get(position).getPhotoUrl()).showPopupWindow();

                        break;
                }

            }

            @Override
            public void onItemLongClick(View v) {

            }
        });
    }

    //    private void initAdapter2(){
//        if (photoes2.size() !=0){
//            addLayout.setVisibility(View.VISIBLE);
//            photoes2.get(0).setType(1);
//            mListOrderAcceptAdapter= new ReUploadAdapter(this,photoes2);
//            // mListOrderAcceptAdapter.setOnItemClickListeners(this);
//            recycleListView2.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//            recycleListView2.setHasFixedSize(true);
//            recycleListView2.setAdapter(mListOrderAcceptAdapter);
//        }else {
//            addLayout.setVisibility(View.GONE);
//        }
//    }
    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        String code;
        if (photoes.size()>0){
            if (imagNo == 1) {
                photoes.get(positions).setPhotoUrl(result.getImage().getCompressPath());
                code = photoes.get(positions).getCode();
            }else{
                photoes.get(positions).setPhotoUrl2(result.getImage().getCompressPath());
                code = "FJZP";
            }

            uploadImg(result.getImage().getCompressPath(),code);

        }
        mListOrderAcceptAdapter.notifyDataSetChanged();

    }
    private UpImgAsyncTask mbuttonAsyncTask;
    public void uploadImg(String url,String code){

        mbuttonAsyncTask = new UpImgAsyncTask();
        mbuttonAsyncTask.execute(url,code);

    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }
    private class UpImgAsyncTask extends AsyncTask<String,Object,String> {
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
                JSONObject myJson = new JSONObject(response);
                JSONObject jsonObject = myJson.getJSONObject("data");
                imageName = jsonObject.getString("name");
                if (myJson.getString("success").equals("true")){
                    Toast.makeText(getApplicationContext(),"上传成功",Toast.LENGTH_LONG).show();
                }else
                    Toast.makeText(getApplicationContext(),"上传失败",Toast.LENGTH_LONG).show();
            }catch (Exception e){
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
            try{
                re =  uploadImage(file,params[1]);
            }catch (Exception e){
                e.printStackTrace();
            }
            return re;
        }
    }
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");
    private final OkHttpClient client = new OkHttpClient();
    public String uploadImage(File  file,String code) throws Exception{

        //2.创建RequestBody
        RequestBody fileBody = RequestBody.create(MEDIA_TYPE_PNG, file);

        //3.构建MultipartBody
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "testImage.png", fileBody)
                .addFormDataPart("deviceId", Constants.ID)//MobileInfoUtil.getIMEI(getContext())
                .addFormDataPart("accessToken", SharedPreferencesUtil.getString(getApplicationContext(),"token"))
                .addFormDataPart("vin", vin)
                .addFormDataPart("orderCode", orderCode)
                .addFormDataPart("imgType", code)
                .build();

        //4.构建请求
        Request request = new Request.Builder()
                .url("http://39.100.119.162:9090/onstage/upload/vehicle/retrieve/garage/apply/reject")
                .post(requestBody)
                .build();

        //5.发送请求
        Response response = client.newCall(request).execute();
        String re = response.toString();
        Log.e("hhh",re);
        return response.body().string();
    }
}
