package com.xiaoka.wangxi.campuslife.pojo.result;

import com.google.gson.annotations.SerializedName;
import com.xiaoka.wangxi.campuslife.pojo.SystemConfig;

/**
 * Copyright (C) , 2012-2018 , 四川小咖科技有限公司
 *
 * @author Lzh
 * @version 5.0.0.000
 * @date 2018/6/8
 * @since 5.0.0.000
 */
public class SystemResult extends EmResult {

    public SystemConfig system;

    @SerializedName("driver_pay_channel")
    public String driverPayType;

}
