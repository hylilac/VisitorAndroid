package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visitorandroid.Model.DialogMethod;
import com.example.visitorandroid.Model.UserInfo;
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
import static com.example.visitorandroid.Model.objData.GetInstance2;
import static com.example.visitorandroid.R.id.order_result_btn_accept;
import static com.example.visitorandroid.R.id.order_result_btn_back;
import static com.example.visitorandroid.R.id.order_result_btn_refuse;
import static com.example.visitorandroid.R.id.order_result_interviewee;
import static com.example.visitorandroid.R.id.order_result_time;
import static com.example.visitorandroid.R.id.order_result_visitor;

public class MyFragmentOrderResult extends Fragment implements View.OnClickListener {

    private String content;
    private Activity activity;

    private Button orderresult_btnback;
    private TextView orderresult_visitor;
    private TextView orderresult_interviewee;
    private TextView orderresult_time;
    private Button orderresult_btnaccept;
    private Button orderresult_btnrefuse;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }



    public MyFragmentOrderResult(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fg_orderresult,container,false);

        bindViews(view);

        return view;
    }

    private void bindViews(View view) {

        orderresult_btnback = (Button) view.findViewById(order_result_btn_back);
        orderresult_visitor = (TextView) view.findViewById(R.id.order_result_visitor);
        orderresult_interviewee = (TextView) view.findViewById(R.id.order_result_interviewee);
        orderresult_time = (TextView) view.findViewById(R.id.order_result_time);
        orderresult_btnaccept = (Button) view.findViewById(R.id.order_result_btn_accept);
        orderresult_btnrefuse = (Button) view.findViewById(R.id.order_result_btn_refuse);

        orderresult_visitor.setText(content.substring(0,1));
        orderresult_interviewee.setText(content.substring(6,8));
        orderresult_time.setText(content.substring(9));

        orderresult_btnback.setOnClickListener(this);
        orderresult_btnaccept.setOnClickListener(this);
        orderresult_btnrefuse.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_result_btn_back:
                activity.onBackPressed();
                break;
            case R.id.order_result_btn_accept:
                String address_acceptorder="http://www.tytechkj.com/App/Permission/";
//                queryAcceptOrder(address_acceptorder);
                break;
            case R.id.order_result_btn_refuse:
                String address_refuseorder="http://www.tytechkj.com/App/Permission/";
//                queryRefuseOrder(address_refuseorder);
                break;
        }
    }

//    private void queryAcceptOrder(String address) {
//        DialogMethod.MyProgressDialog(getContext(),"正在上传中...",true);
//        RequestBody requestBody = new FormBody.Builder()
//                .add("CID",GetInstance().User.getGUID())
//                .add("UID", String.valueOf(GetInstance2().data.getID()))
//                .add("DID",String.valueOf(GetInstance2().data.getPID()))
//                .build();
//        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String responseText = response.body().string();
//                Gson gson = new Gson();
//                users = gson.fromJson(responseText, UserInfo.class);
//                if (!users.IsError) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            DialogMethod.MyProgressDialog(getContext(),"",false);
//                            BackMethod();
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        DialogMethod.MyProgressDialog(getContext(),"",false);
//                        Toast.makeText(getContext(),"获取部门失败",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//    }

//    private void queryRefuseOrder(String address) {
//        DialogMethod.MyProgressDialog(getContext(),"正在上传中...",true);
//        RequestBody requestBody = new FormBody.Builder()
//                .add("CID",GetInstance().User.getGUID())
//                .add("UID", String.valueOf(GetInstance2().data.getID()))
//                .add("DID",String.valueOf(GetInstance2().data.getPID()))
//                .build();
//        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String responseText = response.body().string();
//                Gson gson = new Gson();
//                users = gson.fromJson(responseText, UserInfo.class);
//                if (!users.IsError) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            DialogMethod.MyProgressDialog(getContext(),"",false);
//                            BackMethod();
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        DialogMethod.MyProgressDialog(getContext(),"",false);
//                        Toast.makeText(getContext(),"获取部门失败",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//    }

    private void BackMethod() {
        activity.onBackPressed();
        Intent intent = new Intent("android.intent.action.CART_BROADCAST");
        intent.putExtra("data","refresh");
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }
}
