package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.visitorandroid.Model.Data;
import com.example.visitorandroid.R;
import com.squareup.picasso.Picasso;

import static android.R.attr.data;
import static com.example.visitorandroid.Model.BaseViewModel.GetInstance;
import static com.example.visitorandroid.Model.objData.GetInstance2;
import static com.example.visitorandroid.R.id.nav_sub_headericon;
import static com.example.visitorandroid.R.id.nav_sub_nickname;
import static com.example.visitorandroid.R.id.nav_sub_tel;
import static com.example.visitorandroid.R.id.order_result_btn_back;

public class MyFragmentRyManageResult extends Fragment implements View.OnClickListener {

    private String content1;
    private String content2;
    private Activity activity;

    private Button ryresult_btnback;
    private TextView ryresult_username;
    private TextView ryresult_bm;

    private MyFragmentSelectBm fgSelectBm;

    private Data data = new Data();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public MyFragmentRyManageResult(String content1,String content2) {
        this.content1 = content1;
        this.content2 = content2;
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
        String account = ryresult_username.getText().toString() + content1;
        String bm = ryresult_bm.getText().toString() + content2;
        ryresult_username.setText(account);
        ryresult_bm.setText(bm);

        ryresult_btnback.setOnClickListener(this);
        ryresult_bm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ry_result_btn_back:
                BackMethod();
                break;
            case R.id.ry_result_bm:
                FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
                hideAllFragment(fTransaction);
                if (fgSelectBm == null) {
                    fgSelectBm = new MyFragmentSelectBm("");
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

    private void BackMethod() {
        activity.onBackPressed();
        Intent intent = new Intent("android.intent.action.CART_BROADCAST");
        intent.putExtra("data","refresh");
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CART_BROADCAST");
        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent){
                String msg = intent.getStringExtra("data");
                String bmname = intent.getStringExtra("revisebm");
                if("refresh".equals(msg)){
                    ryresult_bm.setText("部门：" + bmname + "");
                }
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);
    }
}
