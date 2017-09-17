package com.example.visitorandroid.Model;

import java.util.List;

/**
 * Created by hy on 2017/9/11.
 */

public class RyResultViewModel {

    public Boolean IsError;

    public String Message;

    public List<RyData> RyData;

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

    public List getRyData() {
        return RyData;
    }

    public void setRyData(List RyData) {
        RyData = RyData;
    }
}
