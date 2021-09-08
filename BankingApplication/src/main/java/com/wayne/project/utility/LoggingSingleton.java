package com.wayne.project.utility;

import org.apache.logging.log4j.*;

public class LoggingSingleton {

    private static Logger logger;

    public LoggingSingleton() {

    }

    public static Logger getLoggingSingletonInstance() {

        if(logger == null) {
            logger = LogManager.getLogger(LogExample.class);
        }
        return logger;
    }
}
