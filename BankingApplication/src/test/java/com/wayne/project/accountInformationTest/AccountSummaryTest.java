package com.wayne.project.accountInformationTest;

import com.wayne.project.accountInformation.AccountSummary;
import com.wayne.project.accountInformation.IAccountSummary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountSummaryTest {

    @Test
    public void gettingAccountSummaryTest() throws SQLException{
        IAccountSummary accountSummaryMock = mock(AccountSummary.class);
        when(accountSummaryMock.getAccountSummary()).thenReturn("Customer Name :- abc\nAccount Balance :- 12300\nAccountNumber :- 78965412\nEmail :- abc@gmail.com");
        String expectedResult= "Customer Name :- abc" +
                "\nAccount Balance :- 12300"+
                "\nAccountNumber :- 78965412"+
                "\nEmail :- abc@gmail.com";
        String actualResult = accountSummaryMock.getAccountSummary();
        Assertions.assertEquals(expectedResult,actualResult);
    }

}
