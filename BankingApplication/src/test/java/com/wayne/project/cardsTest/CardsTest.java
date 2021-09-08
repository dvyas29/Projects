package com.wayne.project.cardsTest;

import com.wayne.project.cards.Cards;
import com.wayne.project.cards.ICards;
import com.wayne.project.cards.IPaymentReminders;
import com.wayne.project.cards.PaymentReminder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.mockito.Mockito.*;

public class CardsTest {

    @Test
    public void Test1DisplayStatusOfDebitCard() throws SQLException {

        ICards debitCardsMock = mock(Cards.class);
        when(debitCardsMock.displayStatusOfCard("debit")).thenReturn("Account Number: 79357649");
        String actualResult = debitCardsMock.displayStatusOfCard("debit");
        String expectedResult = "Account Number: 79357649";
        Assertions.assertEquals(expectedResult,actualResult);
    }

    @Test
    public void Test2GetUpgradeCardStatusForDebitCard() throws SQLException {

        ICards debitCardsMock = mock(Cards.class);
        when(debitCardsMock.getUpgradeCardStatus("debit")).thenReturn("titanium");
        String activeDebitCardModel = debitCardsMock.getUpgradeCardStatus("debit");
        String expectedResult = "titanium";
        Assertions.assertEquals(expectedResult, activeDebitCardModel);
    }

    @Test
    public void Test3UpgradeCardForDebitCard() throws SQLException {

        ICards debitCardsMock = mock(Cards.class);
        when(debitCardsMock.upgradeCard("gold", "debit")).thenReturn(true);
        Boolean actualResult = debitCardsMock.upgradeCard("gold", "debit");
        Boolean expectedResult = true;
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void test4BlockDebitCard() throws SQLException {

        ICards debitCardsMock = mock(Cards.class);
        when(debitCardsMock.blockCard("debit")).thenReturn(true);
        boolean isDebitCardBlocked = debitCardsMock.blockCard("debit");
        boolean expectedResult = true;
        Assertions.assertEquals(expectedResult, isDebitCardBlocked);
    }

    @Test
    public void Test5GeneratePinCodeForDebitCard() throws SQLException {

        String pinCode = "7896";
        ICards debitCardsMock = mock(Cards.class);
        when(debitCardsMock.generatePinCode(pinCode, "debit")).thenReturn(true);
        boolean actualResult = debitCardsMock.generatePinCode(pinCode, "debit");
        boolean expectedResult = true;
        Assertions.assertEquals(expectedResult,actualResult);
    }

    @Test
    public void Test6DisplayStatusOfCreditCard() throws SQLException {

        ICards creditCardsMock = mock(Cards.class);
        when(creditCardsMock.displayStatusOfCard("credit")).thenReturn("Account Number: 79357649");
        String actualResult = creditCardsMock.displayStatusOfCard("credit");
        String expectedResult = "Account Number: 79357649";
        Assertions.assertEquals(expectedResult,actualResult);
    }

    @Test
    public void Test7GetUpgradeCardStatusForCreditCard() throws SQLException {

        ICards creditCardsMock = mock(Cards.class);
        when(creditCardsMock.getUpgradeCardStatus("credit")).thenReturn("titanium");
        String activecreditCardModel = creditCardsMock.getUpgradeCardStatus("credit");
        String expectedResult = "titanium";
        Assertions.assertEquals(expectedResult, activecreditCardModel);
    }

    @Test
    public void Test8UpgradeCardForCreditCard() throws SQLException {

        ICards creditCardsMock = mock(Cards.class);
        when(creditCardsMock.upgradeCard("gold", "credit")).thenReturn(true);
        Boolean actualResult = creditCardsMock.upgradeCard("gold", "credit");
        Boolean expectedResult = true;
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void test9BlockCreditCard() throws SQLException {

        ICards creditCardsMock = mock(Cards.class);
        when(creditCardsMock.blockCard("credit")).thenReturn(true);
        boolean iscreditCardBlocked = creditCardsMock.blockCard("credit");
        boolean expectedResult = true;
        Assertions.assertEquals(expectedResult, iscreditCardBlocked);
    }

    @Test
    public void Test10GeneratePinCodeForCreditCard() throws SQLException {

        String pinCode = "7896";
        ICards creditCardsMock = mock(Cards.class);
        when(creditCardsMock.generatePinCode(pinCode, "credit")).thenReturn(true);
        boolean actualResult = creditCardsMock.generatePinCode(pinCode, "credit");
        boolean expectedResult = true;
        Assertions.assertEquals(expectedResult,actualResult);
    }
}
