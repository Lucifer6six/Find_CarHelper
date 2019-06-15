package com.find_carhelper.ui.activity;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.find_carhelper.R;
import com.find_carhelper.bean.BillListBean;
import com.find_carhelper.bean.NewsBean;
import com.find_carhelper.bean.UserBean;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.http.Constants;
import com.find_carhelper.http.NetRequest;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.adapter.MyAcountAdapter;
import com.find_carhelper.ui.adapter.MyTeamAdapter;
import com.find_carhelper.ui.base.MVPBaseActivity;
import com.find_carhelper.utils.MobileInfoUtil;
import com.find_carhelper.utils.SharedPreferencesUtil;
import com.find_carhelper.widgets.OnItemClickListeners;
import com.google.gson.Gson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;

public class MyCountActivity extends MVPBaseActivity implements OnItemClickListeners {
    private RecyclerView recycleListView;
    private MyAcountAdapter mListOrderAcceptAdapter;
    private ImageView withdraw;
    private String withDraw;
    private String totalDraw;
    private String tips;
    private String tips2;
    TextView canDraw,total,tip1,tip2;
    private boolean canApply = false;
    private String reason = "";
    private List<BillListBean> list;
    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_my_count;
    }

    @Override
    protected void initViews() {
        recycleListView = findViewById(R.id.list_money);
        withdraw = findViewById(R.id.withdraw);
        total = findViewById(R.id.all_money);
        canDraw = findViewById(R.id.total_money);
        tip1 = findViewById(R.id.tips);
        tip2 = findViewById(R.id.tips2);
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!canApply) {
                    Intent intent = new Intent(MyCountActivity.this,WithDrawActivity.class);
                    intent.putExtra("draw",totalDraw);
                    startActivity(intent);
                    //startActivity(new Intent(MyCountActivity.this,WithDrawActivity.class));
                }else {
                    Toast.makeText(MyCountActivity.this,reason,Toast.LENGTH_SHORT).show();
                }
            }
        });
        //initAdapter();
        registerLeftClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        registerLeftClickEvent(view -> {
            finish();
        });
    }

    private void initAdapter(List<BillListBean> list){
        mListOrderAcceptAdapter = new MyAcountAdapter(list,mContext);
        mListOrderAcceptAdapter.setOnItemClickListeners(this);
        recycleListView.setLayoutManager(new LinearLayoutManager(mContext));
        recycleListView.setHasFixedSize(true);
        recycleListView.setAdapter(mListOrderAcceptAdapter);
    }

    @Override
    protected void initData() {
        getData();
    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }
    public void getData(){
        String url = Constants.GET_ACCOUNT_INFO;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", Constants.ID);//MobileInfoUtil.getIMEI(getContext())
        params.put("accessToken", SharedPreferencesUtil.getString(MyCountActivity.this,"token"));
        // ...
        NetRequest.getFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                // 请求成功的回调
                Log.e("TAG",result.toString());
                if (!TextUtils.isEmpty(result)){
                    JSONObject jsonObject = JSON.parseObject(result);
                    if (jsonObject.getString("success").equals("true")){
                        //Toast.makeText(MyCountActivity.this,"查询成功",Toast.LENGTH_SHORT).show();
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        list =  JSON.parseArray(jsonObject1.getJSONArray("billList").toJSONString(), BillListBean.class);
                        totalDraw = jsonObject1.getString("tobeWithdrawAmount");
                        withDraw = jsonObject1.getString("withdrawnAmount");
                        tips = jsonObject1.getString("openDateTips");
                        tips2 = jsonObject1.getString("paymentDateTips");
                        if (jsonObject1.getString("canApply").equals("true")){
                            canApply = true;
                        }else{
                            canApply = false;
                            reason = jsonObject1.getString("reason");
                        }
                        initAdapter(list);
                        setData();
                    }else{
                        Toast.makeText(MyCountActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
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
    public void setData(){
        total.setText("已累计提现"+withDraw+"元");
        canDraw.setText(totalDraw);
        tip1.setText(tips);
        tip2.setText(tips2);
    }
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, Object data, int position) {

    }
}
