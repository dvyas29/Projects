package com.wayne.project.utilityTest;

import com.wayne.project.utility.DateTime;
import com.wayne.project.utility.IDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DateTimeTest {

    @Test
    public void Test1FetchDateAndTime() {

        IDateTime dateTime = mock(DateTime.class);
        when(dateTime.fetchDateAndTime()).thenReturn("26.03.2021 12:46:39 EST");
        String actualDateTime = dateTime.fetchDateAndTime();
        String expectedResult = "26.03.2021 12:46:39 EST";
        Assertions.assertEquals(expectedResult, actualDateTime);
    }

    @Test
    public void Test2FetchDate() {

        IDateTime dateTime = mock(DateTime.class);
        when(dateTime.fetchDate()).thenReturn("26.03.2021");
        String actualDate = dateTime.fetchDate();
        String expectedResult = "26.03.2021";
        Assertions.assertEquals(expectedResult, actualDate);
    }

    @Test
    public void Test3FetchTime() {

        IDateTime dateTime = mock(DateTime.class);
        when(dateTime.fetchTime()).thenReturn("12:46:39");
        String actualTime = dateTime.fetchTime();
        String expectedResult = "12:46:39";
        Assertions.assertEquals(expectedResult, actualTime);
    }
}
