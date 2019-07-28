package com.find_carhelper.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class LoginActivity extends MVPBaseActivity implements View.OnClickListener{
    private TextView registTv;
    private EditText name,psw;
    private Button login;
    private TextView mCountDownTextView;
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
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
            mCountDownTextView = findViewById(R.id.tvCountDown);
            registTv = findViewById(R.id.regist_tv);
            name = findViewById(R.id.name);
            psw = findViewById(R.id.psw);
            login = findViewById(R.id.login);
            registTv.setOnClickListener(this);
            login.setOnClickListener(this);
        //initCountText();
        mCountDownTextView.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),EditPswActivity.class));
        });

        if(Constants.DEBUG){
            name.setText("15150535368");
            psw.setText("Hy123456");
        }
        registerLeftClickEvent(view -> finish());
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
            case R.id.regist_tv:
                startActivity(new Intent(LoginActivity.this,RegistActivity.class));
                break;
            case R.id.login:
                loginAction();
                break;
        }
    }
    private void loginAction(){
        String url = Constants.SERVICE_NAME+Constants.LOGIN;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", Constants.ID);
        params.put("username", name.getText().toString());
        params.put("password", psw.getText().toString());
        // ...
        NetRequest.postFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                // 请求成功的回调
                Log.e(TAG,"result == "+ result);

                if (!TextUtils.isEmpty(result)){
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("success").equals("true")){
                        JSONObject  data = jsonObject.getJSONObject("data");
                        String token = data.getString("accessToken");
                        if (!TextUtils.isEmpty(token)){
                            SharedPreferencesUtil.putString(LoginActivity.this,"token",token);
                            Log.e("TAG","写入成功"+token);
                        }
                        Constants.isLogin = true;
                        Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
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
}
