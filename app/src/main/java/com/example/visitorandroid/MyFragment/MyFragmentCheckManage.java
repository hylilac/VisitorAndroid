package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.visitorandroid.Adapter.MyCheckOrderAdapter;
import com.example.visitorandroid.Adapter.MyCheckUserAdapter;
import com.example.visitorandroid.Adapter.MyOrderListAdapter;
import com.example.visitorandroid.Model.BaseViewModel;
import com.example.visitorandroid.Model.CheckOrderViewModel;
import com.example.visitorandroid.Model.CheckUserViewModel;
import com.example.visitorandroid.Model.DialogMethod;
import com.example.visitorandroid.Model.OrderListViewModel;
import com.example.visitorandroid.Model.UserInfo;
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

import static android.R.attr.order;
import static com.example.visitorandroid.Model.BaseViewModel.GetInstance;
import static com.example.visitorandroid.R.drawable.shapedrawableleft;
import static com.example.visitorandroid.R.id.check_order;
import static com.example.visitorandroid.R.id.nav_sub_sex;
import static com.example.visitorandroid.R.id.order_list;
import static com.example.visitorandroid.R.id.rg_tab_bar;

public class MyFragmentCheckManage extends Fragment implements View.OnClickListener, RadioGroup
        .OnCheckedChangeListener, AdapterView.OnItemClickListener {

    private String content;
    private Activity activity;

    private Button checkmanage_btnback;
    private RadioGroup check_tab_bar;
    private RadioButton checkorder;
    private ListView check_manage_list;

    private List<CheckOrderViewModel> mDataOrder = null;
    private List<CheckUserViewModel> mDataUser = null;
    private Context mContext;
    private MyCheckOrderAdapter mAdapterOrder = null;
    private MyCheckUserAdapter mAdapterUser = null;
    private UserInfo user;
    private List<CheckOrderViewModel> checkOrderList = null;
    private List<CheckUserViewModel> checkUserList = null;
    private RadioButton checkuser;
    private int flag = 0;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public MyFragmentCheckManage(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fg_checkmanage,container,false);

        bindViews(view);

        return view;
    }

    private void bindViews(View view) {

        checkmanage_btnback = (Button) view.findViewById(R.id.check_manage_btn_back);
        check_tab_bar = (RadioGroup) view.findViewById(R.id.check_tab_bar);
        checkorder = (RadioButton) view.findViewById(R.id.check_order);
        checkuser = (RadioButton) view.findViewById(R.id.check_user);
        check_manage_list = (ListView) view.findViewById(R.id.check_manage_list);

        checkmanage_btnback.setOnClickListener(this);
        check_tab_bar.setOnCheckedChangeListener(this);
        checkorder.setChecked(true);
        check_manage_list.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.check_manage_btn_back:
                activity.onBackPressed();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        mContext = getActivity();
        mDataOrder = new LinkedList<CheckOrderViewModel>();
        mDataUser = new LinkedList<CheckUserViewModel>();
        switch (checkedId) {
            case R.id.check_order:
                checkuser.setBackgroundResource(R.drawable.shapedrawableright);
                checkuser.setTextColor(Color.rgb(0,122,255));
                checkorder.setBackgroundResource(R.drawable.shapedrawableleft);
                checkorder.setTextColor(Color.WHITE);
                String address_checkorder="http://www.tytechkj.com/App/Permission/GetVerifyOrder";
                queryCheckOrder(address_checkorder);
                flag = 0;
                break;
            case R.id.check_user:
                checkorder.setBackgroundResource(R.drawable.shapedrawableright1);
                checkorder.setTextColor(Color.rgb(0,122,255));
                checkuser.setBackgroundResource(R.drawable.shapedrawableleft1);
                checkuser.setTextColor(Color.WHITE);
                String address_checkuser="http://www.tytechkj.com/App/Permission/GetVerifyUser";
                queryCheckUser(address_checkuser);
                flag = 1;
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view,final int position, long id) {

        final String[] choose = new String[]{"确认通过", "取消"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setItems(choose, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (choose[which].equals("确认通过")) {
                    if (flag == 0){
                        int orderid = mDataOrder.get(position).getID();
                        String visitorid = mDataOrder.get(position).getVisitor_ID();
                        String address_passorder="http://www.tytechkj.com/App/Permission/PassVisitorVerify";
                        queryPassOrder(address_passorder,orderid,visitorid);
                    }else if (flag == 1){
                        int userid = mDataUser.get(position).getID();
                        String address_passuser="http://www.tytechkj.com/App/Permission/PassUserVerify";
                        queryPassUser(address_passuser,userid);
                    }

                } else if (choose[which].equals("取消")) {

                }
            }
        }).create();

        builder.show();
    }

    /**
     * CID 公司ID
     */

    private void queryCheckOrder(String address) {
        DialogMethod.MyProgressDialog(getContext(),"正在上传中...",true);
        RequestBody requestBody = new FormBody.Builder()
                .add("CID",String.valueOf(GetInstance().CompanyView.getID()))
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                checkOrderList = gson.fromJson(responseText,
                        new TypeToken<List<CheckOrderViewModel>>(){}.getType());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogMethod.MyProgressDialog(getContext(),"",false);
                        mDataOrder.clear();
                        for (CheckOrderViewModel checkOrder : checkOrderList){
                            BaseViewModel.GetInstance().setCheckOrder(checkOrder);
                            mDataOrder.add(new CheckOrderViewModel(checkOrder.getID(),checkOrder.getVisitor_ID(),checkOrder.getV_name(),checkOrder.getBv_name(),checkOrder.getVisitorTime()));
                        }
                        mAdapterOrder = new MyCheckOrderAdapter((LinkedList<CheckOrderViewModel>) mDataOrder, mContext);
                        check_manage_list.setAdapter(mAdapterOrder);
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
                        Toast.makeText(getContext(),"获取部门失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * CID 公司ID
     */

    private void queryCheckUser(String address) {
        DialogMethod.MyProgressDialog(getContext(),"正在上传中...",true);
        RequestBody requestBody = new FormBody.Builder()
                .add("CID",String.valueOf(GetInstance().CompanyView.getID()))
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                checkUserList = gson.fromJson(responseText,
                        new TypeToken<List<CheckUserViewModel>>(){}.getType());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogMethod.MyProgressDialog(getContext(),"",false);
                        mDataUser.clear();
                        for (CheckUserViewModel checkUser : checkUserList){
                            BaseViewModel.GetInstance().setCheckUser(checkUser);
                            mDataUser.add(new CheckUserViewModel(checkUser.getV_name(),checkUser.getBv_name(),checkUser.getVisitorTime()));
                        }
                        mAdapterUser = new MyCheckUserAdapter((LinkedList<CheckUserViewModel>) mDataUser, mContext);
                        check_manage_list.setAdapter(mAdapterUser);
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
                        Toast.makeText(getContext(),"获取部门失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * ID 考核订单ID
     * VisitorID 访客ID
     */

    private void queryPassOrder(String address,int orderid,String visitorid) {
        DialogMethod.MyProgressDialog(getContext(),"正在上传中...",true);
        RequestBody requestBody = new FormBody.Builder()
                .add("ID",String.valueOf(orderid))
                .add("visitorID",visitorid)
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                user = gson.fromJson(responseText, UserInfo.class);
                if (!user.IsError) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogMethod.MyProgressDialog(getContext(), "", false);
                            String address_checkorder="http://www.tytechkj.com/App/Permission/GetVerifyOrder";
                            queryCheckOrder(address_checkorder);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogMethod.MyProgressDialog(getContext(),"",false);
                        Toast.makeText(getContext(),"获取部门失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * ID 考核用户ID
     */

    private void queryPassUser(String address,int userid) {
        DialogMethod.MyProgressDialog(getContext(),"正在上传中...",true);
        RequestBody requestBody = new FormBody.Builder()
                .add("ID",String.valueOf(userid))
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                user = gson.fromJson(responseText, UserInfo.class);
                if (!user.IsError) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogMethod.MyProgressDialog(getContext(), "", false);
                            String address_checkuser="http://www.tytechkj.com/App/Permission/GetVerifyUser";
                            queryCheckUser(address_checkuser);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogMethod.MyProgressDialog(getContext(),"",false);
                        Toast.makeText(getContext(),"获取部门失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
