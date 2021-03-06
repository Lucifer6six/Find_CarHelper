package com.find_carhelper.ui.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.find_carhelper.R;
import com.find_carhelper.bean.UserBean;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.http.Constants;
import com.find_carhelper.http.NetRequest;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.base.MVPBaseActivity;
import com.find_carhelper.utils.MobileInfoUtil;
import com.find_carhelper.utils.SharedPreferencesUtil;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Request;

public class InviteFriendsActivity extends MVPBaseActivity {
    private EditText phoneNo;
    private TextView nickNameTv,realNameTv,statusTv;
    private Button inviteAction;
    private ImageView back_up;
    private String memberCode;
    private LinearLayout info,error;
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
        return R.layout.activity_invite_friends;
    }

    @Override
    protected void initViews() {
            phoneNo = findViewById(R.id.phoneNo);
            nickNameTv = findViewById(R.id.nick_name);
            realNameTv = findViewById(R.id.real_name);
            statusTv = findViewById(R.id.auth_stutes);
            inviteAction = findViewById(R.id.invite);
            info = findViewById(R.id.info);
        error = findViewById(R.id.error);
        phoneNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 11){
                    getData();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
            inviteAction.setOnClickListener(view -> {
                if (phoneNo.getText()!=null){
                        inviteAction();
                }

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
    public void setData(String nickName,String realName,String status){
            info.setVisibility(View.VISIBLE);
            nickNameTv.setText(nickName);
            realNameTv.setText(realName);
            statusTv.setText(status);
            error.setVisibility(View.GONE);
    }
    public void getData(){

        String url =Constants.SERVICE_NAME+ Constants.GET_INFO;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", Constants.ID);//
        params.put("accessToken", SharedPreferencesUtil.getString(getApplicationContext(),"token"));
        params.put("phoneNo", phoneNo.getText().toString());
        // ...
        NetRequest.getFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                // 请求成功的回调
                Log.e("TAG",result.toString());
                if (!TextUtils.isEmpty(result)){
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("success").equals("true")){
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        String nickName = jsonObject1.getString("nickname");
                        String realName = jsonObject1.getString("realName");
                        String status = jsonObject1.getString("status");
                        memberCode = jsonObject1.getString("code");
                        setData(nickName,realName,status);
                    }else{
                        error.setVisibility(View.VISIBLE);
                        info.setVisibility(View.GONE);
                        Toast.makeText(InviteFriendsActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
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

    public void inviteAction(){

        String url = Constants.SERVICE_NAME+Constants.GET_INFO;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", Constants.ID);//
        params.put("accessToken", SharedPreferencesUtil.getString(getApplicationContext(),"token"));
        params.put("phoneNo", phoneNo.getText().toString());
        params.put("memberCode", memberCode);
        // ...
        NetRequest.postFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                // 请求成功的回调
                Log.e("TAG",result.toString());
                if (!TextUtils.isEmpty(result)){
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getString("success").equals("true")){
                        Toast.makeText(InviteFriendsActivity.this,"已发出邀请",Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(InviteFriendsActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
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
    protected void onEventComing(EventCenter eventCenter) {

    }
}
