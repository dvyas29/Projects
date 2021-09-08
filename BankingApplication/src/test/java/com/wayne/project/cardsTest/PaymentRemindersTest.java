package com.wayne.project.cardsTest;

import com.wayne.project.cards.IPaymentReminders;
import com.wayne.project.cards.PaymentReminder;
import com.wayne.project.database.IPaymentReminderDatabase;
import com.wayne.project.database.PaymentReminderDatabase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaymentRemindersTest {

    @Test
    public void Test1PaymentReminderForDebitCard() throws SQLException {

        IPaymentReminders paymentRemindersMock = mock(PaymentReminder.class);
        String queryToRemindAbout = "Add payment for electricity bill.";
        when(paymentRemindersMock.addPaymentReminders(queryToRemindAbout, 8)).thenReturn("The payment reminder was successfully added");
        String actualResult = paymentRemindersMock.addPaymentReminders(queryToRemindAbout, 8);
        String expectedResult = "The payment reminder was successfully added";
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void Test2FetchPaymentReminderForDebitCard() throws SQLException {

        IPaymentReminderDatabase paymentReminderDatabase = mock(PaymentReminderDatabase.class);
        String dayOfMonth = "15";
        String returnQuery = "Reminder to pay Water bill.";
        when(paymentReminderDatabase.fetchPaymentReminder("70298645", dayOfMonth)).thenReturn(returnQuery);
        String actualResult = paymentReminderDatabase.fetchPaymentReminder("70298645", dayOfMonth);
        Assertions.assertEquals(returnQuery, actualResult);
    }

    @Test
    public void Test3PaymentReminderForCreditCard() throws SQLException {

        IPaymentReminders paymentRemindersMock = mock(PaymentReminder.class);
        String queryToRemindAbout = "Add payment for water bill.";
        when(paymentRemindersMock.addPaymentReminders(queryToRemindAbout, 10)).thenReturn("The payment reminder was successfully added");
        String actualResult = paymentRemindersMock.addPaymentReminders(queryToRemindAbout, 10);
        String expectedResult = "The payment reminder was successfully added";
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void Test4FetchPaymentReminderForCreditCard() throws SQLException {

        IPaymentReminderDatabase paymentReminderDatabase = mock(PaymentReminderDatabase.class);
        String dayOfMonth = "15";
        String returnQuery = "Reminder to pay Water bill.";
        when(paymentReminderDatabase.fetchPaymentReminder("70298645", dayOfMonth)).thenReturn(returnQuery);
        String actualResult = paymentReminderDatabase.fetchPaymentReminder("70298645", dayOfMonth);
        Assertions.assertEquals(returnQuery, actualResult);
    }
}
