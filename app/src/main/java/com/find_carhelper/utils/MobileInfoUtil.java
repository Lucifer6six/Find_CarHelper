package com.find_carhelper.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.find_carhelper.http.Constants;

/**
 * 获取手机信息工具类
 *
 * @author HLQ
 * @createtime 2016-12-7下午2:06:03
 * @remarks
 */
public class MobileInfoUtil {

    /**
     * 获取手机IMEI
     *
     * @param context
     * @return
     */
    public static final String getIMEI(Context context) {
        try {
//            //实例化TelephonyManager对象
//            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            //获取IMEI号
//            String imei = telephonyManager.getDeviceId();
//            //在次做个验证，也不是什么时候都能获取到的啊
//            if (imei == null) {
//                imei = "";
//            }
            String imei = Constants.ID;

            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

}