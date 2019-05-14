package com.find_carhelper.ui.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.find_carhelper.R;
import com.find_carhelper.base.BaseAppCompatActivity;
import com.find_carhelper.presenter.BasePresenter;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

/**
 * <pre>
 * 文件名：	MVPBaseActivity
 * 作　者：	jqli
 * 时　间：	2018/3/13 11:54
 * 描　述：
 * </pre>
 */
public abstract class MVPBaseActivity<P extends BasePresenter> extends BaseAppCompatActivity {
    protected final String TAG = this.getClass().getName();
    protected P mPresenter;
    protected CommonTitleBar mCommonTitleBar;
    protected View.OnClickListener onRightClickListener;
    protected View.OnClickListener onLeftClickListener;

    @Override
    protected void onCreate(Bundle arg) {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mPresenter = createPresenter();
        //presenter取得与界面的联系
        if (mPresenter != null) {
            mPresenter.attachView(this);//因为之后所有的子类都要实现对应的View接口
        }

        super.onCreate(arg);
    }

    /**
     * 用了CommonTitleBar的界面才可以使用.否则抛出异常.
     * 这里只对用的比较多的textview文本toolbar进行封装,
     * 其他特殊标题头子类自己实现
     * 初始化标题栏
     *
     * @param title 标题
     */
    public void setTitleBar(String title) {
        mCommonTitleBar = findViewById(R.id.title_bar);

        if (mCommonTitleBar.getCenterTextView() == null) {
            throw new IllegalArgumentException("You must return a centertext layout");
        }

        if (title != null) {
            mCommonTitleBar.getCenterTextView().setText(title);
        } else {
            throw new IllegalArgumentException("You must return a title");
        }

        //初始化标题栏
        initTitleListener();
    }

    /**
     * 初始化标题栏
     *
     * @param resId 标题
     */
    public void setTitleBar(int resId) {
        mCommonTitleBar = findViewById(R.id.title_bar);

        if (mCommonTitleBar.getCenterTextView() == null) {
            throw new IllegalArgumentException("You must return a centertext layout");
        }

        if (resId != 0) {
            mCommonTitleBar.getCenterTextView().setText(getResources().getText(resId));
        } else {
            throw new IllegalArgumentException("You must return a center textview layout resource Id");
        }

        //初始化标题栏
        initTitleListener();
    }

    /**
     * 用了CommonTitleBar的界面才可以使用.否则抛出异常.
     * 这里只对用的比较多的右侧和中间textview文本toolbar进行封装,
     * 其他特殊标题头子类自己实现
     * 初始化标题栏
     *
     * @param rightId 不传布局的时候不显示
     * @param resId   标题
     */
    public void setTitleBar(int resId, int rightId) {
        mCommonTitleBar = findViewById(R.id.title_bar);

        if (mCommonTitleBar.getRightTextView() == null) {
            throw new IllegalArgumentException("You must return a right textview layout");
        }

        if (rightId != 0) {
            mCommonTitleBar.getRightTextView().setVisibility(View.VISIBLE);
        } else {
            mCommonTitleBar.getRightTextView().setVisibility(View.INVISIBLE);
        }

        if (mCommonTitleBar.getCenterTextView() == null) {
            throw new IllegalArgumentException("You must return a centertext layout");
        }

        if (resId != 0) {
            mCommonTitleBar.getCenterTextView().setText(getResources().getText(resId));
        } else {
            throw new IllegalArgumentException("You must return a center textview layout resource Id");
        }

        if (rightId != 0) {
            mCommonTitleBar.getRightTextView().setText(getResources().getText(rightId));
        } else {
            throw new IllegalArgumentException("You must return a rightText");
        }

        //初始化标题栏
        initTitleListener();
    }


    /**
     * 用了CommonTitleBar的界面才可以使用.否则抛出异常.
     * 这里只对用的比较多的右侧和中间textview文本toolbar进行封装,
     * 其他特殊标题头子类自己实现
     * 初始化标题栏
     *
     * @param rightText 为null或者为空的时候不显示
     * @param title     标题
     */
    public void setTitleBar(String title, String rightText) {
        mCommonTitleBar = findViewById(R.id.title_bar);
        mCommonTitleBar.setVisibility(View.VISIBLE);
        if (mCommonTitleBar.getRightTextView() == null) {
            throw new IllegalArgumentException("You must return a right textview layout");
        }

        if (TextUtils.isEmpty(rightText)) {
            mCommonTitleBar.getRightTextView().setVisibility(View.INVISIBLE);
        } else {
            mCommonTitleBar.getRightTextView().setVisibility(View.VISIBLE);
        }

        if (mCommonTitleBar.getCenterTextView() == null) {
            throw new IllegalArgumentException("You must return a centertext layout");
        }

        if (title != null) {
            mCommonTitleBar.getCenterTextView().setText(title);
        } else {
            throw new IllegalArgumentException("You must return a title");
        }

        if (rightText != null) {
            mCommonTitleBar.getRightTextView().setText(rightText);
        } else {
            throw new IllegalArgumentException("You must return a rightText");
        }

        //初始化标题栏
        initTitleListener();
    }

    public void hideTitleBar(){
        mCommonTitleBar.setVisibility(View.GONE);
    }
    /**
     * 初始化监听
     */
    private void initTitleListener() {
        mCommonTitleBar.setListener(new CommonTitleBar.OnTitleBarListener() {
            @Override
            public void onClicked(View v, int action, String extra) {
                if (action == CommonTitleBar.ACTION_LEFT_TEXT) {
                    if (onLeftClickListener != null) {
                        onLeftClickListener.onClick(v);
                    }
                } else if (action == CommonTitleBar.ACTION_LEFT_BUTTON) {
                    if (onLeftClickListener != null) {
                        onLeftClickListener.onClick(v);
                    }
                } else if (action == CommonTitleBar.ACTION_RIGHT_TEXT) {
                    if (onRightClickListener != null) {
                        onRightClickListener.onClick(v);
                    }
                } else if (action == CommonTitleBar.ACTION_RIGHT_BUTTON) {
                    if (onRightClickListener != null) {
                        onRightClickListener.onClick(v);
                    }
                }
            }
        });
    }

    /**
     * 用了CommonTitleBar的界面才可以使用.否则抛出异常.
     */
    protected void registerRightClickEvent(View.OnClickListener onClickListener) {
        if (mCommonTitleBar == null) {
            mCommonTitleBar = findViewById(R.id.title_bar);
            initTitleListener();
        }
        onRightClickListener = onClickListener;
    }

    /**
     * 用了CommonTitleBar的界面才可以使用.否则抛出异常.
     * 注册左侧标题栏的返回事件监听.
     */
    protected void registerLeftClickEvent(View.OnClickListener onClickListener) {
        if (mCommonTitleBar == null) {
            mCommonTitleBar = findViewById(R.id.title_bar);
            initTitleListener();
        }

        onLeftClickListener = onClickListener;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        //presenter断开与界面的联系
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    /**
     * 创建Presenter
     *
     * @return
     */
    protected abstract P createPresenter();

    /**
     * 从控件所在位置移动到控件的底部
     *
     * @return
     */
    public static TranslateAnimation moveToViewBottom() {

        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        mHiddenAction.setDuration(300);
        return mHiddenAction;
    }

    /**
     * 从控件的底部移动到控件所在位置
     *
     * @return
     */
    public static TranslateAnimation moveToViewLocation() {

        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mHiddenAction.setDuration(300);
        return mHiddenAction;
    }

}