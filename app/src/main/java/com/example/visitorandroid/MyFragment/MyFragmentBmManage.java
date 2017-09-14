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

import com.example.visitorandroid.Model.BmViewModel;
import com.example.visitorandroid.Adapter.MyBmAdapter;
import com.example.visitorandroid.R;

import java.util.LinkedList;
import java.util.List;

public class MyFragmentBmManage extends Fragment implements View.OnClickListener, AdapterView
        .OnItemClickListener, TextWatcher {

    private String content;
    private Activity activity;

    private Button bmmanage_btnback;
    private Button bmmanage_btnadd;
    private ListView bmmanage_list;

    private List<BmViewModel> mData = null;
    private Context mContext;
    private MyBmAdapter mAdapter = null;

    private EditText bmname;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }



    public MyFragmentBmManage(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_bmmanage,container,false);

        bindViews(view);

        return view;
    }

    private void bindViews(View view) {
        bmmanage_btnback = (Button) view.findViewById(R.id.bm_manage_btn_back);
        bmmanage_btnadd = (Button) view.findViewById(R.id.bm_manage_btn_add);
        bmmanage_list = (ListView) view.findViewById(R.id.bm_manage_list);

        mContext = getActivity();
        mData = new LinkedList<BmViewModel>();
        mData.add(new BmViewModel("董事会"));
        mData.add(new BmViewModel("技术部"));
        mData.add(new BmViewModel("后勤"));
        mAdapter = new MyBmAdapter((LinkedList<BmViewModel>) mData, mContext);
        bmmanage_list.setAdapter(mAdapter);

        bmmanage_btnback.setOnClickListener(this);
        bmmanage_btnadd.setOnClickListener(this);
        bmmanage_list.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bm_manage_btn_back:
                activity.onBackPressed();
                break;
            case R.id.bm_manage_btn_add:
                MyDialog("","取消","添加");
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        BmViewModel bm = mData.get(position);
        String bmstring = bm.getaName();
        MyDialog(bmstring,"取消","修改");
    }

    private void MyDialog(String bmstring,String cancel,String save) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("部门名称");
        View vv = LayoutInflater.from(getActivity()).inflate(R.layout.etdialog, null);
        bmname = (EditText)vv.findViewById(R.id.et_dialog);
        bmname.setText(bmstring);
        builder.setView(vv);
        builder.setCancelable(false);
        builder.setPositiveButton(save, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.setNegativeButton(cancel, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        builder.show();
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
