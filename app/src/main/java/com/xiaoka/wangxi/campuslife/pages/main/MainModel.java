package com.xiaoka.wangxi.campuslife.pages.main;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class MainModel implements MainContract.Model {

    private Context context;

    public MainModel(Context context) {
        this.context = context;
    }

    @Override
    public List<Integer> mockDatas() {

        List<Integer> integers = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            integers.add(i + 1);
        }

        return integers;
    }
}
