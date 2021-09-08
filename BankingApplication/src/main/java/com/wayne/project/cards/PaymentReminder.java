package com.wayne.project.cards;

import com.wayne.project.database.DatabaseFactory;
import com.wayne.project.database.DatabaseFactoryNormal;
import com.wayne.project.database.IPaymentReminderDatabase;
import com.wayne.project.utility.IDateTime;
import com.wayne.project.utility.UtilityFactory;
import com.wayne.project.utility.UtilityFactoryNormal;
import java.sql.SQLException;

public class PaymentReminder implements IPaymentReminders {

    private IPaymentReminderDatabase paymentReminderDatabase;
    private String customerId;
    private DatabaseFactory databaseFactory;
    private UtilityFactory utilityFactory;

    public PaymentReminder(String customerId) {

        this.customerId = customerId;
        utilityFactory = new UtilityFactoryNormal();
        databaseFactory = new DatabaseFactoryNormal();
        paymentReminderDatabase = databaseFactory.createPaymentReminderDatabaseObject();
    }

    @Override
    public String addPaymentReminders(String queryToRemindAbout, int dayOfMonthToRemind) throws SQLException {

        boolean isPaymentReminderAdded = paymentReminderDatabase.insertPaymentReminder(customerId, queryToRemindAbout, dayOfMonthToRemind);
        if(isPaymentReminderAdded) {
            return "The payment reminder was successfully added";
        }
        return "The operation was not successful. Please try after sometime.";
    }

    @Override
    public String fetchPaymentReminders() throws SQLException {

        IDateTime dateTime;
        dateTime = utilityFactory.createDateTimeObject("EST");
        String date = dateTime.fetchDate();
        String dayOfMonth = date.substring(3,5);
        String queries = paymentReminderDatabase.fetchPaymentReminder(customerId, dayOfMonth);
        if(queries == null) {
            return "The operation was not successful. Please try after sometime.";
        }
        return queries;
    }
}