package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.visitorandroid.R;

public class MyFragmentNickname extends Fragment implements View.OnClickListener {

    private String content;
    private Activity activity;

    private EditText et_nav_nickname;
    private Button nickname_btnback;
    private Button nickname_btnsave;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public MyFragmentNickname(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_nickname,container,false);

        bindViews(view);

        return view;
    }

    private void bindViews(View view) {

        et_nav_nickname = (EditText) view.findViewById(R.id.et_nav_nickname);
        nickname_btnback = (Button) view.findViewById(R.id.nickname_btn_back);
        nickname_btnsave = (Button) view.findViewById(R.id.nickname_btn_save);

        et_nav_nickname.setText("lilachy");

        nickname_btnback.setOnClickListener(this);
        nickname_btnsave.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nickname_btn_back:
                activity.onBackPressed();
                break;
            case R.id.nickname_btn_save:
                if (et_nav_nickname.getText().toString().equals("lilachy")){
                    nickname_btnsave.setEnabled(false);
                }else {
                    nickname_btnsave.setEnabled(true);
                    activity.onBackPressed();
                }
//                MainActivity.Model.setNickName(et_nav_nickname.getText().toString());

                break;
        }
    }
}
