package com.wayne.project.accountInformationTest;

import com.wayne.project.accountInformation.AccountClosure;
import com.wayne.project.accountInformation.IAccountClosure;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountClosureTest{

    @Test
    public void accountClosurePassTest() throws SQLException{
        IAccountClosure accountClosureMock = mock(AccountClosure.class);
        when(accountClosureMock.closeAccount("y")).thenReturn(true);
        boolean expectedResult = true;
        boolean actualResult = accountClosureMock.closeAccount("y");
        Assertions.assertEquals(expectedResult,actualResult);
    }

    @Test
    public void accountClosureFailTest() throws SQLException{
        IAccountClosure accountClosureMock = mock(AccountClosure.class);
        when(accountClosureMock.closeAccount("n")).thenReturn(false);
        boolean expectedResult = false;
        boolean actualResult = accountClosureMock.closeAccount("n");
        Assertions.assertEquals(expectedResult,actualResult);
    }
}
