package com.example.visitorandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.visitorandroid.Model.BmViewModel;
import com.example.visitorandroid.Model.RyViewModel;
import com.example.visitorandroid.R;

import java.util.LinkedList;

public class MyRyAdapter extends BaseAdapter {

    private LinkedList<RyViewModel> mData;
    private Context mContext;

    public MyRyAdapter(LinkedList<RyViewModel> mData, Context mContext) {
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
        rynickname.setText(mData.get(position).getRynickname());
        rybm.setText(mData.get(position).getRybm());
        return convertView;
    }
}
