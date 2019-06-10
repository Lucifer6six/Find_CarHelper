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
import com.find_carhelper.bean.CarBean;
import com.find_carhelper.widgets.OnItemClickListeners;

import org.w3c.dom.Text;

import java.util.List;


/**
 *列表抢单
 */
public class AlreadyCompleteAcceptAdapter extends RecyclerView.Adapter<AlreadyCompleteAcceptAdapter.RepairViewHolder> {

    private Context mContext;
    private OnItemClickListeners onItemClickListeners;
    private List<CarBean> list;

    public AlreadyCompleteAcceptAdapter(Context context, List<CarBean> list) {
        this.mContext = context;
        this.list  = list;
    }

    public void setOnItemClickListeners(OnItemClickListeners onItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners;
    }

    @NonNull
    @Override
    public AlreadyCompleteAcceptAdapter.RepairViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_coopation_already_order_list, parent, false);
        return new AlreadyCompleteAcceptAdapter.RepairViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AlreadyCompleteAcceptAdapter.RepairViewHolder holder, final int position) {
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
                holder.time.setVisibility(View.INVISIBLE);
                holder.money.setText(list.get(position).getRewardAmount());
                String text = list.get(position).getOrderStatusName();
                if (text.equals("已完成")){
                    holder.shenqingBtn.setTextColor(mContext.getResources().getColor(R.color.green));
                }else if (text.equals("审核中")){
                    holder.shenqingBtn.setTextColor(mContext.getResources().getColor(R.color.purpol));
                }else if (text.equals("驳回")){
                    holder.shenqingBtn.setTextColor(mContext.getResources().getColor(R.color.orange_red));
                    holder.time.setVisibility(View.VISIBLE);
                }else{
                    holder.shenqingBtn.setTextColor(mContext.getResources().getColor(R.color.black));
                }

                holder.shenqingBtn.setText(text);
            }

        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    static class RepairViewHolder extends RecyclerView.ViewHolder {
        TextView shenqingBtn;
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
        }
    }
}
