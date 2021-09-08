package com.wayne.project.accountInformation;

import com.opencsv.CSVWriter;
import com.wayne.project.database.DatabaseFactory;
import com.wayne.project.database.DatabaseFactoryNormal;
import com.wayne.project.database.IAccountStatementDatabase;
import com.wayne.project.utility.UtilityFactoryNormal;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AccountStatement implements IAccountStatement{
    private static Logger log = UtilityFactoryNormal.createLoggerObject();
    IAccountStatementDatabase accountStatement;
    Calendar calendar = Calendar.getInstance();
    private int currentMonth = calendar.get(Calendar.MONTH);
    private int currentYear = calendar.get(Calendar.YEAR);
    private String fileName;
    List<String[]> list = new ArrayList<>();
    String customerId;
    DatabaseFactory databaseFactory;


    public AccountStatement(String customerId) {
        this.customerId = customerId;
        databaseFactory = new DatabaseFactoryNormal();
        accountStatement = databaseFactory.createAccountStatementDatabase();
    }

    @Override
    public void showCurrentMonthStatement() {

        log.info("Showing current month statement");
        try {
            list = accountStatement.getCurrentMonthStatement(customerId, currentMonth, currentYear);
            fileName = "src/main/java/CurrentMonthStatement.csv";
            FileWriter fileWriter = new FileWriter(fileName);
            CSVWriter csvWriter = new CSVWriter(fileWriter);

            for (String[] statements : list) {
                csvWriter.writeNext(statements);
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void showQuarterlyStatement() {

        try {
            list = accountStatement.getQuarterlyStatement(customerId,currentYear);
            fileName = "src/main/java/QuarterStatement.csv";
            FileWriter fileWriter = new FileWriter(fileName);
            CSVWriter csvWriter = new CSVWriter(fileWriter);

            for (String[] statements : list) {
                csvWriter.writeNext(statements);
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void showYearlyStatement() {

        try {
            list = accountStatement.getYearlyStatement(customerId,currentYear);
            fileName = "src/main/java/CurrentYearStatement.csv";
            FileWriter fileWriter = new FileWriter(fileName);
            CSVWriter csvWriter = new CSVWriter(fileWriter);

            for (String[] statements : list) {
                csvWriter.writeNext(statements);
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void showCustomStatement(String startDate, String finalDate) {

        try {
            list = accountStatement.getCustomStatement(customerId,startDate,finalDate);
            fileName = "src/main/java/CustomStatement.csv";
            FileWriter fileWriter = new FileWriter(fileName);
            CSVWriter csvWriter = new CSVWriter(fileWriter);

            for (String[] statements : list) {
                csvWriter.writeNext(statements);
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
