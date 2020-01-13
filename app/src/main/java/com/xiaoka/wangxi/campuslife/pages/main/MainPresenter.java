package com.xiaoka.wangxi.campuslife.pages.main;

import android.Manifest;
import android.content.Context;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import io.reactivex.functions.Consumer;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private MainContract.Model model;
    private Context context;

    public MainPresenter(Context context, MainContract.View view) {
        this.view = view;
        this.context = context;
        model = new MainModel(context);
    }


    @Override
    public void checkPermission() {
        RxPermissions permissions = new RxPermissions((FragmentActivity) context);
        permissions.setLogging(true);
        permissions.requestEach(Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) {
                        Log.e("tag", permission.name);
                    }
                });
    }

    @Override
    public List<Integer> getMockData() {
        List<Integer> data = model.mockDatas();
        for (int i = 0; i < data.size(); i++) {
            int a = data.get(i);
            data.set(i, a * 2);
        }
        return data;
    }
}
