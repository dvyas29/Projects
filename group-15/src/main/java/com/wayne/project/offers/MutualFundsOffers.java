package com.wayne.project.offers;

import com.wayne.project.database.DatabaseFactory;
import com.wayne.project.database.DatabaseFactoryNormal;
import com.wayne.project.database.IOffersDatabase;

import java.sql.SQLException;

public class MutualFundsOffers implements IMutualFundsOffers{
    IOffersDatabase mutualFundsOfferDatabase;
    DatabaseFactory databaseFactory;

    MutualFundsOffers(){
        databaseFactory = new DatabaseFactoryNormal();
        mutualFundsOfferDatabase = databaseFactory.createOffersDatabase();
    }
    @Override
    public void showMutualFundsOffers() throws SQLException {
        System.out.println(mutualFundsOfferDatabase.getMutualFundsOffers());
    }
}
