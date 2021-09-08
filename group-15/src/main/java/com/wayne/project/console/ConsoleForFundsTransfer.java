package com.wayne.project.console;

import com.wayne.project.fundsTransfer.*;
import com.wayne.project.database.*;
import com.wayne.project.utility.*;
import java.sql.SQLException;
import java.util.Scanner;

public class ConsoleForFundsTransfer implements IConsoleForFundsTransfer {

    private String customerId;
    private Scanner userInput;
    private String BeneficiaryName;
    private String BeneficiaryIFSCode;
    private String BankName;
    private String BeneficiaryAccountNumber;
    private String BeneficiaryConfirmAccountNumber;
    private int TransferLimit;
    String commandEntered = null;
    private ConsoleFactory consoleFactory;
    private FundsTransferFactory fundsTransferFactory;
    private UtilityFactory utilityFactory;
    private DatabaseFactory databaseFactory;
    private IBeneficiary beneficiary;
    private IBeneficiaryTransfer beneficiaryTransfer;
    private IBeneficiaryDatabase beneficiaryDatabase;
    private ITransferFundsDatabase transferFundsDatabase;
    private IValidations validations;
    private IDateTime date;

    public ConsoleForFundsTransfer(String customerId) throws SQLException{

        consoleFactory = new ConsoleFactoryNormal();
        userInput = consoleFactory.createScannerInstance();
        this.customerId = customerId;
        fundsTransferFactory = new FundsTransferFactoryNormal();
        utilityFactory = new UtilityFactoryNormal();
        databaseFactory = new DatabaseFactoryNormal();
        beneficiary = fundsTransferFactory.createBeneficiaryObject(customerId);
        beneficiaryTransfer = fundsTransferFactory.createBeneficiaryTransferObject(customerId);
        beneficiaryDatabase = databaseFactory.createBeneficiaryDatabaseObject();
        transferFundsDatabase = databaseFactory.createTransferFundsDatabaseObject();
        validations = utilityFactory.createValidationsObject();
        date = utilityFactory.createDateTimeObject("EST");
        userInput = consoleFactory.createScannerInstance();
    }

    @Override
    public void fundsTransfer(String customerId) throws SQLException {

        System.out.println("Funds Transfer");
        System.out.println("Choose an option");
        System.out.println("1. Beneficiaries");
        System.out.println("2. Transfer Funds To Beneficiary");
        System.out.println("'quit' To Exit the application");
        commandEntered = userInput.next();
        if (commandEntered.equals("1")) {
            displayBeneficiaryFunctions(customerId);
        }
        else if (commandEntered.equals("2")) {
            displayTransferFundsFunctions(customerId);
        }
        else if (commandEntered.equals("quit")) {
            System.out.println("Exiting the application");
            return;
        }
    }

    @Override
    public void displayBeneficiaryFunctions(String customerId) throws SQLException {

        String beneficiaries;
        do {
            System.out.println("Beneficiaries");
            System.out.println("Choose an option:");
            System.out.println("1. Add New Beneficiary");
            System.out.println("2. List Beneficiaries");
            System.out.println("3. Delete Beneficiary");
            System.out.println("'quit' To Exit the application");
            commandEntered = userInput.next();
            if (commandEntered.equals("1")) {
                System.out.println("Enter Beneficiary Details to add:");
                System.out.print("Enter Beneficiary Name:");
                BeneficiaryName = userInput.next();
                System.out.print("Enter Bank Name:");
                BankName = userInput.next();
                System.out.print("Enter Beneficiary AccountNumber:");
                BeneficiaryAccountNumber = userInput.next();
                System.out.print("Confirm Beneficiary AccountNumber:");
                BeneficiaryConfirmAccountNumber = userInput.next();
                if (validations.validateAccountNumber(BeneficiaryAccountNumber,BeneficiaryConfirmAccountNumber)) {
                    System.out.print("Enter Beneficiary Beneficiary IFSC code:");
                    BeneficiaryIFSCode = userInput.next();
                    validations.validateIFSCode(BeneficiaryIFSCode);
                    System.out.print("Enter Beneficiary TransferLimit:");
                    TransferLimit = userInput.nextInt();
                    beneficiary.addBeneficiary(customerId, BeneficiaryName, BankName, BeneficiaryAccountNumber, BeneficiaryIFSCode, TransferLimit);
                    System.out.println("Beneficiary Added!");
                }
            }
            else if (commandEntered.equals("2")){
                beneficiaries = beneficiary.beneficiaryList(customerId);
                System.out.println(beneficiaries);
            }
            else if (commandEntered.equals("3")){
                System.out.print("Enter the beneficiary name:");
                BeneficiaryName = userInput.next();
                beneficiary.removeBeneficiary(customerId,BeneficiaryName);
                System.out.println("Beneficiary Deleted.");
            }
            else if (commandEntered.equals("quit")) {
                System.out.println("Exiting the application");
                return;
            }

        }while(true);

    }

    @Override
    public void displayTransferFundsFunctions(String customerId) throws SQLException {

        double Amount;
        int transferLimit;
        String paymentFor;
        String confirmation;
        String beneficiaries;
        String status = "Approved";
        String dateTime = date.fetchDate();
        do {
            System.out.println("Transfer Funds to Beneficiaries");
            System.out.println("Choose an option:");
            System.out.println("1. Transfer Within the Same Bank");
            System.out.println("2. Transfer to Different Bank");
            System.out.println("'quit' To Exit the application");
            commandEntered = userInput.next();
            if (commandEntered.equals("1")) {
                userInput = consoleFactory.createScannerInstance();
                beneficiaries = beneficiary.beneficiaryList(customerId);
                System.out.println(beneficiaries);
                System.out.println("Select a beneficiary name to transfer funds");
                BeneficiaryName = userInput.next();
                BankName = beneficiaryDatabase.getBankName(BeneficiaryName);
                beneficiaryTransfer.confirmTransaction(BeneficiaryName,BankName);
                System.out.println("Enter Amount:");
                Amount = userInput.nextDouble();
                System.out.println("What is the purpose of transfer?");
                paymentFor = userInput.next();
                System.out.println("Confirm Transfer: Y/N:");
                confirmation = userInput.next();
                if (beneficiaryTransfer.checkAvailableBalance(customerId,Amount)) {
                    transferLimit = beneficiaryDatabase.getTransferLimit(BeneficiaryName);
                    if (beneficiaryTransfer.performTransaction(Amount, paymentFor, confirmation, transferLimit)) {
                        beneficiaryTransfer.saveTransaction(customerId, BeneficiaryName, BeneficiaryAccountNumber, Amount, paymentFor, status, dateTime);
                        System.out.println("Transaction succeeded. ");
                    } else {
                        System.out.println("Transaction failed. ");
                    }
                }
                else {
                    System.out.println("Insufficient balance in your account to transfer funds");
                }

            }
            else if (commandEntered.equals("2")){
                System.out.println("Select the transaction type:");
                System.out.println("a. IMPS \n b. NEFT \n c. RTGS");
                System.out.print("Enter the transaction type: ");
                String transactionType = userInput.next();
                beneficiaries = beneficiary.beneficiaryList(customerId);
                System.out.println(beneficiaries);
                System.out.println("Select a Beneficiary to transfer funds");
                BeneficiaryName = userInput.next();
                BankName = beneficiaryDatabase.getBankName(BeneficiaryName);
                if (BankName.equalsIgnoreCase("wayne")) {
                    System.out.println("This beneficiary transfer is for within bank, please select another beneficiary with a different bank name");
                }
                else {
                    System.out.print("Enter Amount:");
                    Amount = userInput.nextInt();
                    System.out.print("What is the purpose of transfer?");
                    paymentFor = userInput.next();
                    System.out.print("Confirm Transfer: Y/N:");
                    confirmation = userInput.next();
                    beneficiaryTransfer.checkAvailableBalance(customerId, Amount);
                    transferLimit = beneficiaryDatabase.getTransferLimit(BeneficiaryName);
                    beneficiaryTransfer.performTransactionForOtherBank(customerId,Amount, BankName, paymentFor, confirmation, transactionType,transferLimit);
                    beneficiaryTransfer.saveTransaction(customerId,BeneficiaryName,BeneficiaryAccountNumber,Amount,paymentFor,status,dateTime);
                    System.out.println("Transaction succeeded. ");
                }
            }
            else if (commandEntered.equals("quit")) {
                System.out.println("Exiting the application");
                return;
            }
            else {
                System.out.println("Bad command");
            }
        }while(true);
    }
}
