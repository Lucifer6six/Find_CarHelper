package com.upbest.rxlibrary;



import java.io.IOException;
import java.util.concurrent.TimeoutException;

import io.reactivex.observers.DisposableObserver;

/**
 * <pre>
 * 文件名：	HttpResultSubscriber
 * 作　者：	gykang
 * 时　间：	2018/5/29 17:08
 * 描　述：
 * @author kang gui yang
 * </pre>
 */
public abstract class HttpResultSubscriber<T> extends DisposableObserver<T> {


    @Override
    public void onNext(T value) {
        _onNext(value);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof IOException) {
            _onError("网络连接异常");
        } else if (e instanceof TimeoutException) {
            _onError("网络超时了");
        } else {
            _onError(e.getMessage());
        }
    }

    @Override
    public void onComplete() {

    }

    protected abstract void _onNext(T t);

    protected abstract void _onError(String message);
}
