package com.wayne.project.loginTest;

import com.wayne.project.database.LoginDatabase;
import com.wayne.project.login.AdminLogin;
import com.wayne.project.login.IAdminLogin;
import com.wayne.project.utility.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.wayne.project.database.ILoginDatabase;

public class AdminLoginTest {

    @Test
    public void Test1AdminLoginForgotPasswordAsNo() throws FileNotFoundException, SQLException {

        IAdminLogin adminLogin = mock(AdminLogin.class);
        when(adminLogin.adminLogin("admin1","Dalhousie@901","no")).thenReturn(true);
        boolean isLoginSuccessful = adminLogin.adminLogin("admin1","Dalhousie@901","no");
        Assertions.assertEquals(isLoginSuccessful, true);
    }

    @Test
    public void Test2AdminLoginForgotPasswordAsYes() throws FileNotFoundException, SQLException {

        IAdminLogin adminLogin = mock(AdminLogin.class);
        String adminUsername = "admin1";
        String adminPassword = "root";
        when(adminLogin.adminLogin(adminUsername,adminPassword,"yes")).thenReturn(true);
        boolean hasTheUserForgottenPassword = adminLogin.adminLogin(adminUsername,adminPassword,"yes");
        Assertions.assertEquals(hasTheUserForgottenPassword, true);
        ILoginDatabase adminLoginDatabase = mock(LoginDatabase.class);
        UtilityFactory utilityFactory = new UtilityFactoryNormal();
        IRandomCharacter randomCharacterObject = utilityFactory.createRandomGeneratorObject(10);
        IEmailService emailService = mock(EmailService.class);
        String adminEmail = "admin_8754214@wayne.com";
        when(emailService.sendEmail(adminEmail,"Password Reset","Password Reset to:" + randomCharacterObject)).thenReturn(true);
        boolean isEmailSent = emailService.sendEmail(adminEmail,"Password Reset","Password Reset to:" + randomCharacterObject);
        Assertions.assertEquals(true, isEmailSent);
        when(adminLoginDatabase.setAdminPassword(adminUsername,adminPassword)).thenReturn(true);
        boolean isThePasswordSet = adminLoginDatabase.setAdminPassword(adminUsername,adminPassword);
        Assertions.assertEquals(true, isThePasswordSet);
    }
}