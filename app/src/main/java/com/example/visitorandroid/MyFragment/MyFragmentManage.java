package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
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


public class MyFragmentManage extends Fragment implements View.OnClickListener, AdapterView
        .OnItemClickListener {

    private String content;
    private Activity activity;

    private TextView txtTopbar;
    private View div_tabbar;
    private RadioGroup radios;

    private MyFragmentJoinCompany fgjoin;
    private MyFragmentCreateCompany fgcreate;

    private Button manage_btnback;
    private Button join_company;
    private Button create_company;

    private TextView bm_manage;
    private TextView ry_manage;
    private TextView order_list_manage;
    private TextView check_manage;
    private TextView authority_manage;
    private TextView order_manage;

    private MyFragmentBmManage fgmanage;
    private MyFragmentRyManage fgrymanage;
    private MyFragmentOrderListManage fgorderlist;
    private MyFragmentCheckManage fgcheckmanage;
    private MyFragmentAuthorityManage fgauthoritymanage;
    private MyFragmentOrderManage fgordermanage;
    private UserInfo user;
    private LinearLayout mange1;
    private LinearLayout mange2;
    private TextView manage_topbar;
    private TextView txtcontent;

    private View view;

    private List<ManageName> mData  = null;
    private MyManageAdapter mAdapter = null;
    private ListView managekey_list;
    private Context mContext;
    private int companypid;
    private Button managekey_btnback;
private BaseViewModel BaseModel;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public MyFragmentManage(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String address_company="http://www.tytechkj.com/App/Permission/GetCompanyInfo";
        queryCompany(address_company);

        BaseModel = GetInstance();
        if (GetInstance().getCompanyView() == null){

            view = inflater.inflate(R.layout.fg_manage,container,false);
            manage_btnback = (Button) view.findViewById(R.id.manage_btn_back);
            manage_topbar = (TextView) view.findViewById(R.id.manage_top_bar);

            manage_btnback.setOnClickListener(this);


            txtcontent = (TextView) view.findViewById(R.id.txt_content);
            txtcontent.setText("您当前尚未加入企业");

            join_company = (Button) view.findViewById(R.id.join_company);
            create_company = (Button) view.findViewById(R.id.create_company);

            join_company.setOnClickListener(this);
            create_company.setOnClickListener(this);

        }else if(GetInstance().getCompanyView() != null) {
            view = inflater.inflate(R.layout.fg_managekey,container,false);
            managekey_btnback = (Button) view.findViewById(R.id.manage_key_btn_back);

            manage_topbar.setText(GetInstance().CompanyView.getC_Name());

            managekey_list = (ListView) view.findViewById(R.id.manage_key_list);

            mContext = getActivity();

            mData = new LinkedList<ManageName>();
            mData.clear();

//            int ss = BaseViewModel.GetInstance().CompanyView.getPID();
            int ss =0;
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

        txtTopbar = activity.findViewById(R.id.txt_topbar);
        txtTopbar.setVisibility(View.GONE);

        div_tabbar = activity.findViewById(R.id.div_tab_bar);
        div_tabbar.setVisibility(View.GONE);

        radios = activity.findViewById(R.id.rg_tab_bar);
        radios.setVisibility(View.GONE);

        return view;
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
        FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
        hideAllFragment(fTransaction);
        switch (view.getId()){
            case R.id.manage_btn_back:
                txtTopbar.setVisibility(View.VISIBLE);
                div_tabbar.setVisibility(View.VISIBLE);
                radios.setVisibility(View.VISIBLE);
                activity.onBackPressed();
                break;

            case R.id.join_company:
                if(fgjoin == null){
                    fgjoin = new MyFragmentJoinCompany("加入公司");
                    fTransaction.add(R.id.fb_company,fgjoin);
                    fTransaction.addToBackStack(null);
                }else{
                    fTransaction.add(R.id.fb_company,fgjoin);
                    fTransaction.addToBackStack(null);
                    fTransaction.show(fgjoin);
                }
                break;
            case R.id.create_company:
                if(fgjoin == null){
                    fgcreate = new MyFragmentCreateCompany("创建公司");
                    fTransaction.add(R.id.fb_company,fgcreate);
                    fTransaction.addToBackStack(null);
                }else{
                    fTransaction.add(R.id.fb_company,fgcreate);
                    fTransaction.addToBackStack(null);
                    fTransaction.show(fgcreate);
                }
                break;

            case R.id.manage_key_btn_back:
                txtTopbar.setVisibility(View.VISIBLE);
                div_tabbar.setVisibility(View.VISIBLE);
                radios.setVisibility(View.VISIBLE);
                activity.onBackPressed();
                break;

//            case R.id.bm_manage:
//                if(fgmanage == null){
//                    fgmanage = new MyFragmentBmManage("部门管理");
//                    fTransaction.add(R.id.fb_manage_key,fgmanage);
//                    fTransaction.addToBackStack(null);
//                }else{
//                    fTransaction.add(R.id.fb_manage_key,fgmanage);
//                    fTransaction.addToBackStack(null);
//                    fTransaction.show(fgmanage);
//                }
//                break;
//            case R.id.ry_manage:
//                if(fgrymanage == null){
//                    fgrymanage = new MyFragmentRyManage("人员管理");
//                    fTransaction.add(R.id.fb_manage_key,fgrymanage);
//                    fTransaction.addToBackStack(null);
//                }else{
//                    fTransaction.add(R.id.fb_manage_key,fgrymanage);
//                    fTransaction.addToBackStack(null);
//                    fTransaction.show(fgrymanage);
//                }
//                break;
//            case R.id.order_list_manage:
//                if(fgorderlist == null){
//                    fgorderlist = new MyFragmentOrderListManage("预约列表");
//                    fTransaction.add(R.id.fb_manage_key,fgorderlist);
//                    fTransaction.addToBackStack(null);
//                }else{
//                    fTransaction.add(R.id.fb_manage_key,fgorderlist);
//                    fTransaction.addToBackStack(null);
//                    fTransaction.show(fgorderlist);
//                }
//                break;
//            case R.id.check_manage:
//                if(fgcheckmanage == null){
//                    fgcheckmanage = new MyFragmentCheckManage("审核");
//                    fTransaction.add(R.id.fb_manage_key,fgcheckmanage);
//                    fTransaction.addToBackStack(null);
//                }else{
//                    fTransaction.add(R.id.fb_manage_key,fgcheckmanage);
//                    fTransaction.addToBackStack(null);
//                    fTransaction.show(fgcheckmanage);
//                }
//                break;
//            case R.id.authority_manage:
//                if(fgauthoritymanage == null){
//                    fgauthoritymanage = new MyFragmentAuthorityManage("权限分配");
//                    fTransaction.add(R.id.fb_manage_key,fgauthoritymanage);
//                    fTransaction.addToBackStack(null);
//                }else{
//                    fTransaction.add(R.id.fb_manage_key,fgauthoritymanage);
//                    fTransaction.addToBackStack(null);
//                    fTransaction.show(fgauthoritymanage);
//                }
//                break;
//            case R.id.order_manage:
//                if(fgordermanage == null){
//                    fgordermanage = new MyFragmentOrderManage("预约单");
//                    fTransaction.add(R.id.fb_manage_key,fgordermanage);
//                    fTransaction.addToBackStack(null);
//                }else{
//                    fTransaction.add(R.id.fb_manage_key,fgordermanage);
//                    fTransaction.addToBackStack(null);
//                    fTransaction.show(fgordermanage);
//                }
//                break;
        }
        fTransaction.commit();
}

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fgmanage != null)fragmentTransaction.hide(fgmanage);
        if(fgrymanage != null)fragmentTransaction.hide(fgrymanage);
        if(fgorderlist != null)fragmentTransaction.hide(fgorderlist);
        if(fgcheckmanage != null)fragmentTransaction.hide(fgcheckmanage);
        if(fgauthoritymanage != null)fragmentTransaction.hide(fgauthoritymanage);
        if(fgordermanage != null)fragmentTransaction.hide(fgordermanage);
        if(fgjoin != null)fragmentTransaction.hide(fgjoin);
        if(fgcreate != null)fragmentTransaction.hide(fgcreate);
    }

    /**
     * ID 当前用户ID
     */

    private void queryCompany(String address) {
        DialogMethod.MyProgressDialog(getActivity(),"正在获取公司信息中...",true);
        RequestBody requestBody = new FormBody.Builder()
                .add("ID", GetInstance().User.getGUID())
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                user = gson.fromJson(responseText,UserInfo.class);
                String s= new Gson().toJson(user.Data);
                CompanyViewModel lll= new Gson().fromJson( s,CompanyViewModel.class);
                BaseViewModel.GetInstance().setCompanyView(lll);
                if (!user.IsError) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogMethod.MyProgressDialog(getContext(), "", false);
                            String address_Pid="http://www.tytechkj.com/App/Permission/GetCurrentPid";
                            queryPid(address_Pid);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogMethod.MyProgressDialog(getContext(),"",false);
                        Toast.makeText(getContext(),"获取公司信息失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * UID 当前用户ID
     * CID 当前公司ID
     */

    private void queryPid(String address) {
        DialogMethod.MyProgressDialog(getActivity(),"正在获取公司信息中...",true);
        RequestBody requestBody = new FormBody.Builder()
                .add("UID", GetInstance().User.getGUID())
                .add("CID", String.valueOf(GetInstance().CompanyView.getID()))
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                CompanyViewModel lll= gson.fromJson(responseText,CompanyViewModel.class);
                BaseViewModel.GetInstance().setCompanyView(lll);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogMethod.MyProgressDialog(getContext(), "", false);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogMethod.MyProgressDialog(getContext(),"",false);
                        Toast.makeText(getContext(),"获取公司信息失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
