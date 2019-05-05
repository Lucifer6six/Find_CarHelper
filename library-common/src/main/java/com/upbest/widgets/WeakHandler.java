package com.upbest.widgets;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.WeakReference;

/**
 * <p>
 * 子类如果是内部类请使用静态内部类
 * 使用弱引用持有ui/逻辑对象，解决handler持有外部类引用造成的内存溢出
 */

public abstract class WeakHandler<T> extends Handler {

    protected WeakReference<T> mReference;

    /**
     * 创建子线程Handler使用的构造器
     *
     * @param reference ui/逻辑对象引用
     */
    public WeakHandler(Looper looper, T reference) {
        super(looper);
        this.mReference = new WeakReference<>(reference);
    }

    /**
     * 创建主线程Handler使用的构造器
     *
     * @param reference ui/逻辑对象引用
     */
    public WeakHandler(T reference) {
        this.mReference = new WeakReference<>(reference);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        T t = mReference.get();
        if (t == null) {
            return;
        }
        handleMessage(t, msg);
    }

    /**
     * @param t       持有的引用
     * @param message 收到的消息
     */
    protected abstract void handleMessage(T t, Message message);

}
