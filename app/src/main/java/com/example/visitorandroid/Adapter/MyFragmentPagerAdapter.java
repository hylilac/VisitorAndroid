package com.example.visitorandroid.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.visitorandroid.MainActivity;
import com.example.visitorandroid.MyFragment.MyFragmentBetter;
import com.example.visitorandroid.MyFragment.MyFragmentMessage;
import com.example.visitorandroid.MyFragment.MyFragmentModel;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 3;

    public String content;

    private MyFragmentModel myFragmentModel = null;
    private MyFragmentMessage myFragmentMessage = null;
    private MyFragmentBetter myFragmentBetter = null;

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);

        myFragmentModel = new MyFragmentModel("");
        myFragmentMessage = new MyFragmentMessage("功能模块");
        myFragmentBetter = new MyFragmentBetter("我");
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case MainActivity.PAGE_ONE:
                fragment = myFragmentModel;
                break;
            case MainActivity.PAGE_TWO:
                fragment = myFragmentMessage;
                break;
            case MainActivity.PAGE_THREE:
                fragment = myFragmentBetter;
                break;
        }
        return fragment;
    }
}