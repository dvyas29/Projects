package com.wayne.project.login;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public interface ICustomerLogin {

    boolean customerLogin(String userName, String password, String forgotPassword) throws SQLException, FileNotFoundException;
}
