package com.example.tungmai.feedy.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tungmai.feedy.R;
import com.example.tungmai.feedy.adapter.PageTabAdapter;
import com.example.tungmai.feedy.models.User;

/**
 * Created by TungMai on 5/17/2017.
 */

public class HomeFragment extends android.support.v4.app.Fragment {

    private View view;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private User user;

    private PageTabAdapter pageTabAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
//        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


        initTabsLayout();

        return view;
    }

    private void initTabsLayout() {
        tabLayout = (TabLayout) view.findViewById(R.id.tabs_layout);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("Diễn đàn"));
        tabLayout.addTab(tabLayout.newTab().setText("Nấu ăn"));
        tabLayout.addTab(tabLayout.newTab().setText("Mẹo làm bếp"));

        tabLayout.setTabTextColors(getResources().getColorStateList(R.color.color_text_tablayout));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));
        tabLayout.setBackgroundColor(getResources().getColor(R.color.colorBackgroudTabLayout));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        pageTabAdapter = new PageTabAdapter(getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageTabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}
