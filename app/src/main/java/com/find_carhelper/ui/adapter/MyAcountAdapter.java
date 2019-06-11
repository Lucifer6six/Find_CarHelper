package com.find_carhelper.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.find_carhelper.R;
import com.find_carhelper.bean.BillListBean;
import com.find_carhelper.widgets.OnItemClickListeners;

import org.w3c.dom.Text;

import java.util.List;
import java.util.zip.Inflater;


/**
 *列表抢单
 */
public class MyAcountAdapter extends RecyclerView.Adapter<MyAcountAdapter.RepairViewHolder> {

    private Context mContext;
    private OnItemClickListeners onItemClickListeners;
    private List<BillListBean> list;

    public MyAcountAdapter( List<BillListBean> list,Context context) {
        this.mContext = context;
        this.list = list;
    }

    public void setOnItemClickListeners(OnItemClickListeners onItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners;
    }

    @NonNull
    @Override
    public MyAcountAdapter.RepairViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_acount_list, parent, false);
        return new MyAcountAdapter.RepairViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAcountAdapter.RepairViewHolder holder, final int position) {
        if (onItemClickListeners != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListeners.onItemClick(holder, null, position));
        }
        if (list!=null){
          if (list.size()>0){
               holder.year.setText(list.get(position).getYear());
               holder.money.setText("¥"+list.get(position).getTotalAmount());
              List<BillListBean.list> lists = list.get(position).getList();
               for (int i= 0;i<lists.size();i++){
                   View  listView =  LayoutInflater.from(mContext).inflate(R.layout.item_my_acount_list_item, null);

                   TextView month = listView.findViewById(R.id.month);
                   TextView time1 = listView.findViewById(R.id.time1);
                   TextView time2 = listView.findViewById(R.id.time2);
                   TextView money = listView.findViewById(R.id.money);
                   TextView status = listView.findViewById(R.id.status);
                   month.setText(lists.get(i).getMonth()+"月");
                   time1.setText("申请时间"+lists.get(i).getApplyTime());
                   time2.setText("到账时间"+lists.get(i).getPaymentTime());
                   money.setText("￥"+lists.get(i).getAmount());
                   String str = lists.get(i).getStatus();
                   if (str.equals("提现中")){
                       status.setTextColor(mContext.getResources().getColor(R.color.text_money_orange));
                   }else {
                       status.setTextColor(mContext.getResources().getColor(R.color.text_color_gray));
                   }
                   status.setText(str);
                   holder.room.addView(listView);
              }
          }

        }


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    static class RepairViewHolder extends RecyclerView.ViewHolder {

        private TextView year;
        private TextView money;
        private LinearLayout room;

        public RepairViewHolder(View itemView) {
            super(itemView);
            year = itemView.findViewById(R.id.year);
            money = itemView.findViewById(R.id.money);
            room = itemView.findViewById(R.id.room);
        }
    }
}
