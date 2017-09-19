package com.example.visitorandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.visitorandroid.Model.CheckOrderViewModel;
import com.example.visitorandroid.Model.OrderListViewModel;
import com.example.visitorandroid.R;

import java.util.LinkedList;

public class MyCheckOrderAdapter extends BaseAdapter {

    private LinkedList<CheckOrderViewModel> mData;
    private Context mContext;

    public MyCheckOrderAdapter(LinkedList<CheckOrderViewModel> mData, Context mContext) {
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
        TextView txt_order_message = (TextView) convertView.findViewById(R.id.order_message);
        TextView txt_order_time = (TextView) convertView.findViewById(R.id.order_time);
        TextView txt_order_result = (TextView) convertView.findViewById(R.id.order_result);

        txt_order_message.setText(mData.get(position).getV_name() + "申请拜访" + mData.get(position).getBv_name());
        String time = mData.get(position).getVisitorTime();
        txt_order_time.setText(time.substring(0,4)+ "年" + time.substring(5,7)
                + "月" + time.substring(8,10) + "日");
        txt_order_result.setText("待审核");

        return convertView;
    }
}
