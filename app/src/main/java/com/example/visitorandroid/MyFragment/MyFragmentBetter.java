package com.example.visitorandroid.MyFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visitorandroid.Model.BaseViewModel;
import com.example.visitorandroid.Model.CompanyViewModel;
import com.example.visitorandroid.Model.DialogMethod;
import com.example.visitorandroid.Model.UserInfo;
import com.example.visitorandroid.Model.UserViewModel;
import com.example.visitorandroid.R;
import com.example.visitorandroid.util.HttpUtil;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.visitorandroid.Model.BaseViewModel.GetInstance;
import static com.example.visitorandroid.R.id.nav_sub_headericon;
import static com.squareup.picasso.Picasso.with;
import static java.lang.System.load;

public class MyFragmentBetter extends Fragment implements View.OnClickListener {

    public String content;

    //Fragment Object
    private MyFragmentHeader fgHeader;
    private MyFragmentManage fgManage;
    private MyFragmentInMessage fgInManage;
    private MyFragmentSetting fgSetting;

    private CircleImageView icon_image;
    private TextView nav_username;
    private TextView nav_manage;
    private TextView nav_message;
    private TextView nav_history;
    private TextView nav_setting;
    private UserInfo user;
    private MyFragmentManageKey fgManageKey;

    public MyFragmentBetter(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_better,container,false);

        bindViews(view);

        return view;
    }

    private  void bindViews(View view){

        icon_image = (CircleImageView) view.findViewById(R.id.icon_image);
        nav_username = (TextView) view.findViewById(R.id.nav_username);
        nav_manage = (TextView) view.findViewById(R.id.nav_manage);
        nav_message = (TextView) view.findViewById(R.id.nav_message);
        nav_history = (TextView) view.findViewById(R.id.nav_history);
        nav_setting = (TextView) view.findViewById(R.id.nav_setting);

        String picstring = BaseViewModel.GetInstance().User.getHeadPicUrl();
        if (picstring != null) {
            Picasso.with(getContext())
                    .load(picstring)
                    .into(icon_image);
        }

        String name = BaseViewModel.GetInstance().User.getNickName();

        nav_username.setText(BaseViewModel.GetInstance().User.getNickName());

        icon_image.setOnClickListener(this);
        nav_manage.setOnClickListener(this);
        nav_message.setOnClickListener(this);
        nav_history.setOnClickListener(this);
        nav_setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
        hideAllFragment(fTransaction);
        switch (view.getId()){
            case R.id.icon_image:
                if(fgHeader == null){
                    fgHeader = new MyFragmentHeader("个人头像");
                    fTransaction.add(R.id.fb_content,fgHeader);
                    fTransaction.addToBackStack(null);
                }else{
                    fTransaction.add(R.id.fb_content,fgHeader);
                    fTransaction.addToBackStack(null);
                    fTransaction.show(fgHeader);
                }
                break;
            case R.id.nav_manage:
                String address_company="http://www.tytechkj.com/App/Permission/GetCompanyInfo";
                queryCompany(address_company);
                if (BaseViewModel.GetInstance().CompanyView != null){
                    fgManageKey = new MyFragmentManageKey("公司管理");
                    fTransaction.add(R.id.fb_content,fgManageKey);
                    fTransaction.addToBackStack(null);
                }else if (BaseViewModel.GetInstance().CompanyView == null){
                    fgManage = new MyFragmentManage("公司管理");
                    fTransaction.add(R.id.fb_content,fgManage);
                    fTransaction.addToBackStack(null);
                }
                break;
            case R.id.nav_message:
                DialogMethod.MyDialog(getContext(),"该功能仍在测试中，请期待！");
//                if(fgInManage == null){
//                    fgInManage = new MyFragmentInMessage("发送站内信");
//                    fTransaction.add(R.id.fb_content,fgInManage);
//                    fTransaction.addToBackStack(null);
//                }else{
//                    fTransaction.add(R.id.fb_content,fgInManage);
//                    fTransaction.addToBackStack(null);
//                    fTransaction.show(fgInManage);
//                }
                break;
            case R.id.nav_history:
                DialogMethod.MyDialog(getContext(),"该功能仍在测试中，请期待！");
                break;
            case R.id.nav_setting:
                if(fgSetting == null){
                    fgSetting = new MyFragmentSetting("设置");
                    fTransaction.add(R.id.fb_content,fgSetting);
                    fTransaction.addToBackStack(null);
                }else{
                    fTransaction.add(R.id.fb_content,fgSetting);
                    fTransaction.addToBackStack(null);
                    fTransaction.show(fgSetting);
                }
                break;
        }
        fTransaction.commit();
    }

    /**
     * ID 当前用户ID
     */

    private void queryCompany(String address) {
        DialogMethod.MyProgressDialog(getActivity(),"正在处理中...",true);
        RequestBody requestBody = new FormBody.Builder()
                .add("ID", GetInstance().User.getGUID())
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                user = gson.fromJson(responseText,UserInfo.class);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogMethod.MyProgressDialog(getContext(), "", false);
                        if (!user.IsError){

                            String s= new Gson().toJson(user.Data);
                            CompanyViewModel lll= new Gson().fromJson( s,CompanyViewModel.class);
                            BaseViewModel.GetInstance().setCompanyView(lll);

                            String address_Pid="http://www.tytechkj.com/App/Permission/GetCurrentPid";
                            queryPid(address_Pid);
                        }else {
                            Toast.makeText(getContext(),user.Message,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        DialogMethod.MyProgressDialog(getContext(),"",false);
                        Toast.makeText(getContext(),"获取公司信息失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * UID 当前用户ID
     * CID 当前公司ID
     */

    private void queryPid(String address) {
        DialogMethod.MyProgressDialog(getActivity(),"正在获处理...",true);
        RequestBody requestBody = new FormBody.Builder()
                .add("UID", GetInstance().User.getGUID())
                .add("CID", String.valueOf(GetInstance().CompanyView.getID()))
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                CompanyViewModel lll= gson.fromJson(responseText,CompanyViewModel.class);
                BaseViewModel.GetInstance().setCompanyView(lll);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        DialogMethod.MyProgressDialog(getContext(), "", false);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        DialogMethod.MyProgressDialog(getContext(),"",false);
                        Toast.makeText(getContext(),"获取公司PID失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fgHeader != null)fragmentTransaction.hide(fgHeader);
        if(fgManage != null)fragmentTransaction.hide(fgManage);
        if(fgManageKey != null)fragmentTransaction.hide(fgManageKey);
        if(fgInManage != null)fragmentTransaction.hide(fgInManage);
        if(fgSetting != null)fragmentTransaction.hide(fgSetting);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CART_BROADCAST");
        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent){
                String msg = intent.getStringExtra("data");
                if("refresh".equals(msg)){
                    Picasso.with(getContext())
                            .load(BaseViewModel.GetInstance().User.getHeadPicUrl())
                            .into(icon_image);
                    nav_username.setText(BaseViewModel.GetInstance().User.getNickName());
                }
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);
    }
}