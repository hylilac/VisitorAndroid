package com.example.visitorandroid.Model;

public class OrderListViewModel {

    private int imageId;
    private String ordermessage;
    private String ordertime;
    private String orderresult;

    public OrderListViewModel() {
    }

    public OrderListViewModel(String ordermessage, String ordertime, String orderresult, int imageId) {
        this.imageId = imageId;
        this.ordermessage = ordermessage;
        this.ordertime = ordertime;
        this.orderresult  =orderresult;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getOrdermessage() {
        return ordermessage;
    }

    public void setOrdermessage(String ordermessage) {
        this.ordermessage = ordermessage;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public String getOrderresult() {
        return orderresult;
    }

    public void setOrderresult(String orderresult) {
        this.orderresult = orderresult;
    }
}
