package com.example.visitorandroid;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.visitorandroid.MyFragment.MyFragmentBetter;
import com.example.visitorandroid.MyFragment.MyFragmentMessage;
import com.example.visitorandroid.MyFragment.MyFragmentModel;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    //Activity UI Object
    private RadioGroup rg_tab_bar;
    private RadioButton rb_model;

    //Fragment Object
    private MyFragmentModel fgModel;
    private MyFragmentMessage fgMessage;
    private MyFragmentBetter fgBetter;
    private FragmentManager fManager;
    private TextView txtTopbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTopbar = (TextView) findViewById(R.id.txt_topbar);

        fManager = getFragmentManager();
        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        rg_tab_bar.setOnCheckedChangeListener(this);
        //获取第一个单选按钮，并设置其为选中状态
        rb_model = (RadioButton) findViewById(R.id.rb_model);
        rb_model.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (checkedId){
            case R.id.rb_model:
                txtTopbar.setVisibility(View.INVISIBLE);
                if(fgModel == null){
                    fgModel = new MyFragmentModel("第一个Fragment");
                    fTransaction.add(R.id.ly_content,fgModel);
                }else{
                    fTransaction.show(fgModel);
                }
                break;
            case R.id.rb_message:
                txtTopbar.setVisibility(View.VISIBLE);
                txtTopbar.setText("站内信");
                if(fgMessage == null){
                    fgMessage = new MyFragmentMessage("第二个Fragment");
                    fTransaction.add(R.id.ly_content,fgMessage);
                }else{
                    fTransaction.show(fgMessage);
                }
                break;
            case R.id.rb_better:
                txtTopbar.setVisibility(View.VISIBLE);
                txtTopbar.setText("我");
                if(fgBetter == null){
                    fgBetter = new MyFragmentBetter("第三个Fragment");
                    fTransaction.add(R.id.ly_content,fgBetter);
                }else{
                    fTransaction.show(fgBetter);
                }
                break;
        }
        fTransaction.commit();
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fgModel != null)fragmentTransaction.hide(fgModel);
        if(fgMessage != null)fragmentTransaction.hide(fgMessage);
        if(fgBetter != null)fragmentTransaction.hide(fgBetter);
    }
}
