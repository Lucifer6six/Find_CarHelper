package com.find_carhelper.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.find_carhelper.R;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.http.Constants;
import com.find_carhelper.http.NetRequest;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.base.MVPBaseActivity;
import com.find_carhelper.utils.SharedPreferencesUtil;
import com.find_carhelper.widgets.CountDownTextView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Request;

public class RegistActivity extends MVPBaseActivity implements View.OnClickListener{
    private CountDownTextView mCountDownTextView;
    private EditText codeEdit,psw,repsw;
    private Button regist;
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
        return R.layout.activity_regist;
    }

    @Override
    protected void initViews() {
        mCountDownTextView = findViewById(R.id.tvCountDown);
        codeEdit = findViewById(R.id.code);
        regist = findViewById(R.id.regist);
        psw = findViewById(R.id.psw);
        repsw = findViewById(R.id.repsw);
        findViewById(R.id.login_tv).setOnClickListener(this);
        regist.setOnClickListener(this);
        initCountText();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_tv:
                startActivity(new Intent(RegistActivity.this,LoginActivity.class));
                break;
            case R.id.regist:
                //regist();
                startActivity(new Intent(RegistActivity.this,AuthActivity.class));
                break;

        }
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
                        Log.e("------", "onTick: " + untilFinished);
                    }
                })
                .setOnCountDownFinishListener(new CountDownTextView.OnCountDownFinishListener() {
                    @Override
                    public void onFinish() {
                        Toast.makeText(RegistActivity.this, "倒计时完毕", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(RegistActivity.this, "短信已发送", Toast.LENGTH_SHORT).show();
                        mCountDownTextView.startCountDown(100);
                        getCodeRequest();
                    }
                });
    }
    private void getCodeRequest(){
        String url = Constants.GET_MSG_CODE;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", "123456");
        params.put("phoneNo", "18651090153");
        // ...
        NetRequest.postFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                // 请求成功的回调
                Log.e(TAG,"result == "+ result);

                if (!TextUtils.isEmpty(result)){
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("success").equals("true")){
                        String  data = jsonObject.getString("data");
                        if (!TextUtils.isEmpty(data)){
                            codeEdit.setText(data);
                        }
                    };
                }
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                // 请求失败的回调
            }
        });
    }
    private void regist(){
        String psw1 = psw.getText().toString();
        String psw2 = repsw.getText().toString();
        String url = Constants.REGISTER;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", "123456");
        params.put("phoneNo", "18651090153");
        params.put("code", codeEdit.getText().toString());
        params.put("password",psw1);
        params.put("repassword", psw2);
        // ...
        NetRequest.postFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                // 请求成功的回调
                if (!TextUtils.isEmpty(result)){
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("success").equals("true")){
                        String  data = jsonObject.getString("data");
                        if (!TextUtils.isEmpty(data)){
                            //codeEdit.setText(data);
                            Toast.makeText(RegistActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                            SharedPreferencesUtil.setStoreJobNumber(RegistActivity.this,data,"token");
                        }

                    }else{
                        Toast.makeText(RegistActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                // 请求失败的回调
            }
        });
    }
}
