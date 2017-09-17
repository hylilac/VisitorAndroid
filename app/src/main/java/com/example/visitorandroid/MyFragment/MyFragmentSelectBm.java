package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.visitorandroid.Adapter.MyBmAdapter;
import com.example.visitorandroid.Model.BmViewModel;
import com.example.visitorandroid.Model.Data;
import com.example.visitorandroid.R;

import java.util.LinkedList;
import java.util.List;

public class MyFragmentSelectBm extends Fragment implements View.OnClickListener, AdapterView
        .OnItemClickListener, TextWatcher {

    private String content;
    private Activity activity;

    private Button selectbm_btnback;
    private ListView selectbm_list;

    private List<Data> mData = null;
    private Context mContext;
    private MyBmAdapter mAdapter = null;

    private EditText bmname;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }



    public MyFragmentSelectBm(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_selectbm,container,false);

        bindViews(view);

        return view;
    }

    private void bindViews(View view) {
        selectbm_btnback = (Button) view.findViewById(R.id.select_bm_btn_back);
        selectbm_list = (ListView) view.findViewById(R.id.select_bm_list);

        mContext = getActivity();

        mData = new LinkedList<Data>();
//        mData.add(new BmViewModel("董事会"));
//        mData.add(new BmViewModel("技术部"));
//        mData.add(new BmViewModel("后勤"));
        mAdapter = new MyBmAdapter((LinkedList<Data>) mData, mContext);
        selectbm_list.setAdapter(mAdapter);

        selectbm_btnback.setOnClickListener(this);
        selectbm_list.setOnItemClickListener(this);

//        String address_bm="http://www.tytechkj.com/App/Permission/ChangeNickName";
//        queryBm(address_bm,nickname);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.select_bm_btn_back:
                activity.onBackPressed();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        Data bm = mData.get(position);
        String bmstring = bm.getC_Name();
        activity.onBackPressed();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
