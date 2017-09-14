package com.example.visitorandroid.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.visitorandroid.Model.OrderListViewModel;
import com.example.visitorandroid.R;

import java.util.LinkedList;

public class MyOrderListAdapter extends BaseAdapter {

    private LinkedList<OrderListViewModel> mData;
    private Context mContext;

    public MyOrderListAdapter(LinkedList<OrderListViewModel> mData, Context mContext) {
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_orderlist,parent,false);
        ImageView order_headicon = (ImageView) convertView.findViewById(R.id.order_head_icon);
        TextView txt_order_message = (TextView) convertView.findViewById(R.id.order_message);
        TextView txt_order_time = (TextView) convertView.findViewById(R.id.order_time);
        TextView txt_order_result = (TextView) convertView.findViewById(R.id.order_result);

        order_headicon.setImageResource(mData.get(position).getImageId());
        txt_order_message.setText(mData.get(position).getOrdermessage());
        txt_order_time.setText(mData.get(position).getOrdertime());
        txt_order_result.setText(mData.get(position).getOrderresult());

        if (mData.get(position).getOrderresult().equals("已通过")){
            txt_order_result.setTextColor(Color.RED);
        }

        return convertView;
    }
}
