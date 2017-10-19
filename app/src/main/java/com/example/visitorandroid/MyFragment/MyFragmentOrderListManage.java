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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.visitorandroid.Adapter.MyOrderListAdapter;
import com.example.visitorandroid.Model.BaseViewModel;
import com.example.visitorandroid.Model.DialogMethod;
import com.example.visitorandroid.Model.OrderListViewModel;
import com.example.visitorandroid.R;
import com.example.visitorandroid.util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.visitorandroid.Model.BaseViewModel.GetInstance;


public class MyFragmentOrderListManage extends Fragment implements View.OnClickListener,
        AdapterView.OnItemClickListener {

    private Activity activity;
    public String content;

    private Button orderlist_btnback;
    private ListView order_list;

    private List<OrderListViewModel> mData = null;
    private Context mContext;
    private MyOrderListAdapter mAdapter = null;
    private MyFragmentOrderResult fgOrderResult;

    private List<OrderListViewModel> orderList = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public MyFragmentOrderListManage(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_orderlist,container,false);

        bindViews(view);

        return view;
    }

    private void bindViews(View view) {

        orderlist_btnback = (Button) view.findViewById(R.id.order_list_btn_back);
        order_list = (ListView) view.findViewById(R.id.order_list);

        mContext = getActivity();
        mData = new LinkedList<OrderListViewModel>();

        orderlist_btnback.setOnClickListener(this);
        order_list.setOnItemClickListener(this);

        String address_order="http://www.tytechkj.com/App/Permission/getallvisitorOrder";
        queryOrder(address_order);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_list_btn_back:
                activity.onBackPressed();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        OrderListViewModel orderList = mData.get(position);

        FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
        hideAllFragment(fTransaction);
        fgOrderResult = new MyFragmentOrderResult(orderList.getV_name(),orderList.getBv_name(),orderList.getVisitorTime());
        fTransaction.add(R.id.fb_order_list, fgOrderResult);
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fgOrderResult != null)fragmentTransaction.hide(fgOrderResult);
    }

    /**
     * ID 公司ID
     */
    private void queryOrder(String address) {
        DialogMethod.MyProgressDialog(getContext(),"正在上传中...",true);
        RequestBody requestBody = new FormBody.Builder()
                .add("ID",String.valueOf(GetInstance().CompanyView.getC_ID()))
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                orderList = gson.fromJson(responseText,
                        new TypeToken<List<OrderListViewModel>>(){}.getType());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        DialogMethod.MyProgressDialog(getContext(),"",false);

                        mData.clear();
                        for (OrderListViewModel order : orderList){
                            BaseViewModel.GetInstance().setOrderList(order);
                            if (order.getPublic())
                            {
                                mData.add(new OrderListViewModel(order.getV_name(),order.getBv_name(),order.getVisitorTime()));
                            }
                        }
                        mAdapter = new MyOrderListAdapter((LinkedList<OrderListViewModel>) mData, mContext);
                        order_list.setAdapter(mAdapter);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        DialogMethod.MyProgressDialog(getContext(),"",false);
                        Toast.makeText(getContext(),"获取预约订单失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
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
                if("refresh".equals(msg)){
                    String address_order="http://www.tytechkj.com/App/Permission/";
                    queryOrder(address_order);
                }
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);
    }
}
