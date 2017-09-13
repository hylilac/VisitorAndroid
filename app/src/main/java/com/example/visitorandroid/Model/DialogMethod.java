package com.example.visitorandroid.Model;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

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

    /*
 *bitmap转base64
 */
    public static String bitmapToBase64(Bitmap bitmap){
        String result="";
        ByteArrayOutputStream bos=null;
        try {
            if(null!=bitmap){
                bos=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);//将bitmap放入字节数组流中

                bos.flush();//将bos流缓存在内存中的数据全部输出，清空缓存
                bos.close();

                byte []bitmapByte=bos.toByteArray();
                result= Base64.encodeToString(bitmapByte, Base64.DEFAULT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(null!=null){
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    /*
     *bitmap转base64
     */
    public static Bitmap base64ToBitmap(String base64String){
        byte[] bytes = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap bitmap= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }
}
