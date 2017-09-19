package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.visitorandroid.Adapter.MyEmployeeAdapter;
import com.example.visitorandroid.Model.BaseViewModel;
import com.example.visitorandroid.Model.DialogMethod;
import com.example.visitorandroid.Model.DepartmentInfo;
import com.example.visitorandroid.Model.EmployeeInfo;
import com.example.visitorandroid.Model.EmployeeViewModel;
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

public class MyFragmentRyManage extends Fragment implements View.OnClickListener, AdapterView
        .OnItemClickListener {

    private String content;
    private Activity activity;

    private Button rymanage_btnback;
    private ListView ry_manage_list;

    private List<EmployeeViewModel> mData = null;
    private Context mContext;
    private MyEmployeeAdapter mAdapter = null;
    private MyFragmentRyManageResult fgRmManageResult;
    private EmployeeInfo user;

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
        mData = new LinkedList<EmployeeViewModel>();
        mAdapter = new MyEmployeeAdapter((LinkedList<EmployeeViewModel>) mData, mContext);
        ry_manage_list.setAdapter(mAdapter);

        rymanage_btnback.setOnClickListener(this);
        ry_manage_list.setOnItemClickListener(this);

        String address_ry="http://www.tytechkj.com/App/Permission/GetDepartmentEmployee";
        queryRy(address_ry);
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
        EmployeeViewModel ry = mData.get(position);
        String ryGUID = ry.getGUID();

        FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
        hideAllFragment(fTransaction);

        fgRmManageResult = new MyFragmentRyManageResult(ryGUID,ry.getNickName(),ry.getDepartmentName());
        fTransaction.add(R.id.fb_ry_manage, fgRmManageResult);
        fTransaction.addToBackStack(null);

        fTransaction.commit();
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fgRmManageResult != null)fragmentTransaction.hide(fgRmManageResult);
    }

    /**
     * UserID 当前用户ID
     * CompanyID 公司ID
     */

    private void queryRy(String address) {
        DialogMethod.MyProgressDialog(getContext(),"正在上传中...",true);
        RequestBody requestBody = new FormBody.Builder()
                .add("UserID",GetInstance().User.getGUID())
                .add("CompanyID", String.valueOf(GetInstance().CompanyView.getID()))
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                user = gson.fromJson(responseText, EmployeeInfo.class);

                if (!user.IsError) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogMethod.MyProgressDialog(getContext(),"",false);
                            mData.clear();
                            for(EmployeeViewModel employee : user.Data){
                                BaseViewModel.GetInstance().setEmployee(employee);
                                mData.add(new EmployeeViewModel(employee.getGUID(),employee.getNickName(),employee.getDepartmentName()));
                            }
                            mAdapter = new MyEmployeeAdapter((LinkedList<EmployeeViewModel>) mData, mContext);
                            ry_manage_list.setAdapter(mAdapter);
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
                        Toast.makeText(getContext(),"获取部门失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CART_BROADCAST");
        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent){
                String msg = intent.getStringExtra("data");
                if("refresh".equals(msg)){
                    String address_ry="http://www.tytechkj.com/App/Permission/GetDepartmentEmployee";
                    queryRy(address_ry);
                }
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);
    }
}
