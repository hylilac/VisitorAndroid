package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visitorandroid.Adapter.MyManageAdapter;
import com.example.visitorandroid.Model.BaseViewModel;
import com.example.visitorandroid.Model.CompanyViewModel;
import com.example.visitorandroid.Model.DialogMethod;
import com.example.visitorandroid.Model.ManageName;
import com.example.visitorandroid.Model.UserInfo;
import com.example.visitorandroid.R;
import com.example.visitorandroid.util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.visitorandroid.Model.BaseViewModel.GetInstance;


public class MyFragmentManageKey extends Fragment implements View.OnClickListener, AdapterView
        .OnItemClickListener {

    public String content;
    private Activity activity;

    private TextView txtTopbar;
    private View div_tabbar;
    private RadioGroup radios;

    private MyFragmentBmManage fgmanage;
    private MyFragmentRyManage fgrymanage;
    private MyFragmentOrderListManage fgorderlist;
    private MyFragmentCheckManage fgcheckmanage;
    private MyFragmentAuthorityManage fgauthoritymanage;
    private MyFragmentOrderManage fgordermanage;
    private UserInfo user;

    private List<ManageName> mData  = null;
    private MyManageAdapter mAdapter = null;
    private ListView managekey_list;
    private Context mContext;
    private Button managekey_btnback;

    private TextView managekey_topbar;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public MyFragmentManageKey(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fg_managekey,container,false);

        bindViews(view);

        txtTopbar = activity.findViewById(R.id.txt_topbar);
        txtTopbar.setVisibility(View.GONE);

        div_tabbar = activity.findViewById(R.id.div_tab_bar);
        div_tabbar.setVisibility(View.GONE);

        radios = activity.findViewById(R.id.rg_tab_bar);
        radios.setVisibility(View.GONE);

        return view;
    }

    private void bindViews(View view) {

        managekey_btnback = (Button) view.findViewById(R.id.manage_key_btn_back);
        managekey_topbar = (TextView) view.findViewById(R.id.manage_key_top_bar);
        managekey_list = (ListView) view.findViewById(R.id.manage_key_list);

        managekey_topbar.setText(GetInstance().CompanyView.getC_Name());

        mContext = getActivity();

        mData = new LinkedList<ManageName>();
        mData.clear();

        int ss = BaseViewModel.GetInstance().CompanyView.getPID();
        if (ss<3){
            if (ss<2){
                mData.add(new ManageName("部门管理"));
                mData.add(new ManageName("人员管理"));
                mData.add(new ManageName("预约列表"));
            }
            mData.add(new ManageName("审核"));
            if (ss == 0){
                mData.add(new ManageName("权限分配"));
            }
        }

        mData.add(new ManageName("预约单"));
        mAdapter = new MyManageAdapter((LinkedList<ManageName>) mData, mContext);
        managekey_list.setAdapter(mAdapter);

        managekey_btnback.setOnClickListener(this);
        managekey_list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        ManageName manageList = mData.get(position);
        FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
        hideAllFragment(fTransaction);
        if (manageList.getManagename().equals("部门管理")){
            if(fgmanage == null){
                fgmanage = new MyFragmentBmManage("部门管理");
                fTransaction.add(R.id.fb_manage_key,fgmanage);
                fTransaction.addToBackStack(null);
            }else{
                fTransaction.add(R.id.fb_manage_key,fgmanage);
                fTransaction.addToBackStack(null);
                fTransaction.show(fgmanage);
            }
        } else if (manageList.getManagename().equals("人员管理")){
            if(fgrymanage == null){
                fgrymanage = new MyFragmentRyManage("人员管理");
                fTransaction.add(R.id.fb_manage_key,fgrymanage);
                fTransaction.addToBackStack(null);
            }else{
                fTransaction.add(R.id.fb_manage_key,fgrymanage);
                fTransaction.addToBackStack(null);
                fTransaction.show(fgrymanage);
            }
        } else if (manageList.getManagename().equals("预约列表")){
            if(fgorderlist == null){
                fgorderlist = new MyFragmentOrderListManage("预约列表");
                fTransaction.add(R.id.fb_manage_key,fgorderlist);
                fTransaction.addToBackStack(null);
            }else{
                fTransaction.add(R.id.fb_manage_key,fgorderlist);
                fTransaction.addToBackStack(null);
                fTransaction.show(fgorderlist);
            }
        } else if (manageList.getManagename().equals("审核")){
            if(fgcheckmanage == null){
                fgcheckmanage = new MyFragmentCheckManage("审核");
                fTransaction.add(R.id.fb_manage_key,fgcheckmanage);
                fTransaction.addToBackStack(null);
            }else{
                fTransaction.add(R.id.fb_manage_key,fgcheckmanage);
                fTransaction.addToBackStack(null);
                fTransaction.show(fgcheckmanage);
            }
        } else if (manageList.getManagename().equals("权限分配")){
            if(fgauthoritymanage == null){
                fgauthoritymanage = new MyFragmentAuthorityManage("权限分配");
                fTransaction.add(R.id.fb_manage_key,fgauthoritymanage);
                fTransaction.addToBackStack(null);
            }else{
                fTransaction.add(R.id.fb_manage_key,fgauthoritymanage);
                fTransaction.addToBackStack(null);
                fTransaction.show(fgauthoritymanage);
            }
        } else if (manageList.getManagename().equals("预约单")){
            if(fgordermanage == null){
                fgordermanage = new MyFragmentOrderManage("预约单");
                fTransaction.add(R.id.fb_manage_key,fgordermanage);
                fTransaction.addToBackStack(null);
            }else{
                fTransaction.add(R.id.fb_manage_key,fgordermanage);
                fTransaction.addToBackStack(null);
                fTransaction.show(fgordermanage);
            }
        }
        fTransaction.commit();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.manage_key_btn_back:
                txtTopbar.setVisibility(View.VISIBLE);
                div_tabbar.setVisibility(View.VISIBLE);
                radios.setVisibility(View.VISIBLE);
                activity.onBackPressed();
                break;
        }
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fgmanage != null)fragmentTransaction.hide(fgmanage);
        if(fgrymanage != null)fragmentTransaction.hide(fgrymanage);
        if(fgorderlist != null)fragmentTransaction.hide(fgorderlist);
        if(fgcheckmanage != null)fragmentTransaction.hide(fgcheckmanage);
        if(fgauthoritymanage != null)fragmentTransaction.hide(fgauthoritymanage);
        if(fgordermanage != null)fragmentTransaction.hide(fgordermanage);
    }
}
