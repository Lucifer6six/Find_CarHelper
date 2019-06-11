package com.find_carhelper.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;

/**
 * author Mzy
 * date 2019/6/11
 * 写bug啊
 */
public class PermissionChecker {
    public static final String TAG = PermissionChecker.class.getSimpleName();
    public static final int READ_PHONE_STATE = 1;

    private static PermissionChecker checker;

    private PermissionChecker(){
    }

    public static PermissionChecker getInstance(){
        if (checker == null){
            checker = new PermissionChecker();
        }
        return checker;
    }
    @SuppressLint("NewApi")
    public static void requestReadPhoneState(Activity activity) {
        int hasWriteContactsPermission = activity.checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[] {Manifest.permission.READ_PHONE_STATE},
                    READ_PHONE_STATE);
            return;
        }
    }
}
