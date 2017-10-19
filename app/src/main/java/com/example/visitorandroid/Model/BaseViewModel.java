package com.example.visitorandroid.Model;

import com.example.visitorandroid.bean.WeiXinInfo;
import com.example.visitorandroid.bean.WeiXinToken;

/**
 * 单例
 */

public class BaseViewModel {

    private BaseViewModel(){}
        private static BaseViewModel uniqueStance;


    public static BaseViewModel GetInstance()
    {
        if (uniqueStance == null) {
            synchronized (BaseViewModel.class) {
                if (uniqueStance == null) {
                    uniqueStance = new BaseViewModel();
                }
            }
        }
        return  uniqueStance;
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

    public EmployeeViewModel Employee;

    public void setEmployee(EmployeeViewModel employee) {
        Employee = employee;
    }

    public EmployeeViewModel getEmployee() {
        return Employee;
    }

    public OrderListViewModel OrderList;

    public void setOrderList(OrderListViewModel orderList) {
        OrderList = orderList;
    }

    public OrderListViewModel getOrderList() {
        return OrderList;
    }

    public CheckOrderViewModel CheckOrder;

    public void setCheckOrder(CheckOrderViewModel checkOrder) {
        CheckOrder = checkOrder;
    }

    public CheckOrderViewModel getCheckOrder() {
        return CheckOrder;
    }

    public CheckUserViewModel CheckUser;

    public void setCheckUser(CheckUserViewModel checkUser) {
        CheckUser = checkUser;
    }

    public CheckUserViewModel getCheckUser() {
        return CheckUser;
    }

    public AuthorityViewModel Authority;

    public void setAuthority(AuthorityViewModel authority) {
        Authority = authority;
    }

    public AuthorityViewModel getAuthority() {
        return Authority;
    }

    public WeiXinInfo weiXinInfo;

    public void setWeiXinInfo(WeiXinInfo weiXinInfo) {
        this.weiXinInfo = weiXinInfo;
    }

    public WeiXinInfo getWeiXinInfo() {
        return weiXinInfo;
    }

    public WeiXinToken weiXinToken;

    public void setWeiXinToken(WeiXinToken weiXinToken) {
        this.weiXinToken = weiXinToken;
    }

    public WeiXinToken getWeiXinToken() {
        return weiXinToken;
    }
}
