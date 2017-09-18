package com.example.visitorandroid.MyFragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.visitorandroid.Model.BaseViewModel;
import com.example.visitorandroid.Model.UserViewModel;
import com.example.visitorandroid.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.visitorandroid.R.id.nav_sub_headericon;
import static com.squareup.picasso.Picasso.with;
import static java.lang.System.load;

public class MyFragmentBetter extends Fragment implements View.OnClickListener {

    private String content;

    //Fragment Object
    private MyFragmentHeader fgHeader;
    private MyFragmentManage fgManage;
    private MyFragmentInMessage fgInManage;
    private MyFragmentSetting fgSetting;

    private CircleImageView icon_image;
    private TextView nav_username;
    private TextView nav_manage;
    private TextView nav_message;
    private TextView nav_history;
    private TextView nav_setting;

    public MyFragmentBetter(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_better,container,false);

        bindViews(view);

        return view;
    }

    private  void bindViews(View view){

        icon_image = (CircleImageView) view.findViewById(R.id.icon_image);
        nav_username = (TextView) view.findViewById(R.id.nav_username);
        nav_manage = (TextView) view.findViewById(R.id.nav_manage);
        nav_message = (TextView) view.findViewById(R.id.nav_message);
        nav_history = (TextView) view.findViewById(R.id.nav_history);
        nav_setting = (TextView) view.findViewById(R.id.nav_setting);

        String picstring = BaseViewModel.GetInstance().User.getHeadPicUrl();
        Picasso.with(getContext())
                .load(picstring)
                .into(icon_image);

        nav_username.setText(BaseViewModel.GetInstance().User.getNickName());

        icon_image.setOnClickListener(this);
        nav_manage.setOnClickListener(this);
        nav_message.setOnClickListener(this);
        nav_history.setOnClickListener(this);
        nav_setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction fTransaction =  getFragmentManager().beginTransaction();
        hideAllFragment(fTransaction);
        switch (view.getId()){
            case R.id.icon_image:
                if(fgHeader == null){
                    fgHeader = new MyFragmentHeader("个人信息");
                    fTransaction.add(R.id.fb_content,fgHeader);
                    fTransaction.addToBackStack(null);
                }else{
                    fTransaction.add(R.id.fb_content,fgHeader);
                    fTransaction.addToBackStack(null);
                    fTransaction.show(fgHeader);
                }
                break;
            case R.id.nav_manage:
                if(fgManage == null){
                    fgManage = new MyFragmentManage("公司管理");
                    fTransaction.add(R.id.fb_content,fgManage);
                    fTransaction.addToBackStack(null);
                }else{
                    fTransaction.add(R.id.fb_content,fgManage);
                    fTransaction.addToBackStack(null);
                    fTransaction.show(fgManage);
                }
                break;
            case R.id.nav_message:
                if(fgInManage == null){
                    fgInManage = new MyFragmentInMessage("发送站内信");
                    fTransaction.add(R.id.fb_content,fgInManage);
                    fTransaction.addToBackStack(null);
                }else{
                    fTransaction.add(R.id.fb_content,fgInManage);
                    fTransaction.addToBackStack(null);
                    fTransaction.show(fgInManage);
                }
                break;
            case R.id.nav_history:
                break;
            case R.id.nav_setting:
                if(fgSetting == null){
                    fgSetting = new MyFragmentSetting("设置");
                    fTransaction.add(R.id.fb_content,fgSetting);
                    fTransaction.addToBackStack(null);
                }else{
                    fTransaction.add(R.id.fb_content,fgSetting);
                    fTransaction.addToBackStack(null);
                    fTransaction.show(fgSetting);
                }
                break;
        }
        fTransaction.commit();
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fgHeader != null)fragmentTransaction.hide(fgHeader);
        if(fgManage != null)fragmentTransaction.hide(fgManage);
        if(fgInManage != null)fragmentTransaction.hide(fgInManage);
        if(fgSetting != null)fragmentTransaction.hide(fgSetting);
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
                    Picasso.with(getContext())
                            .load(BaseViewModel.GetInstance().User.getHeadPicUrl())
                            .into(icon_image);
                    nav_username.setText(BaseViewModel.GetInstance().User.getNickName());
                }
            }
        };
        broadcastManager.registerReceiver(mItemViewListClickReceiver, intentFilter);
    }
}