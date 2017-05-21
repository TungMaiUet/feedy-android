package com.example.tungmai.feedy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.tungmai.feedy.fragment.BlogFragment;
import com.example.tungmai.feedy.fragment.FeedyFragment;
import com.example.tungmai.feedy.fragment.TipsFragment;

/**
 * Created by TungMai on 3/15/2017.
 */

public class PageTabAdapter extends FragmentStatePagerAdapter {
    private int countTabs;


    public PageTabAdapter(FragmentManager fm, int countTabs) {
        super(fm);
        this.countTabs = countTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                BlogFragment blogFragment = new BlogFragment();
                return blogFragment;
            case 1:
                FeedyFragment feedFragment = new FeedyFragment();
                return feedFragment;

            case 2:
                TipsFragment tipsFragment = new TipsFragment();
                return tipsFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return countTabs;
    }
}
