package com.example.visitorandroid.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hy on 2017/9/11.
 */

public class ResultViewModel {

    @SerializedName("IsError")
    public Boolean IsError;

    @SerializedName("Message")
    public String Message;

    @SerializedName("Data")
    public Data data;

    public class Data{

        @SerializedName("GUID")
        public String GUID;

        @SerializedName("UserName")
        public String UserName;

        @SerializedName("Pwd")
        public String Pwd;

        @SerializedName("NickName")
        public String NickName;

        @SerializedName("Sex")
        public String Sex;

        @SerializedName("IdentifyCard")
        public String IdentifyCard;

        @SerializedName("Mobile")
        public String Mobile;

        @SerializedName("HeadPicUrl")
        public String HeadPicUrl;

        @SerializedName("Address")
        public String Address;

        @SerializedName("InsertDateTime")
        public String InsertDateTime;

        @SerializedName("UpdateDateTime")
        public String UpdateDateTime;
    }

}
