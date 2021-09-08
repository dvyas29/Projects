package com.wayne.project.fundsTransferTest;

import com.wayne.project.fundsTransfer.IBeneficiary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BeneficiaryTest {

    @Test
    public void addBeneficiaryTest() throws SQLException {

        IBeneficiary addBeneficiary;
        addBeneficiary = mock(IBeneficiary.class);
        when(addBeneficiary.addBeneficiary("gary","Rob","wayne","123456","WYN00012",3000)).thenReturn(true);
        boolean isBeneficiaryAdded = addBeneficiary.addBeneficiary("gary","Rob","wayne","123456","WYN00012",3000);
        boolean expectedResult = true;
        Assertions.assertEquals(expectedResult,isBeneficiaryAdded);
    }

    @Test
    public void beneficiariesListTest() throws SQLException {

        IBeneficiary listBeneficiaries;
        listBeneficiaries = mock(IBeneficiary.class);
        when(listBeneficiaries.beneficiaryList("cust123")).thenReturn("rob wayne 123456 WYN00012 3000");
        String returnBeneficiaries = listBeneficiaries.beneficiaryList("cust123");
        String expectedBeneficiaries = "rob wayne 123456 WYN00012 3000";
        Assertions.assertEquals(expectedBeneficiaries,returnBeneficiaries);
    }

    @Test
    public void removeBeneficiaryTest() throws SQLException {

        IBeneficiary delete;
        delete = mock(IBeneficiary.class);
        when(delete.removeBeneficiary("cust123","Rob")).thenReturn(true);
        boolean isBeneficiaryDeleted = delete.removeBeneficiary("cust123","Rob");
        boolean expectedResult = true;
        Assertions.assertEquals(expectedResult,isBeneficiaryDeleted);
    }
}
