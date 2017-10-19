package com.example.visitorandroid.MyFragment;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.visitorandroid.R;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.example.visitorandroid.R.id.message_list;

public class MyFragmentMessage extends Fragment {

    public String content;
    private ListView messageList;

    public MyFragmentMessage(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_message,container,false);

        bindViews(view);

        return view;
    }

    private void bindViews(View view) {

        messageList = (ListView) view.findViewById(message_list);
    }
}