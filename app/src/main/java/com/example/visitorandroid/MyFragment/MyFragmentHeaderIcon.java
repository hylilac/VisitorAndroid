package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
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
    private Activity activity;

    private ImageView navHeaderPhoto;

    private Button usericon_btback;
    private Button usericon_btphoto;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public MyFragmentHeaderIcon(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_headericon,container,false);

        bindViews(view);

        return view;
    }

    private void bindViews(View view) {

        usericon_btback = (Button) view.findViewById(R.id.usericon_btn_back);
        usericon_btphoto = (Button) view.findViewById(R.id.usericon_btn_photo);

        usericon_btback.setOnClickListener(this);
        usericon_btphoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.usericon_btn_back:
                activity.onBackPressed();
                break;
            case R.id.usericon_btn_photo:
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
