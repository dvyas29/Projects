package com.wayne.project.console;

import com.wayne.project.loans.LoansFactory;
import com.wayne.project.loans.LoansFactoryNormal;
import com.wayne.project.utility.IValidations;
import com.wayne.project.utility.UtilityFactory;
import com.wayne.project.utility.UtilityFactoryNormal;
import com.wayne.project.loans.ILoans;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class ConsoleForLoans implements IConsoleForLoans{

    Scanner userInput;
    String commandEntered = null;
    String customerName;
    String email;
    double amount;
    int timePeriod;
    double annualIncome;
    private String customerId;
    private ILoans loans;
    private UtilityFactory utilityFactory;
    private IValidations validations;
    private LoansFactory loansFactory;
    private ConsoleFactory consoleFactory = new ConsoleFactoryNormal();

    public ConsoleForLoans(String customerId) throws FileNotFoundException, SQLException {

        this.customerId = customerId;
        loansFactory = new LoansFactoryNormal();
        utilityFactory = new UtilityFactoryNormal();
        userInput = consoleFactory.createScannerInstance();
        validations = utilityFactory.createValidationsObject();
        loans = loansFactory.createLoansObject(customerId);
    }

    @Override
    public void loans(String customerId) throws SQLException, IOException {

        do {
            System.out.println("Loans");
            System.out.println("Choose an option");
            System.out.println("1. Apply for personal Loan");
            System.out.println("2. Automobile Loan");
            System.out.println("3. Loan payment");
            System.out.println("4. Existing loan information");
            System.out.println("5. Loan transaction history");
            System.out.println("6. loan re-payment schedule");
            System.out.println("'quit' To Exit the application");
            commandEntered = userInput.next();
            if (commandEntered.equals("1")) {
                System.out.println("Enter your Name:");
                customerName = userInput.next();
                validations.validateName(customerName);
                System.out.println("Enter the loan amount required:");
                amount = userInput.nextDouble();
                System.out.println("Enter the loan time period (in months):");
                timePeriod = userInput.nextInt();
                System.out.println("Enter your Annual Income:");
                annualIncome = userInput.nextDouble();
                if (loans.applyForLoan(customerId, customerName,amount,timePeriod, annualIncome)) {
                    System.out.println("You are eligible to apply for a personal loan, out team will connect with you.");
                }
                else {
                    System.out.println("Sorry! You are not eligible to apply for personal loan");
                }

            }
            else if (commandEntered.equals("2")) {
                System.out.println("Enter your Name:");
                customerName = userInput.next();
                validations.validateName(customerName);
                System.out.println("Enter the loan amount:");
                amount = userInput.nextDouble();
                System.out.println("Enter the loan time period (in months):");
                timePeriod = userInput.nextInt();
                System.out.println("Enter Your Email: ");
                email = userInput.next();
                System.out.println("Enter your Annual Income:");
                annualIncome = userInput.nextDouble();
                if (loans.applyForAutomotiveLoan(customerId, customerName,email,amount,timePeriod, annualIncome)) {
                    System.out.println("You are eligible to apply for an automotive loan, out team will connect with you.");
                }
                else {
                    System.out.println("Sorry! You are not eligible to apply for an automotive loan");
                }
            }
            else if (commandEntered.equals("3")) {
                System.out.println("Loan payment for the current month");
                double outstandingLoanAmountForThisMonth = loans.outstandingLoanAmount(customerId);
                System.out.println("outstanding Loan Amount For This Month is " + outstandingLoanAmountForThisMonth);
                System.out.println("proceed to make payment? (Yes/No):");
                String input = userInput.next();
                if (input.equals("Yes")) {
                    loans.loanPayment(customerId, outstandingLoanAmountForThisMonth);
                    System.out.println("Payment of Loan for this month is made successfully! Please check the payment schedule for next payment.");
                }
                else {
                    System.out.println("Cancelled transaction");
                }

            }
            else if (commandEntered.equals("4")) {
                String existingLoans = loans.showExistingLoans(customerId);
                System.out.println(existingLoans);
            }
            else if (commandEntered.equals("5")) {
                String transactionHistory = loans.getTransactionHistory(customerId);
                System.out.println(transactionHistory);
            }
            else if (commandEntered.equals("6")) {
                loans.loanRepaymentSchedule(customerId);
            }
            else if (commandEntered.equals("quit")) {
                System.out.println("Exiting the application");
                return;
            }
        } while (true);
    }
}
