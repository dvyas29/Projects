package com.wayne.project.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

// https://www.javatpoint.com/java-get-current-date
// Looked into API for calling date and time for EST Time zone
public class DateTime implements IDateTime {

    private SimpleDateFormat simpleDateFormat;
    private Date date;
    private String currentTime;
    private String currentDate;
    private String currentDateAndTime;
    private String[] splitDateTimeString;

    public DateTime(String timeZone) {

        simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss z");
        date = new Date();
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        currentDateAndTime = simpleDateFormat.format(date);
        splitDateTimeString = simpleDateFormat.format(date).split(" ");
    }

    @Override
    public String fetchDateAndTime() {

        return currentDateAndTime;
    }

    @Override
    public String fetchDate() {

        return splitDateTimeString[0];
    }

    @Override
    public String fetchTime() {

        return splitDateTimeString[1];
    }
}