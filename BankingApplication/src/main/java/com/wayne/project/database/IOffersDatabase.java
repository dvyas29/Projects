package com.wayne.project.database;

import java.sql.SQLException;

public interface IOffersDatabase {
    public String getCreditCardOffers() throws SQLException;
    public String getLoanOffers() throws SQLException;
    public String getMutualFundsOffers() throws SQLException;
    public String geLifeInsuranceOffers() throws SQLException;
    public String getAutomotiveLoanOffers() throws SQLException;
    public String getTouristLoanOffers() throws SQLException;

}
