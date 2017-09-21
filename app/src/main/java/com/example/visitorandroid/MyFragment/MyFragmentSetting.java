package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.visitorandroid.LoginActivity;
import com.example.visitorandroid.Model.DialogMethod;
import com.example.visitorandroid.R;
import com.tencent.android.tpush.XGPushManager;

import static com.example.visitorandroid.R.id.create_company;
import static com.example.visitorandroid.R.id.join_company;
import static com.example.visitorandroid.R.id.txt_topbar;
import static com.tencent.android.tpush.XGPushManager.registerPush;

public class MyFragmentSetting extends Fragment implements View.OnClickListener {

    private String content;
    private Activity activity;

    private TextView txtTopbar;
    private View div_tabbar;
    private RadioGroup radios;

    private TextView nav_fun;
    private TextView nav_password;
    private TextView nav_quit;
    private Button setting_btnback;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public MyFragmentSetting(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_setting,container,false);

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

        nav_fun = (TextView) view.findViewById(R.id.nav_fun);
        nav_password = (TextView) view.findViewById(R.id.nav_password);
        nav_quit = (TextView) view.findViewById(R.id.nav_quit);
        setting_btnback = (Button) view.findViewById(R.id.setting_btn_back);

        setting_btnback.setOnClickListener(this);
        nav_fun.setOnClickListener(this);
        nav_password.setOnClickListener(this);
        nav_quit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setting_btn_back:
                txtTopbar.setVisibility(View.VISIBLE);
                div_tabbar.setVisibility(View.VISIBLE);
                radios.setVisibility(View.VISIBLE);
                activity.onBackPressed();
                break;
            case R.id.nav_fun:
                DialogMethod.MyDialog(getContext(),"该功能仍在测试中，请期待！");
                break;
            case R.id.nav_password:
                DialogMethod.MyDialog(getContext(),"该功能仍在测试中，请期待！");
                break;
            case R.id.nav_quit:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("退出访客系统后，你将不再收到来自访客系统的消息。");
                builder.setCancelable(false);
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog,int which){
                        XGPushManager.registerPush(getContext(), "*");
                        SharedPreferences.Editor editor = PreferenceManager.
                                getDefaultSharedPreferences(getActivity()).edit();
                        editor.clear();
                        editor.apply();
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();
                break;
        }
    }
}
