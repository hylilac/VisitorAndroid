package com.example.visitorandroid.Model;

public class CheckUserViewModel {

    public CheckUserViewModel() {
    }

    public CheckUserViewModel(String v_name, String bv_name, String VisitorTime) {
        this.v_name = v_name;
        this.bv_name = bv_name;
        this.VisitorTime = VisitorTime;
    }

    public int ID;

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public String v_name;

    public void setV_name(String v_name) {
        this.v_name = v_name;
    }

    public String getV_name() {
        return v_name;
    }

    public String bv_name;

    public void setBv_name(String bv_name) {
        this.bv_name = bv_name;
    }

    public String getBv_name() {
        return bv_name;
    }

    public String c_card;

    public void setC_card(String c_card) {
        this.c_card = c_card;
    }

    public String getC_card() {
        return c_card;
    }

    public String Area;

    public void setArea(String area) {
        Area = area;
    }

    public String getArea() {
        return Area;
    }

    public String v_content;

    public void setV_content(String v_content) {
        this.v_content = v_content;
    }

    public String getV_content() {
        return v_content;
    }

    public String Mobile;

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getMobile() {
        return Mobile;
    }

    public String VisitorTime;

    public void setVisitorTime(String visitorTime) {
        VisitorTime = visitorTime;
    }

    public String getVisitorTime() {
        return VisitorTime;
    }

    public String visitor_ID;

    public void setVisitor_ID(String visitor_ID) {
        this.visitor_ID = visitor_ID;
    }

    public String getVisitor_ID() {
        return visitor_ID;
    }

    public String order_state;

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public String getOrder_state() {
        return order_state;
    }

    public Boolean IsPublic;

    public void setPublic(Boolean aPublic) {
        IsPublic = aPublic;
    }

    public Boolean getPublic() {
        return IsPublic;
    }

    public String InsertTime;

    public void setInsertTime(String insertTime) {
        InsertTime = insertTime;
    }

    public String getInsertTime() {
        return InsertTime;
    }
}
