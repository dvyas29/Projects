package com.wayne.project.offers;

import com.wayne.project.database.DatabaseFactory;
import com.wayne.project.database.DatabaseFactoryNormal;
import com.wayne.project.database.IOffersDatabase;

import java.sql.SQLException;

public class AutomotiveLoanOffers implements IAutomotiveLoanOffers{
    IOffersDatabase automotiveLoanOffers;
    DatabaseFactory databaseFactory;

    AutomotiveLoanOffers(){
        databaseFactory = new DatabaseFactoryNormal();
        automotiveLoanOffers = databaseFactory.createOffersDatabase();
    }
    @Override
    public void showAutomotiveLoanOffers() throws SQLException {
        System.out.println(automotiveLoanOffers.getAutomotiveLoanOffers());
    }
}
