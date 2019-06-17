package com.find_carhelper.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.find_carhelper.BuildConfig;
import com.find_carhelper.R;
import com.find_carhelper.ui.activity.ImagePreviewActivity;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author yangchuncheng
 * @date 2019/3/14
 */
public class Utils {
    public static void closeSilently(Closeable closeable){
        if(closeable != null){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void handleError(Context context,Throwable e){
        if(BuildConfig.DEBUG){
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        }else {

        }
    }

    public static void snackShow(View view, String msg){
        if(view == null)return;
        Snackbar sk = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
        sk.getView().setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimary));
        sk.setActionTextColor(Color.RED);
        sk.show();
    }
    static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String format(Date date){
        if(date == null) return "";
        return FORMAT.format(date);
    }

    public void showErrorDialog(Context context,String msg){
        //new AlertDialog.Builder(context).s
    }

    public static void save2Preference(Object o) throws IllegalAccessException {
        Class clazz = o.getClass();
        Field[] fields = clazz.getFields();
        for(Field f : fields){
            f.get(o);
            f.getName();
        }
    }

    public static  Date getSysTime(){
        //TODO  获取服务器时间，在客户端启动计时器更新时间
        return new Date();
    }


    //BottomNavigationView超过4个时也显示文字
    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    public static void startPhotoPreview(Context context,String path){
        Intent intent = new Intent(context, ImagePreviewActivity.class);
        intent.putExtra("path",path);
        context.startActivity(intent);
    }

    public static Bitmap decodeTargetSize(String sourcePath, int optionWidth, int optionHeight){
        File sourceFile = new File(sourcePath);
        if(!sourceFile.exists()) return null;
        BitmapFactory.Options opts = null;
        if (optionWidth > 0 && optionHeight > 0) {
            opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(sourcePath, opts);
            // 计算图片缩放比例
            final int minSideLength = Math.min(optionWidth, optionHeight);
            opts.inSampleSize = computeSampleSize(opts, minSideLength,
                    optionWidth * optionHeight);
            opts.inJustDecodeBounds = false;
            opts.inInputShareable = true;
            opts.inPurgeable = true;
            return BitmapFactory.decodeFile(sourcePath, opts);
        }
        return null;
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math
                .floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }
    public static String addComma(String str) {
        DecimalFormat decimalFormat = new DecimalFormat(",##0.00");
        return decimalFormat.format(Double.parseDouble(str));
    }
}
