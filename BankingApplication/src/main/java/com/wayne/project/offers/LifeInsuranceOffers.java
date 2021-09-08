package com.wayne.project.offers;

import com.wayne.project.database.DatabaseFactory;
import com.wayne.project.database.DatabaseFactoryNormal;
import com.wayne.project.database.IOffersDatabase;

import java.sql.SQLException;

public class LifeInsuranceOffers implements ILifeInsuranceOffers{
    IOffersDatabase lifeInsuranceOffers;
    DatabaseFactory databaseFactory;

    LifeInsuranceOffers(){
        databaseFactory = new DatabaseFactoryNormal();
        lifeInsuranceOffers = databaseFactory.createOffersDatabase();
    }
    @Override
    public void showLifeInsuranceOffers() throws SQLException {
        System.out.println(lifeInsuranceOffers.geLifeInsuranceOffers());
    }
}
