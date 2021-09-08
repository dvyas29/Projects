package com.wayne.project.login;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public interface IAdminLogin {

    public boolean adminLogin(String username, String password, String forgotPassword) throws SQLException, FileNotFoundException;
}
