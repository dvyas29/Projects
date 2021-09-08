package com.wayne.project.console;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

public class ConsoleFactoryNormal extends ConsoleFactory {

    private IConsoleForCards consoleForCardsObject;
    private IConsoleForDebitCards consoleForDebitCardsObject;
    private IConsoleForCreditCards consoleForCreditCardsObject;
    private IConsoleRunnerCustomer consoleRunnerCustomer;
    private IConsoleForFundsTransfer consoleForFundsTransferObject;
    private IConsoleForContactInformation consoleForContactInformation;
    private IConsoleForLoans consoleForLoans;
    private IConsoleForAccountInformation consoleForAccountInformation;
    private IConsoleForOffers consoleForOffers;
    private Scanner userInput;
    private IConsoleRunnerAdmin createConsoleRunnerAdmin;

    public IConsoleForAccountInformation createConsoleForAccountInformationObject(String customerId) throws SQLException{

        if(consoleForAccountInformation == null){
            consoleForAccountInformation = new ConsoleForAccountInformation(customerId);
        }
        return consoleForAccountInformation;
    }

    @Override
    public IConsoleForOffers createConsoleForOffers() throws SQLException {
        if(consoleForOffers == null){
            consoleForOffers = new ConsoleForOffers();
        }
        return consoleForOffers;
    }

    public IConsoleForCards createConsoleForCardsObject(String customerId) throws SQLException {

        if(consoleForCardsObject == null) {
            consoleForCardsObject = new ConsoleForCards(customerId);
        }
        return consoleForCardsObject;
    }

    public IConsoleForDebitCards createConsoleForDebitCardsObject(String customerId) throws SQLException {

        if(consoleForDebitCardsObject == null) {
            consoleForDebitCardsObject = new ConsoleForDebitCards(customerId);
        }
        return consoleForDebitCardsObject;
    }

    public IConsoleForCreditCards createConsoleForCreditCardsObject(String customerId) throws SQLException {

        if(consoleForCreditCardsObject == null) {
            consoleForCreditCardsObject = new ConsoleForCreditCards(customerId);
        }
        return consoleForCreditCardsObject;
    }

    @Override
    public IConsoleForFundsTransfer createConsoleForFundsTransferObject(String customerId) throws SQLException {

        if (consoleForFundsTransferObject == null) {
            consoleForFundsTransferObject = new ConsoleForFundsTransfer(customerId);
        }
        return consoleForFundsTransferObject;
    }

    public IConsoleRunnerCustomer createConsoleRunnerCustomer() throws SQLException {

        if(consoleRunnerCustomer == null) {
            consoleRunnerCustomer = new ConsoleRunnerCustomer();
        }
        return consoleRunnerCustomer;
    }

    @Override
    public IConsoleRunnerAdmin createConsoleRunnerAdmin() throws SQLException {

        if (createConsoleRunnerAdmin == null) {
            createConsoleRunnerAdmin = new ConsoleRunnerAdmin();
        }
        return createConsoleRunnerAdmin;
    }

    @Override
    public IConsoleForContactInformation createConsoleForContactInformationObject(String customerId) throws SQLException {

        if(consoleForContactInformation == null) {
            consoleForContactInformation = new ConsoleForContactInformation(customerId);
        }
        return consoleForContactInformation;
    }

    @Override
    public IConsoleForLoans createConsoleForLoansObject(String customerId) throws SQLException, FileNotFoundException {

        if(consoleForLoans == null) {
            consoleForLoans = new ConsoleForLoans(customerId);
        }
        return consoleForLoans;
    }

    public Scanner createScannerInstance() {

        if(userInput == null) {
            userInput = new Scanner(System.in);
        }
        return userInput;
    }
}
