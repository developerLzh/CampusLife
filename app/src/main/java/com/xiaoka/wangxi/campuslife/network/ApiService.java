package com.xiaoka.wangxi.campuslife.network;

import com.xiaoka.wangxi.campuslife.pojo.result.SystemResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    /**
     * 获取系统配置
     *
     * @param appKey
     * @return
     */
    @GET("driver/api/v1/systemConfig")
    Observable<SystemResult> getSysCofig(@Query("app_key") String appKey);
}
