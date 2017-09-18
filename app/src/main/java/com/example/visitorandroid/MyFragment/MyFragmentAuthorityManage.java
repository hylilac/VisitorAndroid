package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.visitorandroid.Adapter.MyEmployeeAdapter;
import com.example.visitorandroid.Model.EmployeeViewModel;
import com.example.visitorandroid.R;

import java.util.LinkedList;
import java.util.List;

public class MyFragmentAuthorityManage extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private String content;
    private Activity activity;

    private Button authoritymanage_btnback;
    private ListView authoritymanage_list;

    private List<EmployeeViewModel> mData = null;
    private Context mContext;
    private MyEmployeeAdapter mAdapter = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }



    public MyFragmentAuthorityManage(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_authoritymanage,container,false);

        bindViews(view);

        return view;
    }

    private void bindViews(View view) {

        authoritymanage_btnback = (Button) view.findViewById(R.id.authority_manage_btn_back);
        authoritymanage_list = (ListView) view.findViewById(R.id.authority_manage_list);

        mContext = getActivity();
        mData = new LinkedList<EmployeeViewModel>();
//        mData.add(new RyViewModel("testhy","普通员工"));
//        mData.add(new RyViewModel("","子管理员"));
//        mData.add(new RyViewModel("milou","管理员"));
        mAdapter = new MyEmployeeAdapter((LinkedList<EmployeeViewModel>) mData, mContext);
        authoritymanage_list.setAdapter(mAdapter);

        authoritymanage_btnback.setOnClickListener(this);
        authoritymanage_list.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.authority_manage_btn_back:
                activity.onBackPressed();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final String[] choose = new String[]{"子管理员", "普通员工", "取消"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setItems(choose, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (choose[which].equals("子管理员")) {

                } else if (choose[which].equals("普通员工")) {

                }
            }
        }).create();

        builder.show();
    }
}
