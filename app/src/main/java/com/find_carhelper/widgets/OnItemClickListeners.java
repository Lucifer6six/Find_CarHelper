package com.find_carhelper.widgets;


import android.support.v7.widget.RecyclerView;

/**
 * RecyclerView 的列表监听
 *
 * @param <T>
 * @author kang gui yang
 */
public interface OnItemClickListeners<T> {

    /**
     * 列表监听
     *
     * @param viewHolder
     * @param data
     * @param position
     */
    void onItemClick(RecyclerView.ViewHolder viewHolder, T data, int position);
}
