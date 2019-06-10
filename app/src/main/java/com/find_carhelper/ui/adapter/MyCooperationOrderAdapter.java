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
import com.find_carhelper.bean.CarBean;
import com.find_carhelper.ui.activity.RequestInStoreActivity;
import com.find_carhelper.widgets.OnItemClickListeners;

import org.w3c.dom.Text;

import java.util.List;


/**
 *列表抢单
 */
public class MyCooperationOrderAdapter extends RecyclerView.Adapter<MyCooperationOrderAdapter.RepairViewHolder> implements View.OnClickListener {

    private Context mContext;
    private OnItemClickListeners onItemClickListeners;
    private List<CarBean> list;

    public MyCooperationOrderAdapter(List<CarBean> list,Context context) {
        this.mContext = context;
        this.list = list;
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
        if (list!=null){

            if (list.size()>0){

                    holder.carType.setText(list.get(position).getVehicleModel());
                    holder.status.setText(list.get(position).getAssignTask());
                    holder.carId.setText(list.get(position).getLpn());
                    holder.carNo.setText("车架号  "+list.get(position).getVin());
                    holder.tips1.setText(list.get(position).getRegion()+
                                    "/"+list.get(position).getPositioningMethod()+
                            "/"+list.get(position).getHasKey()+"/"+list.get(position).getPartya()
                            );
                    holder.money.setText(list.get(position).getRewardAmount());

            }

        }


    }


    @Override
    public int getItemCount() {
        return list.size();
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
        TextView status;
        public RepairViewHolder(View itemView) {
            super(itemView);
            shenqingBtn = itemView.findViewById(R.id.request_save);
            carType = itemView.findViewById(R.id.car_type);
            carId = itemView.findViewById(R.id.car_id);
            carNo = itemView.findViewById(R.id.car_no);
            tips1 = itemView.findViewById(R.id.tips1);
            money = itemView.findViewById(R.id.money);
            time = itemView.findViewById(R.id.time);
            status = itemView.findViewById(R.id.status);
            shenqingBtn.setOnClickListener(MyCooperationOrderAdapter.this);
        }
    }
}
