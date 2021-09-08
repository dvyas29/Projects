package com.wayne.project.cardsTest;

import com.wayne.project.cards.AutoPay;
import com.wayne.project.cards.IAutoPay;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AutoPayTest {

    /*
    * Auto payment is supported for Credit Cards and not for Debit cards
    * */
    @Test
    public void Test1AutoPaymentForUtilityBillsUsingCreditCard() throws SQLException {

        IAutoPay autoPayMock = mock(AutoPay.class);
        String queryToRemindAbout = "Add payment for electricity bill.";
        when(autoPayMock.addAutoPayForUtilityBills(6, 50, "credit", "electricity", "32578")).thenReturn(true);
        boolean actualResult = autoPayMock.addAutoPayForUtilityBills(6, 50, "credit", "electricity", "32578");
        boolean expectedResult = true;
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void Test2TriggerAutoPaymentFromCreditCard() throws SQLException {

        IAutoPay autoPayMock = mock(AutoPay.class);
        boolean expectedResult = true;
        when(autoPayMock.triggerAutoPaymentFromCreditCard()).thenReturn(true);
        boolean actualResult = autoPayMock.triggerAutoPaymentFromCreditCard();
        Assertions.assertEquals(expectedResult, actualResult);
    }
}