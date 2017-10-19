package com.example.visitorandroid;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
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

import com.ansen.http.net.HTTPCaller;
import com.ansen.http.net.RequestDataCallback;
import com.example.visitorandroid.Model.BaseViewModel;
import com.example.visitorandroid.Model.DialogMethod;
import com.example.visitorandroid.Model.MobileInfo;
import com.example.visitorandroid.Model.DepartmentInfo;
import com.example.visitorandroid.Model.UserInfo;
import com.example.visitorandroid.Model.UserViewModel;
import com.example.visitorandroid.bean.WeiXin;
import com.example.visitorandroid.bean.WeiXinInfo;
import com.example.visitorandroid.bean.WeiXinPay;
import com.example.visitorandroid.bean.WeiXinToken;
import com.example.visitorandroid.util.HttpUtil;
import com.example.visitorandroid.util.SystemUtil;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.ShowMessageFromWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.provider.UserDictionary.Words.APP_ID;
import static com.example.visitorandroid.Model.BaseViewModel.GetInstance;
import static com.example.visitorandroid.R.id.nav_sub_headericon;
import static com.example.visitorandroid.R.id.nav_sub_nickname;
import static com.example.visitorandroid.R.id.nav_sub_tel;

public class LoginActivity extends Activity implements View.OnClickListener {

    private ImageView titleIcon;
    private TextView titleLogin;
    private EditText loginUsername;
    private EditText loginPassword;
    private Button btLogin;
    private Button btReg;
    private Button btForget;
    private Button btnWeChat;

    private UserInfo user;
    private MobileInfo mobile;
    private SharedPreferences prefs;

    public static String APP_ID = "wxddab69c0697cb4e2";
    public static String App_Secret = "60781ad3fbaeacb0bd35eed748861949";
    private IWXAPI wxAPI;
    public static final int IMAGE_SIZE=32768;//微信分享图片大小限制


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

        EventBus.getDefault().register(this);//注册
        wxAPI = WXAPIFactory.createWXAPI(this,APP_ID,true);
        wxAPI.registerApp(APP_ID);

        bindViews();

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String account = prefs.getString("username",null);
        if (account !=null){
            isAutoLogin();
        }
    }

    private void bindViews() {

        titleIcon = (ImageView) findViewById(R.id.title_icon);
        titleLogin = (TextView) findViewById(R.id.title_login_text);
        loginUsername = (EditText) findViewById(R.id.et_login_username);
        loginPassword = (EditText) findViewById(R.id.et_login_password);
        btLogin = (Button) findViewById(R.id.bt_login);
        btReg = (Button) findViewById(R.id.bt_login_reg);
        btForget = (Button) findViewById(R.id.bt_forget);
        btnWeChat = (Button) findViewById(R.id.btn_weChat);

        btLogin.setOnClickListener(this);
        btReg.setOnClickListener(this);
        btForget.setOnClickListener(this);
        btnWeChat.setOnClickListener(this);
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
            case R.id.btn_weChat:

                login();

                break;
        }
    }

    @Subscribe
    public void onEventMainThread(WeiXin weiXin){
        Log.i("ansen","收到eventbus请求 type:"+weiXin.getType());
        if(weiXin.getType()==1){//登录
            getAccessToken(weiXin.getCode());
        }else if(weiXin.getType()==2){//分享
            switch (weiXin.getErrCode()){
                case BaseResp.ErrCode.ERR_OK:
                    Log.i("ansen", "微信分享成功.....");
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL://分享取消
                    Log.i("ansen", "微信分享取消.....");
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED://分享被拒绝
                    Log.i("ansen", "微信分享被拒绝.....");
                    break;
            }
        }else if(weiXin.getType()==3){//微信支付
            if(weiXin.getErrCode()==BaseResp.ErrCode.ERR_OK){//成功
                Log.i("ansen", "微信支付成功.....");
            }
        }
    }

    private void login() {
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = String.valueOf(System.currentTimeMillis());
        wxAPI.sendReq(req);
    }

    public void getAccessToken(String code){
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid="+APP_ID+"&secret="+App_Secret+
                "&code="+code+"&grant_type=authorization_code";
        HTTPCaller.getInstance().get(WeiXinToken.class, url, null, new RequestDataCallback<WeiXinToken>() {
            @Override
            public void dataCallback(WeiXinToken obj) {
                if(obj.getErrcode()==0){//请求成功
                    getWeiXinUserInfo(obj);
                    BaseViewModel.GetInstance().setWeiXinToken(obj);
                }else{//请求失败
                    showToast(obj.getErrmsg());
                }
            }
        });
    }

    public void getWeiXinUserInfo(WeiXinToken weiXinToken){
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+
                weiXinToken.getAccess_token()+"&openid="+weiXinToken.getOpenid();
        HTTPCaller.getInstance().get(WeiXinInfo.class, url, null, new RequestDataCallback<WeiXinInfo>() {
            @Override
            public void dataCallback(WeiXinInfo obj) {
                BaseViewModel.GetInstance().setWeiXinInfo(obj);

                if (!obj.getNickname().isEmpty()){
                    Intent intent = new Intent(LoginActivity.this,InfoPerfect.class);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 微信分享
     * @param friendsCircle  是否分享到朋友圈
     */
    public void share(boolean friendsCircle){
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = "www.baidu.com";//分享url
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "分享标题";
        msg.description = "分享描述";
        msg.thumbData =getThumbData();//封面图片byte数组

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = friendsCircle ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        wxAPI.sendReq(req);
    }

    /**
     * 获取分享封面byte数组 我们这边取的是软件启动icon
     * @return
     */
    private  byte[] getThumbData() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize=2;
        Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher,options);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        int quality = 100;
        while (output.toByteArray().length > IMAGE_SIZE && quality != 10) {
            output.reset(); // 清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, output);// 这里压缩options%，把压缩后的数据存放到baos中
            quality -= 10;
        }
        bitmap.recycle();
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发起支付
     * @param weiXinPay
     */
    public void pay(WeiXinPay weiXinPay){
        PayReq req = new PayReq();
        req.appId = APP_ID;//appid
        req.nonceStr=weiXinPay.getNoncestr();//随机字符串，不长于32位。推荐随机数生成算法
        req.packageValue=weiXinPay.getPackage_value();//暂填写固定值Sign=WXPay
        req.sign=weiXinPay.getSign();//签名
        req.partnerId=weiXinPay.getPartnerid();//微信支付分配的商户号
        req.prepayId=weiXinPay.getPrepayid();//微信返回的支付交易会话ID
        req.timeStamp=weiXinPay.getTimestamp();//时间戳

        wxAPI.registerApp(APP_ID);
        wxAPI.sendReq(req);
    }

    public void showToast(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//取消注册
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogMethod.MyProgressDialog(LoginActivity.this,"",false);

                        if (!user.IsError){

                            String s= new Gson().toJson(user.Data);
                            UserViewModel lll= new Gson().fromJson( s,UserViewModel.class);
                            BaseViewModel.GetInstance().setUser( lll);

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

                            Toast.makeText(LoginActivity.this,user.Message,
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

                        DialogMethod.MyProgressDialog(LoginActivity.this,"",false);
                        Toast.makeText(LoginActivity.this,"登录请求失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void queryMobile(String address) {
        DialogMethod.MyProgressDialog(this,"正在处理中...",true);
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        DialogMethod.MyProgressDialog(LoginActivity.this,"",false);
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
                        Toast.makeText(LoginActivity.this,"获取信息请求失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
