package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visitorandroid.Adapter.MyCompanyListAdapter;
import com.example.visitorandroid.Model.BaseViewModel;
import com.example.visitorandroid.Model.CompanyViewModel;
import com.example.visitorandroid.Model.DialogMethod;
import com.example.visitorandroid.Model.UserInfo;
import com.example.visitorandroid.R;
import com.example.visitorandroid.util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.visitorandroid.R.id.et_createcompany;

public class MyFragmentCreateCompany extends Fragment implements View.OnClickListener {

    public String content;
    private Activity activity;
    private Button createcompany_btnback;
    private EditText createcompany;
    private Button btn_createcompany;
    private UserInfo user;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public MyFragmentCreateCompany(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_createcompany,container,false);

        bindViews(view);

        return view;
    }

    private void bindViews(View view) {

        createcompany_btnback = (Button) view.findViewById(R.id.create_company_btn_back);
        createcompany = (EditText) view.findViewById(R.id.et_createcompany);
        btn_createcompany = (Button) view.findViewById(R.id.btn_create_company);

        createcompany_btnback.setOnClickListener(this);
        btn_createcompany.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.create_company_btn_back:
                activity.onBackPressed();
                break;
            case R.id.btn_create_company:
                if (createcompany.getText().toString().isEmpty()){
                    DialogMethod.MyDialog(getContext(),"公司名称不能为空");
                }else{
                    String address_createcompany="http://www.tytechkj.com/App/Permission/CreatCompany";
                    queryCreateCompany(address_createcompany,createcompany.getText().toString());
                }
                break;
        }
    }

    private void queryCreateCompany(String address,String companyname) {
        DialogMethod.MyProgressDialog(getActivity(),"正在处理中...",true);
        RequestBody requestBody = new FormBody.Builder()
                .add("ID",BaseViewModel.GetInstance().User.getGUID())
                .add("CategoryID",companyname)
                .build();
        HttpUtil.sendOkHttpRequest(address,requestBody,new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                user = gson.fromJson(responseText, UserInfo.class);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        DialogMethod.MyProgressDialog(getContext(), "", false);

                        if (!user.IsError) {
                            activity.onBackPressed();
                        }else{
                            Toast.makeText(getContext(),user.Message,
                                    Toast.LENGTH_SHORT).show();
                        }
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
                        Toast.makeText(getContext(),"创建公司失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}