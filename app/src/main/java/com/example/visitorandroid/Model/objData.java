package com.example.visitorandroid.Model;

public class objData {

    public objData(){}

    public static objData uniqueStance;


    public static objData GetInstance2()
    {
        if(uniqueStance ==null)
            uniqueStance=new objData();
        return uniqueStance;
    }

    public  Data data;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

}
