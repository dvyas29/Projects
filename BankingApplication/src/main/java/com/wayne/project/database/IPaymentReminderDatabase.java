package com.wayne.project.database;

import java.sql.SQLException;

public interface IPaymentReminderDatabase {

    public boolean insertPaymentReminder(String customerId, String queryToRemindAbout, int dayOfMonthToRemind) throws SQLException;

    public String fetchPaymentReminder(String customerId, String dayOfMonth) throws SQLException;
}
