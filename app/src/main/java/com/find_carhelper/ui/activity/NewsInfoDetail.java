package com.find_carhelper.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.find_carhelper.R;
import com.find_carhelper.bean.NewsBean;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.http.Constants;
import com.find_carhelper.http.NetRequest;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.base.MVPBaseActivity;
import com.find_carhelper.utils.MobileInfoUtil;
import com.find_carhelper.utils.SharedPreferencesUtil;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

public class NewsInfoDetail extends MVPBaseActivity {
    public String id;
    public String type;
    public Button agree,disageree;
    TextView pageTitle,timeTv,new_detail;
    public String agreeURL;
    public String disagreeUrl;
    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_news_info_detail;
    }

    @Override
    protected void initViews() {
       id =  getIntent().getStringExtra("id");
       type = getIntent().getStringExtra("type");
        agree = findViewById(R.id.agree);
        disageree = findViewById(R.id.abort);
        pageTitle = findViewById(R.id.pageTitle);
        timeTv = findViewById(R.id.pageTime);
        new_detail = findViewById(R.id.new_detail);
       if (type.equals("NOTICE")){
           agree.setVisibility(View.GONE);
           disageree.setVisibility(View.GONE);
       }else{
           agree.setVisibility(View.VISIBLE);
           disageree.setVisibility(View.VISIBLE);
       }
        disageree.setOnClickListener(view -> {
            choose(disagreeUrl);
        });
       agree.setOnClickListener(view -> {
            choose(agreeURL);
       });
        registerLeftClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        initDatas();
    }
    public void initDatas(){
        String url = Constants.NEWS_DETAIL+id;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", Constants.ID);//
        params.put("accessToken", SharedPreferencesUtil.getString(getApplicationContext(), "token"));
        params.put("type", type);
        // ...
        NetRequest.getFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                // 请求成功的回调
                Log.e("TAG",result.toString());
                if (!TextUtils.isEmpty(result)){
                    JSONObject jsonObject =  JSON.parseObject(result);
                    if (jsonObject.getString("success").equals("true")){
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        pageTitle.setText(jsonObject1.getString("title"));
                        timeTv.setText(jsonObject1.getString("createTime"));
                        new_detail.setText(jsonObject1.getString("text"));
                        JSONArray jsonArray = jsonObject1.getJSONArray("buttons");
                        if (jsonArray.size()>0){
                            JSONObject btn1 = jsonArray.getJSONObject(0);
                            JSONObject btn2 = jsonArray.getJSONObject(1);
                            if (btn1.getString("type").equals("YES")){
                                agreeURL = btn1.getString("linkUrl");
                                disagreeUrl = btn2.getString("linkUrl");
                            }else {
                                disagreeUrl = btn1.getString("linkUrl");
                                agreeURL = btn2.getString("linkUrl");
                            }
                        }else{
                            agree.setVisibility(View.GONE);
                            disageree.setVisibility(View.GONE);
                        }

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
    public void choose(String url){
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", Constants.ID);//
        params.put("accessToken", SharedPreferencesUtil.getString(getApplicationContext(), "token"));
        // ...
        NetRequest.postFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                // 请求成功的回调
                Log.e("TAG",result.toString());
                if (!TextUtils.isEmpty(result)){
                    JSONObject jsonObject =  JSON.parseObject(result);
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
    protected BasePresenter createPresenter() {
        return null;
    }
}
