package com.find_carhelper.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.find_carhelper.R;
import com.find_carhelper.bean.FindCarCooperatingBean;
import com.find_carhelper.bean.FindCarInfo;
import com.find_carhelper.widgets.OnItemClickListeners;

import java.util.List;


/**
 * 列表抢单
 */
public class FindCarCompletingOrderAdapter extends RecyclerView.Adapter<FindCarCompletingOrderAdapter.RepairViewHolder> implements View.OnClickListener {

    private Context mContext;
    private OnItemClickListeners onItemClickListeners;
    private List<FindCarInfo> list;

    public FindCarCompletingOrderAdapter(Context context, List<FindCarInfo> list) {
        this.mContext = context;
        this.list = list;
    }

    public void setOnItemClickListeners(OnItemClickListeners onItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners;
    }

    @NonNull
    @Override
    public FindCarCompletingOrderAdapter.RepairViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_find_car_coptering_list, parent, false);
        return new FindCarCompletingOrderAdapter.RepairViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FindCarCompletingOrderAdapter.RepairViewHolder holder, final int position) {
        if (onItemClickListeners != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListeners.onItemClick(holder, null, position));
        }
        holder.itemView.setOnClickListener(FindCarCompletingOrderAdapter.this);
        holder.acept_order.setTag(position);
        holder.itemView.setTag(position);
        if (list != null) {
            if (list.size() > 0) {

                holder.carType.setText(list.get(position).getVehicleModel());
                holder.carId.setText(list.get(position).getLpn());
                holder.carNo.setText("车架号  " + list.get(position).getVin());
                holder.address_tips.setText(list.get(position).getRegion()
                        + "/" + list.get(position).getPartya()
                );
                holder.money.setText(list.get(position).getRewardAmount());
                holder.accept_orders.setText(list.get(position).getOrderMemberCount());
            }

        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View view) {

        int position = (int) view.getTag(); //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (view.getId()) {
                case R.id.acept_order:
                    mOnItemClickListener.onItemClick(view, ViewName.PRACTISE, position);
                    break;
                default:
                    mOnItemClickListener.onItemClick(view, ViewName.ITEM, position);
                    break;
            }
        }
    }

    public class RepairViewHolder extends RecyclerView.ViewHolder {
        TextView carType;
        TextView carId;
        TextView carNo;
        TextView address_tips;
        TextView money;
        Button acept_order;
        TextView accept_orders;
        public RepairViewHolder(View itemView) {
            super(itemView);
            carType = itemView.findViewById(R.id.car_type);
            carId = itemView.findViewById(R.id.car_id);
            carNo = itemView.findViewById(R.id.car_no);
            address_tips = itemView.findViewById(R.id.address_tips);
            money = itemView.findViewById(R.id.money);
            acept_order = itemView.findViewById(R.id.acept_order);
            accept_orders = itemView.findViewById(R.id.accept_orders);
            acept_order.setOnClickListener(FindCarCompletingOrderAdapter.this);
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
