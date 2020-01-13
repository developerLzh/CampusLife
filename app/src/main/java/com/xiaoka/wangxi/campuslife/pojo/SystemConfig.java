package com.xiaoka.wangxi.campuslife.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Copyright (C) , 2012-2018 , 四川小咖科技有限公司
 *
 * @author Lzh
 * @version 5.0.0.000
 * @date 2018/6/8
 * @since 5.0.0.000
 */
public class SystemConfig {

    public int id;

    @SerializedName("withdrawals_base")
    public double tixianBase;//提现基数

    @SerializedName("withdrawals_min")
    public double tixianMin;//提现最小值

    @SerializedName("withdrawals_max")
    public double tixianMax;//提现最大值

    @SerializedName("withdrawals_memo")
    public String tixianMemo;//提现备注
}
