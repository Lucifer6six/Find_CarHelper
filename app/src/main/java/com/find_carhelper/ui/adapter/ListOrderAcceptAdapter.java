package com.find_carhelper.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.find_carhelper.R;
import com.find_carhelper.widgets.OnItemClickListeners;


/**
 *列表抢单
 */
public class ListOrderAcceptAdapter extends RecyclerView.Adapter<ListOrderAcceptAdapter.RepairViewHolder> {

    private Context mContext;
    private OnItemClickListeners onItemClickListeners;


    public ListOrderAcceptAdapter(Context context) {
        this.mContext = context;
    }

    public void setOnItemClickListeners(OnItemClickListeners onItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners;
    }

    @NonNull
    @Override
    public ListOrderAcceptAdapter.RepairViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_work_order_list_basic, parent, false);
        return new ListOrderAcceptAdapter.RepairViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListOrderAcceptAdapter.RepairViewHolder holder, final int position) {
        if (onItemClickListeners != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListeners.onItemClick(holder, null, position));
        }

    }


    @Override
    public int getItemCount() {
        return 3;
    }

    static class RepairViewHolder extends RecyclerView.ViewHolder {


        public RepairViewHolder(View itemView) {
            super(itemView);
        }
    }
}
