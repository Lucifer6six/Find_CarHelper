package com.find_carhelper.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MenuItem;

import com.find_carhelper.R;
import com.find_carhelper.utils.Utils;
import com.github.chrisbanes.photoview.PhotoView;


public class ImagePreviewActivity extends AppCompatActivity {
    PhotoView mPhotoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        setContentView(R.layout.activity_image_preview);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar  != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("图片预览");
        }

        mPhotoView = findViewById(R.id.preview_iv);

        Intent intent = getIntent();
        if(intent != null){
            String path = intent.getStringExtra("path");
            if(!TextUtils.isEmpty(path)){
                Bitmap bitmap = Utils.decodeTargetSize(path,dm.widthPixels,dm.heightPixels);
                if(bitmap != null){
                    mPhotoView.setImageBitmap(bitmap);
                }

            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
