package com.wayne.project.utility;

public interface IEmailService {

    public boolean sendEmail(String receiverEmail, String emailSubject, String emailMessage);
}
