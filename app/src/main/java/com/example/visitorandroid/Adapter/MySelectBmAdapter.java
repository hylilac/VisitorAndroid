package com.example.visitorandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.visitorandroid.Model.Data;
import com.example.visitorandroid.R;

import java.util.LinkedList;

public class MySelectBmAdapter extends BaseAdapter {

    private LinkedList<Data> mData;
    private Context mContext;

    public MySelectBmAdapter(LinkedList<Data> mData, Context mContext) {
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_selectbm,parent,false);
        TextView txt_select_bm = (TextView) convertView.findViewById(R.id.ry_select_bm);
        txt_select_bm.setText(mData.get(position).getC_Name());
        return convertView;
    }
}
