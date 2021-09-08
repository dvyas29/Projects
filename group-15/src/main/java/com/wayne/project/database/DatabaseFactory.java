package com.wayne.project.database;

public abstract class DatabaseFactory {

    public abstract IAccountSummaryDatabase createAccountSummaryDatabase();

    public abstract IAccountStatementDatabase createAccountStatementDatabase();

    public abstract ICustomerDetailsDatabase createCustomerDetailsDatabase();

    public abstract IRequestChequeBookDatabase createRequestChequeBookDatabase();

    public abstract IOffersDatabase createOffersDatabase();

    public abstract ILoginDatabase createLoginDatabaseObject();

    public abstract ICardsDatabase createCardsDatabaseObject();

    public abstract IAutoPayDatabase createAutoPayDatabaseObject();

    public abstract IPaymentReminderDatabase createPaymentReminderDatabaseObject();

    public abstract IBeneficiaryDatabase createBeneficiaryDatabaseObject();

    public abstract ITransferFundsDatabase createTransferFundsDatabaseObject();

    public abstract IAdminOperationsDatabase createAdminOperationsDatabaseObject();

    public abstract IContactInformationDatabase createContactInformationDatabaseObject();

    public abstract ILoansDatabase createLoansDatabaseObject();
}
