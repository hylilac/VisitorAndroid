package com.example.visitorandroid.Model;

import android.graphics.Bitmap;

/**
 * Created by hy on 2017/9/11.
 */

public class UserViewModel {

    // UserInfo user =new UserInfo()

    public UserViewModel()
    {

    }



    public String GUID ;

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public String getGUID() {
        return GUID;
    }

    public String UserName ;

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserName() {
        return UserName;
    }

    public String Pwd ;

    public void setPwd(String pwd) {
        Pwd = pwd;
    }

    public String getPwd() {
        return Pwd;
    }

    public String NickName ;

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getNickName() {
        return NickName;
    }

    public String Sex ;

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getSex() {
        return Sex;
    }

    public String IdentifyCard;

    public String getIdentifyCard() {
        return IdentifyCard;
    }

    public void setIdentifyCard(String identifyCard) {
        IdentifyCard = identifyCard;
    }

    public String Mobile ;

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getMobile() {
        return Mobile;
    }

    public String HeadPicUrl ;

    public void setHeadPicUrl(String headPicUrl) {
        HeadPicUrl = headPicUrl;
    }

    public String getHeadPicUrl() {
        return HeadPicUrl;
    }

    public String Address;

    public void setAddress(String address) {
        Address = address;
    }

    public String getAddress() {
        return Address;
    }

    public String InsertDateTime ;

    public void setInsertDateTime(String insertDateTime) {
        InsertDateTime = insertDateTime;
    }

    public String getInsertDateTime() {
        return InsertDateTime;
    }

    public String UpdateDateTime ;

    public void setUpdateDateTime(String updateDateTime) {
        UpdateDateTime = updateDateTime;
    }

    public String getUpdateDateTime() {
        return UpdateDateTime;
    }

    public Bitmap HeaderPic;

    public void setHeaderPic(Bitmap headerPic) {
        HeaderPic = headerPic;
    }

    public Bitmap getHeaderPic() {
        return HeaderPic;
    }

    public int ID;

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public String C_Name;

    public void setC_Name(String c_Name) {
        C_Name = c_Name;
    }

    public String getC_Name() {
        return C_Name;
    }

    public int PID;

    public void setPID(int PID) {
        this.PID = PID;
    }

    public int getPID() {
        return PID;
    }

    public String InsertTime;

    public void setInsertTime(String insertTime) {
        InsertTime = insertTime;
    }

    public String getInsertTime() {
        return InsertTime;
    }
}
