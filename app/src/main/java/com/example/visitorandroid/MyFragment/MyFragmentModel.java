package com.example.visitorandroid.MyFragment;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visitorandroid.Adapter.MyAdapter;
import com.example.visitorandroid.Adapter.MyManageAdapter;
import com.example.visitorandroid.Model.Icon;
import com.example.visitorandroid.R;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;

public class MyFragmentModel extends Fragment {

    private String content;
    private Activity activity;
    private RollPagerView mRollViewPager;
    private TextView txtTopbar;
    private View div_tabbar;
    private GridView grid_photo;
    private Context mContext;
    private ArrayList<Icon> mData;
    private MyAdapter<Icon> mAdapter = null;


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

        bindViews(view);

        return view;
    }

    private void bindViews(View view) {
        mRollViewPager = (RollPagerView) view.findViewById(R.id.roll_view_pager);
        grid_photo = (GridView) view.findViewById(R.id.grid_photo);

        mContext = getActivity();

        //设置播放时间间隔
        mRollViewPager.setPlayDelay(1000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        //设置适配器
        mRollViewPager.setAdapter(new MyManageAdapter.ImagePagerAdapter());

        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器
        //mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
        mRollViewPager.setHintView(new ColorPointHintView(getContext(), Color.RED, Color.GRAY));
        //mRollViewPager.setHintView(new TextHintView(this));
        //mRollViewPager.setHintView(null);

        mData = new ArrayList<Icon>();
        mData.add(new Icon(R.mipmap.iv_icon_1, "消息列表"));
        mData.add(new Icon(R.mipmap.iv_icon_2, "预约单"));
        mData.add(new Icon(R.mipmap.iv_icon_3, "位置"));
        mData.add(new Icon(R.mipmap.iv_icon_4, "快递"));
        mData.add(new Icon(R.mipmap.iv_icon_5, "车牌识别"));
        mData.add(new Icon(R.mipmap.iv_icon_6, "企业管理"));
        mAdapter = new MyAdapter<Icon>(mData, R.layout.item_grid_icon) {
            @Override
            public void bindView(ViewHolder holder, Icon obj) {
                holder.setImageResource(R.id.img_icon, obj.getiId());
                holder.setText(R.id.txt_icon, obj.getiName());
            }
        };

        grid_photo.setAdapter(mAdapter);

        grid_photo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "你点击了~" + position + "~项", Toast.LENGTH_SHORT).show();
            }
        });

    }

}