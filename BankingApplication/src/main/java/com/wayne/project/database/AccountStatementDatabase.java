package com.wayne.project.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class AccountStatementDatabase extends ConnectToDatabase implements IAccountStatementDatabase {

    private Connection connectionForAccountStatement;
    private Statement statementForAccountStatement;
    private ResultSet resultSetForAccountStatement;
    private String transactionDate;
    private String transactionStatus;
    private double amount;
    private String[] transaction;
    private List<String[]> list = new ArrayList<>();

    @Override
    public List<String[]> getCurrentMonthStatement(String customerId, int currentMonth, int currentYear) throws SQLException {

        try{
            connectionForAccountStatement = this.createDatabaseConnection();
            statementForAccountStatement = connectionForAccountStatement.createStatement();
            String getCurrentMonthStatementQuery = "Select * FROM transactions WHERE customer_id ='"+customerId+"';";
            resultSetForAccountStatement = statementForAccountStatement.executeQuery(getCurrentMonthStatementQuery);
            String[] date;
            while(resultSetForAccountStatement.next()) {
                gettingTransactions();
                date = transactionDate.split("-");

                if(Integer.parseInt(date[1]) == currentMonth+1) {
                    list.add(transaction);
                }
            }
            return list;
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            statementForAccountStatement.close();
            resultSetForAccountStatement.close();
            this.closeDatabaseConnection();
        }
        return null;
    }

    @Override
    public List<String[]> getQuarterlyStatement(String customerId, int currentYear) throws SQLException {

        try{
            connectionForAccountStatement = createDatabaseConnection();
            statementForAccountStatement = connectionForAccountStatement.createStatement();
            String getQuarterlyStatementQuery = "Select * FROM transactions WHERE customer_id ="+customerId+";";
            resultSetForAccountStatement = statementForAccountStatement.executeQuery(getQuarterlyStatementQuery);
            String[] date;
            while(resultSetForAccountStatement.next()) {
                gettingTransactions();
                date = transactionDate.split("-");

                if(Integer.parseInt(date[0]) == currentYear) {
                    list.add(transaction);
                }
            }
            return list;
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            statementForAccountStatement.close();
            resultSetForAccountStatement.close();
            this.closeDatabaseConnection();
        }
        return null;
    }

    @Override
    public List<String[]> getYearlyStatement(String customerId, int currentYear) throws SQLException {
        try{
            connectionForAccountStatement = createDatabaseConnection();
            statementForAccountStatement = connectionForAccountStatement.createStatement();
            String getYearlyStatementQuery = "Select * FROM transactions WHERE customer_id ="+customerId+";";
            resultSetForAccountStatement = statementForAccountStatement.executeQuery(getYearlyStatementQuery);
            String[] date;
            while(resultSetForAccountStatement.next()) {
                gettingTransactions();
                date = transactionDate.split("-");
                if(Integer.parseInt(date[0]) == currentYear) {
                    list.add(transaction);
                }
            }
            return list;
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            statementForAccountStatement.close();
            resultSetForAccountStatement.close();
            this.closeDatabaseConnection();
        }

        return null;
    }

    @Override
    public List<String[]> getCustomStatement(String customerId, String startDate, String finalDate) throws SQLException {
        try{
            connectionForAccountStatement = createDatabaseConnection();
            statementForAccountStatement = connectionForAccountStatement.createStatement();
            String getCurrentMonthStatementQuery = "Select * FROM transactions WHERE customer_id ="+customerId+
                                                   "AND transaction_date BETWEEN "+startDate+" AND "+finalDate+";";
            resultSetForAccountStatement = statementForAccountStatement.executeQuery(getCurrentMonthStatementQuery);

            while(resultSetForAccountStatement.next()) {
                gettingTransactions();
                list.add(transaction);
            }
            return list;
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            statementForAccountStatement.close();
            resultSetForAccountStatement.close();
            this.closeDatabaseConnection();
        }

        return null;
    }

    private void gettingTransactions() throws SQLException {
        transactionDate = resultSetForAccountStatement.getString("transaction_time");
        amount = resultSetForAccountStatement.getDouble("amount");
        transactionStatus = resultSetForAccountStatement.getString("status");
        transaction = (transactionDate+
                      ","+amount+
                      ","+transactionStatus).split(",");
    }
}
