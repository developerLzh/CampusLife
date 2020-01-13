package com.xiaoka.wangxi.campuslife.pages.main.fragments.idle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.xiaoka.wangxi.campuslife.R;
import com.xiaoka.wangxi.campuslife.adapter.FPAdapter;

import java.util.ArrayList;
import java.util.List;

public class IdleFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;

    private FPAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_idle,container,false);

        return v;

    }

    List<Fragment> fragments = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.idle_view_pager);
        tabLayout = view.findViewById(R.id.idle_tab_layout);

        fragments.add(new BusinessFragment());
        fragments.add(new RentFragment());

        viewPager.setOffscreenPageLimit(5);
        adapter = new FPAdapter(getFragmentManager(), fragments);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("买卖");
        tabLayout.getTabAt(1).setText("租赁");

        ImageView toSearch = view.findViewById(R.id.to_search);
        toSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"跳转到查找",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
