package com.xiaoka.wangxi.campuslife.network;

import android.content.Context;
import android.net.ParseException;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.xiaoka.wangxi.campuslife.R;
import com.xiaoka.wangxi.campuslife.utils.StringUtils;
import com.xiaoka.wangxi.campuslife.utils.ToastUtil;
import com.xiaoka.wangxi.campuslife.widget.LoadingButton;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * Created by Administrator on 2016/9/26.
 */

public class MySubscriber<T> implements Observer<T>, ProgressDismissListener {

    private Context context;

    private ProgressHandler progressHandler;
    private LoadingBtnHandler loadingBtnHandler;

    private NoErrSubscriberListener<T> noErrSubscriberListener;
    private HaveErrSubscriberListener<T> haveErrSubscriberListener;

    /**
     * @param needShowProgress        是否显示加载框
     * @param dialogCancelable        加载框是否可以取消
     * @param noErrSubscriberListener 不需要错误码的回调事件
     */
    public MySubscriber(
            Context context,
            boolean needShowProgress,
            boolean dialogCancelable,
            NoErrSubscriberListener<T> noErrSubscriberListener) {
        this.context = context;
        this.noErrSubscriberListener = noErrSubscriberListener;
        if (needShowProgress) {
            progressHandler = new ProgressHandler(context, dialogCancelable, this);
        }
    }

    /**
     * @param needShowProgress          是否显示加载框
     * @param dialogCancelable          加载框是否可以取消
     * @param haveErrSubscriberListener 需要错误码回调的事件
     */
    public MySubscriber(
            Context context,
            boolean needShowProgress,
            boolean dialogCancelable,
            HaveErrSubscriberListener<T> haveErrSubscriberListener) {
        this.context = context;
        this.haveErrSubscriberListener = haveErrSubscriberListener;
        if (needShowProgress) {
            progressHandler = new ProgressHandler(context, dialogCancelable, this);
        }
    }

    /**
     * @param context
     * @param button                    加载的按钮
     * @param haveErrSubscriberListener
     */
    public MySubscriber(Context context, LoadingButton button, HaveErrSubscriberListener<T> haveErrSubscriberListener) {
        this.context = context;
        this.haveErrSubscriberListener = haveErrSubscriberListener;
        loadingBtnHandler = new LoadingBtnHandler(button, this);
    }

    /**
     * @param context
     * @param button                  加载的按钮
     * @param noErrSubscriberListener
     */
    public MySubscriber(Context context, LoadingButton button, NoErrSubscriberListener<T> noErrSubscriberListener) {
        this.context = context;
        this.noErrSubscriberListener = noErrSubscriberListener;
        loadingBtnHandler = new LoadingBtnHandler(button, this);
    }

    /**
     * 处理错误信息
     */
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof ApiException) {
            if (StringUtils.isNotBlank(e.getMessage())) {
                ToastUtil.showMessage(context, e.getMessage());//服务器定义的错误
                if (((ApiException) e).getErrCode() == ErrCode.EMPLOY_NOT_EXIST.getCode()
                        || ((ApiException) e).getErrCode() == ErrCode.SERVER_DOWN.getCode()) {

                }
            } else {
                ToastUtil.showMessage(context, "网络开小差了");//服务器定义的错误
            }

            if (null != haveErrSubscriberListener) {
                haveErrSubscriberListener.onError(((ApiException) e).getErrCode());
            }
        } else {
            if (e instanceof HttpException) {
                ToastUtil.showMessage(context, context.getString(R.string.response_error) + ((HttpException) e).code());//400、500、404之类的响应码错误
            } else if (e instanceof SocketTimeoutException || e instanceof SocketException) {
                ToastUtil.showMessage(context, context.getString(R.string.out_time));//连接超时错误
            } else if (e instanceof ConnectException) {
                ToastUtil.showMessage(context, context.getString(R.string.no_internet));//连接失败错误
            } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
                ToastUtil.showMessage(context, context.getString(R.string.parse_error));//解析错误
            }
            if (null != haveErrSubscriberListener) {
                haveErrSubscriberListener.onError(-100);
            }
        }

        if (null != progressHandler) {
            progressHandler.sendEmptyMessage(ProgressHandler.DISMISS_DIALOG);
        } else if (null != loadingBtnHandler) {
            loadingBtnHandler.sendEmptyMessage(LoadingBtnHandler.HIDE_BTN_LOADING);
        } else {
            this.onProgressDismiss();
        }
    }

    @Override
    public void onComplete() {
        Log.e("MySubscriber", "mission complete");

        if (null != progressHandler) {
            progressHandler.sendEmptyMessage(ProgressHandler.DISMISS_DIALOG);
        } else if (null != loadingBtnHandler) {
            loadingBtnHandler.sendEmptyMessage(LoadingBtnHandler.HIDE_BTN_LOADING);
        } else {
            this.onProgressDismiss();
        }
    }


    @Override
    public void onSubscribe(Disposable d) {
        if (null != progressHandler) {
            progressHandler.sendEmptyMessage(ProgressHandler.SHOW_DIALOG);
        } else if (null != loadingBtnHandler) {
            loadingBtnHandler.sendEmptyMessage(LoadingBtnHandler.SHOW_BTN_LOADING);
        }
    }

    @Override
    public void onNext(T t) {
        if (haveErrSubscriberListener != null) {
            haveErrSubscriberListener.onNext(t);
        }
        if (noErrSubscriberListener != null) {
            noErrSubscriberListener.onNext(t);
        }
    }

    /**
     * 在加载框消失时是整个流程的最后一步
     * 好像可以防止内存泄露
     */
    @Override
    public void onProgressDismiss() {

    }
}
