package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.visitorandroid.Adapter.MyBmAdapter;
import com.example.visitorandroid.Adapter.MyOrderListAdapter;
import com.example.visitorandroid.Adapter.MyRyAdapter;
import com.example.visitorandroid.Model.BaseViewModel;
import com.example.visitorandroid.Model.BmViewModel;
import com.example.visitorandroid.Model.Data;
import com.example.visitorandroid.Model.DialogMethod;
import com.example.visitorandroid.Model.OrderListViewModel;
import com.example.visitorandroid.Model.ResultViewModel;
import com.example.visitorandroid.Model.UserInfo;
import com.example.visitorandroid.Model.UserViewModel;
import com.example.visitorandroid.R;
import com.example.visitorandroid.util.HttpUtil;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.attr.data;
import static com.example.visitorandroid.Model.BaseViewModel.GetInstance;
import static com.example.visitorandroid.Model.objData.GetInstance2;
import static com.example.visitorandroid.R.id.ry_manage_list;


public class MyFragmentOrderListManage extends Fragment implements View.OnClickListener,
        AdapterView.OnItemClickListener {

    private String content;
    private Activity activity;

    private Button orderlist_btnback;
    private ListView order_list;

    private List<OrderListViewModel> mData = null;
    private Context mContext;
    private MyOrderListAdapter mAdapter = null;
    private MyFragmentOrderResult fgOrderResult;

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
//        mData.add(new OrderListViewModel("xx申请拜访xxx","2017年09月11日","已拒绝",R.drawable.ic_navend));
//        mData.add(new OrderListViewModel("xxx申请拜访xx","2017年09月12日","已通过",R.drawable.ic_navend));
//        mData.add(new OrderListViewModel("xxxx申请拜访xxxx","2017年09月13日","已拒绝",R.drawable.ic_navend));
//        mAdapter = new MyOrderListAdapter((LinkedList<OrderListViewModel>) mData, mContext);
        order_list.setAdapter(mAdapter);

        orderlist_btnback.setOnClickListener(this);
        order_list.setOnItemClickListener(this);

//        String address_order="http://www.tytechkj.com/App/Permission/";
//        queryOrder(address_order);
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

        OrderListViewModel orderresult = mData.get(position);
        FragmentTransaction fTransaction = getFragmentManager().beginTransaction();
        hideAllFragment(fTransaction);
        if (fgOrderResult == null) {
            fgOrderResult = new MyFragmentOrderResult(orderresult.getOrdermessage() +
            orderresult.getOrdertime());
            fTransaction.add(R.id.fb_order_list, fgOrderResult);
            fTransaction.addToBackStack(null);
        } else {
            fTransaction.add(R.id.fb_order_list, fgOrderResult);
            fTransaction.addToBackStack(null);
            fTransaction.show(fgOrderResult);
        }
        fTransaction.commit();
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fgOrderResult != null)fragmentTransaction.hide(fgOrderResult);
    }

//    private void queryOrder(String address) {
//        DialogMethod.MyProgressDialog(getContext(),"正在上传中...",true);
//        RequestBody requestBody = new FormBody.Builder()
//                .add("UserID",GetInstance().User.getGUID())
//                .add("CompanyID", String.valueOf(GetInstance2().data.getID()))
//                .build();
//        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String responseText = response.body().string();
//                Gson gson = new Gson();
//                user = gson.fromJson(responseText, ResultViewModel.class);
//                if (!user.IsError) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            DialogMethod.MyProgressDialog(getContext(),"",false);
//                            mData.clear();
//                            for(Data data : user.Data){
//                                mData.add(new Data(data.getC_Name(),data.getEmployeeCount(),data.getNickName(),data.getDepartmentName()));
//                            }
//                            mAdapter = new MyRyAdapter((LinkedList<Data>) mData, mContext);
//                            order_list.setAdapter(mAdapter);
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        DialogMethod.MyProgressDialog(getContext(),"",false);
//                        Toast.makeText(getContext(),"获取部门失败",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(getActivity());
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("android.intent.action.CART_BROADCAST");
//        BroadcastReceiver mItemViewListClickReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent){
//                String msg = intent.getStringExtra("data");
//                if("refresh".equals(msg)){
//                    String address_order="http://www.tytechkj.com/App/Permission/";
//                    queryOrder(address_order);
//                }
//            }
//        };
//        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);
//    }
}
