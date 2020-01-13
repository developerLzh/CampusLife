package com.xiaoka.wangxi.campuslife;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.StringRes;
import androidx.multidex.MultiDexApplication;

import static com.xiaoka.wangxi.campuslife.Constant.SHARED_PREFERENCES_NAME;

public class XApp extends MultiDexApplication {

    private static XApp instance;    //实例化对象

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static XApp getInstance() {
        return instance;
    }

    /**
     * 获取app的SharedPreferences.
     *
     * @return SharedPreferences对象
     */
    public static SharedPreferences getMyPreferences() {
        return instance.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 获取SharedPreferences.Editor.
     *
     * @return editor对象
     */
    public static SharedPreferences.Editor getPreferencesEditor() {
        return instance.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).edit();
    }

    /**
     * 通过字符串资源id,获取字符串.
     *
     * @param resId 需要获取字符串的资源id
     * @return 返回资源id对应的字符串, 如果获取则返回null
     */
    public static String getMyString(@StringRes int resId) {
        String str = null;
        if (instance != null) {
            str = instance.getString(resId);
        }
        return str;
    }
}
