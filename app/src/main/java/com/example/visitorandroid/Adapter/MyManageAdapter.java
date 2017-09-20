package com.example.visitorandroid.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.visitorandroid.Model.ManageName;
import com.example.visitorandroid.R;

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
}
