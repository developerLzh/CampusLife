package com.xiaoka.wangxi.campuslife.network;

/**
 * Created by Administrator on 2016/9/26.
 */

public interface HaveErrSubscriberListener<T> {
    void onNext(T t);

    void onError(int code);
}
