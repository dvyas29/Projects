package com.wayne.project.accountInformation;

import com.wayne.project.database.DatabaseFactory;
import com.wayne.project.database.DatabaseFactoryNormal;
import com.wayne.project.database.IAccountSummaryDatabase;

import java.sql.SQLException;

public class AccountSummary implements IAccountSummary {

    private IAccountSummaryDatabase accountSummaryDatabase;
    private DatabaseFactory databaseFactory;
    private String customerId;

    public AccountSummary(String customerId) {
        this.customerId = customerId;
        databaseFactory  = new DatabaseFactoryNormal();
        accountSummaryDatabase = databaseFactory.createAccountSummaryDatabase();
    }

    @Override
    public String getAccountSummary() throws SQLException {

        double accountBalance;
        String accountName;
        String accountNumber;
        String email;
        accountBalance = accountSummaryDatabase.getAccountBalance(customerId);
        accountName = accountSummaryDatabase.getAccountName(customerId);
        accountNumber =accountSummaryDatabase.getAccountNumber(customerId);
        email = accountSummaryDatabase.getEmail(customerId);
        String account ="Customer Name :- "+ accountName +
                        "\nAccount Balance :- "+accountBalance+
                        "\nAccountNumber :- "+accountNumber+
                        "\nEmail :- "+email;
        return account;
    }

    @Override
    public boolean updateAccountBalance(String accountType, float amount) throws SQLException {

       boolean isAccountBalanceUpdated = accountSummaryDatabase.updateAccountBalance(customerId, accountType, amount);
       return isAccountBalanceUpdated;
    }

}
