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

public class MyFragmentTel extends Fragment implements View.OnClickListener {

    private String content;
    private Activity activity;

    private EditText et_navtel;
    private Button tel_btnback;
    private Button tel_btnsave;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public MyFragmentTel(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_tel,container,false);

        bindViews(view);

        return view;
    }

    private void bindViews(View view) {

        et_navtel = (EditText) view.findViewById(R.id.et_nav_tel);
        tel_btnback = (Button) view.findViewById(R.id.tel_btn_back);
        tel_btnsave = (Button) view.findViewById(R.id.tel_btn_save);

        et_navtel.setText("18052528856");

        tel_btnback.setOnClickListener(this);
        tel_btnsave.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tel_btn_back:
                activity.onBackPressed();
                break;
            case R.id.tel_btn_save:
                if (et_navtel.getText().toString().equals("lilachy")){
                    tel_btnsave.setEnabled(false);
                }else {
                    tel_btnsave.setEnabled(true);
                    activity.onBackPressed();
                }
//                MainActivity.Model.setNickName(et_navtel.getText().toString());

                break;
        }
    }
}