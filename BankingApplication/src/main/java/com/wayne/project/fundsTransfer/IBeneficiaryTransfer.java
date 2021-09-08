package com.wayne.project.fundsTransfer;

import java.sql.SQLException;

public interface IBeneficiaryTransfer  {

    public boolean confirmTransaction(String BeneficiaryName, String BankName);
    public boolean checkAvailableBalance(String customerId,double Amount);
    public boolean saveTransaction(String customerId, String BeneficiaryName, String BeneficiaryAccountNumber,double Amount, String paymentFor,String status, String dateTime) throws SQLException;
    public boolean performTransaction(double Amount, String paymentFor, String confirmation,int transferLimit) throws SQLException;
    public boolean performTransactionForOtherBank(String customerId,double Amount, String BankName, String paymentFor, String confirmation, String transactionType,int transferLimit) throws SQLException;
}
