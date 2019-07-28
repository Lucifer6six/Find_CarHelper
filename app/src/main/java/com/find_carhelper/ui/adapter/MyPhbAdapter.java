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
import com.find_carhelper.bean.MainPageDataBean;
import com.find_carhelper.widgets.OnItemClickListeners;

import java.util.List;

import cc.ibooker.zcountdownviewlib.CountDownView;


/**
 * 列表抢单
 */
public class MyPhbAdapter extends RecyclerView.Adapter<MyPhbAdapter.RepairViewHolder> {

    private Context mContext;
    private OnItemClickListeners onItemClickListeners;
    private List<MainPageDataBean.ListBean> list;
    private int location = 0;
    private Long startTime;
    private int type;

    public MyPhbAdapter(List<MainPageDataBean.ListBean> list, Context context, int type) {
        this.mContext = context;
        this.list = list;
        startTime = System.currentTimeMillis();
        this.type = type;
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

        if (position == 0) {

            holder.icon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_top1));
            holder.icon.setVisibility(View.VISIBLE);
            holder.no.setVisibility(View.INVISIBLE);

        } else if (position == 1) {

            holder.icon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_top2));
            holder.icon.setVisibility(View.VISIBLE);
            holder.no.setVisibility(View.INVISIBLE);

        } else if (position == 2) {

            holder.icon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.icon_top3));
            holder.icon.setVisibility(View.VISIBLE);
            holder.no.setVisibility(View.INVISIBLE);

        } else {

            holder.icon.setVisibility(View.INVISIBLE);
            holder.no.setVisibility(View.VISIBLE);

        }
        switch (type) {

            case 1:
                holder.start.setText("已回收");
                holder.end.setText("辆");
                break;
            case 2:
                holder.start.setText("已寻得");
                holder.end.setText("辆");
                break;
            case 3:
                holder.start.setText("用时");
                holder.end.setText("");
                break;

        }
        if (list != null) {
            if (list.size() > 0) {

                holder.company.setText(list.get(position).getLeft());
                holder.value.setText(list.get(position).getRight());
                holder.no.setText("" + (position + 1));


            }
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class RepairViewHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView company;
        TextView value;
        TextView no;
        TextView start;
        TextView end;

        public RepairViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            company = itemView.findViewById(R.id.company);
            value = itemView.findViewById(R.id.value);
            no = itemView.findViewById(R.id.no);
            start = itemView.findViewById(R.id.text_start);
            end = itemView.findViewById(R.id.text_end);
        }
    }
}
