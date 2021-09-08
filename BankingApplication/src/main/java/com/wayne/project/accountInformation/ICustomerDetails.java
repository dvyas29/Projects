package com.wayne.project.accountInformation;

import java.sql.SQLException;

public interface ICustomerDetails {

    public boolean updateAddress(String address) throws SQLException;
    public boolean updateSSN(double SSN) throws SQLException;
    public boolean updateEmail(String email) throws SQLException;
}
