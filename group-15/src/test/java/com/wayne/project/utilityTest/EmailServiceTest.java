package com.wayne.project.utilityTest;

import com.wayne.project.utility.EmailService;
import com.wayne.project.utility.IEmailService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmailServiceTest {

    @Test
    public void Test1() {

        IEmailService emailService = mock(EmailService.class);
        String receiverEmail = "zack.snyder@gmail.com";
        String emailSubject = "Request to give an insider preview on Justice League";
        String emailMessage = "Hello Sir, Could you please send me a preview of Justice League. Regards, Guru";
        when(emailService.sendEmail(receiverEmail, emailSubject, emailMessage)).thenReturn(true);
        boolean isTheEmailSent = emailService.sendEmail(receiverEmail, emailSubject, emailMessage);
        boolean expectedResult = true;
        Assertions.assertEquals(expectedResult, isTheEmailSent);
    }
}