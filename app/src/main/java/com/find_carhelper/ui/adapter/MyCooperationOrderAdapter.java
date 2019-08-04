package com.find_carhelper.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.find_carhelper.R;
import com.find_carhelper.bean.CarBean;
import com.find_carhelper.ui.activity.RequestInStoreActivity;
import com.find_carhelper.widgets.OnItemClickListeners;

import org.w3c.dom.Text;

import java.util.List;

import cc.ibooker.zcountdownviewlib.CountDownView;


/**
 *列表抢单
 */
public class MyCooperationOrderAdapter extends RecyclerView.Adapter<MyCooperationOrderAdapter.RepairViewHolder> implements View.OnClickListener {

    private Context mContext;
    private OnItemClickListeners onItemClickListeners;
    private List<CarBean> list;
    private int location = 0;
    private Long startTime;
    public MyCooperationOrderAdapter(List<CarBean> list,Context context) {
        this.mContext = context;
        this.list = list;
        startTime = System.currentTimeMillis();
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
        location = position;
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

                    int seconds = Integer.parseInt(list.get(position).getCountdown());

                    if (seconds<5*60*60&&seconds>0){
                        Long endTime = System.currentTimeMillis();
                        int timeLost = (int)(endTime-startTime)/1000;
                        initCountDown(holder.countdownView_,seconds-timeLost);
                        holder.time_layout.setVisibility(View.VISIBLE);
                        holder.countdownView.setVisibility(View.INVISIBLE);
                    }else if (seconds == 0){
                        holder.time_layout.setVisibility(View.INVISIBLE);
                        holder.countdownView.setVisibility(View.INVISIBLE);
                    }else{
                        Long endTime = System.currentTimeMillis();
                        int timeLost = (int)(endTime-startTime)/1000;
                        initCountDown1(holder.countdownView,seconds-timeLost);
                        holder.time_layout.setVisibility(View.INVISIBLE);
                        holder.countdownView.setVisibility(View.VISIBLE);
                    }
                    if (list.get(position).getOrderStatus().equals("DEFER_APPLY")){
                        holder.time_layout.setVisibility(View.INVISIBLE);
                        holder.countdownView.setVisibility(View.INVISIBLE);
                    }
                    holder.time_layout.setTag(position);
                    holder.shenqingBtn.setTag(position);
            }
        }
    }
    public void initCountDown(CountDownView countDownView,int seconds){
        // 基本属性设置
        countDownView.setCountTime(seconds) // 设置倒计时时间戳
                .setHourTvTextColorHex("#ffffff")
                .setHourTvGravity(CountDownView.CountDownViewGravity.GRAVITY_CENTER)
                .setHourTvTextSize(10)
                .setHourTvBackgroundColorHex("#f66633")
                .setHourColonTvSize(11, 0)
                .setHourColonTvTextColorHex("#ffffff")
                .setHourColonTvGravity(CountDownView.CountDownViewGravity.GRAVITY_CENTER)
                .setHourColonTvTextSize(11)

                .setMinuteTvTextColorHex("#ffffff")
                .setMinuteTvGravity(CountDownView.CountDownViewGravity.GRAVITY_CENTER)
                .setMinuteTvTextSize(10)
                .setMinuteTvBackgroundColorHex("#f66633")
                .setMinuteColonTvSize(11, 0)
                .setMinuteColonTvTextColorHex("#ffffff")
                .setMinuteColonTvGravity(CountDownView.CountDownViewGravity.GRAVITY_CENTER)
                .setMinuteColonTvTextSize(11)

                .setSecondTvTextColorHex("#ffffff")
                .setSecondTvGravity(CountDownView.CountDownViewGravity.GRAVITY_CENTER)
                .setSecondTvBackgroundColorHex("#f66633")
                .setSecondTvTextSize(10)
                // 开启倒计时
                .startCountDown()
                // 设置倒计时结束监听
                .setCountDownEndListener(new CountDownView.CountDownEndListener() {
                    @Override
                    public void onCountDownEnd() {
                        //Toast.makeText(mContext, "倒计时结束", Toast.LENGTH_SHORT).show();
                    }
                });



    }
    public void initCountDown1(CountDownView countDownView,int seconds){
        // 基本属性设置
        countDownView.setCountTime(seconds) // 设置倒计时时间戳
                .setHourTvTextColorHex("#f2692e")
                .setHourTvGravity(CountDownView.CountDownViewGravity.GRAVITY_CENTER)
                .setHourTvTextSize(16)
                .setHourTvBackgroundColorHex("#fef0ea")
                .setHourColonTvSize(18, 0)
                .setHourColonTvTextColorHex("#f2692e")
                .setHourColonTvGravity(CountDownView.CountDownViewGravity.GRAVITY_CENTER)
                .setHourColonTvTextSize(21)

                .setMinuteTvTextColorHex("#f2692e")
                .setMinuteTvGravity(CountDownView.CountDownViewGravity.GRAVITY_CENTER)
                .setMinuteTvTextSize(16)
                .setMinuteTvBackgroundColorHex("#fef0ea")
                .setMinuteColonTvSize(18, 0)
                .setMinuteColonTvTextColorHex("#f2692e")
                .setMinuteColonTvGravity(CountDownView.CountDownViewGravity.GRAVITY_CENTER)
                .setMinuteColonTvTextSize(21)

                .setSecondTvTextColorHex("#f2692e")
                .setSecondTvGravity(CountDownView.CountDownViewGravity.GRAVITY_CENTER)
                .setSecondTvBackgroundColorHex("#fef0ea")
                .setSecondTvTextSize(16)
                // 开启倒计时
                .startCountDown()
                // 设置倒计时结束监听
                .setCountDownEndListener(new CountDownView.CountDownEndListener() {
                    @Override
                    public void onCountDownEnd() {
                        //Toast.makeText(mContext, "倒计时结束", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag(); //getTag()获取数据
        if (mOnItemClickListener != null)
        switch (view.getId()){

            case R.id.time_layout:
                mOnItemClickListener.onItemClick(view, MyCooperationOrderAdapter.ViewName.PRACTISE, position);
                break;
            case R.id.request_save:
                mOnItemClickListener.onItemClick(view, MyCooperationOrderAdapter.ViewName.PRACTISE, position);
                break;

        }

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
        LinearLayout time_layout;
        private CountDownView countdownView,countdownView_;

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
            countdownView = itemView.findViewById(R.id.countdownView);
            time_layout = itemView.findViewById(R.id.time_layout);
            countdownView_ = itemView.findViewById(R.id.countdownView_1);
            shenqingBtn.setOnClickListener(MyCooperationOrderAdapter.this);
            time_layout.setOnClickListener(MyCooperationOrderAdapter.this);
        }
    }
    //item里面有多个控件可以点击
    public enum ViewName {
        ITEM,
        PRACTISE,
        ORDERS;
    }


    public interface OnItemClickListener {
        void onItemClick(View v, MyCooperationOrderAdapter.ViewName viewName, int position);

        void onItemLongClick(View v);
    }

        private  OnItemClickListener mOnItemClickListener;//声明自定义的接口

    //定义方法并暴露给外面的调用者
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
