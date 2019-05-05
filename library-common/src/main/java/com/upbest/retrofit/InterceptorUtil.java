package com.upbest.retrofit;

import com.upbest.util.LogUtil;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * <pre>
 * 文件名：	InterceptorUtil
 * 作　者：	gykang
 * 时　间：	2018/7/19 10:47
 * 描　述：  Retrofit2 拦截器工具类
 * @author kang gui yang
 * </pre>
 */
public class InterceptorUtil {

    private static final String TAG = InterceptorUtil.class.getSimpleName();

    /**
     * 日志拦截器
     *
     * @return
     */
    public static HttpLoggingInterceptor logInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    LogUtil.e(TAG, URLDecoder.decode(message, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    LogUtil.e(TAG, message);
                }
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    /**
     * Header拦截器
     *
     * @return
     */
    public static Interceptor headerInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request mRequest = chain.request();
                //在这里你可以做一些想做的事,比如token失效时,重新获取token
                return chain.proceed(mRequest);
            }
        };

    }

}
