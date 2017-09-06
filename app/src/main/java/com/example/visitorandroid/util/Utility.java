package com.example.visitorandroid.util;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {

    public static String handleMobileResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                String yamCode = jsonObject.getString("YZMCode");
                if (!yamCode.isEmpty()) {
                    return yamCode;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return "error";
    }

    public static boolean handleRegResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Boolean isError = jsonObject.getBoolean("IsError");
                if (!isError) {
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleLoginResponse(String response){
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONObject jsonObject = new JSONObject(response);
                Boolean isError = jsonObject.getBoolean("IsError");
                if (!isError) {
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}

