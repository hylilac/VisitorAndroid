package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visitorandroid.MainActivity;
import com.example.visitorandroid.R;

import static android.R.attr.data;


public class MyFragmentHeaderIcon extends Fragment implements View.OnClickListener {

    private String content;

    private TextView txtTopbar;
    private Button backButton;
    private Button backBtCancel;
    private Button backBtSend;
    private RadioGroup radios;

    private ImageView navHeaderPhoto;

    public MyFragmentHeaderIcon(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_headericon,container,false);

        txtTopbar = getActivity().findViewById(R.id.txt_topbar);
        txtTopbar.setText(content);
        radios =  getActivity().findViewById(R.id.rg_tab_bar);
        radios.setBackgroundColor(Color.BLACK);
        radios.setVisibility(View.INVISIBLE);
        View view1 = getActivity().findViewById(R.id.div_tab_bar);
        view1.setVisibility(View.INVISIBLE);
        backButton = getActivity().findViewById(R.id.back_button);
        backButton.setVisibility(View.VISIBLE);
        backButton.setText("个人信息");
        backBtCancel = getActivity().findViewById(R.id.back_bt_cancel);
        backBtCancel.setVisibility(View.INVISIBLE);
        backBtSend = getActivity().findViewById(R.id.back_bt_send);
        backBtSend.setVisibility(View.VISIBLE);
        //backBtSend.setText("");

        navHeaderPhoto = (ImageView) view.findViewById(R.id.nav_headerphoto);

        Drawable drawable= getResources().getDrawable(R.drawable.icon_camera);
        drawable.setBounds(0,0,20,0);
        backBtSend.setCompoundDrawables(null,null,drawable,null);

        backButton.setOnClickListener(this);
        backBtSend.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_button:
                txtTopbar.setVisibility(View.VISIBLE);
                txtTopbar.setText("个人信息");
                backButton.setVisibility(View.INVISIBLE);
                radios.setVisibility(View.VISIBLE);
                getActivity().onBackPressed();
                break;
            case R.id.back_bt_send:
                isTakePhoto();



                break;
        }
    }

    private void isTakePhoto() {
        final String[] choose = new String[]{"拍照", "从手机相册选择", "取消"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setItems(choose, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (choose[which].equals("拍照")){
                    Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(it, Activity.DEFAULT_KEYS_DIALER);
                }else if (choose[which].equals("从手机相册选择")){

                }
            }
        }).create();

        builder.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Activity.RESULT_OK){
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            navHeaderPhoto.setImageBitmap(bitmap);
        }
    }
}
