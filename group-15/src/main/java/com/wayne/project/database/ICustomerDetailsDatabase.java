package com.wayne.project.database;

import java.sql.SQLException;

public interface ICustomerDetailsDatabase {

    public boolean updateAddress(String customerId, String address) throws SQLException;
    public boolean updateSSN(String customerId, double SSN) throws SQLException;
    public boolean updateEmail(String customerId, String email) throws SQLException;


}
