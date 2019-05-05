package com.upbest.widgets;

import android.content.Context;

import com.upbest.custom.SimpleProgressDialog;


/**
 * <pre>
 * 文件名：	ProgressDialogUtil.java
 * 描　述：	ProgressDialog工具类
 * @author kang gui yang
 * </pre>
 */
public class ProgressDialogUtil {

    private SimpleProgressDialog dialog = null;

    private static ProgressDialogUtil instance;

    public static ProgressDialogUtil getInstance() {
        if (instance == null) {
            instance = new ProgressDialogUtil();
        }
        return instance;
    }

    /**
     * 显示ProgressDialog
     *
     * @param context
     * @param needCancel 当前请求是否可以取消
     */
    public void showProgressDialog(Context context, boolean needCancel) {
        closeProgressDialog();
        dialog = new SimpleProgressDialog(context, needCancel);
        dialog.setCanceledOnTouchOutside(false);
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPercent(String percent) {
        if (dialog != null) {
            dialog.setPercent(percent);
        }
    }

    /**
     * 显示带msg的ProgressDialog
     *
     * @param msg
     * @param context
     * @param needCancel
     */
    public void showProgressDialogMsg(String msg, Context context,
                                      boolean needCancel) {
        closeProgressDialog();
        dialog = new SimpleProgressDialog(msg, context, needCancel);
        dialog.setCanceledOnTouchOutside(false);
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭ProgressDialog
     */
    public void closeProgressDialog() {
        try {
            if (dialog != null) {
                dialog.dismiss();
                dialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
