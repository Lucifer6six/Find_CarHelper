package com.find_carhelper.ui.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.find_carhelper.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ShowImageActivity extends AppCompatActivity {
    ImageView image;
    DisplayImageOptions mOptions;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        image = findViewById(R.id.image);
        url = getIntent().getStringExtra("url");
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(ShowImageActivity.this).writeDebugLogs().build();
        ImageLoader.getInstance().init(configuration);

        mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(false).cacheOnDisc(false)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.displayImage(url, image);
    }
}
