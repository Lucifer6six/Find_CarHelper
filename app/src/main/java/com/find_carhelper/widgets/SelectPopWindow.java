package com.find_carhelper.widgets;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.TextView;

import com.bigkoo.pickerview.view.OptionsPickerView;
import com.find_carhelper.R;

import java.text.SimpleDateFormat;

import razerdp.basepopup.BasePopupWindow;

/**
 * author Mzy
 * date 2019/4/19
 * 报修工单筛选POP
 */

public class SelectPopWindow extends BasePopupWindow implements View.OnClickListener,MarkerOrderPopWindow.getdata {
private MarkerOrderPopWindow.getdata mGetdata;
private TextView orderStatus;
private TextView timeStart,timeEnd;
private SimpleDateFormat mSimpleDateFormat;
private OptionsPickerView mOptionsPickerView;
    private OptionsPickerView pvOptions;
    public SelectPopWindow(Context context, MarkerOrderPopWindow.getdata getdata) {
        super(context);
        setPopupGravity(Gravity.CENTER|Gravity.CENTER);
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
        return createPopupById(R.layout.select_pop);
    }

    private void bindEvent() {
        findViewById(R.id.cancel).setOnClickListener(this);
        findViewById(R.id.accept).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.accept:
                mGetdata.getdatas("1");
                dismiss();
                break;
        }
    }

    @Override
    public void getdatas(String str) {

    }

}
