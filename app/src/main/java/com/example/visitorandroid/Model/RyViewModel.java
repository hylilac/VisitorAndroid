package com.example.visitorandroid.Model;

public class RyViewModel {
    private String rynickname;
    private String rybm;

    public RyViewModel() {
    }

    public RyViewModel(String rynickname,String rybm) {
        this.rynickname = rynickname;
        this.rybm = rybm;
    }

    public String getRynickname() {
        return rynickname;
    }

    public void setRynickname(String rynickname) {
        this.rynickname = rynickname;
    }

    public String getRybm() {
        return rybm;
    }

    public void setRybm(String rybm) {
        this.rybm = rybm;
    }
}
