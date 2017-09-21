package com.example.visitorandroid.MyFragment;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.visitorandroid.R;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

/**
 * Created by hy on 2017/9/21.
 */

public class ImagePagerAdapter extends StaticPagerAdapter {

    private int[] imgs = {
            R.mipmap.icon_1,
            R.mipmap.icon_2,
            R.mipmap.icon_3,
            R.mipmap.icon_4,
            R.mipmap.icon_5,
            R.mipmap.icon_6,
            R.mipmap.icon_7
    };


    @Override
    public View getView(ViewGroup container, int position) {
        ImageView view = new ImageView(container.getContext());
        view.setImageResource(imgs[position]);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return view;
    }


    @Override
    public int getCount() {
        return imgs.length;
    }
}
