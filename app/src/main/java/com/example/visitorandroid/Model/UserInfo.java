package com.example.visitorandroid.Model;

public class UserInfo {

    public Boolean IsError;

    public String Message;

    public Object Data;

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

    public Object getData(){
        return Data;
    }

    public void setData(Object Data){
        this.Data = Data;
    }
}
