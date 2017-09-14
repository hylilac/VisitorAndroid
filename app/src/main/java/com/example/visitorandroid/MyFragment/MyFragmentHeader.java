package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visitorandroid.MainActivity;
import com.example.visitorandroid.Model.BaseViewModel;
import com.example.visitorandroid.Model.DialogMethod;
import com.example.visitorandroid.Model.UserInfo;
import com.example.visitorandroid.Model.UserViewModel;
import com.example.visitorandroid.R;
import com.example.visitorandroid.util.HttpUtil;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.ContentValues.TAG;
import static com.example.visitorandroid.Model.BaseViewModel.GetInstance;
import static com.example.visitorandroid.R.id.et_nav_nickname;
import static com.example.visitorandroid.R.id.icon_image;
import static org.litepal.LitePalApplication.getContext;

public class MyFragmentHeader extends Fragment implements View.OnClickListener {

    private String content;
    private Activity activity;

    private TextView txtTopbar;
    private View div_tabbar;
    private RadioGroup radios;

    private MyFragmentHeaderIcon fgHeaderIcon;
    private MyFragmentNickname fgNickname;
    private MyFragmentTel fgTel;

    private TextView nav_headericon;
    private TextView nav_nickname;
    private TextView nav_tel;
    private TextView nav_sex;
    private TextView nav_other;

    private ImageView nav_sub_headericon;
    private TextView nav_sub_nickname;
    private TextView nav_sub_account;
    private TextView nav_sub_tel;
    private TextView nav_sub_sex;

    private Button user_btnback;

    private UserInfo user;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public MyFragmentHeader(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fg_header, container, false);

        txtTopbar = activity.findViewById(R.id.txt_topbar);
        txtTopbar.setVisibility(View.GONE);

        div_tabbar = activity.findViewById(R.id.div_tab_bar);
        div_tabbar.setVisibility(View.GONE);

        radios = activity.findViewById(R.id.rg_tab_bar);
        radios.setVisibility(View.GONE);

        bindViews(view);

        return view;
    }

    private void bindViews(View view) {

        user_btnback = (Button) view.findViewById(R.id.user_btn_back);
        nav_headericon = (TextView) view.findViewById(R.id.nav_headericon);
        nav_nickname = (TextView) view.findViewById(R.id.nav_nickname);
        nav_tel = (TextView) view.findViewById(R.id.nav_tel);
        nav_sex = (TextView) view.findViewById(R.id.nav_sex);
        nav_other = (TextView) view.findViewById(R.id.nav_other);

        nav_sub_headericon = (ImageView) view.findViewById(R.id.nav_sub_headericon);
        nav_sub_nickname = (TextView) view.findViewById(R.id.nav_sub_nickname);
        nav_sub_account = (TextView) view.findViewById(R.id.nav_sub_account);
        nav_sub_tel = (TextView) view.findViewById(R.id.nav_sub_tel);
        nav_sub_sex = (TextView) view.findViewById(R.id.nav_sub_sex);

        String picstring = GetInstance().User.getHeadPicUrl();
        Picasso.with(getContext())
                .load("http://www.tytechkj.com/app/HeadPic/" + picstring)
                .into(nav_sub_headericon);

        nav_sub_nickname.setText(GetInstance().User.getNickName());
        nav_sub_account.setText(GetInstance().User.getUserName());
        nav_sub_tel.setText(GetInstance().User.getMobile());
        nav_sub_sex.setText(GetInstance().User.getSex());

        user_btnback.setOnClickListener(this);
        nav_headericon.setOnClickListener(this);
        nav_nickname.setOnClickListener(this);
        nav_tel.setOnClickListener(this);
        nav_sex.setOnClickListener(this);
        nav_other.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
        hideAllFragment(fTransaction);
        switch (view.getId()) {
            case R.id.user_btn_back:
                txtTopbar.setVisibility(View.VISIBLE);
                div_tabbar.setVisibility(View.VISIBLE);
                radios.setVisibility(View.VISIBLE);
                activity.onBackPressed();
                break;
            case R.id.nav_headericon:
                if (fgHeaderIcon == null) {
                    fgHeaderIcon = new MyFragmentHeaderIcon("个人头像");
                    fTransaction.add(R.id.fb_header, fgHeaderIcon);
                    fTransaction.addToBackStack(null);
                } else {
                    fTransaction.add(R.id.fb_header, fgHeaderIcon);
                    fTransaction.addToBackStack(null);
                    fTransaction.show(fgHeaderIcon);
                }
                break;

            case R.id.nav_nickname:
                if (fgNickname == null) {
                    fgNickname = new MyFragmentNickname("昵称");
                    fTransaction.add(R.id.fb_header, fgNickname);
                    fTransaction.addToBackStack(null);
                } else {
                    fTransaction.add(R.id.fb_header, fgNickname);
                    fTransaction.addToBackStack(null);
                    fTransaction.show(fgNickname);
                }
                break;
            case R.id.nav_tel:
                if (fgTel == null) {
                    fgTel = new MyFragmentTel("手机号");
                    fTransaction.add(R.id.fb_header, fgTel);
                    fTransaction.addToBackStack(null);
                } else {
                    fTransaction.add(R.id.fb_header, fgTel);
                    fTransaction.addToBackStack(null);
                    fTransaction.show(fgTel);
                }
                break;
            case R.id.nav_sex:
                isSex();
                break;
            case R.id.nav_other:
                break;
        }
        fTransaction.commit();
    }

    private void isSex() {
        final String[] choose = new String[]{"男", "女", "取消"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setItems(choose, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (choose[which].equals("男")) {
                    nav_sub_sex.setText("男");
                } else if (choose[which].equals("女")) {
                    nav_sub_sex.setText("女");
                }
                String sex = nav_sub_sex.getText().toString();
                String address_sex="http://www.tytechkj.com/App/Permission/ChangeSex";
                querySex(address_sex,sex);
            }
        }).create();

        builder.show();
    }

    private void querySex(String address, final String sexstring) {
        DialogMethod.MyProgressDialog(getContext(),"正在上传中...",true);
        RequestBody requestBody = new FormBody.Builder()
                .add("ID", GetInstance().User.getGUID())
                .add("Sex",sexstring)
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                user = gson.fromJson(responseText, UserInfo.class);
                if (!user.IsError) {
                    GetInstance().User.Sex = sexstring;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            nav_sub_sex.setText(sexstring);
                            DialogMethod.MyProgressDialog(getContext(), "", false);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogMethod.MyProgressDialog(getContext(),"",false);
                        Toast.makeText(getContext(),"上传性别失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (fgHeaderIcon != null) fragmentTransaction.hide(fgHeaderIcon);
        if (fgNickname != null) fragmentTransaction.hide(fgNickname);
        if (fgTel != null) fragmentTransaction.hide(fgTel);
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
                            .load("http://www.tytechkj.com/app/HeadPic/" + GetInstance().User.getHeadPicUrl())
                            .into(nav_sub_headericon);
                    nav_sub_nickname.setText(GetInstance().User.getNickName());
                    nav_sub_tel.setText(GetInstance().User.getMobile());
                }
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);
    }
}
