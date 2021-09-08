package com.wayne.project.accountInformation;

import java.sql.SQLException;

public abstract class AccountInformationFactory {
    public abstract IAccountClosure createAccountClosureObject(String customerId) throws SQLException;
    public abstract IAccountStatement createAccountStatementObject(String customerId) throws SQLException;
    public abstract IAccountSummary createAccountSummaryObject(String customerId) throws SQLException;
    public abstract ICustomerDetails createCustomerDetailsObject(String customerId) throws SQLException;
    public abstract IRequestChequeBook createChequeBookObject(String customerId) throws SQLException;
}
