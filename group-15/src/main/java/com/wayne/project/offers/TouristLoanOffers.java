package com.wayne.project.offers;

import com.wayne.project.database.DatabaseFactory;
import com.wayne.project.database.DatabaseFactoryNormal;
import com.wayne.project.database.IOffersDatabase;

import java.sql.SQLException;

public class TouristLoanOffers implements ITouristLoanOffers{
    IOffersDatabase touristLoanOffers;
    DatabaseFactory databaseFactory;

    TouristLoanOffers(){
        databaseFactory = new DatabaseFactoryNormal();
        touristLoanOffers = databaseFactory.createOffersDatabase();
    }
    @Override
    public void showTouristLoanOffers() throws SQLException {
        System.out.println(touristLoanOffers.getTouristLoanOffers());
    }
}
