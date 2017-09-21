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

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.example.visitorandroid.Model.BaseViewModel.GetInstance;


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

        View view = inflater.inflate(R.layout.fg_manage,container,false);

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

        manage_btnback = (Button) view.findViewById(R.id.manage_btn_back);

        manage_btnback.setOnClickListener(this);

        join_company = (Button) view.findViewById(R.id.join_company);
        create_company = (Button) view.findViewById(R.id.create_company);

        join_company.setOnClickListener(this);
        create_company.setOnClickListener(this);

        String address_company="http://www.tytechkj.com/App/Permission/GetCompanyInfo";
        queryCompany(address_company);
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
        }
        fTransaction.commit();
}

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fgjoin != null)fragmentTransaction.hide(fgjoin);
        if(fgcreate != null)fragmentTransaction.hide(fgcreate);
    }

    /**
     * ID 当前用户ID
     */

    private void queryCompany(String address) {
        DialogMethod.MyProgressDialog(getActivity(),"正在处理中...",true);
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
                }else {
                    DialogMethod.MyDialog(getContext(),user.Message);
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
        DialogMethod.MyProgressDialog(getActivity(),"正在获处理...",true);
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
                        Toast.makeText(getContext(),"获取公司PID失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
