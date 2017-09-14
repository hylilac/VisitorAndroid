package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.visitorandroid.R;

import static com.example.visitorandroid.R.id.order_result_btn_accept;
import static com.example.visitorandroid.R.id.order_result_btn_back;
import static com.example.visitorandroid.R.id.order_result_btn_refuse;
import static com.example.visitorandroid.R.id.order_result_interviewee;
import static com.example.visitorandroid.R.id.order_result_time;
import static com.example.visitorandroid.R.id.order_result_visitor;

public class MyFragmentOrderResult extends Fragment implements View.OnClickListener {

    private String content;
    private Activity activity;

    private Button orderresult_btnback;
    private TextView orderresult_visitor;
    private TextView orderresult_interviewee;
    private TextView orderresult_time;
    private Button orderresult_btnaccept;
    private Button orderresult_btnrefuse;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }



    public MyFragmentOrderResult(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fg_orderresult,container,false);

        bindViews(view);

        return view;
    }

    private void bindViews(View view) {

        orderresult_btnback = (Button) view.findViewById(order_result_btn_back);
        orderresult_visitor = (TextView) view.findViewById(R.id.order_result_visitor);
        orderresult_interviewee = (TextView) view.findViewById(R.id.order_result_interviewee);
        orderresult_time = (TextView) view.findViewById(R.id.order_result_time);
        orderresult_btnaccept = (Button) view.findViewById(R.id.order_result_btn_accept);
        orderresult_btnrefuse = (Button) view.findViewById(R.id.order_result_btn_refuse);

        orderresult_visitor.setText(content.substring(0,1));
        orderresult_interviewee.setText(content.substring(6,8));
        orderresult_time.setText(content.substring(9));

        orderresult_btnback.setOnClickListener(this);
        orderresult_btnaccept.setOnClickListener(this);
        orderresult_btnrefuse.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_result_btn_back:
                activity.onBackPressed();
                break;
            case R.id.order_result_btn_accept:

                break;
            case R.id.order_result_btn_refuse:

                break;
        }
    }
}
