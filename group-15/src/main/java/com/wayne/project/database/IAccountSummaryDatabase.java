package com.wayne.project.database;

import java.sql.SQLException;

public interface IAccountSummaryDatabase {
    public String getAccountName(String customerId) throws SQLException;
    public double getAccountBalance(String customerId) throws SQLException;
    public String getAccountNumber(String customerId) throws SQLException;
    public String getAccountType(String customerId) throws SQLException;
    public String getEmail(String customerId) throws SQLException;
    public boolean updateAccountBalance(String id, String customerId, float amount) throws SQLException;
    public boolean addAmountToAccount(String accountNumber, double currentBalance, float addAmount) throws SQLException;
    public boolean deductAmountFromAccount(String customerId, double currentBalance, double deductAmount) throws SQLException;
}
