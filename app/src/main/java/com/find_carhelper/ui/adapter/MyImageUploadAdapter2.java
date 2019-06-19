package com.find_carhelper.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.find_carhelper.R;
import com.find_carhelper.bean.photoBean;
import com.find_carhelper.widgets.OnItemClickListeners;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;


/**
 * 列表抢单
 */
public class MyImageUploadAdapter2 extends RecyclerView.Adapter<MyImageUploadAdapter2.RepairViewHolder> {

    private Context mContext;
    private OnItemClickListeners onItemClickListeners;
    List<photoBean> images;
    DisplayImageOptions mOptions;
    public MyImageUploadAdapter2(Context context, List<photoBean> images) {
        this.mContext = context;
        this.images = images;
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context).writeDebugLogs().build();
        ImageLoader.getInstance().init(configuration);
        mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(false).cacheOnDisc(false)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
    }

    public void setOnItemClickListeners(OnItemClickListeners onItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners;
    }

    @NonNull
    @Override
    public MyImageUploadAdapter2.RepairViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_img_list_basic, parent, false);
        return new MyImageUploadAdapter2.RepairViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyImageUploadAdapter2.RepairViewHolder holder, final int position) {


        if (onItemClickListeners != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListeners.onItemClick(holder, null, position));
        }

        holder.imageView.setVisibility(View.VISIBLE);
        if (images.size()>0){
            String url = images.get(position).getPhotoUrl();
            if (!TextUtils.isEmpty(images.get(position).getPhotoUrl())){
                if (!url.contains("/data")){
                    ImageLoader imageLoader = ImageLoader.getInstance();
                imageLoader.displayImage(images.get(position).getPhotoUrl(), holder.imageView);
                }else{
                    Bitmap bitmap = BitmapFactory.decodeFile(images.get(position).getPhotoUrl());
                    holder.imageView.setImageBitmap(bitmap);
                }
                holder.defaultImg.setVisibility(View.GONE);
                holder.title.setVisibility(View.GONE);
            }else{
                holder.title.setText(images.get(position).getName());
            }

        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    static class RepairViewHolder extends RecyclerView.ViewHolder {
        View myView;
        ImageView imageView;
        TextView title;
        ImageView defaultImg;
        public RepairViewHolder(View itemView) {
            super(itemView);
            myView = itemView;
            imageView = (ImageView) itemView.findViewById(R.id.photoView);
            defaultImg =(ImageView)  itemView.findViewById(R.id.img);
            title = (TextView) itemView.findViewById(R.id.text);
        }
    }
}
