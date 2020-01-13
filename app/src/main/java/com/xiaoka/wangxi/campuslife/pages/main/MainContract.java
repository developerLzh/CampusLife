package com.xiaoka.wangxi.campuslife.pages.main;

import androidx.fragment.app.Fragment;

import java.util.List;

public interface MainContract {
    interface View{
        void initNaviView();
        void showBottom();
        void hideBottom();
        void switchFragment(Fragment targetFragment, boolean removeLast);
    }
    interface Presenter{
        void checkPermission();
        List<Integer> getMockData();
    }
    interface Model{
        List<Integer> mockDatas();
    }
}
