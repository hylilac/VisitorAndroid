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
import android.widget.TextView;
import android.widget.Toast;

import com.example.visitorandroid.Model.DialogMethod;
import com.example.visitorandroid.Model.MobileModel;
import com.example.visitorandroid.Model.UserInfo;
import com.example.visitorandroid.util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.visitorandroid.R.id.bt_code;

public class RegActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    private TextView titleReg;
    private Button regBackButton;
    private EditText regUsername;
    private EditText regCode;
    private Button btCode;
    private EditText regPassword;
    private EditText regPasswords;
    private EditText regNickname;
    private Button btReg;
    private CountDownTime mTime;

    private MobileModel mobile;
    private UserInfo user;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.rgb(56,143,249));
        }

        setContentView(R.layout.activity_reg);

        titleReg = (TextView) findViewById(R.id.title_reg_text);
        regBackButton = (Button) findViewById(R.id.reg_back_button);
        regUsername = (EditText) findViewById(R.id.et_reg_username);
        regCode = (EditText) findViewById(R.id.et_reg_code);
        btCode = (Button) findViewById(R.id.bt_code);
        regPassword = (EditText) findViewById(R.id.et_reg_password);
        regPasswords = (EditText) findViewById(R.id.et_reg_passwords);
        regNickname = (EditText) findViewById(R.id.et_reg_nickname);
        btReg = (Button) findViewById(R.id.bt_reg);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        btCode.setEnabled(false);

        regBackButton.setOnClickListener(this);
        btCode.setOnClickListener(this);
        btReg.setOnClickListener(this);

        regUsername.addTextChangedListener(this);

        mTime = new CountDownTime(60000, 1000);//初始化对象
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reg_back_button:
                Intent intent = new Intent(RegActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case bt_code:
                isCode();
                break;
            case R.id.bt_reg:
                Boolean result = isReg();
                if (result){
                    String address_reg="http://www.tytechkj.com/App/permission/Addemployee";
                    queryReg(address_reg);
                }
                break;
            default:
                break;
        }
    }

    private void isCode() {
        String account = regUsername.getText().toString();
        if (!account.isEmpty() && account.length() == 11) {
            new timeTask().execute();
            String address_code="http://www.tytechkj.com/App/SendService/SendMsgForMobile";
            queryMobile(address_code);
        } else if (account.isEmpty()) {
            DialogMethod.MyDialog(RegActivity.this, "手机号不能为空");
        } else if (account.length() != 11) {
            DialogMethod.MyDialog(RegActivity.this, "手机号格式错误");
        }
    }

    private Boolean isReg() {
        String code = regCode.getText().toString();
        String password = regPassword.getText().toString();
        String passwords = regPasswords.getText().toString();
        String nickname = regNickname.getText().toString();

        if (!code.equals(mobile.YZMCode)){
            DialogMethod.MyDialog(RegActivity.this, "验证码错误");
            return false;
        }
        if (!password.equals(passwords)){
            DialogMethod.MyDialog(RegActivity.this, "两次密码不一致");
            return false;
        }
        if (nickname.isEmpty()){
            DialogMethod.MyDialog(RegActivity.this, "昵称不能为空");
            return false;
        }
        return true;
    }

    private void queryMobile(String address) {
        DialogMethod.MyProgressDialog(RegActivity.this,"正在处理中...",true);
        String account = regUsername.getText().toString();
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

                        DialogMethod.MyProgressDialog(RegActivity.this,"",false);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        DialogMethod.MyProgressDialog(RegActivity.this,"",false);
                        Toast.makeText(RegActivity.this,"获取验证码失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void queryReg(String address_reg) {
        DialogMethod.MyProgressDialog(RegActivity.this,"正在注册中...",true);
        String account = regUsername.getText().toString();
        String password = regPassword.getText().toString();
        final String nickname = regNickname.getText().toString();
        RequestBody requestBody = new FormBody.Builder()
                .add("username",account)
                .add("password",password)
                .add("name",nickname)
                .build();
        HttpUtil.sendOkHttpRequest(address_reg, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                user = gson.fromJson(responseText,UserInfo.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (!user.IsError){
                            DialogMethod.MyProgressDialog(RegActivity.this,"",false);
                            SharedPreferences.Editor editor = PreferenceManager.
                                    getDefaultSharedPreferences(RegActivity.this).edit();

                            editor.putString("regUsername",regUsername.getText().toString());
                            editor.putString("regPassword",regPassword.getText().toString());
                            editor.putString("regNickname",regNickname.getText().toString());
                            editor.apply();

                            Intent intent_reg = new Intent(RegActivity.this,LoginActivity.class);
                            startActivity(intent_reg);
                            finish();
                        }else{

                            Toast.makeText(RegActivity.this,user.Message,
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

                        DialogMethod.MyProgressDialog(RegActivity.this,"",false);
                        Toast.makeText(RegActivity.this,"注册请求失败",
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
            btCode.setEnabled(false);
            btCode.setText("(" + l/1000 + "秒)"+ "后重新验证");
        }

        @Override
        public void onFinish() { //计时结束回调该方法
            btCode.setEnabled(true);
            btCode.setText("重新发送");
        }
    }

    class timeTask extends AsyncTask<Void,Integer,Boolean>{
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
        String account = regUsername.getText().toString();
        if(!account.isEmpty() && account.length()==11)
        {
            btCode.setEnabled(true);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
