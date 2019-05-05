package com.upbest.ioc;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 本类作用..
 *
 * @author Created by kGY on 2017/8/25.
 * Email 18252032703@163.com
 * qq 827746955 mobile 18252032703
 * Thank you for watching my code
 */
public class ViewUtils {


    /**
     * 目前
     *
     * @param activity
     */
    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);
    }

    /**
     * 后期
     *
     * @param view
     */
    public static void inject(View view) {
        inject(new ViewFinder(view), view);
    }

    /**
     * 后期
     *
     * @param view
     * @param object
     */
    public static void inject(View view, Object object) {
        inject(new ViewFinder(view), object);
    }


    /**
     * 兼容上面三个方法 object 反射需要执行的类
     *
     * @param finder
     * @param object
     */
    private static void inject(ViewFinder finder, Object object) {
        injectFiled(finder, object);
        injectEvent(finder, object);

    }


    /**
     * 注入属性
     *
     * @param finder
     * @param object
     */
    private static void injectFiled(ViewFinder finder, Object object) {
        Class<?> clazz = object.getClass();
        // 获取所有属性 包括私有和公有
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ViewById viewById = field.getAnnotation(ViewById.class);
            if (viewById != null) {
                // 获取注解里面的ID值
                int viewId = viewById.value();
                // findViewById 找到View
                View view = finder.findViewById(viewId);
                if (view != null) {
                    // 能够注入所有修饰符  private public protect
                    field.setAccessible(true);
                    // 动态注入View
                    try {
                        field.set(object, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }


    /**
     * 注入事件
     *
     * @param finder
     * @param object
     */
    private static void injectEvent(ViewFinder finder, Object object) {
        // 1 获取类所有的方法
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        // 2 获取里面的value值
        for (Method method : methods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null) {
                int[] viewIds = onClick.value();
                for (int viewId : viewIds) {
                    // 3 findViewById
                    View view = finder.findViewById(viewId);

                    // 扩展功能 检测网络
                    boolean isCheckNet = method.getAnnotation(CheckNet.class) != null;


                    if (view != null) {
                        // 4 点击事件
                        view.setOnClickListener(new DeclaredOnClickListener(method, object, isCheckNet));
                    }
                }

            }
        }

    }


    private static class DeclaredOnClickListener implements View.OnClickListener {
        private Object mObject;
        private Method mMethod;
        private boolean mIsCheckNet;


        public DeclaredOnClickListener(Method method, Object object, boolean isCheckNet) {
            this.mMethod = method;
            this.mObject = object;
            this.mIsCheckNet = isCheckNet;
        }

        @Override
        public void onClick(View view) {
            // 需不需要检测网络
            if (mIsCheckNet) {
                // 需要判断网络  这里写死有点问题 需要配置
                if (!newWorkAvailable(view.getContext())) {
                    Toast.makeText(view.getContext(), "亲,您的网络不太给力哦!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            // 点击会调用这方法
            try {
                // 所有方法都可以 包括私有公有
                mMethod.setAccessible(true);
                // 5 反射执行方法
                mMethod.invoke(mObject, view);

            } catch (Exception e) {
                e.printStackTrace();

                try {
                    mMethod.invoke(mObject, new Object[]{});
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }

    /**
     * 检测网络是否可用
     *
     * @param context
     * @return
     */
    private static boolean newWorkAvailable(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activityNetWorkInfo = connectivityManager.getActiveNetworkInfo();
            if (activityNetWorkInfo != null && activityNetWorkInfo.isConnected()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
