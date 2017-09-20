package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.visitorandroid.R;

import static com.example.visitorandroid.R.id.et_createcompany;

public class MyFragmentCreateCompany extends Fragment {

    private String content;
    private Activity activity;
    private Button createcompany_btnback;
    private EditText createcompany;
    private Button btn_createcompany;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public MyFragmentCreateCompany(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_createcompany,container,false);

        bindViews(view);

        return view;
    }

    private void bindViews(View view) {

        createcompany_btnback = (Button) view.findViewById(R.id.create_company_btn_back);
        createcompany = (EditText) view.findViewById(R.id.et_createcompany);
        btn_createcompany = (Button) view.findViewById(R.id.btn_create_company);
    }


}
