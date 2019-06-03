package com.find_carhelper.utils;

import android.content.Context;
import android.preference.PreferenceManager;

public class SharedPreferencesUtil {



    public static String getStoreJobNumber(Context context,String NAME){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(NAME, null);
    }

    public static void setStoreJobNumber(Context context, String jobNumber,String NAME){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(NAME, jobNumber)
                .apply();
    }

}
