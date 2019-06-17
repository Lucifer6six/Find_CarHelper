package com.find_carhelper.ui.activity;


import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.find_carhelper.R;
import com.find_carhelper.bean.BillListBean;
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

public class WithDrawActivity extends MVPBaseActivity {
    private EditText draw;
    private Button drawAction;
    private TextView tips;
    private ImageView allin;
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_with_draw;
    }

    @Override
    protected void initViews() {
        draw = findViewById(R.id.draw);
        drawAction = findViewById(R.id.drawAction);
        tips = findViewById(R.id.tips);
        allin = findViewById(R.id.allin);
        String drawText =  getIntent().getStringExtra("draw");
        tips.setText("可提现金额"+drawText+"元");
        drawAction.setOnClickListener(v -> {
            if (draw.getText().length()>0)
                drawAction(draw.getText().toString());
            else
                Toast.makeText(WithDrawActivity.this,"请填写提现金额",Toast.LENGTH_SHORT).show();
        });
        allin.setOnClickListener(v -> {
            if (drawText.length()>0){
                draw.setText(drawText);
            }
        });
        registerLeftClickEvent(v -> finish());
    }
    public void drawAction(String amount){
        String url = Constants.WITH_DRAW;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", Constants.ID);//MobileInfoUtil.getIMEI(getContext())
        params.put("accessToken", SharedPreferencesUtil.getString(WithDrawActivity.this,"token"));
        params.put("amount",amount);
        // ...
        NetRequest.postFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                // 请求成功的回调
                Log.e("TAG",result.toString());
                if (!TextUtils.isEmpty(result)){
                    JSONObject jsonObject = JSON.parseObject(result);
                    if (jsonObject.getString("success").equals("true")){
                        //Toast.makeText(MyCountActivity.this,"查询成功",Toast.LENGTH_SHORT).show();
                        Toast.makeText(WithDrawActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(WithDrawActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
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
    protected void initData() {

    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }
}
