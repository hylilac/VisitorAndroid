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
import android.widget.Toast;

import com.example.visitorandroid.Adapter.MyEmployeeAdapter;
import com.example.visitorandroid.Model.BaseViewModel;
import com.example.visitorandroid.Model.DialogMethod;
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
import static com.example.visitorandroid.R.id.ry_manage_list;

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

        mAdapter = new MyEmployeeAdapter((LinkedList<EmployeeViewModel>) mData, mContext);
        authoritymanage_list.setAdapter(mAdapter);

        authoritymanage_btnback.setOnClickListener(this);
        authoritymanage_list.setOnItemClickListener(this);

        String address_authority="http://www.tytechkj.com/App/Permission/GetDepartmentEmployeePermission";
        queryAuthority(address_authority);
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

    /**
     * UserID 当前用户ID
     */

    private void queryAuthority(String address) {
        DialogMethod.MyProgressDialog(getContext(),"正在上传中...",true);
        String ss = GetInstance().User.getGUID();
        RequestBody requestBody = new FormBody.Builder()
                .add("UserID",GetInstance().User.getGUID())
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();

                Gson gson = new Gson();
//                user = gson.fromJson(responseText, EmployeeInfo.class);
//
//                if (!user.IsError) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            DialogMethod.MyProgressDialog(getContext(),"",false);
//                            mData.clear();
//                            for(EmployeeViewModel employee : user.Data){
//                                GetInstance().setEmployee(employee);
//                                mData.add(new EmployeeViewModel(employee.getGUID(),employee.getNickName(),employee.getDepartmentName()));
//                            }
//                            mAdapter = new MyEmployeeAdapter((LinkedList<EmployeeViewModel>) mData, mContext);
//                            ry_manage_list.setAdapter(mAdapter);
//                        }
//                    });
//                }
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

}
