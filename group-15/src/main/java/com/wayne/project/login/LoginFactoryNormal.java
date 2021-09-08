package com.wayne.project.login;

public class LoginFactoryNormal extends LoginFactory{

    public ICustomerLogin createCustomerLoginObject() {

        return new CustomerLogin();
    }

    public IAdminLogin createAdminLoginObject() {

        return new AdminLogin();
    }
}

