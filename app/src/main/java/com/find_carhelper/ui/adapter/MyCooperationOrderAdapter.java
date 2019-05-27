package com.find_carhelper.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.find_carhelper.R;
import com.find_carhelper.ui.activity.RequestInStoreActivity;
import com.find_carhelper.widgets.OnItemClickListeners;

import org.w3c.dom.Text;


/**
 *列表抢单
 */
public class MyCooperationOrderAdapter extends RecyclerView.Adapter<MyCooperationOrderAdapter.RepairViewHolder> implements View.OnClickListener {

    private Context mContext;
    private OnItemClickListeners onItemClickListeners;


    public MyCooperationOrderAdapter(Context context) {
        this.mContext = context;
    }

    public void setOnItemClickListeners(OnItemClickListeners onItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners;
    }

    @NonNull
    @Override
    public MyCooperationOrderAdapter.RepairViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_coopation_order_list, parent, false);
        return new MyCooperationOrderAdapter.RepairViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyCooperationOrderAdapter.RepairViewHolder holder, final int position) {
        if (onItemClickListeners != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListeners.onItemClick(holder, null, position));
        }

    }


    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    public void onClick(View view) {
        mContext.startActivity(new Intent(mContext, RequestInStoreActivity.class));
    }

    public class RepairViewHolder extends RecyclerView.ViewHolder {

        Button shenqingBtn;
        TextView carType;
        TextView carId;
        TextView carNo;
        TextView tips1;
        TextView money;
        TextView time;
        public RepairViewHolder(View itemView) {
            super(itemView);
            shenqingBtn = itemView.findViewById(R.id.request_save);
            carType = itemView.findViewById(R.id.car_type);
            carId = itemView.findViewById(R.id.car_id);
            carNo = itemView.findViewById(R.id.car_no);
            tips1 = itemView.findViewById(R.id.tips1);
            money = itemView.findViewById(R.id.money);
            time = itemView.findViewById(R.id.time);

            shenqingBtn.setOnClickListener(MyCooperationOrderAdapter.this);
        }
    }
}
