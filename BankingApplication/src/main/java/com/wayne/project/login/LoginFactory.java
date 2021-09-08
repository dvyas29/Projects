package com.wayne.project.login;

public abstract class LoginFactory {

    public abstract ICustomerLogin createCustomerLoginObject();
    public abstract IAdminLogin createAdminLoginObject();
}
