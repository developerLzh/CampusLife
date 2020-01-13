package com.xiaoka.wangxi.campuslife.pages.publish;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.xiaoka.wangxi.campuslife.Constant;
import com.xiaoka.wangxi.campuslife.R;
import com.xiaoka.wangxi.campuslife.network.ApiManager;
import com.xiaoka.wangxi.campuslife.network.ApiService;
import com.xiaoka.wangxi.campuslife.network.HttpResultFunc;
import com.xiaoka.wangxi.campuslife.network.MySubscriber;
import com.xiaoka.wangxi.campuslife.network.NoErrSubscriberListener;
import com.xiaoka.wangxi.campuslife.pojo.result.SystemResult;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PublishActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        getSysConfig().subscribe(new MySubscriber<>(this, false, true, new NoErrSubscriberListener<SystemResult>() {
            @Override
            public void onNext(SystemResult result) {
                Toast.makeText(PublishActivity.this,result.driverPayType,Toast.LENGTH_SHORT).show();
            }
        }));


    }


    public Observable<SystemResult> getSysConfig() {
        return ApiManager.getInstance().createApi(Constant.HOST, ApiService.class)
                .getSysCofig("488441998952435da895286632e82f40")
                .filter(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
