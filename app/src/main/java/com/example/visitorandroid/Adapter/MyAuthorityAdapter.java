package com.example.visitorandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.visitorandroid.Model.AuthorityViewModel;
import com.example.visitorandroid.Model.EmployeeViewModel;
import com.example.visitorandroid.R;

import java.util.LinkedList;

public class MyAuthorityAdapter extends BaseAdapter {

    private LinkedList<AuthorityViewModel> mData;
    private Context mContext;

    public MyAuthorityAdapter(LinkedList<AuthorityViewModel> mData, Context mContext) {
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
        if (mData.get(position).getPID() == 0){
            rybm.setText("管理员");
        } else if (mData.get(position).getPID() == 1){
            rybm.setText("子管理员");
        } else if (mData.get(position).getPID() == 3){
            rybm.setText("普通员工");
        }

        return convertView;
    }
}
