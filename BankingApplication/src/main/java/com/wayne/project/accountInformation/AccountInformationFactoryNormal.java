package com.wayne.project.accountInformation;

import java.sql.SQLException;

public class AccountInformationFactoryNormal extends  AccountInformationFactory{

    @Override
    public IAccountClosure createAccountClosureObject(String customerId) throws SQLException {
        return new AccountClosure(customerId);
    }

    @Override
    public IAccountStatement createAccountStatementObject(String customerId) throws SQLException {
        return new AccountStatement(customerId);
    }

    @Override
    public IAccountSummary createAccountSummaryObject(String customerId) throws SQLException {
        return new AccountSummary(customerId);
    }

    @Override
    public ICustomerDetails createCustomerDetailsObject(String customerId) throws SQLException {
        return new CustomerDetails(customerId);
    }

    @Override
    public IRequestChequeBook createChequeBookObject(String customerId) throws SQLException {
        return new RequestChequeBook(customerId);
    }
}
