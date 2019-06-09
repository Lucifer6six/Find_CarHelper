package com.find_carhelper.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil {




    private static String NAME = "SharedPrefeerence_name";

    public static void putString(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "未获取到值");

}
}
