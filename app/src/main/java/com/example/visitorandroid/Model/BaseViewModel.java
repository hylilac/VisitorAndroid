package com.example.visitorandroid.Model;

/**
 * Created by hy on 2017/9/12.
 */

public class BaseViewModel {

    public BaseViewModel(){}
    public static BaseViewModel uniqueStance;


    public static BaseViewModel GetInstance()
    {
        if(uniqueStance ==null)
            uniqueStance=new BaseViewModel();
        return uniqueStance;
    }



    public  UserViewModel User;

    public void setUser(UserViewModel USER) {
        this.User = USER;
    }

    public UserViewModel getUser() {
        return User;
    }
}
