package com.xiaoka.wangxi.campuslife.network;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;


import com.xiaoka.wangxi.campuslife.Constant;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/11/15 0015.
 * 拦截请求添加sign.
 * <p>
 * 为避免线程同步造成数据紊乱的问题，这里都只使用局部变量，不使用全局变量
 */

public class SignInterceptor implements Interceptor {

    private static final String LOG_TAG = SignInterceptor.class.getSimpleName();

    private final static String secret = "123456";  // TODO: 2017/11/16 设置一个秘钥

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Map<String, String> paramsMap = new HashMap<>();
        String mPath = "";

        Request request = chain.request();
        String url = "" + request.url();

        Log.e(LOG_TAG, "inside url --> " + url);

        RequestBody body = request.body();

        if (body == null) {
            if (!TextUtils.isEmpty(url)) {
                String[] result = url.split("[?]");
                if (result.length > 1) {
                    mPath = result[0].replaceAll(Constant.HOST, "");  //获取path
                    String[] params = result[1].split("&");
                    for (String p : params) {
                        String[] ps = p.split("=");
                        paramsMap.put(ps[0], ps[1]);
                    }
                }
            }
        } else if (body instanceof FormBody) {
            if (!TextUtils.isEmpty(url)) {
                for (int i = 0; i < ((FormBody) body).size(); i++) {
                    paramsMap.put(((FormBody) body).encodedName(i), ((FormBody) body).encodedValue(i));
                }
                mPath = url.replaceAll(Constant.HOST, "");
            }
        }

        if (TextUtils.isEmpty(mPath) || paramsMap.isEmpty()) {
            Log.e(LOG_TAG, "url path or paramsMap is empty");
            Log.e(LOG_TAG, "request method is " + request.method());
            return chain.proceed(request);
        }

        return chain.proceed(request.newBuilder()
                .addHeader("sign", sign(paramsMap, mPath))
                .build());
    }


    /**
     * 获取排序后的url.
     */
    private String sortUrl(Map<String, String> paramsMap, String mPath) {
        List<String> sortList = new LinkedList<>();
        sortList.addAll(paramsMap.keySet());
        int size = sortList.size();

        Collections.sort(sortList); // 排序
        StringBuilder sb = new StringBuilder();
        sb.append("/").append(mPath).append("?");
        for (int i = 0; i < size; i++) {
            String key = sortList.get(i);
            sb.append(key).append("=").append(paramsMap.get(key));
            if (i < size - 1) {
                sb.append("&");
            }
        }
        return sb.toString();
    }

    private String sign(Map<String, String> paramsMap, String mPath) {
        return hamcsha1(sortUrl(paramsMap, mPath).getBytes(), secret.getBytes());
    }

    /**
     * sha1值计算.
     *
     * @param data data
     * @param key  key
     * @return 签名后的字符串.
     */
    private String hamcsha1(byte[] data, byte[] key) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            return byte2hex(mac.doFinal(data));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toUpperCase();
    }


}
