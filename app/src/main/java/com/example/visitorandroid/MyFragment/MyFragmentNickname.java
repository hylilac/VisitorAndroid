package com.example.visitorandroid.MyFragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.visitorandroid.MainActivity;
import com.example.visitorandroid.Model.BaseViewModel;
import com.example.visitorandroid.Model.DialogMethod;
import com.example.visitorandroid.Model.UserInfo;
import com.example.visitorandroid.Model.UserViewModel;
import com.example.visitorandroid.R;
import com.example.visitorandroid.util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.example.visitorandroid.Model.BaseViewModel.GetInstance;

public class MyFragmentNickname extends Fragment implements View.OnClickListener, TextWatcher {

    private String content;
    private Activity activity;

    private EditText et_nav_nickname;
    private Button nickname_btnback;
    private Button nickname_btnsave;
    private UserInfo user;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    public MyFragmentNickname(String content) {
        this.content = content;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_nickname,container,false);

        bindViews(view);

        return view;
    }

    private void bindViews(View view) {

        et_nav_nickname = (EditText) view.findViewById(R.id.et_nav_nickname);
        nickname_btnback = (Button) view.findViewById(R.id.nickname_btn_back);
        nickname_btnsave = (Button) view.findViewById(R.id.nickname_btn_save);

        et_nav_nickname.setText(BaseViewModel.GetInstance().User.getNickName());
        nickname_btnsave.setEnabled(false);
        nickname_btnsave.setTextColor(Color.GRAY);

        et_nav_nickname.addTextChangedListener(this);
        nickname_btnback.setOnClickListener(this);
        nickname_btnsave.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.nickname_btn_back:
                activity.onBackPressed();
                Intent intent = new Intent("android.intent.action.CART_BROADCAST");
                intent.putExtra("data","refresh");
                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);
                break;
            case R.id.nickname_btn_save:
                String nickname = et_nav_nickname.getText().toString();
                String address_nickname="http://www.tytechkj.com/App/Permission/ChangeNickName";
                queryNickname(address_nickname,nickname);
                break;
        }
    }

    private void queryNickname(String address, final String nicknamestring) {
        DialogMethod.MyProgressDialog(getContext(),"正在上传中...",true);
        RequestBody requestBody = new FormBody.Builder()
                .add("ID", GetInstance().User.getGUID())
                .add("nickname",nicknamestring)
                .build();
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Gson gson = new Gson();
                user = gson.fromJson(responseText, UserInfo.class);
                String s = new Gson().toJson(user.Data);
                if (!user.IsError) {
                    UserViewModel lll = new Gson().fromJson(s, UserViewModel.class);
                    BaseViewModel.GetInstance().User.NickName = lll.NickName;

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            et_nav_nickname.setText(nicknamestring);
                            DialogMethod.MyProgressDialog(getContext(), "", false);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogMethod.MyProgressDialog(getContext(),"",false);
                        Toast.makeText(getContext(),"上传昵称失败",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (!et_nav_nickname.getText().toString().equals(BaseViewModel.GetInstance().User.getNickName())){
            nickname_btnsave.setTextColor(Color.WHITE);
            nickname_btnsave.setEnabled(true);
        }else if (et_nav_nickname.getText().toString().equals(BaseViewModel.GetInstance().User.getNickName())){
            nickname_btnsave.setTextColor(Color.GRAY);
            nickname_btnsave.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
