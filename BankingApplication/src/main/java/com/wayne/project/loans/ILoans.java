package com.wayne.project.loans;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public interface ILoans {

    public boolean applyForLoan(String customerId, String customerName, double amount, int timePeriod, double annualIncome) throws SQLException;
    public boolean loanPayment(String customerId,double outstandingLoanAmountForThisMonth) throws SQLException;
    public double outstandingLoanAmount(String customerId) throws SQLException;
    public String showExistingLoans(String customerId) throws SQLException;
    public String getTransactionHistory(String customerId) throws SQLException;
    public boolean loanRepaymentSchedule(String customerId) throws SQLException, IOException;
    public String loanAmountDetails(String customerId) throws SQLException;
    public boolean applyForAutomotiveLoan(String customerId, String customerName,String email, double amount , int timePeriod, double annualIncome) throws SQLException;
}
