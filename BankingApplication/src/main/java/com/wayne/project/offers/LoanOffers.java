package com.wayne.project.offers;

import com.wayne.project.database.DatabaseFactory;
import com.wayne.project.database.DatabaseFactoryNormal;
import com.wayne.project.database.IOffersDatabase;

import java.sql.SQLException;

public class LoanOffers implements  ILoanOffers{
    IOffersDatabase loanOffersDatabase;
    DatabaseFactory databaseFactory;

    LoanOffers(){
        databaseFactory = new DatabaseFactoryNormal();
        loanOffersDatabase = databaseFactory.createOffersDatabase();
    }
    @Override
    public void showLoanOffers() throws SQLException {

        System.out.println(loanOffersDatabase.getLoanOffers());
    }
}
