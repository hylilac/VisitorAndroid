package com.example.visitorandroid.MyFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.visitorandroid.R;

public class MyFragmentMessage extends Fragment {

    private String content;

    public MyFragmentMessage(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_message,container,false);
        return view;
    }
}