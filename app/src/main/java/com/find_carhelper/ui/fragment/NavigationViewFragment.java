package com.find_carhelper.ui.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.find_carhelper.BuildConfig;
import com.find_carhelper.R;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.params.CloseParams;

import java.io.File;


/**
 * 侧边栏视图
 */
public class NavigationViewFragment extends Fragment {
    private String TAG = "NavigationViewFragment";
    private Button mBtnOnOff;
    private RelativeLayout mRlCancell;
    private RelativeLayout mRlDataCache;
    private RelativeLayout mRlPicCache;
    private RelativeLayout mAbout;
    private RelativeLayout mRlUpdateApk;

    public NavigationViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_navigation_view, container, false);
        //清除数据缓存
        mRlDataCache = contentView.findViewById(R.id.rl_item1);
        //清除图片缓存
        mRlPicCache = contentView.findViewById(R.id.rl_item2);
        //关于
        mAbout = contentView.findViewById(R.id.rl_item3);
        //检查更新
        mRlUpdateApk = contentView.findViewById(R.id.rl_item4);
        //上班下班
        mBtnOnOff = contentView.findViewById(R.id.btn_on_off);
        //注销
        mRlCancell = contentView.findViewById(R.id.rl_item6);
        //测试按钮当debug的时候显示，release隐藏
        View rlTest = contentView.findViewById(R.id.rl_test);

        initView();
        return contentView;
    }

    /**
     * 初始化布局
     */
    private void initView() {

        mRlDataCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("","hahhahahahahahh");
            }
        });


    }
}