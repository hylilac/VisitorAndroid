package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.visitorandroid.R;

public class MyFragmentAuthorityManage extends Fragment implements View.OnClickListener {

    private String content;
    private Activity activity;

    private Button authoritymanage_btnback;
    private ListView authoritymanage_list;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }



    public MyFragmentAuthorityManage(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_authoritymanage,container,false);

        bindViews(view);

        return view;
    }

    private void bindViews(View view) {

        authoritymanage_btnback = (Button) view.findViewById(R.id.authority_manage_btn_back);
        authoritymanage_list = (ListView) view.findViewById(R.id.authority_manage_list);

        authoritymanage_btnback.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.authority_manage_btn_back:
                break;
        }
    }
}
