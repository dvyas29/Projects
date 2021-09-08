package com.wayne.project.utility;

public interface IValidations {

    public boolean validateAccountNumber(String BeneficiaryAccountNumber, String BeneficiaryConfirmAccountNumber);
    public boolean validateIFSCode(String BeneficiaryIFSCode);
    public boolean validateName(String Name);
    public boolean validateEmail(String email);
    public boolean validatePassword(String password);
    public boolean validateContact(String contact);
}
