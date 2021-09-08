package com.wayne.project.utilityTest;

import com.wayne.project.utility.IValidations;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ValidationsTest {

    @Test
    public void ValidateAccountNumberTest() {

        IValidations validationsMock = mock(IValidations.class);
        when(validationsMock.validateAccountNumber("123654","123654")).thenReturn(true);
        boolean matched = validationsMock.validateAccountNumber("123654","123654");
        Assertions.assertEquals(true,matched);
    }

    @Test
    public void validateIFSCTest() {

        IValidations validationsMock = mock(IValidations.class);
        when(validationsMock.validateIFSCode("WYN123654")).thenReturn(true);
        boolean matched = validationsMock.validateIFSCode("WYN123654");
        Assertions.assertEquals(true,matched);
    }

    @Test
    public void validateEmailTest() {

        IValidations validationsMock = mock(IValidations.class);
        when(validationsMock.validateEmail("wayne@wayne.com")).thenReturn(true);
        boolean matched = validationsMock.validateEmail("wayne@wayne.com");
        Assertions.assertEquals(true,matched);
    }

    @Test
    public void validatePasswordTest() {

        IValidations validationsMock = mock(IValidations.class);
        when(validationsMock.validatePassword("Dinesh@56")).thenReturn(true);
        boolean matched = validationsMock.validatePassword("Dinesh@56");
        Assertions.assertEquals(true,matched);
    }

    @Test
    public void validateNameTest() {

        IValidations validationsMock = mock(IValidations.class);
        when(validationsMock.validateName("Dinesh")).thenReturn(true);
        boolean matched = validationsMock.validateName("Dinesh");
        Assertions.assertEquals(true,matched);
    }

    @Test
    public void validateContactTest() {

        IValidations validationsMock = mock(IValidations.class);
        when(validationsMock.validateContact("989797465")).thenReturn(true);
        boolean matched = validationsMock.validateContact("989797465");
        Assertions.assertEquals(true,matched);
    }
}
