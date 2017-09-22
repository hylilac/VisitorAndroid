package com.example.visitorandroid.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.visitorandroid.MainActivity;
import com.example.visitorandroid.Model.EmployeeViewModel;
import com.example.visitorandroid.MyFragment.MyFragmentBetter;
import com.example.visitorandroid.MyFragment.MyFragmentMessage;
import com.example.visitorandroid.MyFragment.MyFragmentModel;
import com.example.visitorandroid.R;

import java.util.LinkedList;

public class MyEmployeeAdapter extends BaseAdapter {

    private LinkedList<EmployeeViewModel> mData;
    private Context mContext;

    public MyEmployeeAdapter(LinkedList<EmployeeViewModel> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_ry,parent,false);
        TextView rynickname = (TextView) convertView.findViewById(R.id.ry_nickname);
        TextView rybm = (TextView) convertView.findViewById(R.id.ry_sub_nickname);
        rynickname.setText(mData.get(position).getNickName());
        rybm.setText(mData.get(position).getDepartmentName());
        return convertView;
    }

    public static class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private final int PAGER_COUNT = 3;
        private MyFragmentModel myFragmentModel = null;
        private MyFragmentMessage myFragmentMessage = null;
        private MyFragmentBetter myFragmentBetter = null;

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            myFragmentModel = new MyFragmentModel("");
            myFragmentMessage = new MyFragmentMessage("");
            myFragmentBetter = new MyFragmentBetter("");
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
}
