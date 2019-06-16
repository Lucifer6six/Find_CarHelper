package com.find_carhelper.ui.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
            inviteAction.setOnClickListener(view -> {
                if (phoneNo.getText()!=null)
                    getData();
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
            nickNameTv.setText(nickName);
            realNameTv.setText(realName);
            statusTv.setText(status);
            Toast.makeText(InviteFriendsActivity.this,"已发出邀请",Toast.LENGTH_SHORT).show();
    }
    public void getData(){

        String url = Constants.GET_INFO;
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
                        setData(nickName,realName,status);
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
