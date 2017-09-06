package com.example.visitorandroid;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
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
import com.example.visitorandroid.util.HttpUtil;
import com.example.visitorandroid.util.Utility;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.example.visitorandroid.R.id.bt_code;

public class RegActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView titleReg;
    private Button backButton;
    private TextView titleBackText;
    private EditText regusername;
    private EditText regCode;
    private Button btCode;
    private EditText regPassword;
    private EditText regPasswords;
    private EditText regNickname;
    private Button btReg;
    private CountDownTime mTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_reg);

        titleReg = (TextView) findViewById(R.id.title_reg_text);
        backButton = (Button) findViewById(R.id.back_button);
        titleBackText = (TextView) findViewById(R.id.title_back_text);
        regusername = (EditText) findViewById(R.id.et_reg_username);
        regCode = (EditText) findViewById(R.id.et_reg_code);
        btCode = (Button) findViewById(bt_code);
        regPassword = (EditText) findViewById(R.id.et_reg_password);
        regPasswords = (EditText) findViewById(R.id.et_reg_passwords);
        regNickname = (EditText) findViewById(R.id.et_reg_nickname);
        btReg = (Button) findViewById(R.id.bt_reg);

        btCode.setEnabled(false);

        backButton.setOnClickListener(this);
        btCode.setOnClickListener(this);
        btReg.setOnClickListener(this);

        regusername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String account = regusername.getText().toString();
                if(!account.isEmpty() && account.length()==11)
                {
                    btCode.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mTime = new CountDownTime(10000, 1000);//初始化对象
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_button:
                break;
            case bt_code:

                String account = regusername.getText().toString();

                if (!account.isEmpty() && account.length() == 11){
                    mTime.start(); //开始计时
                }else if (account.isEmpty()){
                    DialogMethod.MyDialog(RegActivity.this,"手机号不能为空");
                }else if (account.length() != 11){
                    DialogMethod.MyDialog(RegActivity.this,"手机号格式错误");
                }
//                Pattern p = Pattern.compile("^(13[0-9]|14[57]|15[0-35-9]|17[6-8]|18[0-9])[0-9]{8}$");
//                Matcher m = p.matcher(account);
//                if (m.matches()){
//                    mTime.start(); //开始计时
//                }

//                String address_code="http://www.tytechkj.com/App/SendService/SendMsgForMobile";
//                queryMobile(address_code);
                break;
            case R.id.bt_reg:
//                String address_reg="http://www.tytechkj.com/App/permission/Addemployee";
//                queryReg(address_reg);
                break;
            default:
                break;
        }
    }

    private void queryMobile(String address) {
        RequestBody requestBody = new FormBody.Builder()
                .add("mobile",regusername.getText().toString())
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().toString();
                String result = Utility.handleMobileResponse(responseText);
                if (result.equals(regCode.getText().toString())){

                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegActivity.this,"获取验证码失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void queryReg(String address_reg) {
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
}
