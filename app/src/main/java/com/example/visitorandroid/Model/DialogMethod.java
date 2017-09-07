package com.example.visitorandroid.Model;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

/**
 * Created by hy on 2017/9/6.
 */

public class DialogMethod {

    private static ProgressDialog progressDialog;

    public static void MyDialog(Context context, String stringText){
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

    public static void MyProgressDialog(Context context,String stringText,Boolean flag){
        if (flag){
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(stringText);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }else{
            progressDialog.hide();
        }
    }
}
