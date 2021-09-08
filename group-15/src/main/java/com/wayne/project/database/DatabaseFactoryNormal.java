package com.wayne.project.database;

public class DatabaseFactoryNormal extends DatabaseFactory {

    public IAccountSummaryDatabase createAccountSummaryDatabase() {

        return new AccountSummaryDatabase();
    }

    public IAccountStatementDatabase createAccountStatementDatabase() {

        return new AccountStatementDatabase();
    }

    public ICustomerDetailsDatabase createCustomerDetailsDatabase() {

        return new CustomerDetailsDatabase();
    }

    public IRequestChequeBookDatabase createRequestChequeBookDatabase() {

        return new RequestChequeBookDatabase();
    }

    public IOffersDatabase createOffersDatabase() {

        return new OffersDatabase();
    }

    public ILoginDatabase createLoginDatabaseObject() {

        return new LoginDatabase();
    }

    public ICardsDatabase createCardsDatabaseObject() {

        return new CardsDatabase();
    }

    public IAutoPayDatabase createAutoPayDatabaseObject() {

        return new AutoPayDatabase();
    }

    public IPaymentReminderDatabase createPaymentReminderDatabaseObject() {

        return new PaymentReminderDatabase();
    }

    public ILoansDatabase createLoansDatabaseObject() {

        return new LoansDatabase();
    }

    public IBeneficiaryDatabase createBeneficiaryDatabaseObject() {

        return new BeneficiaryDatabase();
    }

    public ITransferFundsDatabase createTransferFundsDatabaseObject() {

        return new TransferFundsDatabase();
    }

    public IAdminOperationsDatabase createAdminOperationsDatabaseObject() {

        return new AdminOperationsDatabase();
    }

    public IContactInformationDatabase createContactInformationDatabaseObject() {

        return new ContactInformationDatabase();
    }
}
