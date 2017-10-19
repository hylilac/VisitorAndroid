package com.example.visitorandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.visitorandroid.Model.BaseViewModel;
import com.example.visitorandroid.Model.DialogMethod;
import com.example.visitorandroid.Model.MobileModel;
import com.example.visitorandroid.Model.UserInfo;
import com.example.visitorandroid.Model.UserViewModel;
import com.example.visitorandroid.util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.attr.password;

public class InfoPerfect extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private EditText etInfoTel;
    private EditText etInfoCode;
    private Button btnInfoCode;
    private Button btnInfoReg;
    private SharedPreferences prefs;
    private CountDownTime mTime;
    private MobileModel mobile;
    private UserInfo user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_perfect);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.rgb(56,143,249));
        }

        bindViews();
    }

    private void bindViews() {
        etInfoTel = (EditText) findViewById(R.id.et_info_tel);
        etInfoCode = (EditText) findViewById(R.id.et_info_code);
        btnInfoCode = (Button) findViewById(R.id.btn_info_code);
        btnInfoReg = (Button) findViewById(R.id.btn_info_reg);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        btnInfoCode.setEnabled(false);

        etInfoTel.addTextChangedListener(this);

        mTime = new CountDownTime(60000, 1000);//初始化对象

        btnInfoCode.setOnClickListener(this);
        btnInfoReg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_info_code:
                isCode();
                break;
            case R.id.btn_info_reg:
                Boolean result = isReg();
                if (result){
                    String address_wxreg="http://www.tytechkj.com/App/wechat/InsertWeChatUser";
                    queryWXReg(address_wxreg);
                }
                break;
        }
    }

    private void isCode() {
        String account = etInfoTel.getText().toString();
        if (!account.isEmpty() && account.length() == 11) {
            new timeTask().execute();
            String address_code="http://www.tytechkj.com/App/SendService/SendMsgForMobile";
            queryMobile(address_code);
        } else if (account.isEmpty()) {
            DialogMethod.MyDialog(this, "手机号不能为空");
        } else if (account.length() != 11) {
            DialogMethod.MyDialog(this, "手机号格式错误");
        }
    }

    private Boolean isReg() {
        String code = etInfoCode.getText().toString();

        if (!code.equals(mobile.YZMCode)){
            DialogMethod.MyDialog(this, "验证码错误");
            return false;
        }
        return true;
    }

    private void queryMobile(String address) {
        DialogMethod.MyProgressDialog(this,"正在处理中...",true);
        String account = etInfoTel.getText().toString();
        RequestBody requestBody = new FormBody.Builder()
                .add("mobile",account)
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                mobile = gson.fromJson(responseText,MobileModel.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogMethod.MyProgressDialog(InfoPerfect.this,"",false);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        DialogMethod.MyProgressDialog(InfoPerfect.this,"",false);
                        Toast.makeText(InfoPerfect.this,"获取验证码失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void queryWXReg(String address) {
        DialogMethod.MyProgressDialog(this,"正在注册中...",true);
        RequestBody requestBody = new FormBody.Builder()
                .add("mobile",etInfoTel.getText().toString())
                .add("OpenID",BaseViewModel.GetInstance().weiXinInfo.getOpenid())
                .add("Area","微信开放平台")
                .add("Token",BaseViewModel.GetInstance().weiXinToken.getAccess_token())
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                user = gson.fromJson(responseText,UserInfo.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        DialogMethod.MyProgressDialog(InfoPerfect.this,"",false);

                        if (!user.IsError){

                            String s= new Gson().toJson(user.Data);
                            UserViewModel lll= new Gson().fromJson( s,UserViewModel.class);
                            BaseViewModel.GetInstance().setUser( lll);

                            SharedPreferences.Editor editor = PreferenceManager.
                                    getDefaultSharedPreferences(InfoPerfect.this).edit();
                            editor.putString("username",etInfoTel.getText().toString());
                            editor.apply();

                            Intent intent = new Intent(InfoPerfect.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{

                            Toast.makeText(InfoPerfect.this,user.Message,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        DialogMethod.MyProgressDialog(InfoPerfect.this,"",false);
                        Toast.makeText(InfoPerfect.this,"注册请求失败",
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
            btnInfoCode.setEnabled(false);
            btnInfoCode.setText("(" + l/1000 + "秒)"+ "后重新验证");
        }

        @Override
        public void onFinish() { //计时结束回调该方法
            btnInfoCode.setEnabled(true);
            btnInfoCode.setText("重新发送");
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
        String account = etInfoTel.getText().toString();
        if(!account.isEmpty() && account.length()==11)
        {
            btnInfoCode.setEnabled(true);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
