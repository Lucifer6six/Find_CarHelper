package com.find_carhelper.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.find_carhelper.R;
import com.find_carhelper.bean.TeamBean;
import com.find_carhelper.widgets.OnItemClickListeners;

import org.w3c.dom.Text;

import java.util.List;


/**
 *列表抢单
 */
public class MyTeamAdapter extends RecyclerView.Adapter<MyTeamAdapter.RepairViewHolder> {

    private Context mContext;
    private OnItemClickListeners onItemClickListeners;
    List<TeamBean> list;

    public MyTeamAdapter(List<TeamBean> list,Context context) {
        this.mContext = context;
        this.list = list;
    }

    public void setOnItemClickListeners(OnItemClickListeners onItemClickListeners) {
        this.onItemClickListeners = onItemClickListeners;
    }

    @NonNull
    @Override
    public MyTeamAdapter.RepairViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_team_list_basic, parent, false);
        return new MyTeamAdapter.RepairViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyTeamAdapter.RepairViewHolder holder, final int position) {
        if (onItemClickListeners != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListeners.onItemClick(holder, null, position));
        }
        if (list!=null){

            holder.nick_name.setText(list.get(position).getNickname());
            holder.realName.setText(list.get(position).getRealName()+"  "+list.get(position).getPhoneNo());
            holder.inviteTime.setText("邀请时间:"+list.get(position).getInviteTime());
            holder.joinTime.setText("加入时间:"+list.get(position).getJoinTime());

            if (list.get(position).getStatus().equals("已邀请")){
                holder.yi.setVisibility(View.VISIBLE);
                holder.zai.setVisibility(View.INVISIBLE);
                holder.li.setVisibility(View.INVISIBLE);
            }else if (list.get(position).getStatus().equals("在职")){
                holder.yi.setVisibility(View.INVISIBLE);
                holder.zai.setVisibility(View.VISIBLE);
                holder.li.setVisibility(View.INVISIBLE);
            }else{
                holder.yi.setVisibility(View.INVISIBLE);
                holder.zai.setVisibility(View.INVISIBLE);
                holder.li.setVisibility(View.VISIBLE);
            }
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    static class RepairViewHolder extends RecyclerView.ViewHolder {
        private TextView nick_name;
        private TextView realName;
        private TextView inviteTime;
        private TextView joinTime;
        private TextView yi,zai,li;

        public RepairViewHolder(View itemView) {
            super(itemView);

            nick_name = itemView.findViewById(R.id.nick_name);
            realName = itemView.findViewById(R.id.real_name);
            inviteTime = itemView.findViewById(R.id.invite_time);
            joinTime = itemView.findViewById(R.id.join_time);
            yi = itemView.findViewById(R.id.yi);
            zai = itemView.findViewById(R.id.zai);
            li = itemView.findViewById(R.id.li);
        }
    }
}
