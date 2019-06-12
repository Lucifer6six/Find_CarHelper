package com.find_carhelper.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.find_carhelper.R;
import com.find_carhelper.bean.ItemBean;
import com.find_carhelper.widgets.OnItemClickListeners;
import com.jph.takephoto.model.TImage;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * 列表抢单
 */
public class MyImageUploadAdapter extends RecyclerView.Adapter<MyImageUploadAdapter.RepairViewHolder> {

    private Context mContext;
    private OnItemClickListeners onItemClickListeners;
    List<ItemBean> images;

    public MyImageUploadAdapter(Context context, List<ItemBean> images) {
        this.mContext = context;
        this.images = images;
    }

    public void setOnItemClickListeners(OnItemClickListeners onItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners;
    }

    @NonNull
    @Override
    public MyImageUploadAdapter.RepairViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_img_list_basic, parent, false);
        return new MyImageUploadAdapter.RepairViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyImageUploadAdapter.RepairViewHolder holder, final int position) {


        if (onItemClickListeners != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListeners.onItemClick(holder, null, position));
        }

        holder.imageView.setVisibility(View.VISIBLE);
        if (images.size()>0){
            File file = new File((images.get(position).getPath()));
            if (file.exists()){
                Toast.makeText(mContext,""+images.get(0).getPath(),Toast.LENGTH_LONG).show();
                Bitmap bitmap = BitmapFactory.decodeFile(images.get(position).getPath());
                holder.imageView.setImageBitmap(bitmap);
                holder.defaultImg.setVisibility(View.GONE);
                holder.title.setVisibility(View.GONE);
            }

        }
    }


    @Override
    public int getItemCount() {
        return 27;
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
