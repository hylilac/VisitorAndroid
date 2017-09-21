package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.visitorandroid.Adapter.MyDepartmentAdapter;
import com.example.visitorandroid.Model.BaseViewModel;
import com.example.visitorandroid.Model.DepartmentViewModel;
import com.example.visitorandroid.Model.DialogMethod;
import com.example.visitorandroid.Model.DepartmentInfo;
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

public class MyFragmentBmManage extends Fragment implements View.OnClickListener, AdapterView
        .OnItemClickListener {

    private String content;
    private Activity activity;

    private Button bmmanage_btnback;
    private Button bmmanage_btnadd;
    private ListView bmmanage_list;

    private List<DepartmentViewModel> mData = null;
    private Context mContext;
    private MyDepartmentAdapter mAdapter = null;

    private EditText bmname;
    private DepartmentInfo user;
    private UserInfo users;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public MyFragmentBmManage(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_bmmanage,container,false);

        bindViews(view);

        return view;
    }

    private void bindViews(View view) {
        bmmanage_btnback = (Button) view.findViewById(R.id.bm_manage_btn_back);
        bmmanage_btnadd = (Button) view.findViewById(R.id.bm_manage_btn_add);
        bmmanage_list = (ListView) view.findViewById(R.id.bm_manage_list);

        mContext = getActivity();

        mData = new LinkedList<DepartmentViewModel>();

        bmmanage_btnback.setOnClickListener(this);
        bmmanage_btnadd.setOnClickListener(this);
        bmmanage_list.setOnItemClickListener(this);

        String address_bm="http://www.tytechkj.com/App/Permission/GetCurrentDepartment";
        queryBm(address_bm);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bm_manage_btn_back:
                activity.onBackPressed();
                break;
            case R.id.bm_manage_btn_add:
                MyDialog("","取消","添加");
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        DepartmentViewModel bm = mData.get(position);
        String bmstring = bm.getC_Name();
        MyDialog(bmstring,"取消","修改");
    }

    private void MyDialog(final String bmstring, String cancel, String save) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("部门名称");
        View vv = LayoutInflater.from(getActivity()).inflate(R.layout.etdialog, null);
        bmname = (EditText)vv.findViewById(R.id.et_dialog);
        bmname.setText(bmstring);
        builder.setView(vv);
        builder.setCancelable(false);
        builder.setPositiveButton(save, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if (bmstring.equals("")){
                    String address_addbm="http://www.tytechkj.com/App/Permission/AddCurrentDepartment";
                    queryAddBm(address_addbm,bmname.getText().toString());
                }else {
                    String address_revisebm="http://www.tytechkj.com/App/Permission/UpdateCurrentDepartment";
                    queryReviseBm(address_revisebm,bmstring,bmname.getText().toString());
                }
            }
        });
        builder.setNegativeButton(cancel, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.show();
    }

    /**
     * ID 公司ID
     */
    private void queryBm(String address) {
        DialogMethod.MyProgressDialog(getContext(),"正在处理中...",true);
        RequestBody requestBody = new FormBody.Builder()
                .add("ID", String.valueOf(GetInstance().CompanyView.getC_ID()))
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                user = gson.fromJson(responseText, DepartmentInfo.class);

                if (!user.IsError) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogMethod.MyProgressDialog(getContext(),"",false);
                            mData.clear();
                            for(DepartmentViewModel department : user.Data){
                                BaseViewModel.GetInstance().setDepartment(department);
                                mData.add(new DepartmentViewModel(department.getC_Name(),department.getID(),department.getEmployeeCount()));
                            }
                            mAdapter = new MyDepartmentAdapter((LinkedList<DepartmentViewModel>) mData, mContext);
                            bmmanage_list.setAdapter(mAdapter);
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
                        Toast.makeText(getContext(),"获取部门失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * name 增加的部门名字
     * ID 公司ID
     */

    private void queryAddBm(String address,String bmname) {
        DialogMethod.MyProgressDialog(getContext(),"正在处理中...",true);
        RequestBody requestBody = new FormBody.Builder()
                .add("name",bmname)
                .add("ID", String.valueOf(GetInstance().CompanyView.getC_ID()))
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
                            String address_bm="http://www.tytechkj.com/App/Permission/GetCurrentDepartment";
                            queryBm(address_bm);
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
                        Toast.makeText(getContext(),"获取部门失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * unchangename 修改之前的部门名称
     * ID 公司ID
     * changename 修改之后的部门名称
     */

    private void queryReviseBm(String address,String unchangename,String changename) {
        DialogMethod.MyProgressDialog(getContext(),"正在处理中...",true);
        RequestBody requestBody = new FormBody.Builder()
                .add("UnChangeName",unchangename)
                .add("ID", String.valueOf(GetInstance().CompanyView.getC_ID()))
                .add("Changename",changename)
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
                            String address_bm="http://www.tytechkj.com/App/Permission/GetCurrentDepartment";
                            queryBm(address_bm);
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
                        Toast.makeText(getContext(),"修改部门失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
