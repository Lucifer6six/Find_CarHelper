package com.find_carhelper.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.find_carhelper.R;
import com.find_carhelper.bean.NewsBean;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.http.Constants;
import com.find_carhelper.http.NetRequest;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.ui.activity.NewsInfoDetail;
import com.find_carhelper.ui.adapter.SystemNewsAdapter;
import com.find_carhelper.ui.adapter.TogetherNewsAdapter;
import com.find_carhelper.ui.base.MVPBaseFragment;
import com.find_carhelper.utils.MobileInfoUtil;
import com.find_carhelper.utils.SharedPreferencesUtil;
import com.find_carhelper.widgets.OnItemClickListeners;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Request;


public class TogetherNewsFragment extends MVPBaseFragment implements OnItemClickListeners {
    private RecyclerView recycleListView;
    private TogetherNewsAdapter mListOrderAcceptAdapter;
    List<NewsBean> list;
    public static Fragment newInstance() {
       TogetherNewsFragment fragment = new TogetherNewsFragment();
        return fragment;
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_together_news;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void initViews() {
        recycleListView = mRootView.findViewById(R.id.list_orders);
    }

    @Override
    protected void initData() {
        getData();
    }
    public void getData(){
        String url = Constants.SERVICE_NAME+Constants.GET_NEWS;
        HashMap<String, String> params = new HashMap<>();
        // 添加请求参数
        params.put("deviceId", Constants.ID);//
        params.put("accessToken", SharedPreferencesUtil.getString(getContext(), "token"));
        params.put("type", "MESSAGE");
        params.put("pageNum", "0");
        params.put("pageSize", "10");
        // ...
        NetRequest.getFormRequest(url, params, new NetRequest.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                // 请求成功的回调
                Log.e("TAG",result.toString());
                if (!result.equals("401"))
                if (!TextUtils.isEmpty(result)){
                    JSONObject jsonObject =  JSON.parseObject(result);
                    if (jsonObject.getString("success").equals("true")){
                        JSONObject data = jsonObject.getJSONObject("data");
                        JSONObject pageInfo = data.getJSONObject("pageInfo");
                        list =  JSON.parseArray(pageInfo.getJSONArray("list").toJSONString(), NewsBean.class);
                        mHandler.sendEmptyMessage(0);
                    }else{
                        Toast.makeText(getContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
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
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    if (list!=null){
                        initAdapter(list);
                    }
                    break;
            }
        }
    };
    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, Object data, int position) {
        Intent intent = new Intent(getContext(), NewsInfoDetail.class);
        intent.putExtra("id",list.get(position).getId());
        intent.putExtra("type","MESSAGE");
        startActivity(intent);
    }
    private void initAdapter(List<NewsBean> list){
        mListOrderAcceptAdapter = new TogetherNewsAdapter(mContext,list);
        mListOrderAcceptAdapter.setOnItemClickListeners(this);
        recycleListView.setLayoutManager(new LinearLayoutManager(mContext));
        recycleListView.setHasFixedSize(true);
        recycleListView.setAdapter(mListOrderAcceptAdapter);
    }
}
