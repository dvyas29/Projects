package com.wayne.project.cards;

import com.wayne.project.database.DatabaseFactory;
import com.wayne.project.database.DatabaseFactoryNormal;
import com.wayne.project.database.IAutoPayDatabase;
import com.wayne.project.utility.IDateTime;
import com.wayne.project.utility.UtilityFactory;
import com.wayne.project.utility.UtilityFactoryNormal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AutoPay implements IAutoPay {

    private IAutoPayDatabase autoPayDatabase;
    private ICards cards;
    private String customerId;
    private DatabaseFactory databaseFactory;
    private CardsFactory cardsFactory;
    private UtilityFactory utilityFactory;

    public AutoPay(String customerId) throws SQLException {

        this.customerId = customerId;
        databaseFactory = new DatabaseFactoryNormal();
        cardsFactory = new CardsFactoryNormal();
        utilityFactory = new UtilityFactoryNormal();
        autoPayDatabase = databaseFactory.createAutoPayDatabaseObject();
        cards = cardsFactory.createCardsObject(customerId);
    }

    @Override
    public boolean addAutoPayForUtilityBills(int dayOfMonth, float amountToPay, String cardType,
                                             String billType, String billRegistrationNumber) throws SQLException {

        boolean isAutoPayForBillAdded = autoPayDatabase.addAutoPayForUtilityBills(customerId, dayOfMonth, amountToPay,
                cardType, billType, billRegistrationNumber);
        return isAutoPayForBillAdded;
    }

    @Override
    public boolean triggerAutoPaymentFromCreditCard() throws SQLException {

        HashMap<String, List<String>> hashMap = autoPayDatabase.fetchAutoPaymentInformationForCustomer(customerId);
        IDateTime dateTime;
        dateTime = utilityFactory.createDateTimeObject("EST");
        String date = dateTime.fetchDate();
        String dayOfMonth = date.substring(3,5);
        List<String> activateAutoPayForId = new ArrayList<>();
        for(String eachKey: hashMap.keySet()) {
            for( List<String> eachValue: hashMap.values()) {
                if(date.equalsIgnoreCase(eachValue.get(1))) {
                    continue;
                }
                if(eachValue.get(0).equalsIgnoreCase(dayOfMonth) ) {
                    activateAutoPayForId.add(eachKey);
                }
            }
        }
        double sumOfAmount = autoPayDatabase.calculateSumOfAmountForAutoPay(customerId, activateAutoPayForId);
        if(cards.updateDueAmountForAutoPayment(sumOfAmount, "credit")) {
            return true;
        }
        return false;
    }
}