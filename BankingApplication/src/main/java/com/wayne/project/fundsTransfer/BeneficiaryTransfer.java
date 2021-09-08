package com.wayne.project.fundsTransfer;

import com.wayne.project.database.DatabaseFactory;
import com.wayne.project.database.DatabaseFactoryNormal;
import com.wayne.project.database.IAccountSummaryDatabase;
import com.wayne.project.database.ITransferFundsDatabase;
import com.wayne.project.utility.IDateTime;
import com.wayne.project.utility.UtilityFactory;
import com.wayne.project.utility.UtilityFactoryNormal;
import java.sql.SQLException;

public class BeneficiaryTransfer implements IBeneficiaryTransfer {

    private String customerId;
    double balanceAmount;
    double AccountBalance;
    private DatabaseFactory databaseFactory;
    private ITransferFundsDatabase transferFundsDatabase;
    private IAccountSummaryDatabase accountSummaryDatabase;
    private UtilityFactory utilityFactory;
    private IDateTime dateTime;

    public BeneficiaryTransfer(String customerId) throws SQLException {

        this.customerId = customerId;
        databaseFactory = new DatabaseFactoryNormal();
        utilityFactory = new UtilityFactoryNormal();
        transferFundsDatabase = databaseFactory.createTransferFundsDatabaseObject();
        accountSummaryDatabase = databaseFactory.createAccountSummaryDatabase();
        dateTime = utilityFactory.createDateTimeObject("EST");
        AccountBalance = accountSummaryDatabase.getAccountBalance(customerId);
    }

    @Override
    public boolean confirmTransaction(String BeneficiaryName, String BankName){

        if (BankName.equals("Wayne")){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean checkAvailableBalance(String customerId,double Amount) {

        System.out.println(AccountBalance);
        if (AccountBalance > Amount) {
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean performTransaction(double Amount, String paymentFor, String confirmation, int transferLimit) throws SQLException {

        if (confirmation.equals("Y") && transferLimit > Amount) {
            accountSummaryDatabase.deductAmountFromAccount(customerId,AccountBalance,Amount);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean saveTransaction(String customerId, String BeneficiaryName, String BeneficiaryAccountNumber,double Amount, String paymentFor,String status, String date) throws SQLException {

        String paymentType = "debit";
        transferFundsDatabase.persistTransaction(customerId, BeneficiaryName,BeneficiaryAccountNumber,Amount, paymentFor,status,date);
        balanceAmount = AccountBalance - Amount;
        transferFundsDatabase.storeTransaction(customerId,Amount,paymentFor,paymentType, dateTime.fetchDateAndTime(),balanceAmount);
        return true;
    }

    @Override
    public boolean performTransactionForOtherBank(String customerId,double Amount, String BankName, String paymentFor, String confirmation, String transactionType, int transferLimit) throws SQLException {

        if (BankName.equals("Wayne")) {
            return false;
        }
        else {
            if (transactionType.equals("NEFT") && Amount > 1000) {
                Amount = Amount + (0.01 * Amount);
            }
            else if (transactionType.equals("IMPS") && Amount > 1000) {
                Amount = Amount + (0.15 * Amount);
            }
            else if (transactionType.equals("RTGS") && Amount > 1000) {
                Amount = Amount + (0.02 * Amount);
            }
            accountSummaryDatabase.deductAmountFromAccount(customerId,AccountBalance,Amount);
        }
        return true;
    }

}
