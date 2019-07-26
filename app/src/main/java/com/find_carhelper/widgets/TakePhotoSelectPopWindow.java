package com.find_carhelper.widgets;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;

import com.find_carhelper.R;

import razerdp.basepopup.BasePopupWindow;

/**
 * author Mzy
 * date 2019/4/11
 * 点击Marker弹出pop
 */

public class TakePhotoSelectPopWindow extends BasePopupWindow implements View.OnClickListener {
    private  getdata mGetdata;
    public TakePhotoSelectPopWindow(Context context, getdata getdata) {
        super(context);
        setPopupGravity(Gravity.BOTTOM|Gravity.CENTER);
        bindEvent();
        this.mGetdata = getdata;
    }
    @Override
    protected Animation onCreateShowAnimation() {
        return getTranslateVerticalAnimation(1f, 0, 200);
    }

    @Override
    protected Animation onCreateDismissAnimation() {
        return getTranslateVerticalAnimation(0, 1f, 200);
    }

    @Override
    public View onCreateContentView() {
        return createPopupById(R.layout.auth_pop);
    }

    private void bindEvent() {
    }

    @Override
    public void onClick(View v) {

    }
    public interface getdata{
        void getdatas(String str);
    }

}
