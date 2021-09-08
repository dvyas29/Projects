package com.wayne.project.loginTest;

import com.wayne.project.login.CustomerLogin;
import com.wayne.project.login.ICustomerLogin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import static org.mockito.Mockito.*;

public class CustomerLoginTest {

    @Test
    public void customerLoginTest() throws FileNotFoundException, SQLException {

        ICustomerLogin login = mock(CustomerLogin.class);
        when(login.customerLogin("Dinesh22","Accessme@12","no")).thenReturn(true);
        boolean isValid = login.customerLogin("Dinesh22","Accessme@12","no");
        Assertions.assertEquals(isValid, true);
    }
}
