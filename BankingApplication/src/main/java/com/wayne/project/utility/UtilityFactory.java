package com.wayne.project.utility;

import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public abstract class UtilityFactory {

    public abstract IEmailService createEmailObject() throws FileNotFoundException;

    public abstract IDateTime createDateTimeObject(String timeZone);

    public abstract IRandomCharacter createRandomGeneratorObject(int size);

    public abstract INotification createNotificationObject(String customerId);

    public static Logger createLoggerObject() {
        return null;
    }

    public abstract IValidations createValidationsObject();

    public abstract IFileParser createFileParserObject();
}
