package com.wayne.project.database;

import java.sql.SQLException;
import java.util.List;

public interface ILoansDatabase {

    public boolean saveLoansApplication(String customerId,String customerName,String email,double loanSanctioned,int timePeriod,String loanType,String status) throws SQLException;
    public String getLoanSanctionedAmountAndTimePeriod(String customerId) throws SQLException;
    public List<String> getExistingLoans(String customerId) throws SQLException;
    public List<String> getTransactions(String customerId) throws SQLException;
    public boolean updateLoansDetails(String customerId, double loanAvailable,int timePeriod,double outstandingBalance) throws SQLException;
}
