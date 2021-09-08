package com.wayne.project.console;

import com.wayne.project.accountInformation.*;
import com.wayne.project.database.DatabaseFactory;
import com.wayne.project.database.DatabaseFactoryNormal;
import com.wayne.project.database.IAccountSummaryDatabase;

import java.sql.SQLException;
import java.util.Scanner;

public class ConsoleForAccountInformation implements IConsoleForAccountInformation{

    Scanner userInput;
    String commandEntered;
    String customerId;
    IAccountSummary accountSummary;
    IAccountStatement accountStatement;
    IAccountClosure accountClosure;
    ICustomerDetails customerDetails;
    IRequestChequeBook requestCQB;
    ConsoleFactory consoleFactory;
    AccountInformationFactory accountInformationFactory;
    IAccountSummaryDatabase accountSummaryDatabase;
    DatabaseFactory databaseFactory;

    public ConsoleForAccountInformation(String customerId) throws SQLException {
        this.customerId = customerId;
        consoleFactory = new ConsoleFactoryNormal();
        databaseFactory = new DatabaseFactoryNormal();
        userInput = consoleFactory.createScannerInstance();
        accountInformationFactory = new AccountInformationFactoryNormal();
        accountStatement = accountInformationFactory.createAccountStatementObject(customerId);
        accountClosure = accountInformationFactory.createAccountClosureObject(customerId);
        accountSummary = accountInformationFactory.createAccountSummaryObject(customerId);
        requestCQB = accountInformationFactory.createChequeBookObject(customerId);
        customerDetails = accountInformationFactory.createCustomerDetailsObject(customerId);
        accountSummaryDatabase = databaseFactory.createAccountSummaryDatabase();
    }

    public void accountInformation() throws SQLException{

        System.out.println("AccountInformation");
        System.out.println("Choose an option :- ");
        System.out.println("1. Account Summary");
        System.out.println("2. Create Account Statement");
        System.out.println("3. Update Customer Details");
        System.out.println("4. Request For a chequebook");
        System.out.println("5. Request For Closing Account");
        commandEntered = userInput.next();

        if (commandEntered.equalsIgnoreCase("1")) {
            String accountSummaryData = accountSummary.getAccountSummary();
            System.out.println(accountSummaryData);
        }
        else if (commandEntered.equalsIgnoreCase("2")) {
            displayCreateStatementMenu(customerId);
        }
        else if (commandEntered.equalsIgnoreCase("3")) {
            displayUpdateCustomerDetailsMenu(customerId);
        }
        else if (commandEntered.equalsIgnoreCase("4")) {
            displayChoiceForChequeBook(customerId);
        }
        else if (commandEntered.equalsIgnoreCase("5")) {
            try {
                displayChoiceForClosingAccount(customerId);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void displayCreateStatementMenu(String customerId) throws SQLException{

        System.out.println("AccountStatement");
        System.out.println("Choose an option :- ");
        System.out.println("1. Current Month Statement");
        System.out.println("2. Quarterly Statement");
        System.out.println("3. Yearly Statement");
        System.out.println("4. Custom Statement");
        commandEntered = userInput.next();

        if(commandEntered.equalsIgnoreCase("1")){
            accountStatement.showCurrentMonthStatement();
        }
        else if(commandEntered.equalsIgnoreCase("2")){
            accountStatement.showQuarterlyStatement();
        }
        else if(commandEntered.equalsIgnoreCase("3")){
            accountStatement.showYearlyStatement();
        }
        else if(commandEntered.equalsIgnoreCase("4")){
            System.out.println("Enter the start date :- ");
            String startDate = userInput.next();
            System.out.println("Enter the final date :- ");
            String endDate = userInput.next();
            accountStatement.showCustomStatement(startDate,endDate);
        }

    }

    public void displayUpdateCustomerDetailsMenu(String customerId) throws SQLException{

        System.out.println("What do you want to update :-");
        System.out.println("1. Update Address");
        System.out.println("2. Update SSN");
        System.out.println("3. Update Email");
        commandEntered = userInput.next();
        if(commandEntered.equalsIgnoreCase("1")){
            System.out.println("Enter your new address : ");
            String address = userInput.next();
            if(customerDetails.updateAddress(address)){
                System.out.println("Your address has been updated.");
            }
            else {
                System.out.println("There has been an issue and address could not be updated.");
            }
        }
        else if(commandEntered.equalsIgnoreCase("2")){
            System.out.println("Enter your new SSN : ");
            double SSN = Double.parseDouble(userInput.next());
            if(customerDetails.updateSSN(SSN)){
                System.out.println("Your SSN has been updated.");
            }
            else{
                System.out.println("There has been an issue and SSN could not be updated.");
            }
        }
        else if(commandEntered.equalsIgnoreCase("3")){
            System.out.println("Enter your new email : ");
            String email = userInput.next();
            if (customerDetails.updateEmail(email)){
                System.out.println("Your Email has been updated.");
            }
            else{
                System.out.println("There has been an issue and Email could not be updated");
            }
        }
    }

    public void displayChoiceForChequeBook(String customerId) throws  SQLException{

        System.out.println("Do you want a ChequeBook :- /n Y or N");
        commandEntered = userInput.next();
        String confirmationRequest;
        String accountType = accountSummaryDatabase.getAccountType(customerId);
        confirmationRequest = requestCQB.requestChequeBook(accountType,commandEntered);
        System.out.println(confirmationRequest);
    }

    public void displayChoiceForClosingAccount(String customerId) throws SQLException {

        System.out.println("Do you want to close your account :- ");
        System.out.println("Y or N");
        System.out.println("Enter your choice :- ");
        commandEntered = userInput.next();
        if(accountClosure.closeAccount(commandEntered)){
            System.out.println("Your account has been deleted");
        }
        else {
            System.out.println("There has been an issue and your account cannot be closed right now.");
        }
    }
}
