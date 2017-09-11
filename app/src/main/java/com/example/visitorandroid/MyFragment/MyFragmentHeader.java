package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.DialogInterface;
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

import com.example.visitorandroid.R;

public class MyFragmentHeader extends Fragment implements View.OnClickListener {

    private String content;
    private MyFragmentHeaderIcon fgHeaderIcon;
    private MyFragmentNickname fgNickname;
    private MyFragmentTel fgTel;

    private TextView nav_headericon;
    private TextView nav_nickname;
    private TextView nav_tel;
    private TextView nav_sex;
    private TextView nav_other;

    private TextView nav_sub_nickname;
    private TextView nav_sub_account;
    private TextView nav_sub_tel;
    private TextView nav_sub_sex;

    private TextView txtTopbar;
    private Button backButton;
    private Button backBtCancel;
    private Button backBtSend;
    private RadioGroup radios;

    private Activity activity;
    private Button unserinfo_btback;

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
        View view = inflater.inflate(R.layout.fg_header,container,false);

        txtTopbar = activity.findViewById(R.id.txt_topbar);
        txtTopbar.setVisibility(View.GONE);
        radios =  activity.findViewById(R.id.rg_tab_bar);
        radios.setVisibility(View.GONE);
        View view1 = activity.findViewById(R.id.div_tab_bar);
        view1.setVisibility(View.GONE);
//        backButton = getActivity().findViewById(R.id.back_button);
//        backButton.setText("我");
//        backButton.setVisibility(View.VISIBLE);
//        backBtCancel = getActivity().findViewById(R.id.back_bt_cancel);
//        backBtCancel.setVisibility(View.GONE);
//        backBtSend = getActivity().findViewById(R.id.back_bt_send);
//        backBtSend.setVisibility(View.GONE);

        nav_headericon = (TextView) view.findViewById(R.id.nav_headericon);
        nav_nickname = (TextView) view.findViewById(R.id.nav_nickname);
        nav_tel = (TextView) view.findViewById(R.id.nav_tel);
        nav_sex = (TextView) view.findViewById(R.id.nav_sex);
        nav_other = (TextView) view.findViewById(R.id.nav_other);

        nav_sub_nickname = (TextView) view.findViewById(R.id.nav_sub_nickname);
        nav_sub_account = (TextView) view.findViewById(R.id.nav_sub_account);
        nav_sub_tel = (TextView) view.findViewById(R.id.nav_sub_tel);
        nav_sub_sex = (TextView) view.findViewById(R.id.nav_sub_sex);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        nav_sub_nickname.setText(prefs.getString("regNickname",null));
        nav_sub_account.setText(prefs.getString("username",null));
        nav_sub_tel.setText(prefs.getString("username",null));

        unserinfo_btback = (Button) view.findViewById(R.id.unserinfo_btback);
        unserinfo_btback.setOnClickListener(this);

        nav_headericon.setOnClickListener(this);
        nav_nickname.setOnClickListener(this);
        nav_tel.setOnClickListener(this);
        nav_sex.setOnClickListener(this);
        nav_other.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
        hideAllFragment(fTransaction);
        switch (view.getId()){
            case R.id.unserinfo_btback:
                activity.onBackPressed();
                break;
            case R.id.nav_headericon:
                if(fgHeaderIcon == null){
                    fgHeaderIcon = new MyFragmentHeaderIcon("个人头像");
                    fTransaction.replace(R.id.fb_header,fgHeaderIcon);
                    fTransaction.addToBackStack(null);
                }else{
                    fTransaction.replace(R.id.fb_header,fgHeaderIcon);
                    fTransaction.addToBackStack(null);
                    fTransaction.show(fgHeaderIcon);
                }
                break;

            case R.id.nav_nickname:
                if(fgNickname == null){
                    fgNickname = new MyFragmentNickname("昵称");
                    fTransaction.add(R.id.fb_header,fgNickname);
                    fTransaction.addToBackStack(null);
                }else{
                    fTransaction.add(R.id.fb_header,fgNickname);
                    fTransaction.addToBackStack(null);
                    fTransaction.show(fgNickname);
                }
                break;
            case R.id.nav_tel:
                if(fgTel == null){
                    fgTel = new MyFragmentTel("手机号");
                    fTransaction.add(R.id.fb_header,fgTel);
                    fTransaction.addToBackStack(null);
                }else{
                    fTransaction.add(R.id.fb_header,fgTel);
                    fTransaction.addToBackStack(null);
                    fTransaction.show(fgTel);
                }
                break;
            case R.id.nav_sex:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setPositiveButton("男", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog,int which){
                        nav_sub_sex.setText("男");
                    }
                });
                builder.setNegativeButton("女", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        nav_sub_sex.setText("女");
                    }
                });
                builder.show();
                break;
            case R.id.nav_other:
                break;
        }
        fTransaction.commit();
    }
    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fgHeaderIcon != null)fragmentTransaction.hide(fgHeaderIcon);
        if(fgNickname != null)fragmentTransaction.hide(fgNickname);
        if(fgTel != null)fragmentTransaction.hide(fgTel);
    }
}
