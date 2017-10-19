package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
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

import com.example.visitorandroid.Model.BaseViewModel;
import com.example.visitorandroid.Model.DialogMethod;
import com.example.visitorandroid.R;

import static com.example.visitorandroid.R.id.et_messageText;
import static com.example.visitorandroid.R.id.et_receiver;
import static com.example.visitorandroid.R.id.et_sender;
import static com.example.visitorandroid.R.id.et_titleText;
import static com.example.visitorandroid.R.id.nav_sub_nickname;
import static com.example.visitorandroid.R.id.txt_topbar;

public class MyFragmentInMessage extends Fragment implements View.OnClickListener {

    public String content;
    private Activity activity;

    private TextView txtTopbar;
    private View div_tabbar;
    private RadioGroup radios;

    private TextView etSender;
    private EditText etMessageText;
    private Button btn_cancel;
    private Button btn_send;
    private EditText etReceiver;
    private EditText etTitleText;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public MyFragmentInMessage(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_inmessage,container,false);

        txtTopbar = activity.findViewById(R.id.txt_topbar);
        txtTopbar.setVisibility(View.GONE);

        div_tabbar = activity.findViewById(R.id.div_tab_bar);
        div_tabbar.setVisibility(View.GONE);

        radios = activity.findViewById(R.id.rg_tab_bar);
        radios.setVisibility(View.GONE);

        bindViews(view);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        etSender.setText(prefs.getString("regNickname",null));

        return view;
    }

    private void bindViews(View view) {
        etSender = (TextView) view.findViewById(R.id.et_sender);
        etReceiver = (EditText) view.findViewById(R.id.et_receiver);
        etTitleText = (EditText) view.findViewById(R.id.et_titleText);
        etMessageText = (EditText) view.findViewById(R.id.et_messageText);
        btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_send = (Button) view.findViewById(R.id.btn_send);

        etSender.setText(BaseViewModel.GetInstance().User.getNickName());

        btn_cancel.setOnClickListener(this);
        btn_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_cancel:
                isCancel();
                etMessageText.setText("这里填写消息");
                break;
            case R.id.btn_send:
                Boolean kk = isSend();
                if (kk){

                }
                break;
        }
    }

    private void isCancel(){
        txtTopbar.setVisibility(View.VISIBLE);
        div_tabbar.setVisibility(View.VISIBLE);
        radios.setVisibility(View.VISIBLE);
        activity.onBackPressed();
        getActivity().onBackPressed();
    }

    private Boolean isSend() {
        if (etReceiver.getText().toString().isEmpty()){
            DialogMethod.MyDialog(getContext(),"接收人不能为空");
            return false;
        }
        return true;
    }
}
