package com.wayne.project.utility;

import java.sql.SQLException;

public interface INotification {

    public String fetchDateAndTime();

    public String fetchPaymentReminders() throws SQLException;

    public boolean checkForAutoPayments() throws SQLException;

    public String fetchAllAlerts() throws SQLException;
}
