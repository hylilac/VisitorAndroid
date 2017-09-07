package com.example.visitorandroid.MyFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visitorandroid.R;

public class MyFragmentBetter extends Fragment implements NavigationView
        .OnNavigationItemSelectedListener {

    private String content;
    private MyFragmentManage fgManage;
    private FragmentManager fManager;

    public MyFragmentBetter(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_better,container,false);

        NavigationView navView = (NavigationView) view.findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

        TextView nav_username = (TextView) view.findViewById(R.id.nav_username);
        nav_username.setText(content);

        return view;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
//        FragmentTransaction fTransaction = fManager.beginTransaction();
//        hideAllFragment(fTransaction);
        switch (item.getItemId()){
            case R.id.nav_manage:
//                if(fgManage == null){
//                    fgManage = new MyFragmentManage("公司管理");
//                    fTransaction.add(R.id.fb_content,fgManage);
//                }else{
//                    fTransaction.show(fgManage);
//                }

                Toast.makeText(getActivity(),"nisfhjui",Toast.LENGTH_SHORT);

                break;
            case R.id.nav_message:
                break;
            case R.id.nav_history:
                break;
            case R.id.nav_setting:
                break;
        }
//        fTransaction.commit();
        return true;
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fgManage != null)fragmentTransaction.hide(fgManage);
//        if(fgMessage != null)fragmentTransaction.hide(fgMessage);
//        if(fgBetter != null)fragmentTransaction.hide(fgBetter);
    }
}