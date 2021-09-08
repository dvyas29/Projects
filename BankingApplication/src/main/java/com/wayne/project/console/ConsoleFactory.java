package com.wayne.project.console;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

public abstract class ConsoleFactory {

    public abstract IConsoleForCards createConsoleForCardsObject(String customerId) throws SQLException;

    public abstract IConsoleForDebitCards createConsoleForDebitCardsObject(String customerId) throws SQLException;

    public abstract IConsoleForCreditCards createConsoleForCreditCardsObject(String customerId) throws SQLException;

    public abstract IConsoleForFundsTransfer createConsoleForFundsTransferObject(String customerId) throws SQLException;

    public abstract IConsoleRunnerCustomer createConsoleRunnerCustomer() throws SQLException;

    public abstract IConsoleRunnerAdmin createConsoleRunnerAdmin() throws SQLException;

    public abstract IConsoleForContactInformation createConsoleForContactInformationObject(String customerId) throws SQLException;

    public abstract IConsoleForLoans createConsoleForLoansObject(String customerId) throws SQLException, FileNotFoundException;

    public abstract IConsoleForAccountInformation createConsoleForAccountInformationObject(String customerId) throws SQLException;

    public abstract IConsoleForOffers createConsoleForOffers() throws SQLException;

    public abstract Scanner createScannerInstance();
}
