package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.visitorandroid.Model.BaseViewModel;
import com.example.visitorandroid.Model.DialogMethod;
import com.example.visitorandroid.Model.MobileModel;
import com.example.visitorandroid.Model.UserInfo;
import com.example.visitorandroid.Model.UserViewModel;
import com.example.visitorandroid.R;
import com.example.visitorandroid.RegActivity;
import com.example.visitorandroid.util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.visitorandroid.Model.BaseViewModel.GetInstance;
import static com.example.visitorandroid.R.id.et_nav_nickname;

public class MyFragmentTel extends Fragment implements View.OnClickListener, TextWatcher {

    private String content;
    private Activity activity;

    private EditText et_navtel;
    private Button tel_btnback;
    private Button tel_btnsave;
    private UserInfo user;
    private Button nav_btncode;
    private CountDownTime mTime;
    private MobileModel mobile;
    private EditText et_navcode;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public MyFragmentTel(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_tel,container,false);

        bindViews(view);

        return view;
    }

    private void bindViews(View view) {

        et_navtel = (EditText) view.findViewById(R.id.et_nav_tel);
        et_navcode = (EditText) view.findViewById(R.id.et_nav_code);
        nav_btncode = (Button) view.findViewById(R.id.nav_btn_code);
        tel_btnback = (Button) view.findViewById(R.id.tel_btn_back);
        tel_btnsave = (Button) view.findViewById(R.id.tel_btn_save);

        et_navtel.setText(GetInstance().User.getMobile());
        nav_btncode.setEnabled(false);
        tel_btnsave.setEnabled(false);
        tel_btnsave.setTextColor(Color.GRAY);

        et_navtel.addTextChangedListener(this);
        nav_btncode.setOnClickListener(this);
        tel_btnback.setOnClickListener(this);
        tel_btnsave.setOnClickListener(this);

        mTime = new CountDownTime(60000, 1000);//初始化对象
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tel_btn_back:
                et_navtel.setText(GetInstance().User.getMobile());
                BackMethod();
                break;
            case R.id.tel_btn_save:
                String tel = et_navtel.getText().toString();
                String code = et_navcode.getText().toString();
                if (code.equals(mobile.YZMCode)){
                    String address_tel="http://www.tytechkj.com/App/Permission/ChangeMobile";
                    queryTel(address_tel,tel);
                }else {
                    DialogMethod.MyDialog(getActivity(), "验证码错误");
                }

                break;
            case R.id.nav_btn_code:
                isCode();
                break;
        }
    }

    private void isCode() {
        String account = et_navtel.getText().toString();
        if (!account.isEmpty() && account.length() == 11) {
            new timeTask().execute();
            String address_code = "http://www.tytechkj.com/App/SendService/SendMsgForMobile";
            queryCode(address_code,account);
        } else if (account.isEmpty()) {
            DialogMethod.MyDialog(getActivity(), "手机号不能为空");
        } else if (account.length() != 11) {
            DialogMethod.MyDialog(getActivity(), "手机号格式错误");
        }
    }

    private void queryCode(String address,String codestring) {
        RequestBody requestBody = new FormBody.Builder()
                .add("mobile",codestring)
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                mobile = gson.fromJson(responseText,MobileModel.class);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),"获取验证码失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void queryTel(String address, final String telstring) {
        DialogMethod.MyProgressDialog(getContext(),"正在上传中...",true);
        RequestBody requestBody = new FormBody.Builder()
                .add("ID", GetInstance().User.getGUID())
                .add("Mobile",telstring)
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                user = gson.fromJson(responseText, UserInfo.class);
                if (!user.IsError) {
                    GetInstance().User.Mobile = telstring;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            et_navtel.setText(telstring);
                            DialogMethod.MyProgressDialog(getContext(), "", false);
                            BackMethod();
                            et_navcode.setText("");
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
                        Toast.makeText(getContext(),"上传手机号失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * 第一种方法 使用android封装好的 CountDownTimer
     * 创建一个类继承 CountDownTimer
     */
    class CountDownTime extends CountDownTimer {

        //构造函数  第一个参数代表总的计时时长  第二个参数代表计时间隔  单位都是毫秒
        public CountDownTime(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) { //每计时一次回调一次该方法
            nav_btncode.setEnabled(false);
            nav_btncode.setText("(" + l/1000 + "秒)"+ "后重新验证");
        }

        @Override
        public void onFinish() { //计时结束回调该方法
            nav_btncode.setEnabled(true);
            nav_btncode.setText("重新发送");
        }
    }

    class timeTask extends AsyncTask<Void,Integer,Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            try{
                mTime.start(); //开始计时
            }catch (Exception e){
                return false;
            }
            return true;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!et_navtel.getText().toString().equals(GetInstance().User.getMobile())){
            tel_btnsave.setTextColor(Color.WHITE);
            tel_btnsave.setEnabled(true);
        }else if (et_navtel.getText().toString().equals(GetInstance().User.getMobile())){
            tel_btnsave.setTextColor(Color.GRAY);
            tel_btnsave.setEnabled(false);
        }

        if(!et_navtel.getText().toString().isEmpty() && et_navtel.getText().toString().length()==11)
        {
            nav_btncode.setEnabled(true);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private void BackMethod() {
        activity.onBackPressed();
        Intent intent = new Intent("android.intent.action.CART_BROADCAST");
        intent.putExtra("data","refresh");
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }
}