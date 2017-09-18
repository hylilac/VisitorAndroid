package com.example.visitorandroid.Model;

/**
 * Created by lilac on 2017/9/18.
 */

public class EmployeeViewModel {

    public EmployeeViewModel(String nickName,String departmentName)
    {
        this.NickName = nickName;
        this.DepartmentName = departmentName;
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

    public String DepartmentName;

    public void setDepartmentName(String departmentName) {
        DepartmentName = departmentName;
    }

    public String getDepartmentName() {
        return DepartmentName;
    }
}
