package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.visitorandroid.R;

public class MyFragmentOrderManage extends Fragment implements View.OnClickListener {

    private String content;
    private Activity activity;

    private Button ordermanager_btnback;
    private EditText ordermanage_visitor;
    private EditText ordermanage_interviewee;
    private EditText ordermanage_company;
    private EditText ordermanage_time;
    private EditText ordermanage_tel;
    private Button ordermanage_btncommit;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public MyFragmentOrderManage(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_ordermanage,container,false);

        bindViews(view);

        return view;
    }

    private void bindViews(View view) {

        ordermanager_btnback = (Button) view.findViewById(R.id.order_manager_btn_back);
        ordermanage_visitor = (EditText) view.findViewById(R.id.order_manage_visitor);
        ordermanage_interviewee = (EditText) view.findViewById(R.id.order_manage_interviewee);
        ordermanage_company = (EditText) view.findViewById(R.id.order_manage_company);
        ordermanage_time = (EditText) view.findViewById(R.id.order_manage_time);
        ordermanage_tel = (EditText) view.findViewById(R.id.order_manage_tel);
        ordermanage_btncommit = (Button) view.findViewById(R.id.order_manage_btn_commit);

        ordermanager_btnback.setOnClickListener(this);
        ordermanage_btncommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_manager_btn_back:
                activity.onBackPressed();
                break;
            case R.id.order_manage_btn_commit:

                break;
        }
    }

}
