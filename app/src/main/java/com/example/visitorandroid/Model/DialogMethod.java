package com.example.visitorandroid.Model;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.visitorandroid.RegActivity;

/**
 * Created by hy on 2017/9/6.
 */

public class DialogMethod {
    public static void MyDialog(Context context,String stringText){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(stringText);
        builder.setCancelable(false);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog,int which){

            }
        });
        builder.show();
    }
}
