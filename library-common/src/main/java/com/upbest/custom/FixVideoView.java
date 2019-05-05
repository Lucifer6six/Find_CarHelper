package com.upbest.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * @author 解决video 边框加载不均匀问题
 */
public class FixVideoView extends VideoView {

    public FixVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }
}