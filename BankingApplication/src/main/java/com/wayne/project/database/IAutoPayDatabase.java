package com.wayne.project.database;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface IAutoPayDatabase {

    public boolean addAutoPayForUtilityBills(String customerId, int dayOfMonth, float amountToPay, String cardType,
                                             String billType, String billRegistrationNumber) throws SQLException;

    public HashMap<String, List<String>> fetchAutoPaymentInformationForCustomer(String customerId) throws SQLException;

    public double calculateSumOfAmountForAutoPay(String customerId, List<String> activateAutoPayForId) throws SQLException;
}
