package com.wayne.project.accountInformationTest;

import com.wayne.project.accountInformation.IRequestChequeBook;
import com.wayne.project.accountInformation.RequestChequeBook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RequestChequeBookTest {

    @Test
    public void requestChequeBookPassTest() throws SQLException {
        IRequestChequeBook requestChequeBookMock = mock(RequestChequeBook.class);
        when(requestChequeBookMock.requestChequeBook("savings","y")).thenReturn("ChequeBookStatus Updated to Yes");
        String expectedResult = "ChequeBookStatus Updated to Yes";
        String actualResult = requestChequeBookMock.requestChequeBook("savings","y");
        Assertions.assertEquals(expectedResult,actualResult);
    }

    @Test
    public void requestChequeBookFailTest() throws SQLException {
        IRequestChequeBook requestChequeBookMock = mock(RequestChequeBook.class);
        when(requestChequeBookMock.requestChequeBook("savings","n")).thenReturn("ChequeBookStatus not Updated");
        String expectedResult = "ChequeBookStatus not Updated";
        String actualResult = requestChequeBookMock.requestChequeBook("savings","n");
        Assertions.assertEquals(expectedResult,actualResult);
    }
}
