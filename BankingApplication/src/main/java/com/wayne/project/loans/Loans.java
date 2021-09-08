package com.wayne.project.loans;

import com.opencsv.CSVWriter;
import com.wayne.project.adminOperations.AdminOperationsFactory;
import com.wayne.project.adminOperations.AdminOperationsFactoryNormal;
import com.wayne.project.adminOperations.IAdminOperations;
import com.wayne.project.database.*;
import com.wayne.project.utility.IDateTime;
import com.wayne.project.utility.IEmailService;
import com.wayne.project.utility.UtilityFactory;
import com.wayne.project.utility.UtilityFactoryNormal;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Loans implements ILoans{

    private String customerId;
    private double outstandingLoanAmount;
    private IAdminOperations adminOperations;
    private AdminOperationsFactory adminOperationsFactory;
    private IAccountSummaryDatabase accountSummaryDatabase;
    private ILoansDatabase loansDatabase;
    private DatabaseFactory databaseFactory;
    private IAdminOperationsDatabase adminOperationsDatabase;
    private UtilityFactory utilityFactory;
    private IEmailService emailService;
    private IDateTime dateTime;
    private ITransferFundsDatabase transferFundsDatabase;

    public Loans(String customerId) throws FileNotFoundException {

        this.customerId = customerId;
        databaseFactory = new DatabaseFactoryNormal();
        utilityFactory = new UtilityFactoryNormal();
        adminOperationsFactory = new AdminOperationsFactoryNormal();
        adminOperations = adminOperationsFactory.createAdminOperationsObject();
        accountSummaryDatabase = databaseFactory.createAccountSummaryDatabase();
        loansDatabase = databaseFactory.createLoansDatabaseObject();
        adminOperationsDatabase = databaseFactory.createAdminOperationsDatabaseObject();
        emailService = utilityFactory.createEmailObject();
        transferFundsDatabase = databaseFactory.createTransferFundsDatabaseObject();
        dateTime = utilityFactory.createDateTimeObject("EST");
    }

    @Override
    public boolean applyForLoan(String customerId, String customerName,double amount, int timePeriod, double annualIncome) throws SQLException {

        double months;
        String email = adminOperationsDatabase.fetchEmail(customerId);
        months = timePeriod;
        double estimate = 0.25 * annualIncome;
        double loanEstimate = estimate * months * 2.5;
        if (loanEstimate > amount) {
            adminOperations.approveLoanRequest(customerId,customerName,email,amount,loanEstimate,timePeriod,annualIncome);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean loanPayment(String customerId,double outstandingLoanAmountForThisMonth) throws SQLException {

        String amountDetails = loanAmountDetails(customerId);
        String[] values;
        double accountBalance = accountSummaryDatabase.getAccountBalance(customerId);
        if (outstandingLoanAmountForThisMonth < accountBalance) {
            double balance = accountBalance - outstandingLoanAmountForThisMonth;
            accountSummaryDatabase.deductAmountFromAccount(customerId,accountBalance,outstandingLoanAmountForThisMonth);
            transferFundsDatabase.storeTransaction(customerId,outstandingLoanAmount,"loan","debit",dateTime.fetchDateAndTime(),balance);
            values = amountDetails.split(" ");
            double loanAvailable = Double.parseDouble(values[0]);
            int timePeriod = Integer.parseInt(values[1]);
            loansDatabase.updateLoansDetails(customerId,loanAvailable,timePeriod,outstandingLoanAmount(customerId));
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public double outstandingLoanAmount(String customerId) throws SQLException {

        String[] values;
        String amountDetails = loanAmountDetails(customerId);
        values = amountDetails.split(" ");
        double loanAvailable = Double.parseDouble(values[0]);
        int timePeriod = Integer.parseInt(values[1]);
        outstandingLoanAmount = loanAvailable/timePeriod;
        return outstandingLoanAmount;
    }

    @Override
    public boolean loanRepaymentSchedule(String customerId) throws SQLException {

        String[] values;
        String amountDetails = loanAmountDetails(customerId);
        values = amountDetails.split(" ");
        double loanAvailable = Double.parseDouble(values[0]);
        int timePeriod = Integer.parseInt(values[1]);
        double balanceDuePerMonth = loanAvailable/timePeriod;
        for (int i = 0; i < timePeriod; i++) {
            loanAvailable = loanAvailable - balanceDuePerMonth;
            String[] schedule = {"month" + 1,String.valueOf(loanAvailable)};
            File file = new File("src/main/java/paymentSchedule.csv");
            try {
                FileWriter writer = new FileWriter(file);
                CSVWriter csv = new CSVWriter(writer);
                csv.writeNext(schedule);
                csv.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        String email = adminOperationsDatabase.fetchEmail(customerId);
        emailService.sendEmail(email,"Password Reset","Loan payment Schedule = '"+loanAvailable+"'");
        return true;
    }

    @Override
    public String loanAmountDetails(String customerId) throws SQLException {

        String amountDetails = loansDatabase.getLoanSanctionedAmountAndTimePeriod(customerId);
        return amountDetails;
    }

    @Override
    public boolean applyForAutomotiveLoan(String customerId, String customerName, String email,double amount, int timePeriod, double annualIncome) throws SQLException {

        if (amount <= 0) {
            System.out.println("You have entered invalid amount for loan");
        }
        else {
            double estimate = 0.15 * annualIncome;
            double loanEstimate = estimate * timePeriod * 5;

            if(loanEstimate > amount){
                adminOperations.approveLoanRequest(customerId,customerName,email,amount,loanEstimate,timePeriod,annualIncome);
                return true;
            }

        }
        return false;
    }

    @Override
    public String showExistingLoans(String customerId) throws SQLException {
        List<String> list;
        list = loansDatabase.getExistingLoans(customerId);
        String existingLoans ="";

        for(String loanData : list){
            existingLoans+=("/n"+loanData);
        }
        return existingLoans;
    }

    @Override
    public String getTransactionHistory(String customerId) throws SQLException{

        List<String> list = new ArrayList<>();
        list = loansDatabase.getTransactions(customerId);
        String transactions = "";
        for(String s: list){
            transactions = transactions + s +"\n";
        }
        String email = accountSummaryDatabase.getEmail(customerId);
        emailService.sendEmail(email,"Transaction History",transactions);

        return  transactions;
    }
}
