package com.example.visitorandroid.MyFragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.visitorandroid.R;

public class MyFragmentSetting extends Fragment {

    private String content;


    public MyFragmentSetting(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_setting,container,false);
        TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
        txt_content.setText(content);

        TextView txt_topbar = getActivity().findViewById(R.id.txt_topbar);
        txt_topbar.setText(content);
        RadioGroup radios =  getActivity().findViewById(R.id.rg_tab_bar);
        radios.setVisibility(View.INVISIBLE);
        View view1 = getActivity().findViewById(R.id.div_tab_bar);
        view1.setVisibility(View.INVISIBLE);

        return view;
    }
}
