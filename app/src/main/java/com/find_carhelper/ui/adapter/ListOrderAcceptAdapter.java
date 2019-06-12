package com.find_carhelper.ui.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
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
import java.util.Timer;
import java.util.TimerTask;

import cc.ibooker.zcountdownviewlib.CountDownView;


/**
 * 列表抢单
 */
public class ListOrderAcceptAdapter extends RecyclerView.Adapter<ListOrderAcceptAdapter.RepairViewHolder> implements View.OnClickListener {

    private Context mContext;

    private List<CarBean> list;
    private final SparseArray<RepairViewHolder> mCountdownVHList;
    Long startTime ;
    public ListOrderAcceptAdapter(Context context, List<CarBean> list) {
        this.mContext = context;
        this.list = list;
        mCountdownVHList = new SparseArray<>();
        startTime = System.currentTimeMillis();
        //startRefreshTime();
    }

//    public void setOnItemClickListeners(OnItemClickListeners onItemClickListeners) {
//        this.onItemClickListeners = onItemClickListeners;
//    }
    @NonNull
    @Override
    public ListOrderAcceptAdapter.RepairViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_work_order_list_basic, parent, false);
        return new ListOrderAcceptAdapter.RepairViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ListOrderAcceptAdapter.RepairViewHolder holder, final int position) {
//        if (onItemClickListeners != null) {
//            holder.itemView.setOnClickListener(v -> onItemClickListeners.onItemClick(holder, null, position));
//        }
        CarBean curItemInfo = list.get(position);
        holder.bindData(curItemInfo,position);

        // 处理倒计时
//        if (Integer.parseInt(curItemInfo.getCountdown()) > 0) {
//            synchronized (mCountdownVHList) {
//                mCountdownVHList.put(curItemInfo.getId(), holder);
//            }
//        }

    }

    public void initCountDown(CountDownView countDownView, int seconds) {
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
                });

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
                case R.id.list_orders:
                    mOnItemClickListener.onItemClick(view, ViewName.PRACTISE, position);
                    break;
                case R.id.acept_order:
                    mOnItemClickListener.onItemClick(view, ViewName.ORDERS, position);

                    break;
                default:
                    mOnItemClickListener.onItemClick(view, ViewName.ITEM, position);
                    break;
            }
        }
    }

    public class RepairViewHolder extends RecyclerView.ViewHolder {

        private TextView car_type;
        private TextView car_money;
        private TextView address;
        private TextView gpsTips;
        private TextView keyTips;
        private TextView groupTips;
        private CountDownView countdownView;
        private CarBean mItemInfo;
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
            itemView.setOnClickListener(ListOrderAcceptAdapter.this);
            acept_order.setOnClickListener(ListOrderAcceptAdapter.this);
        }

        public void bindData(CarBean itemInfo,int position) {
            mItemInfo = itemInfo;
            car_type.setText(mItemInfo.getVehicleModel());
            car_money.setText(mItemInfo.getRewardAmount());
            address.setText(mItemInfo.getRegion());
            gpsTips.setText(mItemInfo.getPositioningMethod());
            keyTips.setText(mItemInfo.getHasKey());
            groupTips.setText(mItemInfo.getPartya());
            if (!TextUtils.isEmpty(mItemInfo.getCountdown())&&!mItemInfo.getCountdown().equals("0")) {
                acept_order.setVisibility(View.INVISIBLE);
                countdownView.setVisibility(View.VISIBLE);
                initCountDown(countdownView, Integer.parseInt(mItemInfo.getCountdown()));
            } else {
                acept_order.setVisibility(View.VISIBLE);
                countdownView.setVisibility(View.INVISIBLE);
            }
            if (list.get(position).getStatus().equals("已被抢")) {
                car_money.setVisibility(View.INVISIBLE);
                car_status.setVisibility(View.VISIBLE);
            } else {
                car_money.setVisibility(View.VISIBLE);
                car_status.setVisibility(View.INVISIBLE);
            }
            itemView.setTag(position);
            acept_order.setTag(position);
            if (!TextUtils.isEmpty(mItemInfo.getCountdown())&&!mItemInfo.getCountdown().equals("0"))
            refreshTime();
        }

        public void refreshTime() {

            if (null == mItemInfo || Integer.parseInt(mItemInfo.getCountdown()) <= 0) return;
            Long endTime = System.currentTimeMillis();
            Long lost = (endTime-startTime)/1000;

            initCountDown(countdownView,(int)(Long.parseLong(mItemInfo.getCountdown())-lost));
            //countdownView.updateShow(mItemInfo.getEndTime() - curTimeMillis);
        }

        public CarBean getBean() {
            return mItemInfo;
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
