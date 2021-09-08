package com.wayne.project.database;

import java.sql.SQLException;

public interface ITransferFundsDatabase {

    public boolean persistTransaction(String customerId, String BeneficiaryName, String BeneficiaryAccountNumber,double Amount, String paymentFor,String status, String dateTime) throws SQLException;
    public boolean storeTransaction(String customerId, double amount, String paymentFor, String paymentType,String dateTime,double balance) throws SQLException;
}
