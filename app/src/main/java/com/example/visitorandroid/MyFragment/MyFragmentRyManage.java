package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.visitorandroid.Adapter.MyBmAdapter;
import com.example.visitorandroid.Adapter.MyRyAdapter;
import com.example.visitorandroid.Model.BmViewModel;
import com.example.visitorandroid.Model.OrderListViewModel;
import com.example.visitorandroid.Model.RyViewModel;
import com.example.visitorandroid.R;

import java.util.LinkedList;
import java.util.List;

public class MyFragmentRyManage extends Fragment implements View.OnClickListener, AdapterView
        .OnItemClickListener {

    private String content;
    private Activity activity;

    private Button rymanage_btnback;
    private ListView ry_manage_list;

    private List<RyViewModel> mData = null;
    private Context mContext;
    private MyRyAdapter mAdapter = null;
    private MyFragmentRmManageResult fgRmManageResult;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }



    public MyFragmentRyManage(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_rymanage,container,false);

        bindViews(view);

        return view;
    }

    private void bindViews(View view) {

        rymanage_btnback = (Button) view.findViewById(R.id.ry_manage_btn_back);
        ry_manage_list = (ListView) view.findViewById(R.id.ry_manage_list);

        mContext = getActivity();
        mData = new LinkedList<RyViewModel>();
        mData.add(new RyViewModel("testhy","董事会"));
        mData.add(new RyViewModel("","董事会"));
        mData.add(new RyViewModel("milou","董事会"));
        mAdapter = new MyRyAdapter((LinkedList<RyViewModel>) mData, mContext);
        ry_manage_list.setAdapter(mAdapter);

        rymanage_btnback.setOnClickListener(this);
        ry_manage_list.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ry_manage_btn_back:
                activity.onBackPressed();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        RyViewModel ry = mData.get(position);

        FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
        hideAllFragment(fTransaction);
        if (fgRmManageResult == null) {
            fgRmManageResult = new MyFragmentRmManageResult(ry.getRynickname() +
                    ry.getRybm());
            fTransaction.add(R.id.fb_ry_manage, fgRmManageResult);
            fTransaction.addToBackStack(null);
        } else {
            fTransaction.add(R.id.fb_ry_manage, fgRmManageResult);
            fTransaction.addToBackStack(null);
            fTransaction.show(fgRmManageResult);
        }
        fTransaction.commit();
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fgRmManageResult != null)fragmentTransaction.hide(fgRmManageResult);
    }



}
