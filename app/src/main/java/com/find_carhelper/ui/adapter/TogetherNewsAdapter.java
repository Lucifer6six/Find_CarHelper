package com.find_carhelper.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.find_carhelper.R;
import com.find_carhelper.bean.NewsBean;
import com.find_carhelper.widgets.OnItemClickListeners;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;


/**
 *列表抢单
 */
public class TogetherNewsAdapter extends RecyclerView.Adapter<TogetherNewsAdapter.RepairViewHolder> {

    private Context mContext;
    private OnItemClickListeners onItemClickListeners;
    private List<NewsBean> list;
    DisplayImageOptions mOptions;
    public TogetherNewsAdapter(Context context, List<NewsBean> list) {
        this.mContext = context;
        this.list = list;
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
    public TogetherNewsAdapter.RepairViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.system_news_list_item, parent, false);
        return new TogetherNewsAdapter.RepairViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TogetherNewsAdapter.RepairViewHolder holder, final int position) {
        if (onItemClickListeners != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListeners.onItemClick(holder, null, position));
        }

        if (list!=null){
            holder.title.setText(list.get(position).getTitle());
            holder.conent.setText(list.get(position).getSummary());
            holder.date.setText(list.get(position).getCreateTime());
            if (list.get(position).getIconUrl()!=null){

                ImageLoader imageLoader = ImageLoader.getInstance();
                imageLoader.displayImage(list.get(position).getIconUrl(), holder.news_img);
            }
        }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    static class RepairViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView conent;
        private TextView date;
        private ImageView news_img;
        public RepairViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.news_title);
            conent =itemView.findViewById(R.id.news_content);
            date = itemView.findViewById(R.id.news_date);
            news_img = itemView.findViewById(R.id.news_img);
        }
    }
}
