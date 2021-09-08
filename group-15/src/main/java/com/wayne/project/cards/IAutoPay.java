package com.wayne.project.cards;

import java.sql.SQLException;

public interface IAutoPay {

    public boolean addAutoPayForUtilityBills(int dayOfMonth, float amountToPay, String cardType,
                                             String billType, String billRegistrationNumber) throws SQLException;

    public boolean triggerAutoPaymentFromCreditCard() throws SQLException;
}
