package com.upbest.base;

import android.app.Application;

import com.upbest.util.LogUtil;
import com.upbest.util.PreUtils;

/**
 * Project：Fujian_PQHelper
 * Author：zj
 * Date：2019/3/19
 * Version: 3.0.0
 * Description： base库基类
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化log打印
        LogUtil.init();
        //初始化SharedPreferences（加密）
        PreUtils.getInstance().init(this);
    }
}
