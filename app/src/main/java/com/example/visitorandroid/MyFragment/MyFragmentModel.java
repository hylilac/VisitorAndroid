package com.example.visitorandroid.MyFragment;
import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.visitorandroid.R;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

public class MyFragmentModel extends Fragment {

    private String content;
    private Activity activity;
    private RollPagerView mRollViewPager;
    private TextView txtTopbar;
    private View div_tabbar;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public MyFragmentModel(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_model,container,false);

        txtTopbar = activity.findViewById(R.id.txt_topbar);
        txtTopbar.setVisibility(View.GONE);

        div_tabbar = activity.findViewById(R.id.div_tab_bar);
        div_tabbar.setVisibility(View.GONE);

        mRollViewPager = (RollPagerView) view.findViewById(R.id.roll_view_pager);

        //设置播放时间间隔
        mRollViewPager.setPlayDelay(1000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        //设置适配器
        mRollViewPager.setAdapter(new ImagePagerAdapter());

        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        //mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
        mRollViewPager.setHintView(new ColorPointHintView(getContext(), Color.RED, Color.GRAY));
        //mRollViewPager.setHintView(new TextHintView(this));
        //mRollViewPager.setHintView(null);

        return view;
    }
}