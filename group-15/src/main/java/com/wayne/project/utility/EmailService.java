package com.wayne.project.utility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailService implements IEmailService {

    private Properties emailProperties;
    private Session emailSession;
    private String senderEmail;
    private String senderPassword;
    private final String localPath = "src/main/java/com/wayne/project/utility/EmailProperties";

    public EmailService() throws FileNotFoundException {

        FileParser emailProperties = new FileParser();
        BufferedReader stream = new BufferedReader(new FileReader(localPath));
        List<String> emailCredentialsList;
        emailCredentialsList = emailProperties.loadCredentials(stream);
        Properties props = new Properties();
        props.put("mail.smtp.host", emailCredentialsList.get(0));
        props.put("mail.smtp.socketFactory.port", emailCredentialsList.get(1));
        props.put("mail.smtp.socketFactory.class", emailCredentialsList.get(2));
        props.put("mail.smtp.auth", emailCredentialsList.get(3));
        props.put("mail.smtp.port", emailCredentialsList.get(4));
        senderEmail = emailCredentialsList.get(5);
        senderPassword = emailCredentialsList.get(6);
        emailSession = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(senderEmail, senderPassword);
                    }
                });
    }

    @Override
    public boolean sendEmail(String receiverEmail, String emailSubject, String emailMessage) {

        try {
            MimeMessage message = new MimeMessage(emailSession);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverEmail));
            message.setSubject(emailSubject);
            message.setText(emailMessage);
            Transport.send(message);
            System.out.println("The Email was sent successfully.");
            return true;
        } catch (MessagingException e) {
            System.out.println(e.getMessage());;
        }
        return false;
    }
}
