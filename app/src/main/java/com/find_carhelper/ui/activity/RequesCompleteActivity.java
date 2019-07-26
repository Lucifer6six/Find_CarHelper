package com.find_carhelper.ui.activity;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.find_carhelper.R;
import com.find_carhelper.entity.EventCenter;
import com.find_carhelper.presenter.BasePresenter;
import com.find_carhelper.utils.CustomHelper;
import com.find_carhelper.widgets.MyDialog;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TResult;

/**
 * 寻车订单 申请结单
 */
public class RequesCompleteActivity extends TakePhotoActivity implements View.OnClickListener {
    private View commenView;
    private Button takePhoto;
    private Button selectPhoto;
    private RelativeLayout carImagLayout,carIdImgLayout;
    private ImageView photoView,photoView2;
    private CustomHelper customHelper;
    private int imgFlag = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reques_complete);
        initViews();
    }


    public void initViews() {
        commenView = LayoutInflater.from(this).inflate(R.layout.common_layout, null);
        takePhoto = commenView.findViewById(R.id.btnPickByTake);
        selectPhoto = commenView.findViewById(R.id.btnPickBySelect);
        carIdImgLayout = findViewById(R.id.car_id_layout);//photoview2 id照片
        carImagLayout = findViewById(R.id.car_img_layout);//photoview1
        photoView = findViewById(R.id.photoView);
        photoView2 = findViewById(R.id.photoView2);
        customHelper = CustomHelper.of(commenView);
        carImagLayout.setOnClickListener(this);
        carIdImgLayout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
            switch (v.getId()){
                case R.id.car_id_layout:
                    imgFlag = 2;
                    showSelectDialog();
                    break;
                case R.id.car_img_layout:
                    imgFlag = 1;
                    showSelectDialog();
                    break;
            }
    }
    public void showSelectDialog(){
        MyDialog dialogHistory = new MyDialog(RequesCompleteActivity.this, R.style.dialog_theme_pick);
        dialogHistory.setTextTitle("确定清除历史搜索缓存");
        dialogHistory.setOnDialogClickListener(new MyDialog.onDialogListener() {

            @Override
            public void onquxiao() {
                //拍照
                customHelper.onClick(takePhoto, getTakePhoto());
            }

            @Override
            public void onqueding() {
                // 从相册选择
                customHelper.onClick(selectPhoto, getTakePhoto());
            }
        });
        dialogHistory.show();
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);

        Bitmap bitmap = BitmapFactory.decodeFile(result.getImage().getCompressPath());
            showImage(bitmap);

    }
    public void showImage(Bitmap bitmap){
            switch (imgFlag){
                case 1:
                    photoView.setVisibility(View.VISIBLE);
                    photoView.setImageBitmap(bitmap);
                    break;
                case 2:
                    photoView2.setVisibility(View.VISIBLE);
                    photoView2.setImageBitmap(bitmap);
                        break;

            }
    }
    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }
}
