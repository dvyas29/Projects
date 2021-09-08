package com.wayne.project.offers;

import com.wayne.project.database.DatabaseFactory;
import com.wayne.project.database.DatabaseFactoryNormal;
import com.wayne.project.database.IOffersDatabase;

import java.sql.SQLException;

public class CreditCardOffers implements ICreditCardOffers{
    IOffersDatabase creditCardOfferDatabase;
    DatabaseFactory databaseFactory;

    CreditCardOffers(){
        databaseFactory = new DatabaseFactoryNormal();
        creditCardOfferDatabase = databaseFactory.createOffersDatabase();
    }

    @Override
    public void showCreditCardOffers() throws SQLException {
        System.out.println(creditCardOfferDatabase.getCreditCardOffers());
    }
}
