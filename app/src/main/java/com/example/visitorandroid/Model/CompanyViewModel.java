package com.example.visitorandroid.Model;

/**
 * Created by lilac on 2017/9/18.
 */

public class CompanyViewModel {

    public CompanyViewModel()
    {

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
