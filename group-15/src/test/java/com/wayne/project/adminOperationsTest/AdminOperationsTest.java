package com.wayne.project.adminOperationsTest;

import com.wayne.project.adminOperations.AdminOperations;
import com.wayne.project.adminOperations.IAdminOperations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.mockito.Mockito.*;

public class AdminOperationsTest {

    @Test
    public void closeAccountTest() throws SQLException {

        IAdminOperations adminMock = mock(AdminOperations.class);
        when(adminMock.closeAccount("Dinesh2205")).thenReturn(true);
        boolean isAccountDeleted = adminMock.closeAccount("Dinesh2205");
        Assertions.assertEquals(true,isAccountDeleted);
    }

    @Test
    public void updateCustomerQueryTest() throws SQLException {

        IAdminOperations adminMock = mock(AdminOperations.class);
        when(adminMock.updateCustomerQuery("Dinesh2205")).thenReturn(true);
        boolean isQueryUpdated = adminMock.updateCustomerQuery("Dinesh2205");
        Assertions.assertEquals(true,isQueryUpdated);
    }

    @Test
    public void approveLoanRequestTest() throws SQLException {

        IAdminOperations adminMock = mock(AdminOperations.class);
        when(adminMock.approveLoanRequest("Dinesh2205","Dinesh","dinesh@wyncorp.org",6000,7200,12,5000)).thenReturn(true);
        boolean isLoanApproved = adminMock.approveLoanRequest("Dinesh2205","Dinesh","dinesh@wyncorp.org",6000,7200,12,5000);
        Assertions.assertEquals(true,isLoanApproved);
    }
}
