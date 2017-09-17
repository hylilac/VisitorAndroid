package com.example.visitorandroid.Model;

/**
 * Created by lilac on 2017/9/15.
 */

public class Data {

    public Data() {
    }

    public Data(String c_Name,int employeeCount,String nickName,String departmentName) {
        this.C_Name = c_Name;
        this.EmployeeCount = employeeCount;
        this.NickName = nickName;
        this.DepartmentName = departmentName;
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

    public int EmployeeCount;

    public void setEmployeeCount(int employeeCount) {
        EmployeeCount = employeeCount;
    }

    public int getEmployeeCount() {
        return EmployeeCount;
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

