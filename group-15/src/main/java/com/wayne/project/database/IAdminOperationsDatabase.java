package com.wayne.project.database;

import java.sql.SQLException;

public interface IAdminOperationsDatabase {

    public boolean persistCustomer(String customerUserName,String customerName,String customerPassword,String email, String contact, String address,String ssn) throws SQLException;
    public boolean deleteAccount(String customerId) throws SQLException;
    public boolean updateCustomerQueryStatus(String customerId) throws SQLException;
    public String fetchEmail(String customerId) throws SQLException;
}
