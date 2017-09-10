package com.example.visitorandroid;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button backButton;
    private Button backBtCancel;
    private Button backBtSend;

    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_main);

        txtTopbar = (TextView) findViewById(R.id.txt_topbar);
        backButton = (Button) findViewById(R.id.back_button);
        backBtCancel = (Button) findViewById(R.id.back_bt_cancel);
        backBtSend = (Button) findViewById(R.id.back_bt_send);

        fManager = getFragmentManager();
        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        rg_tab_bar.setOnCheckedChangeListener(this);
        //获取第一个单选按钮，并设置其为选中状态
        rb_model = (RadioButton) findViewById(R.id.rb_model);
        rb_model.setChecked(true);

        backButton.setVisibility(View.INVISIBLE);
        backBtCancel.setVisibility(View.INVISIBLE);
        backBtSend.setVisibility(View.INVISIBLE);
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
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                    String nickname = prefs.getString("regNickname",null);
                    fgBetter = new MyFragmentBetter(nickname);
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

    /**点击回退键的处理：判断Fragment栈中是否有Fragment
     * 没，双击退出程序，否则像是Toast提示
     * 有，popbackstack弹出栈
    */

    @Override

    public void onBackPressed() {
        if (fManager.getBackStackEntryCount() == 0) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        } else {
            fManager.popBackStack();
        }
    }
}
