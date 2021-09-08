package com.wayne.project.utility;

import com.wayne.project.cards.*;

import java.sql.SQLException;

public class Notification implements INotification {

    private String date;
    private String time;
    private IDateTime dateTime;
    private IPaymentReminders paymentReminders;
    private IAutoPay autoPay;
    private String customerId;
    private CardsFactory cardsFactory;

    public Notification(String customerId) {

        UtilityFactory utilityFactory = new UtilityFactoryNormal();
        cardsFactory = new CardsFactoryNormal();
        this.customerId = customerId;
        dateTime = utilityFactory.createDateTimeObject("EST");
    }

    @Override
    public String fetchDateAndTime() {

        date = dateTime.fetchDate();
        time = dateTime.fetchTime();
        String resultString = "Current Date is: " + date + "\nCurrent Time is: " + time + "\n";
        return resultString;
    }

    @Override
    public String fetchPaymentReminders() throws SQLException {

        paymentReminders = cardsFactory.createPaymentReminderObject(customerId);
        String resultString = "Payment Reminders:" + paymentReminders.fetchPaymentReminders() + "\n";
        return resultString;
    }

    @Override
    public boolean checkForAutoPayments() throws SQLException {

        autoPay = new AutoPay(customerId);
        boolean autoPayResult = autoPay.triggerAutoPaymentFromCreditCard();
        return autoPayResult;
    }

    @Override
    public String fetchAllAlerts() throws SQLException {

        StringBuilder resultString = new StringBuilder();
        resultString.append(fetchDateAndTime());
        resultString.append(fetchPaymentReminders()).append("\n");
        if(checkForAutoPayments()) {
            resultString.append("Auto payment is successful");
        }
        return resultString.toString();
    }
}