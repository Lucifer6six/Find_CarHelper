package com.find_carhelper.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.view.OptionsPickerView;
import com.find_carhelper.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.text.SimpleDateFormat;

import razerdp.basepopup.BasePopupWindow;

/**
 * author Mzy
 * date 2019/4/19
 * 报修工单筛选POP
 */

public class ShowImgPopWindow extends BasePopupWindow {
    private ImageView img;
    private Context context;
    private String url;
    DisplayImageOptions mOptions;

    public ShowImgPopWindow(Context context, String path) {
        super(context);
        setPopupGravity(Gravity.CENTER | Gravity.CENTER);
        this.context = context;
        this.url = path;
        bindEvent();
    }

    @Override
    protected Animation onCreateShowAnimation() {
        return getTranslateVerticalAnimation(1f, 0, 200);
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return getTranslateVerticalAnimation(0, 1f, 200);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.show_img);
    }

    private void bindEvent() {
        img = findViewById(R.id.img);
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context).writeDebugLogs().build();
        ImageLoader.getInstance().init(configuration);
        mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(false).cacheOnDisc(false)
                .bitmapConfig(Bitmap.Config.RGB_565).build();

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(url, img);
    }


}
