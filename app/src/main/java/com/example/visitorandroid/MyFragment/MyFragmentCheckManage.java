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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.visitorandroid.Adapter.MyOrderListAdapter;
import com.example.visitorandroid.Model.OrderListViewModel;
import com.example.visitorandroid.R;

import java.util.LinkedList;
import java.util.List;

import static com.example.visitorandroid.R.id.check_order;
import static com.example.visitorandroid.R.id.nav_sub_sex;
import static com.example.visitorandroid.R.id.order_list;
import static com.example.visitorandroid.R.id.rg_tab_bar;

public class MyFragmentCheckManage extends Fragment implements View.OnClickListener, RadioGroup
        .OnCheckedChangeListener, AdapterView.OnItemClickListener {

    private String content;
    private Activity activity;

    private Button checkmanage_btnback;
    private RadioGroup check_tab_bar;
    private RadioButton checkorder;
    private ListView check_manage_list;

    private List<OrderListViewModel> mData = null;
    private Context mContext;
    private MyOrderListAdapter mAdapter = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public MyFragmentCheckManage(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fg_checkmanage,container,false);

        bindViews(view);

        return view;
    }

    private void bindViews(View view) {

        checkmanage_btnback = (Button) view.findViewById(R.id.check_manage_btn_back);
        check_tab_bar = (RadioGroup) view.findViewById(R.id.check_tab_bar);
        checkorder = (RadioButton) view.findViewById(R.id.check_order);
        check_manage_list = (ListView) view.findViewById(R.id.check_manage_list);

        checkmanage_btnback.setOnClickListener(this);
        check_tab_bar.setOnCheckedChangeListener(this);
        checkorder.setChecked(true);
        check_manage_list.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.check_manage_btn_back:
                activity.onBackPressed();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        mContext = getActivity();
        mData = new LinkedList<OrderListViewModel>();
        switch (checkedId) {
            case R.id.check_order:
                mData.add(new OrderListViewModel("x申请拜访xxx", "2017年09月11日", "待审核",R.drawable.icon_message));
                mData.add(new OrderListViewModel("xx申请拜访xxxx", "2017年09月12日", "待审核",R.drawable.icon_message));
                mData.add(new OrderListViewModel("xxxx申请拜访x", "2017年09月13日", "待审核",R.drawable.icon_message));
                mAdapter = new MyOrderListAdapter((LinkedList<OrderListViewModel>) mData, mContext);
                break;
            case R.id.check_user:
                mData.add(new OrderListViewModel("xx", "2017年09月11日", "待审核",R.drawable.nav_icon));
                mData.add(new OrderListViewModel("xxx", "2017年09月12日", "待审核",R.drawable.nav_icon));
                mData.add(new OrderListViewModel("xxx", "2017年09月13日", "待审核",R.drawable.nav_icon));
                mAdapter = new MyOrderListAdapter((LinkedList<OrderListViewModel>) mData, mContext);
                break;
        }
        check_manage_list.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        final String[] choose = new String[]{"确认通过", "取消"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setItems(choose, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (choose[which].equals("确认通过")) {

                } else if (choose[which].equals("取消")) {

                }
            }
        }).create();

        builder.show();
    }
}
