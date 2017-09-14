package com.example.visitorandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.visitorandroid.Model.BmViewModel;
import com.example.visitorandroid.R;

import java.util.LinkedList;

public class MyBmAdapter extends BaseAdapter {

    private LinkedList<BmViewModel> mData;
    private Context mContext;

    public MyBmAdapter(LinkedList<BmViewModel> mData, Context mContext) {
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_bm,parent,false);
        TextView txt_aName = (TextView) convertView.findViewById(R.id.bm_name);
        txt_aName.setText(mData.get(position).getaName());
        return convertView;
    }
}
