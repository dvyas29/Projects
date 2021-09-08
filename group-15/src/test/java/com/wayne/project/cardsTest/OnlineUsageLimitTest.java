package com.wayne.project.cardsTest;

import com.wayne.project.cards.IOnlineUsageLimit;
import com.wayne.project.cards.OnlineUsageLimit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OnlineUsageLimitTest {

    @Test
    public void Test1SetOnlineUsageLimitAboveMaximumForDebitCard() throws SQLException {

        IOnlineUsageLimit onlineUsageLimitForDebitCards = mock(OnlineUsageLimit.class);
        when(onlineUsageLimitForDebitCards.setOnlineUsageLimit(500, "debit")).thenReturn(false);
        boolean isOnlineUsageLimitSet = onlineUsageLimitForDebitCards.setOnlineUsageLimit(500, "debit");
        boolean expectedResult = false;
        Assertions.assertEquals(expectedResult, isOnlineUsageLimitSet);
    }

    @Test
    public void Test2SetOnlineUsageLimitLessThanMinimumForDebitCard() throws SQLException {

        IOnlineUsageLimit onlineUsageLimitForDebitCards = mock(OnlineUsageLimit.class);
        when(onlineUsageLimitForDebitCards.setOnlineUsageLimit(100, "debit")).thenReturn(false);
        boolean isOnlineUsageLimitSet = onlineUsageLimitForDebitCards.setOnlineUsageLimit(100, "debit");
        boolean expectedResult = false;
        Assertions.assertEquals(expectedResult, isOnlineUsageLimitSet);
    }

    @Test
    public void Test3SetOnlineUsageLimitWithinPermissibleRangeForDebitCard() throws SQLException {

        IOnlineUsageLimit onlineUsageLimitForDebitCards = mock(OnlineUsageLimit.class);
        when(onlineUsageLimitForDebitCards.setOnlineUsageLimit(400, "debit")).thenReturn(false);
        boolean isOnlineUsageLimitSet = onlineUsageLimitForDebitCards.setOnlineUsageLimit(400, "debit");
        boolean expectedResult = false;
        Assertions.assertEquals(expectedResult, isOnlineUsageLimitSet);
    }

    @Test
    public void Test4SetOnlineUsageLimitAboveMaximumForCreditCard() throws SQLException {

        IOnlineUsageLimit onlineUsageLimitForCreditCards = mock(OnlineUsageLimit.class);
        when(onlineUsageLimitForCreditCards.setOnlineUsageLimit(500, "credit")).thenReturn(false);
        boolean isOnlineUsageLimitSet = onlineUsageLimitForCreditCards.setOnlineUsageLimit(500, "credit");
        boolean expectedResult = false;
        Assertions.assertEquals(expectedResult, isOnlineUsageLimitSet);
    }

    @Test
    public void Test5SetOnlineUsageLimitLessThanMinimumForCreditCard() throws SQLException {

        IOnlineUsageLimit onlineUsageLimitForCreditCards = mock(OnlineUsageLimit.class);
        when(onlineUsageLimitForCreditCards.setOnlineUsageLimit(100, "credit")).thenReturn(false);
        boolean isOnlineUsageLimitSet = onlineUsageLimitForCreditCards.setOnlineUsageLimit(100, "credit");
        boolean expectedResult = false;
        Assertions.assertEquals(expectedResult, isOnlineUsageLimitSet);
    }

    @Test
    public void Test6SetOnlineUsageLimitWithinPermissibleRangeForCreditCard() throws SQLException {

        IOnlineUsageLimit onlineUsageLimitForCreditCards = mock(OnlineUsageLimit.class);
        when(onlineUsageLimitForCreditCards.setOnlineUsageLimit(400, "credit")).thenReturn(false);
        boolean isOnlineUsageLimitSet = onlineUsageLimitForCreditCards.setOnlineUsageLimit(400, "credit");
        boolean expectedResult = false;
        Assertions.assertEquals(expectedResult, isOnlineUsageLimitSet);
    }
}
