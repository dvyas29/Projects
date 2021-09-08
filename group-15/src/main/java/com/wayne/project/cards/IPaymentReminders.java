package com.wayne.project.cards;

import java.sql.SQLException;

public interface IPaymentReminders {

    public String addPaymentReminders(String queryToRemindAbout, int dayOfMonthToRemind) throws SQLException;

    public String fetchPaymentReminders() throws SQLException;
}
