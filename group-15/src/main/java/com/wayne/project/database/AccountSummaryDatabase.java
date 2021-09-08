package com.wayne.project.database;

import java.sql.*;

public class AccountSummaryDatabase extends ConnectToDatabase implements IAccountSummaryDatabase {

    private Connection connectionForAccountSummary;
    private Statement statementForAccountSummary;
    private ResultSet resultSetForAccountSummary;
    private String customerName;
    private double accountBalance;
    private String accountNumber;
    private String accountType;

    @Override
    public String getAccountName(String customerId) throws SQLException {

        try {
            connectionForAccountSummary = createDatabaseConnection();
            statementForAccountSummary = connectionForAccountSummary.createStatement();
            String getNameQuery = "Select customer_name from customer where customer_id ='"+customerId+"';";
            resultSetForAccountSummary = statementForAccountSummary.executeQuery(getNameQuery);
            while(resultSetForAccountSummary.next()) {
                 customerName = resultSetForAccountSummary.getString("customer_name");
            }
            return customerName;
        }

        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            statementForAccountSummary.close();
            resultSetForAccountSummary.close();
            this.closeDatabaseConnection();
        }

        return null;
    }

    @Override
    public double getAccountBalance(String customerId) throws SQLException {

        try {
            connectionForAccountSummary = createDatabaseConnection();
            statementForAccountSummary = connectionForAccountSummary.createStatement();
            String getAccountBalanceQuery = "Select account_balance from account_information where customer_id ='"+customerId+"';";
            resultSetForAccountSummary = statementForAccountSummary.executeQuery(getAccountBalanceQuery);
            while(resultSetForAccountSummary.next()) {
                    accountBalance = resultSetForAccountSummary.getDouble("account_balance");
                }
            return (float)accountBalance;
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            statementForAccountSummary.close();
            resultSetForAccountSummary.close();
            this.closeDatabaseConnection();
        }
        return 0;
    }

    @Override
    public String getAccountNumber(String customerId) throws SQLException {
        try {
            connectionForAccountSummary = createDatabaseConnection();
            statementForAccountSummary = connectionForAccountSummary.createStatement();
            String getAccountNumberQuery = "Select account_number from account_information where customer_id ='"+customerId+"';";
            resultSetForAccountSummary = statementForAccountSummary.executeQuery(getAccountNumberQuery);
            while(resultSetForAccountSummary.next()) {
                accountNumber = resultSetForAccountSummary.getString("Account_number");
            }
            return accountNumber;
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            statementForAccountSummary.close();
            resultSetForAccountSummary.close();
            this.closeDatabaseConnection();
        }
        return null;
    }

    @Override
    public String getAccountType(String customerId) throws SQLException {
        try {
            connectionForAccountSummary = this.createDatabaseConnection();
            statementForAccountSummary = connectionForAccountSummary.createStatement();
            String getAccountTypeQuery = "Select account_type from account_information where customer_id ='"+customerId+"';";
            resultSetForAccountSummary = statementForAccountSummary.executeQuery(getAccountTypeQuery);
            while(resultSetForAccountSummary.next()) {
                accountType = resultSetForAccountSummary.getString("account_type");
            }
            return accountNumber;
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            statementForAccountSummary.close();
            resultSetForAccountSummary.close();
            this.closeDatabaseConnection();
        }
        return null;
    }

    @Override
    public String getEmail(String customerId) throws SQLException {
        try {
            connectionForAccountSummary = createDatabaseConnection();
            statementForAccountSummary = connectionForAccountSummary.createStatement();
            String getEmailQuery = "Select customer_email from customer where customer_id ="+customerId+";";
            resultSetForAccountSummary = statementForAccountSummary.executeQuery(getEmailQuery);
            while(resultSetForAccountSummary.next()) {
                accountNumber = resultSetForAccountSummary.getString("customer_email");
            }
            return accountNumber;
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            statementForAccountSummary.close();
            resultSetForAccountSummary.close();
            this.closeDatabaseConnection();
        }
        return null;
    }

    @Override
    public boolean updateAccountBalance(String customerId, String accountType, float amount) throws SQLException {
        try{
            connectionForAccountSummary = createDatabaseConnection();
            statementForAccountSummary = connectionForAccountSummary.createStatement();
            String updateAccountBalanceQuery = "Update account_information SET account_balance ="+ amount +" WHERE customer_id="
                                                +customerId+"AND account_type ="+accountType+";";
            boolean updateBalance= statementForAccountSummary.execute(updateAccountBalanceQuery);
            return updateBalance;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            statementForAccountSummary.close();
            resultSetForAccountSummary.close();
            this.closeDatabaseConnection();
        }
        return false;
    }

    @Override
    public boolean deductAmountFromAccount(String customerId, double currentBalance, double deductAmount) throws SQLException {

        try {
            connectionForAccountSummary = createDatabaseConnection();
            statementForAccountSummary = connectionForAccountSummary.createStatement();
            String sqlQueryToDeductAmount = "UPDATE account_information SET account_balance = "+ (currentBalance - deductAmount) +" WHERE account_number = '"+accountNumber+"';";
            int isTheAmountDeducted = statementForAccountSummary.executeUpdate(sqlQueryToDeductAmount);
            if(isTheAmountDeducted > 0) {
                return true;
            }
        }catch(Exception e) {
            e.getMessage();
        }
        finally {
            statementForAccountSummary.close();
            resultSetForAccountSummary.close();
            this.closeDatabaseConnection();
        }
        return false;
    }
    @Override
    public boolean addAmountToAccount(String accountNumber, double currentBalance, float addAmount) throws SQLException {
        try {
            connectionForAccountSummary = createDatabaseConnection();
            statementForAccountSummary = connectionForAccountSummary.createStatement();
            String sqlQueryToAddAmount = "UPDATE account_information SET account_balance = "+ (currentBalance + addAmount) +" WHERE account_number = '"+accountNumber+"';";
            int isTheAmountAdded = statementForAccountSummary.executeUpdate(sqlQueryToAddAmount);
            if(isTheAmountAdded > 0) {
                return true;
            }
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            statementForAccountSummary.close();
            resultSetForAccountSummary.close();
            this.closeDatabaseConnection();
        }
        return false;
    }
}
