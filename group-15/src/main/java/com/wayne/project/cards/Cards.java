package com.wayne.project.cards;

import com.wayne.project.database.*;
import java.sql.SQLException;

public class Cards implements ICards {

    private String accountNumber;
    private String customerId;
    private ICardsDatabase cardsDatabase;
    private IAccountSummaryDatabase accountSummaryDatabase;
    private DatabaseFactory databaseFactory;

    public Cards(String customerId) throws SQLException {

        this.customerId = customerId;
        databaseFactory = new DatabaseFactoryNormal();
        cardsDatabase = databaseFactory.createCardsDatabaseObject();
        accountSummaryDatabase = databaseFactory.createAccountSummaryDatabase();
        this.accountNumber = cardsDatabase.fetchCustomerAccountNumber(customerId);
    }

    @Override
    public String displayStatusOfCard(String typeOfCard) throws SQLException {

        String cardStatusConfig = cardsDatabase.displayStatusForCards(accountNumber, typeOfCard);
        return cardStatusConfig;
    }

    @Override
    public String getUpgradeCardStatus(String typeOfCard) throws SQLException {

        String activeTypeOfCard = cardsDatabase.fetchCardModelOfActiveCard(accountNumber, typeOfCard);
        return activeTypeOfCard;
    }

    @Override
    public boolean upgradeCard(String typeOfCardToUpgrade, String typeOfCard) throws SQLException {

        float deductAmount = 0;
        double accountBalance = accountSummaryDatabase.getAccountBalance(customerId);
        if(accountBalance < 400) {
            return false;
        }
        if(typeOfCardToUpgrade.equalsIgnoreCase("gold")) {
            deductAmount = 10;
        }
        if(typeOfCardToUpgrade.equalsIgnoreCase("platinum")) {
            deductAmount = 20;
        }
        accountSummaryDatabase.deductAmountFromAccount(accountNumber, accountBalance, deductAmount);
        boolean isCardUpgraded = cardsDatabase.upgradeExistingCard(accountNumber, typeOfCardToUpgrade, typeOfCard);
        return isCardUpgraded;
    }

    @Override
    public boolean blockCard(String typeOfCard) throws SQLException {

        boolean isBlockingSuccessful = cardsDatabase.updateCardAsBlocked(accountNumber, typeOfCard);
        return isBlockingSuccessful;
    }

    @Override
    public boolean generatePinCode( String pinCode, String typeOfCard) throws SQLException {

        int hashOfPinCode = pinCode.hashCode();
        boolean isPinGenerationSuccessful = cardsDatabase.storePinCode(accountNumber, hashOfPinCode, typeOfCard);
        return isPinGenerationSuccessful;
    }

    @Override
    public boolean updateDueAmountForAutoPayment(double dueAmount, String typeOfCard) throws SQLException {

        boolean resultOfUpdatingDueAmount = cardsDatabase.updateDueAmountForCard(accountNumber, dueAmount, typeOfCard);
        return resultOfUpdatingDueAmount;
    }
}
