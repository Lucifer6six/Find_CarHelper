package com.find_carhelper.presenter;


import java.lang.ref.WeakReference;

/**
 * <pre>
 * 文件名：	BasePresenter
 * 作　者：	jqli
 * 时　间：	2018/3/13 11:34
 * 描　述：
 * @author
 * </pre>
 */
public class BasePresenter<V> {

    protected WeakReference<V> mViewRef;

    /**
     * 界面创建，Presenter与界面取得联系（创建View的弱引用）
     *
     * @param view
     */
    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    /**
     * 获取界面的引用
     *
     * @return
     */
    public V getView() {
        return mViewRef == null ? null : mViewRef.get();
    }

    /**
     * 判断界面是否销毁
     *
     * @return
     */
    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    /**
     * 界面销毁，Presenter与界面断开联系
     */
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}