package com.xiaoka.wangxi.campuslife.pages.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.xiaoka.wangxi.campuslife.R;
import com.xiaoka.wangxi.campuslife.pages.main.fragments.idle.IdleFragment;
import com.xiaoka.wangxi.campuslife.pages.main.fragments.personal.PersonalCenter;
import com.xiaoka.wangxi.campuslife.pages.main.fragments.question.QuestionFragment;
import com.xiaoka.wangxi.campuslife.pages.main.fragments.school_circle.ScCircleFragment;
import com.xiaoka.wangxi.campuslife.pages.publish.PublishActivity;
import com.xiaoka.wangxi.campuslife.widget.SpecialTab;
import com.xiaoka.wangxi.campuslife.widget.SpecialTabRound;

import java.util.List;

import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.item.BaseTabItem;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private NavigationController mNavigationController;

    private ScCircleFragment scCircleFragment;//校园圈
    private IdleFragment idleFragment;//闲置
    private QuestionFragment questionFragment;//问题
    private PersonalCenter personalCenter;//个人中心

    private Fragment currentFragment;//当前选中的fragment

    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this, this);

        initNaviView();

        presenter.checkPermission();//申请权限
    }

    /**
     * 导航栏初始化
     */
    @Override
    public void initNaviView() {
        PageNavigationView pageBottomTabLayout = findViewById(R.id.tab);

        SpecialTabRound mainTab = new SpecialTabRound(this);
        mainTab.initialize(R.drawable.ic_nearby_gray_24dp,R.drawable.ic_nearby_teal_24dp,"发布");
        mainTab.setTextDefaultColor(0xFF888888);
        mainTab.setTextCheckedColor(getResources().getColor(R.color.colorPrimary));

        mNavigationController = pageBottomTabLayout.custom()
                .addItem(newItem(R.drawable.ic_restore_gray_24dp,R.drawable.ic_restore_teal_24dp, "校园圈"))
                .addItem(newItem(R.drawable.ic_favorite_gray_24dp,R.drawable.ic_favorite_teal_24dp, "闲置"))
                .addItem(mainTab)
                .addItem(newItem(R.drawable.ic_restore_gray_24dp,R.drawable.ic_restore_teal_24dp, "问题"))
                .addItem(newItem(R.drawable.ic_favorite_gray_24dp,R.drawable.ic_favorite_teal_24dp, "个人中心"))
                .build();

        scCircleFragment = new ScCircleFragment();
        idleFragment = new IdleFragment();
        questionFragment = new QuestionFragment();
        personalCenter = new PersonalCenter();

        mNavigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                if (index == 0) {
                    switchFragment(scCircleFragment, false);
                } else if (index == 1) {
                    switchFragment(idleFragment, false);
                } else if (index == 2) {
                    startActivity(new Intent(MainActivity.this, PublishActivity.class));
                } else if (index == 3) {
                    switchFragment(questionFragment, false);
                } else if (index == 4) {
                    switchFragment(personalCenter, false);
                }
            }

            @Override
            public void onRepeat(int index) {
                onSelected(index, -1);
            }
        });
        mNavigationController.setSelect(0);
    }

    /**
     * fragment 切换
     *
     * @param targetFragment
     * @return
     */
    @Override
    public void switchFragment(Fragment targetFragment, boolean removeLast) {

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        if (currentFragment == targetFragment) {//相同fragment就不切换
            return;
        }
        if (!targetFragment.isAdded()) {
            transaction.add(R.id.fragment_frame, targetFragment, targetFragment.getClass().getName());
        }
        if (currentFragment != null) {
            if (removeLast) {
                transaction.remove(currentFragment);
            } else {
                transaction.hide(currentFragment);
            }
        }
        transaction.show(targetFragment);
        currentFragment = targetFragment;
        transaction.commit();
    }


    /**
     * 隐藏底部
     */
    @Override
    public void hideBottom() {
        if (null != mNavigationController) {
            mNavigationController.hideBottomLayout();
        }
    }

    /**
     * 展示底部
     */
    @Override
    public void showBottom() {
        if (null != mNavigationController) {
            mNavigationController.showBottomLayout();
        }
    }

    public List<Integer> getMockData() {
        return presenter.getMockData();
    }

    /**
     * 正常tab
     */
    private BaseTabItem newItem(int drawable, int checkedDrawable, String text){
        SpecialTab mainTab = new SpecialTab(this);
        mainTab.initialize(drawable,checkedDrawable,text);
        mainTab.setTextDefaultColor(0xFF888888);
        mainTab.setTextCheckedColor(getResources().getColor(R.color.colorPrimaryDark));
        return mainTab;
    }

}
