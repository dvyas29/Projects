package com.wayne.project.database;

import java.sql.*;
import java.sql.SQLException;

public class TransferFundsDatabase extends ConnectToDatabase implements ITransferFundsDatabase
{

    private Connection connectionForTransferFunds;
    private Statement statementForTransferFunds;
    private ResultSet resultSetForTransferFunds;

    @Override
    public boolean persistTransaction(String customerId, String BeneficiaryName, String BeneficiaryAccountNumber,double Amount, String paymentFor,String status, String dateTime) throws SQLException {

        connectionForTransferFunds = this.createDatabaseConnection();
        statementForTransferFunds = connectionForTransferFunds.createStatement();
        String sqlQueryToSaveTransaction = "Insert into transactions (customer_id,beneficiary_name,beneficiary_account_number,amount,payment_for,status,transaction_time)" +
                "values ('"+customerId+"','"+BeneficiaryName+"','"+BeneficiaryAccountNumber+"',"+Amount+",'"+paymentFor+"','"+status+"','"+dateTime+"');";
        try {
            int updatedRows = statementForTransferFunds.executeUpdate(sqlQueryToSaveTransaction);
            if (updatedRows > 0) {
                return true;
            }
        }
        catch (SQLException e){
            System.out.println();
        }
        finally {
            statementForTransferFunds.close();
            this.closeDatabaseConnection();
        }
        return true;
    }

    public boolean storeTransaction(String customerId, double amount, String paymentFor, String paymentType,String dateTime,double balance) throws SQLException {

        connectionForTransferFunds = this.createDatabaseConnection();
        statementForTransferFunds = connectionForTransferFunds.createStatement();
        String sqlQueryToStoreGeneralTransaction = "Insert into general_transactions (customer_id,amount,payment_for,payment_type,date_time,balance) " +
                "values('"+customerId+"',"+amount+",'"+paymentFor+"','"+paymentType+"','"+dateTime+"',"+balance+");";
        try {
            int addedRecord = statementForTransferFunds.executeUpdate(sqlQueryToStoreGeneralTransaction);
            if (addedRecord > 0) {
                return true;
            }
        }
        catch (SQLException e) {
            System.out.println();
        }
        finally {
            statementForTransferFunds.close();
            this.closeDatabaseConnection();
        }
        return true;
    }
}
