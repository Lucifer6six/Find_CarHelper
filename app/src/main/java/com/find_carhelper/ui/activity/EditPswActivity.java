package com.find_carhelper.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.find_carhelper.R;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.http.Constants;
import com.find_carhelper.http.NetRequest;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.base.MVPBaseActivity;
import com.find_carhelper.utils.MobileInfoUtil;
import com.find_carhelper.utils.SharedPreferencesUtil;
import com.find_carhelper.widgets.CountDownTextView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Request;

public class EditPswActivity extends MVPBaseActivity {
    private CountDownTextView mCountDownTextView;
    private EditText phone,code,psw,repsw;
    private Button changeAction;
    private ImageView back;
    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_edit_psw;
    }

    @Override
    protected void initViews() {
        mCountDownTextView = findViewById(R.id.tvCountDown);
        phone = findViewById(R.id.phone_no);
        code = findViewById(R.id.code);
        psw = findViewById(R.id.psw);
        repsw = findViewById(R.id.repsw);
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> finish());
        if (!TextUtils.isEmpty(Constants.phoneNo)){
            phone.setText(Constants.phoneNo);
        }
        changeAction = findViewById(R.id.change);
        initCountText();
        changeAction.setOnClickListener(view -> {
            changeAction();
        });
    }
    public void changeAction(){
        String phoneNo = phone.getText().toString();
        String codes = code.getText().toString();
        String psws = psw.getText().toString();
        String repsws = repsw.getText().toString();
        if (!TextUtils.isEmpty(phoneNo)&&!TextUtils.isEmpty(codes)
                &&!TextUtils.isEmpty(psws)&&!TextUtils.isEmpty(repsws)){
                changeRequest(phoneNo,codes,psws,repsws);
        }else {
            Toast.makeText(EditPswActivity.this, "请填写完整的信息", Toast.LENGTH_SHORT).show();
        }
    }

    public void changeRequest(String phone,String code,String psws,String repsws){
        String url = Constants.CHANGE_PSW;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", Constants.ID);
        params.put("accessToken",SharedPreferencesUtil.getString(getApplicationContext(),"token"));
        params.put("code", code);
        params.put("password", psws);
        params.put("repassword", repsws);
        // ...
        NetRequest.postFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                // 请求成功的回调
                Log.e(TAG,"result == "+ result);

                if (!TextUtils.isEmpty(result)){
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("success").equals("true")){
                        Toast.makeText(EditPswActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(EditPswActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                // 请求失败的回调
                Log.e("LoginActivity",e.getMessage());
            }
        });
    }

    @Override
    protected void initData() {

    }
    private void initCountText() {
        mCountDownTextView
                .setNormalText("获取验证码")
                .setCountDownText("重新获取(", "s)")
                .setCloseKeepCountDown(false)//关闭页面保持倒计时开关
                .setCountDownClickable(false)//倒计时期间点击事件是否生效开关
                .setShowFormatTime(false)//是否格式化时间
                .setIntervalUnit(TimeUnit.SECONDS)
                .setOnCountDownStartListener(new CountDownTextView.OnCountDownStartListener() {
                    @Override
                    public void onStart() {
                        //Toast.makeText(RegistActivity.this, "开始计时", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnCountDownTickListener(new CountDownTextView.OnCountDownTickListener() {
                    @Override
                    public void onTick(long untilFinished) {
                        //Log.e("------", "onTick: " + untilFinished);
                    }
                })
                .setOnCountDownFinishListener(new CountDownTextView.OnCountDownFinishListener() {
                    @Override
                    public void onFinish() {
                        Toast.makeText(EditPswActivity.this, "倒计时完毕", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(EditPswActivity.this, "短信已发送", Toast.LENGTH_SHORT).show();
                        mCountDownTextView.startCountDown(100);
                        getCodeRequest();
                    }
                });
    }

    private void getCodeRequest(){
        String url = Constants.GET_CODE;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", Constants.ID);
        params.put("accessToken",SharedPreferencesUtil.getString(getApplicationContext(),"token"));
        // ...
        NetRequest.postFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                // 请求成功的回调
                Log.e(TAG,"result == "+ result);

                if (!TextUtils.isEmpty(result)){
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("success").equals("true")){
                        Toast.makeText(EditPswActivity.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                        finish();
                    }else {
                        Toast.makeText(EditPswActivity.this,jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                // 请求失败的回调
            }
        });
    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
