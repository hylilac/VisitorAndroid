package com.example.visitorandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visitorandroid.Model.DialogMethod;
import com.example.visitorandroid.util.HttpUtil;
import com.example.visitorandroid.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.attr.gravity;
import static android.R.attr.id;
import static android.R.attr.layout_alignParentRight;
import static android.R.attr.layout_gravity;
import static android.R.attr.layout_height;
import static android.R.attr.layout_marginTop;
import static android.R.attr.layout_width;
import static android.R.attr.orientation;
import static android.R.attr.padding;
import static android.R.attr.textColor;
import static android.R.attr.textColorHint;
import static android.R.attr.textSize;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView titleIcon;
    private TextView titleLogin;
    private EditText loginUsername;
    private EditText loginPassword;
    private Button btLogin;
    private Button btReg;
    private Button btForget;
    private Boolean loginResult;

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

        btLogin.setOnClickListener(this);
        btReg.setOnClickListener(this);
        btForget.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_login:
                isAutoLogin();
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
        String account = loginUsername.getText().toString();
        String password = loginPassword.getText().toString();
        if (!account.isEmpty() && !password.isEmpty()){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
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
        String account = loginUsername.getText().toString();
        String password = loginPassword.getText().toString();
        RequestBody requestBody = new FormBody.Builder()
                .add("username",account)
                .add("pwd",password)
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().toString();
                loginResult = Utility.handleLoginResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (loginResult){
                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this,"注册失败",
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
                        Toast.makeText(LoginActivity.this,"获取验证码失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
