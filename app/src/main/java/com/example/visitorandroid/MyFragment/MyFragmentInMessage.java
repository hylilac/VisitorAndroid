package com.example.visitorandroid.MyFragment;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.visitorandroid.R;

import static com.example.visitorandroid.R.id.et_messageText;
import static com.example.visitorandroid.R.id.et_sender;
import static com.example.visitorandroid.R.id.nav_sub_nickname;
import static com.example.visitorandroid.R.id.txt_topbar;

public class MyFragmentInMessage extends Fragment implements View.OnClickListener {

    private String content;

    private TextView txtTopbar;
    private Button backButton;
    private Button backBtCancel;
    private Button backBtSend;
    private RadioGroup radios;

    private TextView etSender;
    private EditText etMessageText;

    public MyFragmentInMessage(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_inmessage,container,false);

        txtTopbar = getActivity().findViewById(txt_topbar);
        txtTopbar.setText(content);
        radios =  getActivity().findViewById(R.id.rg_tab_bar);
        radios.setVisibility(View.INVISIBLE);
        View view1 = getActivity().findViewById(R.id.div_tab_bar);
        view1.setVisibility(View.INVISIBLE);
        backButton = getActivity().findViewById(R.id.back_button);
        backButton.setVisibility(View.INVISIBLE);
        backBtCancel = getActivity().findViewById(R.id.back_bt_cancel);
        backBtCancel.setVisibility(View.VISIBLE);
        backBtSend = getActivity().findViewById(R.id.back_bt_send);
        backBtSend.setVisibility(View.VISIBLE);

        etSender = (TextView) view.findViewById(R.id.et_sender);
        etMessageText = (EditText) view.findViewById(R.id.et_messageText);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        etSender.setText(prefs.getString("regNickname",null));

        backBtCancel.setOnClickListener(this);
        backBtSend.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_bt_cancel:
                isCancel();
                etMessageText.setText("这里填写消息");
                break;
            case R.id.back_bt_send:
                isCancel();
                break;
        }
    }

    private void isCancel(){
        txtTopbar.setVisibility(View.VISIBLE);
        txtTopbar.setText("我");
        backBtCancel.setVisibility(View.INVISIBLE);
        backBtSend.setVisibility(View.INVISIBLE);
        radios.setVisibility(View.VISIBLE);
        getActivity().onBackPressed();
    }
}
