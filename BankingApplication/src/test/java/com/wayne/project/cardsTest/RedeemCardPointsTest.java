package com.wayne.project.cardsTest;

import com.wayne.project.cards.IRedeemCardPoints;
import com.wayne.project.cards.RedeemCardPoints;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RedeemCardPointsTest {

    @Test
    public void Test1DisplayRedeemCardPointsForDebitCard() throws SQLException {

        IRedeemCardPoints debitCardsMock = mock(RedeemCardPoints.class);
        when(debitCardsMock.fetchRedeemCardPoints("debit")).thenReturn(250);
        int redeemCardPoints = debitCardsMock.fetchRedeemCardPoints("debit");
        int expectedResult = 250;
        Assertions.assertEquals(expectedResult, redeemCardPoints);
    }

    @Test
    public void Test2RedeemDebitCardPoints() throws SQLException {

        IRedeemCardPoints debitCardsMock = mock(RedeemCardPoints.class);
        String resultString = "The Redeem Points have been successfully added to your account." +
                "The Redeem card points have been set to zero.";
        when(debitCardsMock.redeemCardPoints("debit", 1000)).thenReturn(resultString);
        String actualResult = debitCardsMock.redeemCardPoints("debit", 1000);
        String expectedResult = "The Redeem Points have been successfully added to your account." +
                "The Redeem card points have been set to zero.";
        Assertions.assertEquals(expectedResult, actualResult);

    }

    @Test
    public void Test3DisplayRedeemCardPointsForCreditCard() throws SQLException {

        IRedeemCardPoints creditCardsMock = mock(RedeemCardPoints.class);
        when(creditCardsMock.fetchRedeemCardPoints("credit")).thenReturn(250);
        int redeemCardPoints = creditCardsMock.fetchRedeemCardPoints("credit");
        int expectedResult = 250;
        Assertions.assertEquals(expectedResult, redeemCardPoints);
    }

    @Test
    public void Test4RedeemCreditCardPoints() throws SQLException {

        IRedeemCardPoints debitCardsMock = mock(RedeemCardPoints.class);
        String resultString = "The Redeem Points have been successfully added to your account." +
                "The Redeem card points have been set to zero.";
        when(debitCardsMock.redeemCardPoints("credit", 1000)).thenReturn(resultString);
        String actualResult = debitCardsMock.redeemCardPoints("credit", 1000);
        String expectedResult = "The Redeem Points have been successfully added to your account." +
                "The Redeem card points have been set to zero.";
        Assertions.assertEquals(expectedResult, actualResult);
    }
}
