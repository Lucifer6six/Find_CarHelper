package com.find_carhelper.widgets;


import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;

import com.find_carhelper.R;


public class MyDialog extends Dialog {

    private TextView textView_content, queding, quxiao, line;
    private CheckBox mDontShowBox;
    private View view, mDontShowBoxContainer;

    public interface onDialogListener {

        void onqueding();

        void onquxiao();
    }

    onDialogListener dialogListener;

    public void setOnDialogClickListener(onDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    public MyDialog(Context context, int theme) {
        super(context, R.style.dialog_theme_pick);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.dialog_content, null);
        textView_content = (TextView) view.findViewById(R.id.title_text);
        queding = (TextView) view.findViewById(R.id.queding);
        quxiao = (TextView) view.findViewById(R.id.quxiao);
        line = (TextView) view.findViewById(R.id.line);
        mDontShowBoxContainer = view.findViewById(R.id.dont_show_container);
        mDontShowBox = (CheckBox) view.findViewById(R.id.dont_show);
        queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogListener.onqueding();
                dismiss();
            }
        });
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogListener.onquxiao();
                dismiss();
            }
        });

        setContentView(view);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8);

        dialogWindow.setAttributes(lp);
    }

    public void setQuedingText(String text) {
        queding.setText(text);
    }

    public void setQuedingText(int text) {
        queding.setText(text);
    }

    public void setTextTitle(int text) {
        textView_content.setText(text);
    }

    public void setTextTitle(String text) {
        textView_content.setText(text);
    }

    public void displayDontShowBox() {
        mDontShowBoxContainer.setVisibility(View.VISIBLE);
    }

    public void setSize(int size) {
        textView_content.setTextSize(size);
    }

    public boolean dontShowIsCheck() {
        return mDontShowBox.isChecked();
    }

    public String getTextTitle() {
        return textView_content.getText().toString();
    }

    public void setquxiaogone() {
        quxiao.setVisibility(View.GONE);
        line.setVisibility(View.GONE);
        queding.setBackgroundResource(R.drawable.shape_btm_blue_coner);
    }
}
