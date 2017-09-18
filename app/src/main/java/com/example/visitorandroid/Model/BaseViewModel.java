package com.example.visitorandroid.Model;

/**
 * 单例
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

    public UserViewModel User;

    public void setUser(UserViewModel USER) {
        this.User = USER;
    }

    public UserViewModel getUser() {
        return User;
    }

    public CompanyViewModel CompanyView;

    public void setCompanyView(CompanyViewModel companyView) {
        CompanyView = companyView;
    }

    public CompanyViewModel getCompanyView() {
        return CompanyView;
    }

    public DepartmentViewModel Department;

    public void setDepartment(DepartmentViewModel department) {
        Department = department;
    }

    public DepartmentViewModel getDepartment() {
        return Department;
    }
}
