package com.example.visitorandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.visitorandroid.Model.EmployeeViewModel;
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
}
