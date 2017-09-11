package com.example.visitorandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visitorandroid.Model.DialogMethod;
import com.example.visitorandroid.Model.MobileInfo;
import com.example.visitorandroid.Model.ResultViewModel;
import com.example.visitorandroid.Model.UserInfo;
import com.example.visitorandroid.Model.UserViewModel;
import com.example.visitorandroid.util.HttpUtil;
import com.example.visitorandroid.util.SystemUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView titleIcon;
    private TextView titleLogin;
    private EditText loginUsername;
    private EditText loginPassword;
    private Button btLogin;
    private Button btReg;
    private Button btForget;
    private UserInfo user;
    private MobileInfo mobile;
    private SharedPreferences prefs;

    private ResultViewModel viewmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        titleIcon = (ImageView) findViewById(R.id.title_icon);
        titleLogin = (TextView) findViewById(R.id.title_login_text);
        loginUsername = (EditText) findViewById(R.id.et_login_username);
        loginPassword = (EditText) findViewById(R.id.et_login_password);
        btLogin = (Button) findViewById(R.id.bt_login);
        btReg = (Button) findViewById(R.id.bt_login_reg);
        btForget = (Button) findViewById(R.id.bt_forget);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String account = prefs.getString("username",null);
        String password = prefs.getString("password",null);
        if (account !=null && password != null){
            isAutoLogin();
        }

        btLogin.setOnClickListener(this);
        btReg.setOnClickListener(this);
        btForget.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_login:
                Boolean result = isLogin();
                if (result){
                    String address_login="http://www.tytechkj.com/App/Permission/Login";
                    queryLogin(address_login);
                }
                break;
            case R.id.bt_login_reg:
                Intent intent_reg = new Intent(this,RegActivity.class);
                startActivity(intent_reg);
                finish();
                break;
            case R.id.bt_forget:

                break;
            default:
                break;
        }
    }

    private void isAutoLogin() {

        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private Boolean isLogin() {
        String account = loginUsername.getText().toString();
        String password = loginPassword.getText().toString();
        if (account.isEmpty()){
            DialogMethod.MyDialog(LoginActivity.this, "用户名不能为空");
            return false;
        }
        if (password.isEmpty()){
            DialogMethod.MyDialog(LoginActivity.this, "确认密码不能为空");
            return false;
        }
        return true;
    }

    private void queryLogin(String address) {
        DialogMethod.MyProgressDialog(this,"正在登录中...",true);
        String account = loginUsername.getText().toString();
        String password = loginPassword.getText().toString();
        RequestBody requestBody = new FormBody.Builder()
                .add("username",account)
                .add("pwd",password)
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                user = gson.fromJson(responseText,UserInfo.class);

                UserViewModel Model=UserViewModel.GetInstance();
                Model.setGUID("1234");
                Model.setUserName("456");
                String s= new Gson().toJson(user.Data);
              //  Model = new Gson().fromJson( s,UserViewModel.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!user.IsError){
                            DialogMethod.MyProgressDialog(LoginActivity.this,"",false);
                            SharedPreferences.Editor editor = PreferenceManager.
                                    getDefaultSharedPreferences(LoginActivity.this).edit();
                            editor.putString("username",loginUsername.getText().toString());
                            editor.putString("password",loginPassword.getText().toString());
                            editor.apply();
//                            String address_mobile="http://www.tytechkj.com/App/Permission/UpdateUserMobile";
//                            queryMobile(address_mobile);
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this,"登录失败",
                                    Toast.LENGTH_SHORT).show();
                            DialogMethod.MyProgressDialog(LoginActivity.this,"",false);
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
                        Toast.makeText(LoginActivity.this,"登录请求失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void queryMobile(String address) {
        String UserID = viewmodel.data.GUID;
        String mobileVersion = SystemUtil.getSystemVersion();
        String mobilePlat = "Android";
        String mobileModel = SystemUtil.getSystemModel();
        RequestBody requestBody = new FormBody.Builder()
                .add("UserID",UserID)
                .add("mobileVersion",mobileVersion)
                .add("mobilePlat",mobilePlat)
                .add("mobileModel",mobileModel)
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                mobile = gson.fromJson(responseText,MobileInfo.class);
                String ss = null;
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this,"获取信息请求失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
