package com.example.visitorandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.visitorandroid.Model.ManageName;
import com.example.visitorandroid.R;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.LinkedList;

public class MyManageAdapter extends BaseAdapter {

    private LinkedList<ManageName> mData;
    private Context mContext;

    public MyManageAdapter(LinkedList<ManageName> mData, Context mContext) {
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_manage,parent,false);

        TextView txt_manage_name = (TextView) convertView.findViewById(R.id.manage_name);

        txt_manage_name.setText(mData.get(position).getManagename());

        return convertView;
    }

    /**
     * Created by hy on 2017/9/21.
     */

    public static class ImagePagerAdapter extends StaticPagerAdapter {

        private int[] imgs = {
                R.mipmap.icon_1,
                R.mipmap.icon_2,
                R.mipmap.icon_3,
                R.mipmap.icon_4,
                R.mipmap.icon_5,
                R.mipmap.icon_6,
                R.mipmap.icon_7
        };


        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }


        @Override
        public int getCount() {
            return imgs.length;
        }
    }
}
