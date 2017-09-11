package com.example.visitorandroid.Model;

/**
 * Created by hy on 2017/9/11.
 */

public class MobileInfo {
    public Boolean IsError;

    public String Message;

    public Boolean Data;

    public Boolean NotLigerUIFriendlySerialize;

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

    public Boolean getData(){
        return Data;
    }

    public void setData(Boolean Data){
        this.Data = Data;
    }

    public Boolean getNotLigerUIFriendlySerialize(){
        return NotLigerUIFriendlySerialize;
    }

    public void setNotLigerUIFriendlySerialize(Boolean NotLigerUIFriendlySerialize){
        this.NotLigerUIFriendlySerialize = NotLigerUIFriendlySerialize;
    }
}
