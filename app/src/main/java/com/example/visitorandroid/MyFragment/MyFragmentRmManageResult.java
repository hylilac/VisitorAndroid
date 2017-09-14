package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

        ryresult_username.setText(content.substring(0,5));
        ryresult_bm.setText(content.substring(6));

        ryresult_btnback.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ry_result_btn_back:
                activity.onBackPressed();
                break;
        }
    }
}
