package com.upbest.custom;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.upbest.rxlibrary.R;


/**
 * <pre>
 * 文件名：	MyProgressDialog.java
 * 作　者：	lijianqiang
 * 描　述：	自定义dialog
 * @author kang gui yang
 * </pre>
 */
public class SimpleProgressDialog extends Dialog {

    private boolean needCancel = false;

    private TextView textView;

    /**
     * @param context
     * @param needCancel
     */
    public SimpleProgressDialog(Context context, boolean needCancel) {
        super(context, R.style.simple_dialog);
        getWindow().setContentView(R.layout.layout_simpleprogressdialog);

        textView = findViewById(R.id.progress_msg);
        textView.setVisibility(View.GONE);

        setDialogBG();

        this.needCancel = needCancel;
    }

    private void setDialogBG() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        // 模糊度
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,
                WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
        // 透明度，
        lp.alpha = 1.0f;
        // 黑暗度为
        lp.dimAmount = 0.5f;
    }

    public void setPercent(String percent) {
        textView.setText(percent);
    }

    /**
     * 设置提示文本
     *
     * @param text
     */
    public void setText(String text) {
        if (textView != null) {
            textView.setText(text);
        }
    }

    /**
     * @param context
     * @param needCancel
     */
    public SimpleProgressDialog(String msg, Context context, boolean needCancel) {
        super(context, R.style.simple_dialog);
        getWindow().setContentView(R.layout.layout_simpleprogressdialog);

        this.needCancel = needCancel;

        textView = (TextView) findViewById(R.id.progress_msg);
        textView.setText(msg);

        setDialogBG();
    }

    /**
     * @see android.app.Dialog#onKeyDown(int, android.view.KeyEvent)
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU
                || keyCode == KeyEvent.KEYCODE_SEARCH) {
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (needCancel) {
                // if (HttpObservable.getInstance().countObservers() > 0) {
                // HttpObservable.getInstance().noyifyHttp(
                // FusionCode.MSG_REQUEST_CANCLE);
                // }
                dismiss();
            } else {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
