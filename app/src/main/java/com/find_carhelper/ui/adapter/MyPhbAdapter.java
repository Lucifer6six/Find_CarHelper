package com.find_carhelper.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.find_carhelper.R;
import com.find_carhelper.bean.CarBean;
import com.find_carhelper.widgets.OnItemClickListeners;

import java.util.List;

import cc.ibooker.zcountdownviewlib.CountDownView;


/**
 *列表抢单
 */
public class MyPhbAdapter extends RecyclerView.Adapter<MyPhbAdapter.RepairViewHolder> {

    private Context mContext;
    private OnItemClickListeners onItemClickListeners;
    private List<CarBean> list;
    private int location = 0;
    private Long startTime;
    public MyPhbAdapter(List<CarBean> list, Context context) {
        this.mContext = context;
        this.list = list;
        startTime = System.currentTimeMillis();
    }

    public void setOnItemClickListeners(OnItemClickListeners onItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners;
    }

    @NonNull
    @Override
    public MyPhbAdapter.RepairViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_phb_list, parent, false);
        return new MyPhbAdapter.RepairViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyPhbAdapter.RepairViewHolder holder, final int position) {
        location = position;
        if (onItemClickListeners != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListeners.onItemClick(holder, null, position));
        }

        if (position == 0){

            holder.icon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_top1));

        }else if (position == 1){

            holder.icon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_top2));

        }else
            holder.icon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_top3));


        if (list!=null){
            if (list.size()>0){


            }
        }
    }


    @Override
    public int getItemCount() {
        return 3;
    }


    public class RepairViewHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView company;
        TextView value;

        public RepairViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            company = itemView.findViewById(R.id.company);
            value = itemView.findViewById(R.id.value);
        }
    }
}
