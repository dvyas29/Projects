package com.wayne.project.utility;

import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class UtilityFactoryNormal extends UtilityFactory{

    public IEmailService createEmailObject() throws FileNotFoundException {
        return new EmailService();
    }

    public IDateTime createDateTimeObject(String timeZone) {
        return new DateTime(timeZone);
    }

    public IRandomCharacter createRandomGeneratorObject(int size) {
        return new RandomCharacter(size);
    }

    public INotification createNotificationObject(String customerId) {
        return new Notification(customerId);
    }

    public static Logger createLoggerObject() {

        return LoggingSingleton.getLoggingSingletonInstance();
    }

    public IValidations createValidationsObject() {
        return new Validations();
    }

    public IFileParser createFileParserObject() {
        return new FileParser();
    }
}
