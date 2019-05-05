package com.upbest.retrofit;

import com.upbest.rxlibrary.BuildConfig;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kGod on 2017/2/28.
 * Email 18252032703@163.com
 * Thank you for watching my code
 * Description: RetrofitHelper
 *
 * @author kang gui yang
 */

public class RetrofitHelper {


    private static final int DEFAULT_CONNECT_TIME = 20;
    private static final int DEFAULT_READ_TIME_OUT = 20;
    private static final int DEFAULT_WRITE_TIME_OUT = 20;

    private static Apis mApiService;

    public static RetrofitHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static class SingletonHolder {
        private static final RetrofitHelper INSTANCE = new RetrofitHelper();
    }

    private RetrofitHelper() {
        mApiService = create();
    }

    public Apis getApiService(){
        return mApiService;
    }

    /**
     * 创建RetroFit 实例
     *
     * @return
     */
    private Apis create() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(getOkHttpClient())
                .baseUrl(Apis.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(Apis.class);
    }

    /**
     * Retrofit是基于OkHttpClient的，可以创建一个OkHttpClient进行一些配置
     *
     * @return
     */
    private OkHttpClient getOkHttpClient() {

        // 配置超时拦截器
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 配置连接超时
        builder.connectTimeout(DEFAULT_CONNECT_TIME, TimeUnit.SECONDS);
        // 写入超时
        builder.writeTimeout(DEFAULT_WRITE_TIME_OUT, TimeUnit.SECONDS);
        // 读取超时
        builder.readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS);
        // 添加日志拦截器
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(InterceptorUtil.logInterceptor());
        }

        return builder.build();
    }


}
