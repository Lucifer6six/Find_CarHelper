package com.find_carhelper.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.find_carhelper.R;
import com.find_carhelper.bean.NewsBean;
import com.find_carhelper.widgets.OnItemClickListeners;

import java.util.List;


/**
 *列表抢单
 */
public class SystemNewsAdapter extends RecyclerView.Adapter<SystemNewsAdapter.RepairViewHolder> {

    private Context mContext;
    private OnItemClickListeners onItemClickListeners;
    private List<NewsBean> list;

    public SystemNewsAdapter(Context context, List<NewsBean> list) {
        this.mContext = context;
        this.list = list;
    }

    public void setOnItemClickListeners(OnItemClickListeners onItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners;
    }

    @NonNull
    @Override
    public SystemNewsAdapter.RepairViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.system_news_list_item, parent, false);
        return new SystemNewsAdapter.RepairViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SystemNewsAdapter.RepairViewHolder holder, final int position) {
        if (onItemClickListeners != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListeners.onItemClick(holder, null, position));
        }

        if (list!=null){
            holder.title.setText(list.get(position).getTitle());
            holder.conent.setText(list.get(position).getSummary());
            holder.date.setText(list.get(position).getCreateTime());
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

        public RepairViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.news_title);
            conent =itemView.findViewById(R.id.news_content);
            date = itemView.findViewById(R.id.news_date);
        }
    }
}
