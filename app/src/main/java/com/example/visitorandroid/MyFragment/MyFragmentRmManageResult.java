package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.visitorandroid.R;

import static com.example.visitorandroid.R.id.order_result_btn_back;

public class MyFragmentRmManageResult extends Fragment implements View.OnClickListener {

    private String content;
    private Activity activity;

    private Button ryresult_btnback;
    private TextView ryresult_username;
    private TextView ryresult_bm;

    private MyFragmentSelectBm fgSelectBm;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public MyFragmentRmManageResult(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fg_rymanageresult,container,false);

        bindViews(view);

        return view;
    }

    private void bindViews(View view) {

        ryresult_btnback = (Button) view.findViewById(R.id.ry_result_btn_back);
        ryresult_username = (TextView) view.findViewById(R.id.ry_result_username);
        ryresult_bm = (TextView) view.findViewById(R.id.ry_result_bm);
        String account = ryresult_username.getText().toString() + content.substring(0,5);
        String bm = ryresult_bm.getText().toString() + content.substring(6);
        ryresult_username.setText(account);
        ryresult_bm.setText(bm);

        ryresult_btnback.setOnClickListener(this);
        ryresult_bm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ry_result_btn_back:
                activity.onBackPressed();
                break;
            case R.id.ry_result_bm:
                FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
                hideAllFragment(fTransaction);
                if (fgSelectBm == null) {
                    fgSelectBm = new MyFragmentSelectBm("选择部门");
                    fTransaction.add(R.id.fb_ry_result, fgSelectBm);
                    fTransaction.addToBackStack(null);
                } else {
                    fTransaction.add(R.id.fb_ry_result, fgSelectBm);
                    fTransaction.addToBackStack(null);
                    fTransaction.show(fgSelectBm);
                }
                fTransaction.commit();
                break;
        }
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fgSelectBm != null)fragmentTransaction.hide(fgSelectBm);
    }
}
