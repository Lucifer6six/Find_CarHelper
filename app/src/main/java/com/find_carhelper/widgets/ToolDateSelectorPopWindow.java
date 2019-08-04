package com.find_carhelper.widgets;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.find_carhelper.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import razerdp.basepopup.BasePopupWindow;

/**
 * author Mzy
 * date 2019/4/19
 * 报修工单筛选POP
 */

public class ToolDateSelectorPopWindow extends BasePopupWindow implements View.OnClickListener, MarkerOrderPopWindow.getdata {
    private MarkerOrderPopWindow.getdata mGetdata;

    public ToolDateSelectorPopWindow(Context context, MarkerOrderPopWindow.getdata getdata) {
        super(context);
        setPopupGravity(Gravity.CENTER);
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
