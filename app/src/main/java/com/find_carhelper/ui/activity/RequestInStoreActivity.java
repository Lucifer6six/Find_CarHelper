package com.find_carhelper.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.find_carhelper.R;
import com.find_carhelper.bean.ItemBean;
import com.find_carhelper.ui.adapter.MyImageUploadAdapter;
import com.find_carhelper.utils.CustomHelper;
import com.find_carhelper.widgets.OnItemClickListeners;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import java.util.ArrayList;
import java.util.List;

public class RequestInStoreActivity extends TakePhotoActivity implements OnItemClickListeners {
    private RecyclerView recycleListView;
    private MyImageUploadAdapter mMyImageUploadAdapter;
    private CustomHelper customHelper;
    private View commenView;
    private Button takePhoto;
    private int position;
    ArrayList<TImage> images;
    private List<ItemBean> list;
    private List<String> updateList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_in_store);
        initViews();
    }


    private void initViews() {
        list = new ArrayList<ItemBean>();
        initImages();
        commenView = LayoutInflater.from(this).inflate(R.layout.common_layout,null);
        takePhoto = commenView.findViewById(R.id.btnPickByTake);
        customHelper = CustomHelper.of(commenView);
        recycleListView = findViewById(R.id.img_list);
        initAdapter();
    }

    public void initImages(){
        Log.e("TAG","?????");
        for(int i=0; i<27; i++){
            ItemBean images = new ItemBean();
            images.setPosition(i);
            images.setPath("");
            images.setName(i+"");
            list.add(images);
        }

    }

    private void initAdapter(){
        mMyImageUploadAdapter= new MyImageUploadAdapter(this,list);
        mMyImageUploadAdapter.setOnItemClickListeners(this);
        recycleListView.setLayoutManager(new GridLayoutManager(this,2));
        recycleListView.setHasFixedSize(true);
        recycleListView.setAdapter(mMyImageUploadAdapter);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);


        if (list.size()>0){
            list.get(position).setPath(result.getImage().getCompressPath());
          }

       //
        mMyImageUploadAdapter.notifyDataSetChanged();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder viewHolder, Object data, int position) {
        Toast.makeText(getApplicationContext(),"position",Toast.LENGTH_LONG).show();
        //takePhoto.performClick();
        this.position = position;
        customHelper.onClick(takePhoto,getTakePhoto());
    }

}
