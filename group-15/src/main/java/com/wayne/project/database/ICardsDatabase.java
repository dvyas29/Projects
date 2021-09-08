package com.wayne.project.database;

import java.sql.SQLException;

public interface ICardsDatabase {

    public String fetchCustomerAccountNumber(String customerId) throws SQLException;

    public String displayStatusForCards(String accountNumber, String typeOfCard) throws SQLException;

    public String fetchCardModelOfActiveCard(String accountNumber, String typeOfCard) throws SQLException;

    public boolean upgradeExistingCard(String accountNumber, String typeOfCardToUpgrade, String cardType) throws SQLException ;

    public int fetchRedeemCardPoints(String accountNumber, String typeOfCard) throws SQLException;

    public boolean setRedeemCardPointsToZero(String accountNumber, String typeOfCard) throws SQLException;

    public boolean updateCardAsBlocked(String accountNumber, String typeOfCard) throws SQLException;

    public boolean storePinCode(String accountNumber, int hashOfPinCode, String typeOfCard) throws SQLException;

    public boolean setUsageLimit(String accountNumber, int onlineUsageLimit, String typeOfCard) throws SQLException;

    public boolean updateDueAmountForCard(String accountNumber, double dueAmount, String typeOfCard) throws SQLException;
}
