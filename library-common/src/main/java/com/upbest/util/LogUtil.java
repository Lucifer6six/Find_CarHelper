package com.upbest.util;

import android.util.Log;

import com.orhanobut.logger.Logger;

/**
 * <pre>
 * 文件名：	LogUtil.java
 * @author： kang gui yang
 * 描　述：	日志工具类（统一日志开关）
 * </pre>
 */
public class LogUtil {

    public static void init() {
//        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
//                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
//                .methodCount(2)         // (Optional) How many method line to show. Default 2
//                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//                .tag("My custom tag")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
//                .build();
        //Logger.addLogAdapter(new AndroidLogAdapter());
    }

    ;
    /**
     * 日志开关
     */
    private static boolean logFlag = true;

    public static void closeLog() {
        logFlag = false;
    }

    public static void openLog() {
        logFlag = true;
    }

    /**
     * Log.d(tag, msg);
     *
     * @param tag
     * @param msg
     */
    public static void d(String tag, String msg) {
        if (logFlag) {
            if (null == msg) {
                Log.d(tag, "null");
            } else {
                Log.d(tag, msg);
            }
        }
    }

    /**
     * Log.d(tag, msg);
     *
     * @param msg
     */
    public static void d(String msg) {
        if (logFlag) {
            if (null == msg) {
                Logger.d("null");
            } else {
                Logger.d(msg);
            }
        }
    }

    /**
     * Log.i(tag, msg);
     *
     * @param tag
     * @param msg
     */
    public static void i(String tag, String msg) {
        if (logFlag) {
            if (null == msg) {
                Log.i(tag, "null");
            } else {
                Log.i(tag, msg);
            }
        }
    }

    /**
     * Log.i(tag, msg);
     *
     * @param msg
     */
    public static void i(String msg) {
        if (logFlag) {
            if (null == msg) {
                Logger.i("null");
            } else {
                Logger.i(msg);
            }
        }
    }

    /**
     * Log.e(tag, msg);
     *
     * @param msg
     */
    public static void e(String msg) {
        if (logFlag) {
            if (null == msg) {
                Logger.e("null");
            } else {
                Logger.e(msg);
            }
        }
    }

    /**
     * Log.e(tag, msg);
     *
     * @param tag
     * @param msg
     */
    public static void e(String tag, String msg) {
        if (logFlag) {
            if (null == msg) {
                Log.e(tag, "null");
            } else {
                Log.e(tag, msg);
            }
        }
    }

    /**
     * System.out.println
     *
     * @param tag
     * @param msg
     */
    public static void println(String tag, String msg) {
        if (logFlag) {
            if (null == msg) {
                System.out.println(tag + "  :  null");
            } else {
                System.out.println(tag + "  :  " + msg);
            }
        }
    }
}
