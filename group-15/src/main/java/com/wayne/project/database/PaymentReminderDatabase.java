package com.wayne.project.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PaymentReminderDatabase extends ConnectToDatabase implements IPaymentReminderDatabase {
    private Connection connectionForPaymentReminders;
    private Statement statementForPaymentReminders;
    private ResultSet resultSetForPaymentReminders;

    @Override
    public boolean insertPaymentReminder(String customerId, String queryToRemindAbout, int dayOfMonthToRemind) throws SQLException {

        String sqlQueryToInsertPaymentReminder = "INSERT INTO payment_reminder (customer_id, query, day_of_month) " +
                "VALUES ('" + customerId + "', '" + queryToRemindAbout + "', '" + dayOfMonthToRemind + "');";
        connectionForPaymentReminders = this.createDatabaseConnection();
        statementForPaymentReminders = connectionForPaymentReminders.createStatement();
        try {
            int resultOfInsertion = statementForPaymentReminders.executeUpdate(sqlQueryToInsertPaymentReminder);
            if(resultOfInsertion > 0) {
                return true;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            statementForPaymentReminders.close();
            this.closeDatabaseConnection();
        }
        return false;
    }

    @Override
    public String fetchPaymentReminder(String customerId, String dayOfMonth) throws SQLException {

        StringBuilder resultString = new StringBuilder();
        connectionForPaymentReminders = this.createDatabaseConnection();
        statementForPaymentReminders = connectionForPaymentReminders.createStatement();
        String sqlQueryToFetchPaymentReminders = "select * from payment_reminder where customer_id = '" + customerId + "' " +
                "and day_of_month = '" + dayOfMonth + "';";
        try {
            resultSetForPaymentReminders = statementForPaymentReminders.executeQuery(sqlQueryToFetchPaymentReminders);
            int i = 1;
            while(resultSetForPaymentReminders.next()) {
                resultString.append(i).append(". ");
                resultString.append(resultSetForPaymentReminders.getString("query"));
                resultString.append("\n");
                i++;
            }
        return resultString.toString();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            resultSetForPaymentReminders.close();
            statementForPaymentReminders.close();
            this.closeDatabaseConnection();
        }
        return null;
    }
}
