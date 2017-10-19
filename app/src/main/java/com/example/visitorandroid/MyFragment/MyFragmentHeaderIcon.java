package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.visitorandroid.LoginActivity;
import com.example.visitorandroid.MainActivity;
import com.example.visitorandroid.Model.BaseViewModel;
import com.example.visitorandroid.Model.DialogMethod;
import com.example.visitorandroid.Model.UserInfo;
import com.example.visitorandroid.Model.UserViewModel;
import com.example.visitorandroid.R;
import com.example.visitorandroid.util.HttpUtil;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.R.attr.bitmap;
import static android.os.Build.VERSION_CODES.M;
import static com.example.visitorandroid.Model.BaseViewModel.GetInstance;
import static com.example.visitorandroid.Model.DialogMethod.bitmapToBase64;


public class MyFragmentHeaderIcon extends Fragment implements View.OnClickListener {

    public String content;
    private Activity activity;
    private Uri imageUri;
    private Bitmap bitmap;
    private UserInfo user;

    private ImageView navHeaderPhoto;

    private Button usericon_btback;
    private Button usericon_btphoto;

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;

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

        imageUri=Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "test.jpg"));

        usericon_btback = (Button) view.findViewById(R.id.usericon_btn_back);
        usericon_btphoto = (Button) view.findViewById(R.id.usericon_btn_photo);
        navHeaderPhoto = (ImageView) view.findViewById(R.id.nav_headerphoto);

        String picstring = GetInstance().User.getHeadPicUrl();
        Picasso.with(getContext())
                .load(picstring)
                .into(navHeaderPhoto);

        usericon_btback.setOnClickListener(this);
        usericon_btphoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.usericon_btn_back:
                activity.onBackPressed();
                Intent intent = new Intent("android.intent.action.CART_BROADCAST");
                intent.putExtra("data","refresh");
                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
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
                    startActivityForResult(it, TAKE_PHOTO);
                }else if (choose[which].equals("从手机相册选择")){
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, CHOOSE_PHOTO);
                }
            }
        }).create();

        builder.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode == Activity.RESULT_OK){
                    try {
                        Bundle bundle = data.getExtras();
                        bitmap = (Bitmap) bundle.get("data");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if(resultCode == Activity.RESULT_OK){
                    try {
                        Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                        String[] filePathColumn = { MediaStore.Images.Media.DATA };
                        Cursor cursor = getContext().getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);  //获取照片路径
                        cursor.close();
                        bitmap= BitmapFactory.decodeFile(picturePath);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
        }
        String headerString = DialogMethod.bitmapToBase64(bitmap);
        String address_header="http://www.tytechkj.com/App/Permission/headpicupload";
        queryHeader(address_header,headerString);
    }

    private void queryHeader(String address, final String picstring) {
        DialogMethod.MyProgressDialog(getContext(),"正在处理中...",true);
        RequestBody requestBody = new FormBody.Builder()
                .add("ID", GetInstance().User.getGUID())
                .add("desccode",picstring)
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                user = gson.fromJson(responseText, UserInfo.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogMethod.MyProgressDialog(getContext(), "", false);

                        if (!user.IsError) {

                            String s = new Gson().toJson(user.Data);
                            UserViewModel lll = new Gson().fromJson(s, UserViewModel.class);
                            BaseViewModel.GetInstance().User.HeadPicUrl = lll.HeadPicUrl;
                            navHeaderPhoto.setImageBitmap(DialogMethod.base64ToBitmap(picstring));
                        }else {
                            Toast.makeText(getContext(),user.Message,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        DialogMethod.MyProgressDialog(getContext(),"",false);
                        Toast.makeText(getContext(),"上传图片失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
