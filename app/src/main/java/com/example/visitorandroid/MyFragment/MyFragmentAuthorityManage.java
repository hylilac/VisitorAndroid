package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.visitorandroid.Adapter.MyAuthorityAdapter;
import com.example.visitorandroid.Adapter.MyEmployeeAdapter;
import com.example.visitorandroid.Model.AuthorityInfo;
import com.example.visitorandroid.Model.AuthorityViewModel;
import com.example.visitorandroid.Model.BaseViewModel;
import com.example.visitorandroid.Model.CompanyViewModel;
import com.example.visitorandroid.Model.DialogMethod;
import com.example.visitorandroid.Model.EmployeeInfo;
import com.example.visitorandroid.Model.EmployeeViewModel;
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

import static android.os.Build.VERSION_CODES.M;
import static com.example.visitorandroid.Model.BaseViewModel.GetInstance;
import static com.example.visitorandroid.R.id.ry_manage_list;

public class MyFragmentAuthorityManage extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private String content;
    private Activity activity;

    private Button authoritymanage_btnback;
    private ListView authoritymanage_list;

    private List<AuthorityViewModel> mData = null;
    private Context mContext;
    private MyAuthorityAdapter mAdapter = null;
    private AuthorityInfo user;
    private UserInfo users;
    private int authorityPID;
    private String authorityUID;

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
        mData = new LinkedList<AuthorityViewModel>();

        mAdapter = new MyAuthorityAdapter((LinkedList<AuthorityViewModel>) mData, mContext);
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
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        int authorityPid = mData.get(position).getPID();
        authorityUID = mData.get(position).getGUID();

        if (authorityPid == 0){
            DialogMethod.MyDialog(getContext(),"无法更改管理员权限");
        }else {
            final String[] choose = new String[]{"子管理员", "普通员工", "取消"};
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setCancelable(false);
            builder.setItems(choose, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (choose[which].equals("子管理员")) {
                        authorityPID = 1;
                    } else if (choose[which].equals("普通员工")) {
                        authorityPID = 3;
                    }
                    String address_changeAuthority="http://www.tytechkj.com/App/Permission/ChangeEmployeePermission";

                    queryChangeAuthority(address_changeAuthority,authorityUID,authorityPID);
                }
            }).create();

            builder.show();
        }
    }

    /**
     * UserID 当前用户ID
     */

    private void queryAuthority(String address) {
        DialogMethod.MyProgressDialog(getContext(),"正在处理中...",true);
        String ss = GetInstance().User.getGUID();
        RequestBody requestBody = new FormBody.Builder()
                .add("UserID",GetInstance().User.getGUID())
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                user = gson.fromJson(responseText, AuthorityInfo.class);
                if (!user.IsError) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogMethod.MyProgressDialog(getContext(),"",false);
                            mData.clear();
                            for(AuthorityViewModel authority : user.Data){
                                BaseViewModel.GetInstance().setAuthority(authority);
                                mData.add(new AuthorityViewModel(authority.getGUID(),authority.getNickName(),authority.getPID()));
                            }
                            mAdapter = new MyAuthorityAdapter((LinkedList<AuthorityViewModel>) mData, mContext);
                            authoritymanage_list.setAdapter(mAdapter);
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
                        Toast.makeText(getContext(),"获取权限失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * CID 公司ID
     * UID 修改员工ID
     * PID 修改员工权限ID 1：子管理员 3：普通员工
     */

    private void queryChangeAuthority(String address,String authorityUID, int authorityPID) {
        DialogMethod.MyProgressDialog(getContext(),"正在处理中...",true);
        int ss = BaseViewModel.GetInstance().CompanyView.getC_ID();
        String xx = authorityUID;
        int aa = authorityPID;
        RequestBody requestBody = new FormBody.Builder()
                .add("CID",String.valueOf(BaseViewModel.GetInstance().CompanyView.getC_ID()))
                .add("UID",authorityUID)
                .add("PID",String.valueOf(authorityPID))
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                users = gson.fromJson(responseText, UserInfo.class);
                if (!users.IsError) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogMethod.MyProgressDialog(getContext(),"",false);
                            String address_authority="http://www.tytechkj.com/App/Permission/GetDepartmentEmployeePermission";
                            queryAuthority(address_authority);
                        }
                    });
                }else {
                    DialogMethod.MyDialog(getContext(),users.Message);
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogMethod.MyProgressDialog(getContext(),"",false);
                        Toast.makeText(getContext(),"更改权限失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
