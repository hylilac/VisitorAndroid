package com.example.visitorandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visitorandroid.Model.BaseViewModel;
import com.example.visitorandroid.Model.DialogMethod;
import com.example.visitorandroid.Model.MobileInfo;
import com.example.visitorandroid.Model.DepartmentInfo;
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

import static android.os.Build.VERSION_CODES.M;

public class
LoginActivity extends AppCompatActivity implements View.OnClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏
        }

        setContentView(R.layout.activity_login);

//        LinearLayout linearMain = (LinearLayout) findViewById(R.id.linear_main);
//        linearMain.getBackground().setAlpha(255);

        titleIcon = (ImageView) findViewById(R.id.title_icon);
        titleLogin = (TextView) findViewById(R.id.title_login_text);
        loginUsername = (EditText) findViewById(R.id.et_login_username);
        loginPassword = (EditText) findViewById(R.id.et_login_password);
        btLogin = (Button) findViewById(R.id.bt_login);
        btReg = (Button) findViewById(R.id.bt_login_reg);
        btForget = (Button) findViewById(R.id.bt_forget);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String account = prefs.getString("username",null);
        if (account !=null){
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
        String address_autologin="http://www.tytechkj.com/App/Permission/getcurrentloginuser";
        queryAutoLogin(address_autologin);
    }

    private void queryAutoLogin(String address) {
        DialogMethod.MyProgressDialog(this,"正在登录中...",true);
        String account = prefs.getString("username",null);
        RequestBody requestBody = new FormBody.Builder()
                .add("username",account)
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                BaseViewModel.GetInstance().setUser( gson.fromJson(responseText,UserViewModel.class));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogMethod.MyProgressDialog(LoginActivity.this,"",false);
                        String address_mobile="http://www.tytechkj.com/App/Permission/UpdateUserMobile";
                        queryMobile(address_mobile);
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogMethod.MyProgressDialog(LoginActivity.this,"",false);
                        Toast.makeText(LoginActivity.this,"自动登录请求失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
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
                String s= new Gson().toJson(user.Data);
                UserViewModel lll= new Gson().fromJson( s,UserViewModel.class);
                BaseViewModel.GetInstance().setUser( lll);
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
                            String address_mobile="http://www.tytechkj.com/App/Permission/UpdateUserMobile";
                            queryMobile(address_mobile);
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
        String UserID = BaseViewModel.GetInstance().getUser().getGUID();
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
