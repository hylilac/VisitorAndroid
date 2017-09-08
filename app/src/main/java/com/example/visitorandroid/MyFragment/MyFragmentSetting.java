package com.example.visitorandroid.MyFragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
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

import static com.example.visitorandroid.R.id.create_company;
import static com.example.visitorandroid.R.id.join_company;

public class MyFragmentSetting extends Fragment implements View.OnClickListener {

    private String content;
    private TextView nav_fun;
    private TextView nav_password;
    private TextView nav_quit;

    public MyFragmentSetting(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_setting,container,false);

        TextView txt_topbar = getActivity().findViewById(R.id.txt_topbar);
        txt_topbar.setText(content);
        RadioGroup radios =  getActivity().findViewById(R.id.rg_tab_bar);
        radios.setVisibility(View.INVISIBLE);
        View view1 = getActivity().findViewById(R.id.div_tab_bar);
        view1.setVisibility(View.INVISIBLE);

        nav_fun = (TextView) view.findViewById(R.id.nav_fun);
        nav_password = (TextView) view.findViewById(R.id.nav_password);
        nav_quit = (TextView) view.findViewById(R.id.nav_quit);

        nav_fun.setOnClickListener(this);
        nav_password.setOnClickListener(this);
        nav_quit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.nav_fun:
                break;
            case R.id.nav_password:
                break;
            case R.id.nav_quit:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("退出访客系统后，你将不再收到来自访客系统的消息。");
                builder.setCancelable(false);
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog,int which){
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
