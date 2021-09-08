package com.wayne.project.loansTest;

import com.wayne.project.loans.ILoans;
import com.wayne.project.loans.Loans;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class LoansTest {

    @Test
    public void applyForLoanTest() throws SQLException {

        ILoans loansMock = mock(Loans.class);
        when(loansMock.applyForLoan("Dinesh2205","Dinesh",6000,12,5000)).thenReturn(true);
        boolean isLoanApproved = loansMock.applyForLoan("Dinesh2205","Dinesh",6000,12,5000);
        Assertions.assertEquals(true,isLoanApproved);
    }

    @Test
    public void loanPaymentTest() throws SQLException {

        ILoans loansMock = mock(Loans.class);
        when(loansMock.loanPayment("Dinesh2205",750)).thenReturn(true);
        boolean isLoanPaymentMade = loansMock.loanPayment("Dinesh2205",750);
        Assertions.assertEquals(true,isLoanPaymentMade);
    }

    @Test
    public void outstandingLoanAmountTest() throws SQLException {

        ILoans loansMock = mock(Loans.class);
        when(loansMock.outstandingLoanAmount("Dinesh2205")).thenReturn(750.0);
        double outstandingLoanForCurrentMonth = loansMock.outstandingLoanAmount("Dinesh2205");
        Assertions.assertEquals(750.0,outstandingLoanForCurrentMonth);
    }

    @Test
    public void loanRepaymentScheduleTest() throws IOException, SQLException {

        ILoans loansMock = mock(Loans.class);
        when(loansMock.loanRepaymentSchedule("Dinesh2205")).thenReturn(true);
        boolean isScheduleEmailed = loansMock.loanRepaymentSchedule("Dinesh2205");
        Assertions.assertEquals(true,isScheduleEmailed);
    }

    @Test
    public void loanAmountDetailsTest() throws SQLException {

        ILoans loansMock = mock(Loans.class);
        when(loansMock.loanAmountDetails("Dinesh2205")).thenReturn("5250.0,10");
        String loanAmountPendingAndTime = loansMock.loanAmountDetails("Dinesh2205");
        String expected = "5250.0,10";
        Assertions.assertEquals(expected,loanAmountPendingAndTime);
    }

    @Test
    public void getExistingLoansTest() throws SQLException{
        ILoans loansMock = mock(Loans.class);
        when(loansMock.showExistingLoans("Divyansh29")).thenReturn("Loan Id: 1,Loan Type: Personal ,Loan Amount: 10000,Loan Duration: 12");
        String expectedResult = "Loan Id: 1,Loan Type: Personal ,Loan Amount: 10000,Loan Duration: 12";
        String actualResult = loansMock.showExistingLoans("Divyansh29");
        Assertions.assertEquals(expectedResult,actualResult);
    }

    @Test
    public void getTransationHistoryTest() throws SQLException{
        ILoans loansMock = mock(Loans.class);
        when(loansMock.getTransactionHistory("Divyansh29")).thenReturn("Transaction Id: 1,Transaction Date: 29/01/2021,Amount: 100000,Transaction Status: Success");
        String expectedResult = "Transaction Id: 1,Transaction Date: 29/01/2021,Amount: 100000,Transaction Status: Success";
        String actualResult = loansMock.getTransactionHistory("Divyansh29");
        Assertions.assertEquals(expectedResult,actualResult);
    }
}
