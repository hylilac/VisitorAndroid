package com.example.visitorandroid.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hy on 2017/9/11.
 */

public class ResultViewModel {

    public Boolean IsError;

    public String Message;

    public List<Data> Data;

    public Boolean getIsError(){
        return IsError;
    }

    public void setIsError(Boolean IsError){
        this.IsError = IsError;
    }

    public String getMessage(){
        return Message;
    }

    public void setMessage(String Message){
        this.Message = Message;
    }

    public List getData(){
        return Data;
    }

    public void setData(List Data){
        this.Data = Data;
    }
}
