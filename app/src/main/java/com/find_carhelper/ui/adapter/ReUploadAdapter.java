package com.find_carhelper.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.find_carhelper.R;
import com.find_carhelper.bean.ReasonBean;
import com.find_carhelper.bean.photoBean;
import com.find_carhelper.widgets.OnItemClickListeners;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.w3c.dom.Text;

import java.util.List;


/**
 * 列表抢单
 */
public class ReUploadAdapter extends RecyclerView.Adapter<ReUploadAdapter.RepairViewHolder> implements View.OnClickListener {

    private Context mContext;
    private OnItemClickListeners onItemClickListeners;
    List<ReasonBean> images;
    DisplayImageOptions mOptions;

    public ReUploadAdapter(Context context, List<ReasonBean> images) {
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
    public ReUploadAdapter.RepairViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_img_re_upload, parent, false);
        return new ReUploadAdapter.RepairViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ReUploadAdapter.RepairViewHolder holder, final int position) {


        if (onItemClickListeners != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListeners.onItemClick(holder, null, position));
        }

        //holder.imageView.setVisibility(View.VISIBLE);
        if (images.size() > 0) {
//            String url = images.get(position).getPhotoUrl();
//            if (!TextUtils.isEmpty(images.get(position).getPhotoUrl())){
//                if (!url.contains("/data")){
//                    ImageLoader imageLoader = ImageLoader.getInstance();
//                imageLoader.displayImage(images.get(position).getPhotoUrl(), holder.imageView);
//                }else{
//                    Bitmap bitmap = BitmapFactory.decodeFile(images.get(position).getPhotoUrl());
//                    holder.imageView.setImageBitmap(bitmap);
//                }
//                holder.defaultImg.setVisibility(View.GONE);
//                holder.title.setVisibility(View.GONE);
//            }else{
//                holder.title.setText(images.get(position).getName());
//            }
            holder.title.setText(images.get(position).getName());
            holder.photoName.setText(images.get(position).getName());
            holder.reasonView.setText("驳回原因：" + images.get(position).getReason());
            holder.btnPickByTake.setTag(position);
            holder.btnPickByTake2.setTag(position);
            holder.imageView.setVisibility(View.GONE);
            holder.defaultImg.setVisibility(View.VISIBLE);
            holder.photoName.setVisibility(View.VISIBLE);
            holder.imageView2.setVisibility(View.GONE);
            holder.defaultImg2.setVisibility(View.VISIBLE);
            holder.photoName2.setVisibility(View.VISIBLE);
            if (images.size() > 0) {
                String url = images.get(position).getPhotoUrl();
                if (!TextUtils.isEmpty(images.get(position).getPhotoUrl())) {
                    if (!url.contains("/data")) {
                      //  ImageLoader imageLoader = ImageLoader.getInstance();
                      //  imageLoader.displayImage(images.get(position).getPhotoUrl(), holder.imageView);

                    } else {
                        Bitmap bitmap = BitmapFactory.decodeFile(images.get(position).getPhotoUrl());
                        holder.imageView.setImageBitmap(bitmap);
                        holder.imageView.setVisibility(View.VISIBLE);
                        holder.defaultImg.setVisibility(View.GONE);
                        holder.photoName.setVisibility(View.GONE);
                    }

                }
                String url2 = images.get(position).getPhotoUrl2();
                if (!TextUtils.isEmpty(url2)) {
                    if (!url.contains("/data")) {
                     //   ImageLoader imageLoader = ImageLoader.getInstance();
                     //   imageLoader.displayImage(url2, holder.imageView2);
                    } else {
                        Bitmap bitmap = BitmapFactory.decodeFile(url2);
                        holder.imageView2.setImageBitmap(bitmap);
                        holder.imageView2.setVisibility(View.VISIBLE);
                        holder.defaultImg2.setVisibility(View.GONE);
                        holder.photoName2.setVisibility(View.GONE);
                    }

                }
            }

        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag(); //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (view.getId()) {
                case R.id.btnPickByTake:
                    mOnItemClickListener.onItemClick(view, ViewName.PRACTISE, position);
                    break;
                case R.id.btnPickByTake2:
                    mOnItemClickListener.onItemClick(view, ViewName.PRACTISE, position);
                    break;
                default:
                    mOnItemClickListener.onItemClick(view, ViewName.ITEM, position);
                    break;
            }
        }
    }

    public class RepairViewHolder extends RecyclerView.ViewHolder {
        View myView;
        ImageView imageView, imageView2;
        TextView title;
        TextView photoName;
        TextView photoName2;
        ImageView defaultImg;
        ImageView defaultImg2;
        TextView checkPhoto;
        TextView reasonView;
        CardView btnPickByTake;
        CardView btnPickByTake2;

        public RepairViewHolder(View itemView) {
            super(itemView);
            myView = itemView;
            imageView = itemView.findViewById(R.id.photoView);
            imageView2 = itemView.findViewById(R.id.photoView2);
            defaultImg = itemView.findViewById(R.id.img);
            defaultImg2 = itemView.findViewById(R.id.img2);
            title = itemView.findViewById(R.id.title);
            photoName = itemView.findViewById(R.id.text);
            photoName2 = itemView.findViewById(R.id.text2);
            checkPhoto = itemView.findViewById(R.id.check_photo);
            reasonView = itemView.findViewById(R.id.reason);
            btnPickByTake = itemView.findViewById(R.id.btnPickByTake);
            btnPickByTake2 = itemView.findViewById(R.id.btnPickByTake2);
            btnPickByTake.setOnClickListener(ReUploadAdapter.this);
            btnPickByTake2.setOnClickListener(ReUploadAdapter.this);
        }
    }

    //item里面有多个控件可以点击
    public enum ViewName {
        ITEM,
        PRACTISE,
        ORDERS;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, ViewName viewName, int position);

        void onItemLongClick(View v);
    }

    private OnItemClickListener mOnItemClickListener;//声明自定义的接口

    //定义方法并暴露给外面的调用者
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
