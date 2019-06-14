package com.find_carhelper.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.find_carhelper.R;
import com.find_carhelper.bean.CarBean;
import com.find_carhelper.bean.TeamBean;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.http.Constants;
import com.find_carhelper.http.NetRequest;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.adapter.MyTeamAdapter;
import com.find_carhelper.ui.base.MVPBaseActivity;
import com.find_carhelper.utils.MobileInfoUtil;
import com.find_carhelper.utils.SharedPreferencesUtil;
import com.find_carhelper.widgets.OnItemClickListeners;
import com.wega.library.loadingDialog.LoadingDialog;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class MyTeamActivity extends MVPBaseActivity implements OnItemClickListeners {
    private RecyclerView recycleListView;
    private MyTeamAdapter mListOrderAcceptAdapter;
    private Button inviteBtn;
    public LoadingDialog loadingDialog;
    public List<TeamBean> teamBeans;
    public TextView null_data_tips;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case 1:
                    loadingDialog.cancel();
                    Log.e("!@#","size = "+teamBeans.size());
                    if (teamBeans!=null){
                        initAdapter(teamBeans);
                    }
                    break;
                case 0:
                    Toast.makeText(MyTeamActivity.this,"未查询到团队成员，您可以邀请员工加入团队",Toast.LENGTH_SHORT).show();
                    null_data_tips.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_my_team;
    }

    @Override
    protected void initViews() {
        recycleListView = findViewById(R.id.list_teams);
        inviteBtn = findViewById(R.id.invite);
        null_data_tips = findViewById(R.id.null_data_tips);
        inviteBtn.setOnClickListener(view -> startActivity(new Intent(MyTeamActivity.this,InviteFriendsActivity.class)));
        //initAdapter();
        initLoading();
        registerLeftClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initAdapter(List<TeamBean> list){
        mListOrderAcceptAdapter = new MyTeamAdapter(list,mContext);
        mListOrderAcceptAdapter.setOnItemClickListeners(this);
        recycleListView.setLayoutManager(new LinearLayoutManager(mContext));
        recycleListView.setHasFixedSize(true);
        recycleListView.setAdapter(mListOrderAcceptAdapter);
    }
    public void initLoading(){

        LoadingDialog.Builder builder = new LoadingDialog.Builder(MyTeamActivity.this);
        builder.setLoading_text("加载中...")
                .setFail_text("加载失败")
                .setSuccess_text("加载成功");
        //设置延时5000ms才消失,可以不设置默认1000ms
        //设置默认延时消失事件, 可以不设置默认不调用延时消失事件

        loadingDialog = builder.create();

    }
    @Override
    protected void initData() {
        getTeamData();
    }

    public void getTeamData(){

        String url = Constants.MY_TEAM;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", Constants.ID);//
        params.put("accessToken", SharedPreferencesUtil.getString(getApplicationContext(),"token"));

        // ...
        NetRequest.getFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                // 请求成功的回调
                loadingDialog.cancel();
                Log.e("TAG",result.toString());
                if (!TextUtils.isEmpty(result)){
                    JSONObject jsonObject =  JSON.parseObject(result);
                    if (jsonObject.getString("success").equals("true")){
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        Message msg = new Message();
                        teamBeans =  JSON.parseArray(jsonObject1.getJSONArray("user").toJSONString(), TeamBean.class);
                        msg.what = 1;
                        handler.sendMessage(msg);
                    }else{
                        Toast.makeText(MyTeamActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                // 请求失败的回调
                loadingDialog.cancel();

                Log.e("TAG",request.toString()+e.getMessage());
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

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, Object data, int position) {

    }
}
