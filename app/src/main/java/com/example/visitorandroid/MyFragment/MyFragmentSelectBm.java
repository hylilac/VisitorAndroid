package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.visitorandroid.Adapter.MyBmAdapter;
import com.example.visitorandroid.Adapter.MySelectBmAdapter;
import com.example.visitorandroid.Model.BmViewModel;
import com.example.visitorandroid.Model.Data;
import com.example.visitorandroid.Model.DialogMethod;
import com.example.visitorandroid.Model.ResultViewModel;
import com.example.visitorandroid.Model.UserInfo;
import com.example.visitorandroid.Model.objData;
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
import static com.example.visitorandroid.Model.objData.GetInstance2;

public class MyFragmentSelectBm extends Fragment implements View.OnClickListener, AdapterView
        .OnItemClickListener {

    private String content;
    private Activity activity;

    private Button selectbm_btnback;
    private ListView selectbm_list;

    private List<Data> mData = null;
    private Context mContext;
    private MySelectBmAdapter mAdapter = null;

    private EditText bmname;
    private ResultViewModel user;
    private UserInfo users;
    private String revisebm;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }



    public MyFragmentSelectBm(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_selectbm,container,false);

        bindViews(view);

        return view;
    }

    private void bindViews(View view) {
        selectbm_btnback = (Button) view.findViewById(R.id.select_bm_btn_back);
        selectbm_list = (ListView) view.findViewById(R.id.select_bm_list);

        selectbm_btnback.setText(content);

        mContext = getActivity();

        mData = new LinkedList<Data>();

        selectbm_btnback.setOnClickListener(this);
        selectbm_list.setOnItemClickListener(this);

        String address_bm="http://www.tytechkj.com/App/Permission/GetCurrentDepartment";
        queryBm(address_bm);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.select_bm_btn_back:
                activity.onBackPressed();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        revisebm = mData.get(position).getC_Name();

        String address_revisebm="http://www.tytechkj.com/App/Permission/ChangePersonalDepartment";
        queryReviseBm(address_revisebm,revisebm);
    }

    private void queryBm(String address) {
        DialogMethod.MyProgressDialog(getContext(),"正在上传中...",true);
        RequestBody requestBody = new FormBody.Builder()
                .add("ID", String.valueOf(GetInstance2().data.getID()))
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                user = gson.fromJson(responseText, ResultViewModel.class);
                if (!user.IsError) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogMethod.MyProgressDialog(getContext(),"",false);
                            mData.clear();
                            for(Data data : user.Data){
                                mData.add(new Data(data.getC_Name(),data.getEmployeeCount(),data.getNickName(),data.getDepartmentName()));
                            }
                            mAdapter = new MySelectBmAdapter((LinkedList<Data>) mData, mContext);
                            selectbm_list.setAdapter(mAdapter);
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

    private void queryReviseBm(String address,final String bmstring) {
        DialogMethod.MyProgressDialog(getContext(),"正在上传中...",true);
        RequestBody requestBody = new FormBody.Builder()
                .add("CID",GetInstance().User.getGUID())
                .add("UID", String.valueOf(GetInstance2().data.getID()))
                .add("DID",String.valueOf(GetInstance2().data.getPID()))
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                users = gson.fromJson(responseText, UserInfo.class);
                if (!users.IsError) {
                    //objData.GetInstance2().data.DepartmentName = bmstring;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogMethod.MyProgressDialog(getContext(),"",false);
                            BackMethod();
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

    private void BackMethod() {
        activity.onBackPressed();
        Intent intent = new Intent("android.intent.action.CART_BROADCAST");
        intent.putExtra("data","refresh");
        intent.putExtra("revisebm",revisebm);
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }
}
