package com.example.visitorandroid.MyFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.visitorandroid.R;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.R.attr.id;

public class MyFragmentBetter extends Fragment implements View.OnClickListener {

    private String content;
    private MyFragmentManage fgManage;
    private MyFragmentInMessage fgInManage;
    private MyFragmentSetting fgSetting;
    private MyFragmentHeader fgHeader;

    private CircleImageView icon_image;

    private TextView nav_username;
    private TextView nav_manage;
    private TextView nav_message;
    private TextView nav_history;
    private TextView nav_setting;

    public MyFragmentBetter(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_better,container,false);

        RadioGroup radios =  getActivity().findViewById(R.id.rg_tab_bar);
        radios.setVisibility(View.VISIBLE);
        View view1 = getActivity().findViewById(R.id.div_tab_bar);
        view1.setVisibility(View.VISIBLE);
        Button backButton = getActivity().findViewById(R.id.back_button);
        Button backBtCancel = getActivity().findViewById(R.id.back_bt_cancel);
        Button backBtSend = getActivity().findViewById(R.id.back_bt_send);
        backButton.setVisibility(View.INVISIBLE);
        backBtCancel.setVisibility(View.INVISIBLE);
        backBtSend.setVisibility(View.INVISIBLE);

        icon_image = (CircleImageView) view.findViewById(R.id.icon_image);

        nav_username = (TextView) view.findViewById(R.id.nav_username);
        nav_username.setText(content);

        nav_manage = (TextView) view.findViewById(R.id.nav_manage);
        nav_message = (TextView) view.findViewById(R.id.nav_message);
        nav_history = (TextView) view.findViewById(R.id.nav_history);
        nav_setting = (TextView) view.findViewById(R.id.nav_setting);

        icon_image.setOnClickListener(this);
        nav_manage.setOnClickListener(this);
        nav_message.setOnClickListener(this);
        nav_history.setOnClickListener(this);
        nav_setting.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
        hideAllFragment(fTransaction);
        switch (view.getId()){
            case R.id.icon_image:
                if(fgHeader == null){
                    fgHeader = new MyFragmentHeader("个人信息");
                    fTransaction.add(R.id.fb_content,fgHeader);
                    fTransaction.addToBackStack(null);
                }else{
                    fTransaction.add(R.id.fb_content,fgHeader);
                    fTransaction.addToBackStack(null);
                    fTransaction.show(fgHeader);
                }
                break;
            case R.id.nav_manage:
                if(fgManage == null){
                    fgManage = new MyFragmentManage("公司管理");
                    fTransaction.add(R.id.fb_content,fgManage);
                    fTransaction.addToBackStack(null);
                    Log.d("vtedf","grhhtrj");
                }else{
                    fTransaction.add(R.id.fb_content,fgManage);
                    fTransaction.addToBackStack(null);
                    fTransaction.show(fgManage);
                    Log.d("vtedf","7815646");
                }
                break;
            case R.id.nav_message:
                if(fgInManage == null){
                    fgInManage = new MyFragmentInMessage("发送站内信");
                    fTransaction.add(R.id.fb_content,fgInManage);
                    fTransaction.addToBackStack(null);
                }else{
                    fTransaction.add(R.id.fb_content,fgInManage);
                    fTransaction.addToBackStack(null);
                    fTransaction.show(fgInManage);
                }
                break;
            case R.id.nav_history:
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

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fgHeader != null)fragmentTransaction.hide(fgHeader);
        if(fgManage != null)fragmentTransaction.hide(fgManage);
        if(fgInManage != null)fragmentTransaction.hide(fgInManage);
        if(fgSetting != null)fragmentTransaction.hide(fgSetting);
    }
}