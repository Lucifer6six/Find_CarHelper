package com.find_carhelper.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.find_carhelper.R;
import com.find_carhelper.bean.CarBean;
import com.find_carhelper.ui.activity.GuideActivity;
import com.find_carhelper.widgets.OnItemClickListeners;

import org.w3c.dom.Text;

import java.util.List;

import cc.ibooker.zcountdownviewlib.CountDownView;


/**
 *列表抢单
 */
public class ListOrderAcceptAdapter extends RecyclerView.Adapter<ListOrderAcceptAdapter.RepairViewHolder> {

    private Context mContext;
    private OnItemClickListeners onItemClickListeners;

    private List<CarBean> list;

    public ListOrderAcceptAdapter(Context context,List<CarBean> list) {
        this.mContext = context;
        this.list = list;
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

            if (list!=null){
                if (list.size()>0){

                    holder.car_type.setText(list.get(position).getVehicleModel());
                    holder.car_money.setText(list.get(position).getRewardAmount());
                    holder.address.setText(list.get(position).getRegion());
                    holder.gpsTips.setText(list.get(position).getPositioningMethod());
                    holder.keyTips.setText(list.get(position).getHasKey());
                    holder.groupTips.setText(list.get(position).getPartya());
                   if (!TextUtils.isEmpty(list.get(position).getCountdown())){
                       holder.acept_order.setVisibility(View.INVISIBLE);
                       holder.countdownView.setVisibility(View.VISIBLE);
                       initCountDown(holder.countdownView,3666);
                   }else{
                       holder.acept_order.setVisibility(View.VISIBLE);
                       holder.countdownView.setVisibility(View.INVISIBLE);
                   }
                   if (list.get(position).getStatus().equals("已被抢")){
                       holder.car_money.setVisibility(View.INVISIBLE);
                       holder.car_status.setVisibility(View.VISIBLE);
                   }else{
                       holder.car_money.setVisibility(View.VISIBLE);
                       holder.car_status.setVisibility(View.INVISIBLE);
                   }

                }
            }
    }

    public void initCountDown(CountDownView countDownView,int seconds){
        // 基本属性设置
        countDownView.setCountTime(3663) // 设置倒计时时间戳
                .setHourTvTextColorHex("#f2692e")
                .setHourTvGravity(CountDownView.CountDownViewGravity.GRAVITY_CENTER)
                .setHourTvTextSize(16)
                .setHourTvBackgroundColorHex("#fef0ea")
                .setHourColonTvSize(18, 0)
                .setHourColonTvTextColorHex("#FFFFFF")
                .setHourColonTvGravity(CountDownView.CountDownViewGravity.GRAVITY_CENTER)
                .setHourColonTvTextSize(21)

                .setMinuteTvTextColorHex("#f2692e")
                .setMinuteTvTextSize(16)
                .setMinuteTvBackgroundColorHex("#fef0ea")
                .setMinuteColonTvSize(18, 0)
                .setMinuteColonTvTextColorHex("#FFFFFF")
                .setMinuteColonTvTextSize(21)

                .setSecondTvTextColorHex("#f2692e")
                .setSecondTvBackgroundColorHex("#fef0ea")
                .setSecondTvTextSize(16)

//      .setTimeTvWH(18, 40)
//      .setColonTvSize(30)

                // 开启倒计时
                .startCountDown()

                // 设置倒计时结束监听
                .setCountDownEndListener(new CountDownView.CountDownEndListener() {
                    @Override
                    public void onCountDownEnd() {
                        Toast.makeText(mContext, "倒计时结束", Toast.LENGTH_SHORT).show();
                    }
                })
        ;


    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    static class RepairViewHolder extends RecyclerView.ViewHolder {

        private TextView car_type;
        private TextView car_money;
        private TextView address;
        private TextView gpsTips;
        private TextView keyTips;
        private TextView groupTips;
        private CountDownView countdownView;
        private Button acept_order;
        private TextView car_status;
        public RepairViewHolder(View itemView) {
            super(itemView);
            car_type = itemView.findViewById(R.id.car_type);
            car_money = itemView.findViewById(R.id.car_money);
            address = itemView.findViewById(R.id.address);
            gpsTips = itemView.findViewById(R.id.tips1);
            keyTips = itemView.findViewById(R.id.tips2);
            groupTips = itemView.findViewById(R.id.tips3);
            countdownView = itemView.findViewById(R.id.countdownView);
            acept_order = itemView.findViewById(R.id.acept_order);
            car_status = itemView.findViewById(R.id.car_status);
        }


    }
}
