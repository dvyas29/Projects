package com.wayne.project.contactInformationTest;

import com.wayne.project.contactInformation.ContactInformation;
import com.wayne.project.contactInformation.IContactInformation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.mockito.Mockito.*;

public class ContactInformationTest {

    @Test
    public void getContactInformationTest() throws SQLException {

        IContactInformation contactMock = mock(ContactInformation.class);
        when(contactMock.getContactInformation()).thenReturn("Wayne Banking Corporation, wayne1956@wyncorp.org, 9024587621");
        String contactInformation = contactMock.getContactInformation();
        String expectedDetails = "Wayne Banking Corporation, wayne1956@wyncorp.org, 9024587621";
        Assertions.assertEquals(expectedDetails,contactInformation);
    }

    @Test
    public void sendQueryInformationTest() throws SQLException {

        IContactInformation contactMock = mock(ContactInformation.class);
        when(contactMock.sendQueryInformation("Dinesh","Dinesh2205","dinesh@wyncorp.org","unable to access account statement")).thenReturn(true);
        boolean querySent = contactMock.sendQueryInformation("Dinesh","Dinesh2205","dinesh@wyncorp.org","unable to access account statement");
        boolean expected = true;
        Assertions.assertEquals(expected,querySent);
    }
}
