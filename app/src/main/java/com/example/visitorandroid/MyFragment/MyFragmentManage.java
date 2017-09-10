package com.example.visitorandroid.MyFragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.visitorandroid.R;

import static com.example.visitorandroid.R.id.backup;
import static com.example.visitorandroid.R.id.txt_topbar;

public class MyFragmentManage extends Fragment implements View.OnClickListener {

    private String content;

    private TextView txtTopbar;
    private Button backButton;
    private Button backBtCancel;
    private Button backBtSend;
    private RadioGroup radios;

    private MyFragmentJoinCompany fgjoin;
    private MyFragmentCreateCompany fgcreate;

    private Button join_company;
    private Button create_company;

    public MyFragmentManage(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_manage,container,false);

        TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
        txt_content.setText("您当前尚未加入企业");

        txtTopbar = getActivity().findViewById(txt_topbar);
        txtTopbar.setText(content);
        radios =  getActivity().findViewById(R.id.rg_tab_bar);
        radios.setVisibility(View.INVISIBLE);
        View view1 = getActivity().findViewById(R.id.div_tab_bar);
        view1.setVisibility(View.INVISIBLE);
        backButton = getActivity().findViewById(R.id.back_button);
        backButton.setVisibility(View.VISIBLE);
        backBtCancel = getActivity().findViewById(R.id.back_bt_cancel);
        backBtCancel.setVisibility(View.INVISIBLE);
        backBtSend = getActivity().findViewById(R.id.back_bt_send);
        backBtSend.setVisibility(View.INVISIBLE);

        join_company = (Button) view.findViewById(R.id.join_company);
        create_company = (Button) view.findViewById(R.id.create_company);

        backButton.setText("我");
        backButton.setOnClickListener(this);
        join_company.setOnClickListener(this);
        create_company.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
        hideAllFragment(fTransaction);
        switch (view.getId()){
            case R.id.back_button:
                txtTopbar.setVisibility(View.VISIBLE);
                txtTopbar.setText("我");
                backButton.setVisibility(View.INVISIBLE);
                radios.setVisibility(View.VISIBLE);
                getActivity().onBackPressed();
                break;
            case R.id.join_company:
                if(fgjoin == null){
                    fgjoin = new MyFragmentJoinCompany("加入公司");
                    fTransaction.add(R.id.fb_company,fgjoin);
                    fTransaction.addToBackStack(null);
                }else{
                    fTransaction.add(R.id.fb_company,fgjoin);
                    fTransaction.addToBackStack(null);
                    fTransaction.show(fgjoin);
                }
                break;
            case R.id.create_company:
                if(fgjoin == null){
                    fgcreate = new MyFragmentCreateCompany("创建公司");
                    fTransaction.add(R.id.fb_company,fgcreate);
                    fTransaction.addToBackStack(null);
                }else{
                    fTransaction.add(R.id.fb_company,fgcreate);
                    fTransaction.addToBackStack(null);
                    fTransaction.show(fgcreate);
                }
                break;
        }
        fTransaction.commit();
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fgjoin != null)fragmentTransaction.hide(fgjoin);
        if(fgcreate != null)fragmentTransaction.hide(fgcreate);
    }
}
