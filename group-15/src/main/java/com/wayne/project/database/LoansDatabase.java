package com.wayne.project.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class LoansDatabase extends ConnectToDatabase implements ILoansDatabase {

    private Connection connectionForLoans;
    private Statement statementForLoans;
    private ResultSet resultSetForLoans;
    private int loanId;
    private String loanType;
    private int loanAmount;
    private int loanDuration;



    @Override
    public boolean saveLoansApplication(String customerId,String customerName,String email,double loanAvailable,int timePeriod,String loanType,String status) throws SQLException {

        connectionForLoans = this.createDatabaseConnection();
        statementForLoans = connectionForLoans.createStatement();
        String sqlQueryToSaveLoanApplication = "Insert into loans(customer_id,customer_name,customer_email,loan_available,time_period,loan_type,loan_status) " +
                "values ('"+customerId+"','"+customerName+"','"+email+"',"+loanAvailable+","+timePeriod+",'"+loanType+"','"+status+"');";
        try {
            boolean rowsAffected = statementForLoans.execute(sqlQueryToSaveLoanApplication);
                return rowsAffected;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            statementForLoans.close();
            this.closeDatabaseConnection();
        }
        return true;
    }

    @Override
    public String getLoanSanctionedAmountAndTimePeriod(String customerId) throws SQLException {

        double loanAmount = 0;
        int timePeriod = 0;
        String loanDetails = "";
        connectionForLoans = this.createDatabaseConnection();
        statementForLoans = connectionForLoans.createStatement();
        String sqlQueryToFetchLoanAmountAndTimePeriod = "select loan_available, time_period from loans where customer_id =  '"+customerId+"';";
        try {
            resultSetForLoans = statementForLoans.executeQuery(sqlQueryToFetchLoanAmountAndTimePeriod);
            while (resultSetForLoans.next()) {
                loanAmount = resultSetForLoans.getDouble("loan_available");
                timePeriod = resultSetForLoans.getInt("time_period");
            }
            loanDetails = loanDetails + loanAmount + " " + timePeriod;
            return loanDetails;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            resultSetForLoans.close();
            statementForLoans.close();
            this.closeDatabaseConnection();
        }
        return loanDetails;
    }

    @Override
    public List<String> getExistingLoans(String customerId) throws SQLException {

        List<String> list = new ArrayList<>();
        String loanData;

        try{
            connectionForLoans = this.createDatabaseConnection();
            statementForLoans = connectionForLoans.createStatement();
            String queryForLoansInfo = "Select * from loans where customer_id='"+customerId+"';";
            resultSetForLoans = statementForLoans.executeQuery(queryForLoansInfo);
            while(resultSetForLoans.next()){
                loanId = resultSetForLoans.getInt("loan_id");
                loanType = resultSetForLoans.getString("loan_type");
                loanAmount = resultSetForLoans.getInt("loan_available");
                loanDuration = resultSetForLoans.getInt("time_period");
                loanData = "Loan Id : "+loanId+","+
                           "Loan Type : "+loanType+","+
                           "Loan Amount : "+loanAmount+","+
                           "Loan Duration : "+loanDuration;

                list.add(loanData);
            }
            return  list;
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            statementForLoans.close();
            resultSetForLoans.close();
            this.closeDatabaseConnection();
        }
        return null;
    }

    @Override
    public List<String> getTransactions(String customerId) throws SQLException {

        List<String> list = new ArrayList<>();
        String transactions;
        try{
            connectionForLoans = this.createDatabaseConnection();
            statementForLoans = connectionForLoans.createStatement();
            String getTransactionsQuery = "Select * from transactions WHERE customer_id = '"+customerId+"';";
            resultSetForLoans = statementForLoans.executeQuery(getTransactionsQuery);

            while(resultSetForLoans.next()){
                String transactionId = resultSetForLoans.getString("transaction_id");
                String transactionDate = String.valueOf(resultSetForLoans.getDate("transaction_time"));
                double Amount = resultSetForLoans.getDouble("amount");
                String transactionStatus = resultSetForLoans.getString("status");
                transactions = "Transaction Id : "+transactionId+","+
                               "Transaction Date : "+transactionDate+","+
                               "Amount : "+Amount+","+
                               "Transaction Status : "+transactionStatus;

                list.add(transactions);
            }
            return list;
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            statementForLoans.close();
            resultSetForLoans.close();
            this.closeDatabaseConnection();
        }
        return null;
    }

    @Override
    public boolean updateLoansDetails(String customerId, double loanAvailable,int timeperiod,double outstandingBalance) throws SQLException {

        String sqlQueryToUpdateLoanDetails = "Update loans set loan_available = " +(loanAvailable-outstandingBalance)+", time_period = "+(timeperiod-1)+" where customer_id = '"+customerId+"';";
        connectionForLoans = this.createDatabaseConnection();
        statementForLoans = connectionForLoans.createStatement();
        try {
            int changedRows = statementForLoans.executeUpdate(sqlQueryToUpdateLoanDetails);
            if (changedRows > 0) {
                return true;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            statementForLoans.close();
            this.closeDatabaseConnection();
        }
        return true;
    }

}
