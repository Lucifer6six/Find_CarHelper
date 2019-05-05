package com.upbest.rxlibrary;


import android.text.TextUtils;

import com.upbest.entity.HttpResult;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * <pre>
 * 文件名：	RxManager
 * 作　者：	gykang
 * 时　间：	2018/5/29 15:03
 * 描　述：  RxJava2 封装
 * @author kang gui yang
 * </pre>
 */
public class RxManager {

    /**
     * 私有构造方法 禁止new
     */
    private RxManager() {
    }

    public static RxManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final RxManager INSTANCE = new RxManager();
    }

    /**
     * 订阅请求
     *
     * @param observable
     * @param observer
     * @param <T>
     */
    public <T> DisposableObserver<T> doSubscribe(Observable<HttpResult<T>> observable, DisposableObserver<T> observer) {
        return observable.compose(RxManager.<T>handleResult())
                .compose(RxManager.<T>rxSchedulerHelper())
                .subscribeWith(observer);
    }


    /**
     * 当业务接口多的时候  简化代码
     * 统一线程处理 compose 操作符 简化线程
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * FlowableTransformer 背压用
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelperForFlowable() {

        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 处理请求结果
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<HttpResult<T>, T> handleResult() {
        return new ObservableTransformer<HttpResult<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<HttpResult<T>> upstream) {

                return upstream.flatMap(new Function<HttpResult<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(final HttpResult<T> tHttpResult) throws Exception {

                        if (tHttpResult.isSuccess()) {
                            // 返回对应数据 根据项目情况而定
                            return Observable.create(new ObservableOnSubscribe<T>() {
                                @Override
                                public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                                    emitter.onNext(tHttpResult.getData() == null? (T) "null" :tHttpResult.getData());
                                    emitter.onComplete();
                                }
                            });

                        } else {

                            if (!TextUtils.isEmpty(tHttpResult.getMsg())) {
                                // message != null  need Toast
                                return Observable.error(new ApiException(tHttpResult.getMsg()));
                            } else {
                                // 服务器返回 Error
                                return Observable.error(new ApiException(tHttpResult.getCode()));
                            }

                        }
                    }
                });
            }
        };
    }


}


