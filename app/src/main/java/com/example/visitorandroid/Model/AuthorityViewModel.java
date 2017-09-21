package com.example.visitorandroid.Model;

public class AuthorityViewModel {

    public AuthorityViewModel() {
    }

    public AuthorityViewModel(String GUID,String nickName,int PID) {
        this.GUID = GUID;
        this.NickName = nickName;
        this.PID = PID;
    }

    public String GUID;

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }

    public String getGUID() {
        return GUID;
    }

    public String HeadPicUrl;

    public void setHeadPicUrl(String headPicUrl) {
        HeadPicUrl = headPicUrl;
    }

    public String getHeadPicUrl() {
        return HeadPicUrl;
    }

    public String UserName;

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserName() {
        return UserName;
    }

    public String NickName;

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public String getNickName() {
        return NickName;
    }

    public int PID;

    public void setPID(int PID) {
        this.PID = PID;
    }

    public int getPID() {
        return PID;
    }

    public int CompanyID;

    public void setCompanyID(int companyID) {
        CompanyID = companyID;
    }

    public int getCompanyID() {
        return CompanyID;
    }

    public String UID;

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getUID() {
        return UID;
    }
}
