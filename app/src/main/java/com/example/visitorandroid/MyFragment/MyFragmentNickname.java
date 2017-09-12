package com.example.visitorandroid.MyFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.visitorandroid.R;

public class MyFragmentNickname extends Fragment {

    private String content;
    private MyFragmentManage fgManage;
    private FragmentManager fManager;
    private TextView nav_username;
    private TextView nav_manage;
    private TextView nav_message;
    private TextView nav_history;
    private TextView nav_setting;

    public MyFragmentNickname(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_nickname,container,false);

        TextView txt_topbar = getActivity().findViewById(R.id.txt_topbar);
        txt_topbar.setText(content);
        View view1 = getActivity().findViewById(R.id.div_tab_bar);
        view1.setVisibility(View.INVISIBLE);

        TextView et_nav_nickname = (TextView) view.findViewById(R.id.et_nav_nickname);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        et_nav_nickname.setText(prefs.getString("regNickname",null));

        return view;
    }
}
