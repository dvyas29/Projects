package com.wayne.project.database;

import java.sql.SQLException;

public interface ILoginDatabase {

    public String verifyAdminCredentials(String adminId, String adminPassword) throws SQLException;
    public String verifyCustomerCredentials(String adminId, String adminPassword) throws SQLException;
    public boolean setAdminPassword(String adminId, String adminPassword) throws SQLException;
    public String fetchAdminEmail(String adminId) throws SQLException;
}
