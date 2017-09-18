package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.visitorandroid.MainActivity;
import com.example.visitorandroid.Model.DialogMethod;
import com.example.visitorandroid.Model.UserInfo;
import com.example.visitorandroid.R;
import com.example.visitorandroid.util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.visitorandroid.Model.objData.GetInstance2;

public class MyFragmentOrderManage extends Fragment implements View.OnClickListener {

    private String content;
    private Activity activity;

    private Button ordermanager_btnback;
    private EditText ordermanage_visitor;
    private EditText ordermanage_interviewee;
    private EditText ordermanage_company;
    private EditText ordermanage_time;
    private EditText ordermanage_tel;
    private Button ordermanage_btncommit;
    private String result;
    private int hour;
    private int minute;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public MyFragmentOrderManage(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_ordermanage,container,false);

        bindViews(view);

        return view;
    }

    private void bindViews(View view) {

        ordermanager_btnback = (Button) view.findViewById(R.id.order_manager_btn_back);
        ordermanage_visitor = (EditText) view.findViewById(R.id.order_manage_visitor);
        ordermanage_interviewee = (EditText) view.findViewById(R.id.order_manage_interviewee);
        ordermanage_company = (EditText) view.findViewById(R.id.order_manage_company);
        ordermanage_time = (EditText) view.findViewById(R.id.order_manage_time);
        ordermanage_tel = (EditText) view.findViewById(R.id.order_manage_tel);
        ordermanage_btncommit = (Button) view.findViewById(R.id.order_manage_btn_commit);

        ordermanage_company.setText("");

        ordermanager_btnback.setOnClickListener(this);
        ordermanage_time.setOnClickListener(this);
        ordermanage_btncommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_manager_btn_back:
                activity.onBackPressed();
                break;
            case R.id.order_manage_time:
                result = "";
                Calendar cale1 = Calendar.getInstance();
                hour = cale1.get(Calendar.HOUR_OF_DAY);
                minute = cale1.get(Calendar.MINUTE);
                new DatePickerDialog(getContext(),new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        result += year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日" +
                                hour + "时" + minute + "分";
                        ordermanage_time.setText(result);
                    }
                }
                        ,cale1.get(Calendar.YEAR)
                        ,cale1.get(Calendar.MONTH)
                        ,cale1.get(Calendar.DAY_OF_MONTH)).show();

                break;
            case R.id.order_manage_btn_commit:
//                String address_commit="http://www.tytechkj.com/App/Permission/GetCurrentDepartment";
//                queryCommit(address_commit);
                break;
        }
    }

//    private void queryCommit(String address) {
//        DialogMethod.MyProgressDialog(getContext(),"正在上传中...",true);
//        RequestBody requestBody = new FormBody.Builder()
//                .add("name",bmname)
//                .add("ID", String.valueOf(GetInstance2().data.getID()))
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
//                            String address_bm="http://www.tytechkj.com/App/Permission/GetCurrentDepartment";
//                            queryBm(address_bm);
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

}
