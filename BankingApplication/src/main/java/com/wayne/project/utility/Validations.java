package com.wayne.project.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validations implements IValidations{

    public Validations() {

    }

    @Override
    public boolean validateAccountNumber(String BeneficiaryAccountNumber, String BeneficiaryConfirmAccountNumber) {

        if (BeneficiaryAccountNumber.equals(BeneficiaryConfirmAccountNumber)) {
            return true;
        }
        else
        {
            System.out.println("Account numbers doesn't match. Please check");
            return false;
        }
    }

    @Override
    public boolean validateIFSCode(String BeneficiaryIFSCode) {

        //I took this regular expression from https://www.geeksforgeeks.org/how-to-validate-ifsc-code-using-regular-expression/
        //changed the variable p to pattern and m to matcher
        String regex =  "^[A-Z]{4}0[A-Z0-9]{6}$";
        Pattern pattern = Pattern.compile(regex);
        if (BeneficiaryIFSCode == null) {
            return false;
        }
        Matcher matcher = pattern.matcher(BeneficiaryIFSCode);
        return matcher.matches();
    }

    @Override
    public boolean validateName(String Name) {

        if (Name == null) {
            return false;
        }
        //I took the regular expression from https://www.geeksforgeeks.org/how-to-validate-a-username-using-regular-expressions-in-java/
        String regex = "^[A-Za-z]\\w{5,29}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(Name);
        return match.matches();
    }

    @Override
    public boolean validateEmail(String email) {

        if (email == null) {
            return false;
        }
        //I took the regular expression from https://www.geeksforgeeks.org/check-email-address-valid-not-java/
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher match = pattern.matcher(email);
        return match.matches();
    }

    @Override
    public boolean validatePassword(String password) {

        if (password == null) {
            return false;
        }
        //I took this regular expression from https://www.geeksforgeeks.org/how-to-validate-a-password-using-regular-expressions-in-java/
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(password);
        return match.matches();
    }

    @Override
    public boolean validateContact(String contact) {

        if (contact == null) {
            return false;
        }
        //I took this regular expression from https://howtodoinjava.com/java/regex/java-regex-validate-and-format-north-american-phone-numbers/
        String regex = "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher match = pattern.matcher(contact);
        return match.matches();
    }
}
