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
                Intent intent_login = new Intent(this,MainActivity.class);
                startActivity(intent_login);
                finish();
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
}
