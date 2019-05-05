package com.find_carhelper.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * <pre>
 * 文件名：	ToastUtil.java
 * 作　者：	jq
 * 时　间：	2015年1月12日下午7:25:10
 * 描　述：	Toast工具类
 * @author kang gui yang
 * </pre>
 */
public class ToastUtil {

    private static Toast shortToast = null;

    public static void makeShortText(String msg, Context context) {
        if (context == null) {
            return;
        }

        if (shortToast == null) {
            shortToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            shortToast.setText(msg);
        }
        shortToast.show();
    }


    private static Toast longToast = null;

    public static void makeLongText(String msg, Context context) {
        if (context == null) {
            return;
        }

        if (longToast == null) {
            longToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        } else {
            longToast.setText(msg);
        }
        longToast.show();
    }


    public static void showLongToast(Context context, String msg) {
        makeLongText(msg, context);
    }

    public static void showLongToast(Context context, int id) {
        makeLongText(context.getResources().getString(id), context);
    }

    /**
     * short toast
     * @param context
     * @param msg
     */
    public static void showShotToast(Context context, String msg) {
        makeShortText(msg, context);
    }

    public static void showShotToast(Context context, int id) {
        makeShortText(context.getResources().getString(id), context);
    }


    public static void showCenterToast(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


}
