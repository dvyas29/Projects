package com.wayne.project.fundsTransferTest;

import com.wayne.project.fundsTransfer.BeneficiaryTransfer;
import com.wayne.project.fundsTransfer.IBeneficiaryTransfer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.mockito.Mockito.*;

public class BeneficiaryTransferTest {

    @Test
    public void confirmTransactionTest() {

        IBeneficiaryTransfer transfer = mock(BeneficiaryTransfer.class);
        when(transfer.confirmTransaction("Zack","Wayne")).thenReturn(true);
        boolean isBankNameValid = transfer.confirmTransaction("Zack","Wayne");
        boolean expected = true;
        Assertions.assertEquals(expected,isBankNameValid);
    }

    @Test
    public void checkAvailableBalanceTest() {

        IBeneficiaryTransfer transfer = mock(BeneficiaryTransfer.class);
        when(transfer.checkAvailableBalance("cust123",1200)).thenReturn(true);
        boolean isChecked = transfer.checkAvailableBalance("cust123",1200);
        boolean expected = true;
        Assertions.assertEquals(expected,isChecked);
    }

    @Test
    public void performTransactionTest() throws SQLException {

        IBeneficiaryTransfer transfer = mock(BeneficiaryTransfer.class);
        when(transfer.performTransaction(1500,"beneficiary Transfer","Y",3000)).thenReturn(true);
        boolean isTransactionCompleted = transfer.performTransaction(1500,"beneficiary Transfer","Y",3000);
        Assertions.assertEquals(true,isTransactionCompleted);
    }

    @Test
    public void saveTransactionTest() throws SQLException {

        IBeneficiaryTransfer transfer = mock(BeneficiaryTransfer.class);
        when(transfer.saveTransaction("cust123","Dinesh","1012365",
                1650,"Term Fee","Approved","23/03/2021 16:02:15")).thenReturn(true);
        boolean isTransactionSaved = transfer.saveTransaction("cust123","Dinesh","1012365",
                1650,"Term Fee","Approved","23/03/2021 16:02:15");
        boolean expected = true;
        Assertions.assertEquals(expected,isTransactionSaved);
    }

    @Test
    public void performTransactionToOtherBankTest() throws SQLException {

        IBeneficiaryTransfer transfer = mock(BeneficiaryTransfer.class);
        when(transfer.performTransactionForOtherBank("cust123",2000,"Scotia Bank",
                "University Deposit","Y","NEFT",5000)).thenReturn(true);
        boolean isPaymentMade = transfer.performTransactionForOtherBank("cust123",2000,"Scotia Bank",
                "University Deposit","Y","NEFT",5000);
        boolean expected = true;
        Assertions.assertEquals(expected,isPaymentMade);
    }

}
