package com.example.visitorandroid;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.visitorandroid.Adapter.GuideViewPagerAdapter;
import com.example.visitorandroid.global.AppConstants;
import com.example.visitorandroid.util.SpUtils;

import java.util.ArrayList;
import java.util.List;

public class WelcomeGuideActivity extends AppCompatActivity implements ViewPager
        .OnPageChangeListener {

    private ViewPager vpager_one;
    private ArrayList<View> aList;
    private GuideViewPagerAdapter mAdapter;

    private boolean isLastPage = false;
    private boolean isDragPage = false;
    private boolean canJumpPage = true;

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

        // 判断是否是第一次开启应用
        boolean isFirstOpen = SpUtils.getBoolean(this, AppConstants.FIRST_OPEN);
        // 如果是第一次启动，则先进入功能引导页
        if (!isFirstOpen) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_guide);
        vpager_one = (ViewPager) findViewById(R.id.vp_guide);

        aList = new ArrayList<View>();
        LayoutInflater li = getLayoutInflater();
        aList.add(li.inflate(R.layout.guid_view1,null,false));
        aList.add(li.inflate(R.layout.guid_view2,null,false));
        aList.add(li.inflate(R.layout.guid_view3,null,false));
        mAdapter = new GuideViewPagerAdapter(aList);
        vpager_one.setAdapter(mAdapter);

        //设置滑动监听
        vpager_one.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (isLastPage && isDragPage && positionOffsetPixels == 0){   //当前页是最后一页，并且是拖动状态，并且像素偏移量为0
            if (canJumpPage){
                canJumpPage = false;
                Intent intent = new Intent(WelcomeGuideActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        isLastPage = position == 2;
//        if (Flag == 3){
//            Intent intent = new Intent(WelcomeGuideActivity.this,LoginActivity.class);
//            startActivity(intent);
//            finish();
//        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        isDragPage = state == 1;
    }
}