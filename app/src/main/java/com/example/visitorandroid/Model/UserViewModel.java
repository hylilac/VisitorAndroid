package com.example.visitorandroid.Model;

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
}
