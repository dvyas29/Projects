package com.wayne.project.accountInformationTest;

import com.wayne.project.accountInformation.CustomerDetails;
import com.wayne.project.accountInformation.ICustomerDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerDetailsTest {

    @Test
    public void updateAddressTest()throws SQLException {
        ICustomerDetails customerDetailsMock = mock(CustomerDetails.class);
        when(customerDetailsMock.updateAddress("6056 University Ave, Halifax, NS B3H 1W5, Canada")).thenReturn(true);
        boolean expectedResult = true;
        boolean actualResult = customerDetailsMock.updateAddress("6056 University Ave, Halifax, NS B3H 1W5, Canada");
        Assertions.assertEquals(expectedResult,actualResult);
    }

    @Test
    public void updateSSNTest() throws SQLException {
        ICustomerDetails customerDetailsMock = mock(CustomerDetails.class);
        when(customerDetailsMock.updateSSN(785621421)).thenReturn(true);
        boolean expectedResult = true;
        boolean actualResult = customerDetailsMock.updateSSN(785621421);
        Assertions.assertEquals(expectedResult,actualResult);
    }

    @Test
    public void updateEmailTest() throws SQLException {
        ICustomerDetails customerDetailsMock = mock(CustomerDetails.class);
        when(customerDetailsMock.updateEmail("harry@gmail.com")).thenReturn(true);
        boolean expectedResult = true;
        boolean actualResult = customerDetailsMock.updateEmail("harry@gmail.com");
        Assertions.assertEquals(expectedResult,actualResult);
    }
}
