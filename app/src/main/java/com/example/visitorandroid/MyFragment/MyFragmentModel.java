package com.example.visitorandroid.MyFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.visitorandroid.R;

import static com.example.visitorandroid.R.id.txt_content;

public class MyFragmentModel extends Fragment {

    private String content;

    public MyFragmentModel(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_model,container,false);

        return view;
    }
}