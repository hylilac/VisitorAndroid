package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visitorandroid.Model.BaseViewModel;
import com.example.visitorandroid.Model.Data;
import com.example.visitorandroid.Model.DialogMethod;
import com.example.visitorandroid.Model.ResultViewModel;
import com.example.visitorandroid.Model.UserInfo;
import com.example.visitorandroid.Model.UserViewModel;
import com.example.visitorandroid.Model.objData;
import com.example.visitorandroid.R;
import com.example.visitorandroid.util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.visitorandroid.Model.BaseViewModel.GetInstance;
import static com.example.visitorandroid.R.id.backup;
import static com.example.visitorandroid.R.id.nav_sub_sex;
import static com.example.visitorandroid.R.id.txt_topbar;

public class MyFragmentManage extends Fragment implements View.OnClickListener {

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

    private Button managekey_btnback;
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

        View view = inflater.inflate(R.layout.fg_managekey,container,false);

        txtTopbar = activity.findViewById(R.id.txt_topbar);
        txtTopbar.setVisibility(View.GONE);

        div_tabbar = activity.findViewById(R.id.div_tab_bar);
        div_tabbar.setVisibility(View.GONE);

        radios = activity.findViewById(R.id.rg_tab_bar);
        radios.setVisibility(View.GONE);

        bindViewsKey(view);

//        bindViews(view);


        return view;
    }

    private void bindViewsKey(View view) {

        managekey_btnback = (Button) view.findViewById(R.id.manage_key_btn_back);
        bm_manage = (TextView) view.findViewById(R.id.bm_manage);
        ry_manage = (TextView) view.findViewById(R.id.ry_manage);
        order_list_manage = (TextView) view.findViewById(R.id.order_list_manage);
        check_manage = (TextView) view.findViewById(R.id.check_manage);
        authority_manage = (TextView) view.findViewById(R.id.authority_manage);
        order_manage = (TextView) view.findViewById(R.id.order_manage);

        String address_company="http://www.tytechkj.com/App/Permission/GetCompanyInfo";
        queryCompany(address_company);

        managekey_btnback.setOnClickListener(this);
        bm_manage.setOnClickListener(this);
        ry_manage.setOnClickListener(this);
        order_list_manage.setOnClickListener(this);
        check_manage.setOnClickListener(this);
        authority_manage.setOnClickListener(this);
        order_manage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
        hideAllFragment(fTransaction);
        switch (view.getId()){
            case R.id.manage_key_btn_back:
                txtTopbar.setVisibility(View.VISIBLE);
                div_tabbar.setVisibility(View.VISIBLE);
                radios.setVisibility(View.VISIBLE);
                activity.onBackPressed();
                break;
            case R.id.bm_manage:
                if(fgmanage == null){
                    fgmanage = new MyFragmentBmManage("部门管理");
                    fTransaction.add(R.id.fb_manage_key,fgmanage);
                    fTransaction.addToBackStack(null);
                }else{
                    fTransaction.add(R.id.fb_manage_key,fgmanage);
                    fTransaction.addToBackStack(null);
                    fTransaction.show(fgmanage);
                }
                break;
            case R.id.ry_manage:
                if(fgrymanage == null){
                    fgrymanage = new MyFragmentRyManage("人员管理");
                    fTransaction.add(R.id.fb_manage_key,fgrymanage);
                    fTransaction.addToBackStack(null);
                }else{
                    fTransaction.add(R.id.fb_manage_key,fgrymanage);
                    fTransaction.addToBackStack(null);
                    fTransaction.show(fgrymanage);
                }
                break;
            case R.id.order_list_manage:
                if(fgorderlist == null){
                    fgorderlist = new MyFragmentOrderListManage("预约列表");
                    fTransaction.add(R.id.fb_manage_key,fgorderlist);
                    fTransaction.addToBackStack(null);
                }else{
                    fTransaction.add(R.id.fb_manage_key,fgorderlist);
                    fTransaction.addToBackStack(null);
                    fTransaction.show(fgorderlist);
                }
                break;
            case R.id.check_manage:
                if(fgcheckmanage == null){
                    fgcheckmanage = new MyFragmentCheckManage("审核");
                    fTransaction.add(R.id.fb_manage_key,fgcheckmanage);
                    fTransaction.addToBackStack(null);
                }else{
                    fTransaction.add(R.id.fb_manage_key,fgcheckmanage);
                    fTransaction.addToBackStack(null);
                    fTransaction.show(fgcheckmanage);
                }
                break;
            case R.id.authority_manage:
                if(fgauthoritymanage == null){
                    fgauthoritymanage = new MyFragmentAuthorityManage("权限分配");
                    fTransaction.add(R.id.fb_manage_key,fgauthoritymanage);
                    fTransaction.addToBackStack(null);
                }else{
                    fTransaction.add(R.id.fb_manage_key,fgauthoritymanage);
                    fTransaction.addToBackStack(null);
                    fTransaction.show(fgauthoritymanage);
                }
                break;
            case R.id.order_manage:
                if(fgordermanage == null){
                    fgordermanage = new MyFragmentOrderManage("预约单");
                    fTransaction.add(R.id.fb_manage_key,fgordermanage);
                    fTransaction.addToBackStack(null);
                }else{
                    fTransaction.add(R.id.fb_manage_key,fgordermanage);
                    fTransaction.addToBackStack(null);
                    fTransaction.show(fgordermanage);
                }
                break;
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
    }

    private void queryCompany(String address) {
        DialogMethod.MyProgressDialog(getContext(),"正在上传中...",true);
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
                Data lll= new Gson().fromJson( s,Data.class);
                objData.GetInstance2().setData(lll);
                if (!user.IsError) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogMethod.MyProgressDialog(getContext(), "", false);
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
                        Toast.makeText(getContext(),"上传性别失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


//    private void bindViews(View view) {
//
//        TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
//        txt_content.setText("您当前尚未加入企业");
//
//        manage_btnback = (Button) view.findViewById(R.id.manage_btn_back);
//        join_company = (Button) view.findViewById(R.id.join_company);
//        create_company = (Button) view.findViewById(R.id.create_company);
//
//        manage_btnback.setOnClickListener(this);
//        join_company.setOnClickListener(this);
//        create_company.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View view) {
//        FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
//        hideAllFragment(fTransaction);
//        switch (view.getId()){
//            case R.id.manage_btn_back:
//                txtTopbar.setVisibility(View.VISIBLE);
//                div_tabbar.setVisibility(View.VISIBLE);
//                radios.setVisibility(View.VISIBLE);
//                activity.onBackPressed();
//                break;
//            case R.id.join_company:
//                if(fgjoin == null){
//                    fgjoin = new MyFragmentJoinCompany("加入公司");
//                    fTransaction.add(R.id.fb_company,fgjoin);
//                    fTransaction.addToBackStack(null);
//                }else{
//                    fTransaction.add(R.id.fb_company,fgjoin);
//                    fTransaction.addToBackStack(null);
//                    fTransaction.show(fgjoin);
//                }
//                break;
//            case R.id.create_company:
//                if(fgjoin == null){
//                    fgcreate = new MyFragmentCreateCompany("创建公司");
//                    fTransaction.add(R.id.fb_company,fgcreate);
//                    fTransaction.addToBackStack(null);
//                }else{
//                    fTransaction.add(R.id.fb_company,fgcreate);
//                    fTransaction.addToBackStack(null);
//                    fTransaction.show(fgcreate);
//                }
//                break;
//        }
//        fTransaction.commit();
//    }
//
//    //隐藏所有Fragment
//    private void hideAllFragment(FragmentTransaction fragmentTransaction){
//        if(fgjoin != null)fragmentTransaction.hide(fgjoin);
//        if(fgcreate != null)fragmentTransaction.hide(fgcreate);
//    }
}
