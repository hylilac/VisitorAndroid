package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visitorandroid.Adapter.MyCompanyListAdapter;
import com.example.visitorandroid.Adapter.MyOrderListAdapter;
import com.example.visitorandroid.Model.BaseViewModel;
import com.example.visitorandroid.Model.CompanyViewModel;
import com.example.visitorandroid.Model.DepartmentInfo;
import com.example.visitorandroid.Model.DialogMethod;
import com.example.visitorandroid.Model.OrderListViewModel;
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

import static android.R.attr.order;
import static com.example.visitorandroid.Model.BaseViewModel.GetInstance;
import static com.example.visitorandroid.R.id.et_joincompany;
import static com.example.visitorandroid.R.id.order_list;

public class MyFragmentJoinCompany extends Fragment implements View.OnClickListener, AdapterView
        .OnItemClickListener {

    private String content;
    private Activity activity;

    private UserInfo user;
    private Button compangylist_btnback;
    private TextView joincompany;
    private ListView companylist;

    private List<CompanyViewModel> mData = null;
    private Context mContext;
    private MyCompanyListAdapter mAdapter = null;

    private List<CompanyViewModel> companyList = null;
    private ListView company_listview;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public MyFragmentJoinCompany(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_joincompany,container,false);

        bindViews(view);

        return view;
    }

    private void bindViews(View view) {

        compangylist_btnback = (Button) view.findViewById(R.id.compangy_list_btn_back);
        joincompany = (TextView) view.findViewById(R.id.et_joincompany);
        company_listview = (ListView) view.findViewById(R.id.company_list_view);

        mContext = getActivity();
        mData = new LinkedList<CompanyViewModel>();

        compangylist_btnback.setOnClickListener(this);
        company_listview.setOnItemClickListener(this);
        String address_companylist="http://www.tytechkj.com/App/Permission/GetCompanyList";
        queryCompanyList(address_companylist);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.compangy_list_btn_back:
                activity.onBackPressed();
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        int companyId = mData.get(position).getID();
        String address_joincompany="http://www.tytechkj.com/App/Permission/UserJointCompany";
        queryJoinCompany(address_joincompany,companyId);
    }

    private void queryCompanyList(String address) {
        DialogMethod.MyProgressDialog(getActivity(),"正在处理中...",true);
        HttpUtil.sendOkHttpRequestNoParams(address,new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                companyList = gson.fromJson(responseText,
                        new TypeToken<List<CompanyViewModel>>(){}.getType());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogMethod.MyProgressDialog(getContext(), "", false);
                        mData.clear();
                        for (CompanyViewModel company : companyList){
                            mData.add(new CompanyViewModel(company.getID(),company.getC_Name()));
                        }
                        mAdapter = new MyCompanyListAdapter((LinkedList<CompanyViewModel>) mData, mContext);
                        company_listview.setAdapter(mAdapter);
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
                        Toast.makeText(getContext(),"获取公司列表失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void queryJoinCompany(String address,int companyId) {
        String aa = BaseViewModel.GetInstance().User.getGUID();
        int ss = companyId;
        DialogMethod.MyProgressDialog(getActivity(),"正在处理中...",true);
        RequestBody requestBody = new FormBody.Builder()
                .add("UserID",BaseViewModel.GetInstance().User.getGUID())
                .add("CategoryID",String.valueOf(companyId))
                .build();
        HttpUtil.sendOkHttpRequest(address,requestBody,new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                user = gson.fromJson(responseText, UserInfo.class);
                if (!user.IsError) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogMethod.MyProgressDialog(getContext(), "", false);
                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setMessage("加入企业成功，请等待企业审核！");
                            builder.setCancelable(false);
                            builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog,int which){
                                    activity.onBackPressed();
                                }
                            });
                            builder.show();
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
                        Toast.makeText(getContext(),"加入公司失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
