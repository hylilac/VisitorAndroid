package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.visitorandroid.MainActivity;
import com.example.visitorandroid.Model.BaseViewModel;
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

import static android.R.id.edit;
import static com.example.visitorandroid.Model.BaseViewModel.GetInstance;
import static com.example.visitorandroid.R.id.order_manage_car;
import static com.example.visitorandroid.R.id.order_manage_reason;

public class MyFragmentOrderManage extends Fragment implements View.OnClickListener{

    private String content;
    private Activity activity;

    private Button ordermanager_btnback;
    private EditText v_name;
    private EditText carNum;
    private EditText bv_name;
    private EditText area;
    private EditText visitortime;
    private EditText mobile;
    private EditText reason;
    private Button ordermanage_btncommit;
    private String result;
    private int hour;
    private int minute;
    private UserInfo user;

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
        v_name = (EditText) view.findViewById(R.id.order_manage_visitor);
        carNum = (EditText) view.findViewById(order_manage_car);
        bv_name = (EditText) view.findViewById(R.id.order_manage_interviewee);
        area = (EditText) view.findViewById(R.id.order_manage_company);
        visitortime = (EditText) view.findViewById(R.id.order_manage_time);
        mobile = (EditText) view.findViewById(R.id.order_manage_tel);
        reason = (EditText) view.findViewById(R.id.order_manage_reason);
        ordermanage_btncommit = (Button) view.findViewById(R.id.order_manage_btn_commit);

        area.setText(GetInstance().CompanyView.getC_Name());

        ordermanager_btnback.setOnClickListener(this);
        visitortime.setOnClickListener(this);
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
                        visitortime.setText(result);
                    }
                }
                        ,cale1.get(Calendar.YEAR)
                        ,cale1.get(Calendar.MONTH)
                        ,cale1.get(Calendar.DAY_OF_MONTH)).show();

                break;
            case R.id.order_manage_btn_commit:
                Boolean k = isCommit();
                if (k){
                    String address_commit="http://www.tytechkj.com/App/Permission/InsertVisitorOrder";
                    queryCommit(address_commit);
                }
                break;
        }
    }

    private Boolean isCommit() {
        String vName = v_name.getText().toString();
        String bvName = bv_name.getText().toString();
        String Mobile = mobile.getText().toString();
        if (vName.isEmpty() || bvName.isEmpty()){
            DialogMethod.MyDialog(getContext(),"申请人和拜访人不能为空！");
            return false;
        }
        if (Mobile.isEmpty() || (Mobile.length() != 11)){
            DialogMethod.MyDialog(getContext(),"手机号格式错误");
            mobile.setText("");
            return false;
        }
        return true;
    }

    /**
     * v_name 申请者
     * carNum 车牌号
     * bv_name 拜访人
     * area 公司名称
     * mobile 手机号
     * content 拜访事由
     * order_state 订单状态
     * visitortime 拜访时间
     * UserID 当前用户ID
     */

    private void queryCommit(String address) {
        DialogMethod.MyProgressDialog(getContext(),"正在上传中...",true);
        RequestBody requestBody = new FormBody.Builder()
                .add("v_name",v_name.getText().toString())
                .add("carNum",carNum.getText().toString())
                .add("bv_name",bv_name.getText().toString())
                .add("area",area.getText().toString())
                .add("mobile",mobile.getText().toString())
                .add("content",reason.getText().toString())
                .add("order_state","接受")
                .add("visitortime",visitortime.getText().toString())
                .add("UserID",GetInstance().User.getGUID())
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                user = gson.fromJson(responseText, UserInfo.class);
                if (!user.IsError) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogMethod.MyProgressDialog(getContext(),"",false);
                            v_name.setText("");
                            carNum.setText("");
                            bv_name.setText("");
                            area.setText("");
                            mobile.setText("");
                            reason.setText("");
                            visitortime.setText("");
                            v_name.requestFocus(); //请求获取焦点
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
                        Toast.makeText(getContext(),"提交预约单失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
