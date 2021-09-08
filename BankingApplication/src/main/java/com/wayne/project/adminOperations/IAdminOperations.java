package com.wayne.project.adminOperations;

import java.sql.SQLException;

public interface IAdminOperations {

    public void createCustomer(String customerUserName, String customerName, String customerPassword, String email, String contact, String address,String ssn) throws SQLException;
    public boolean closeAccount(String customerId) throws SQLException;
    public boolean updateCustomerQuery(String customerId) throws SQLException;
    public boolean approveLoanRequest(String customerId, String customerName, String email, double amount, double loanEstimate, int timePeriod, double annualIncome) throws SQLException;
}
