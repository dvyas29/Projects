package com.wayne.project.cards;

import com.wayne.project.database.DatabaseFactory;
import com.wayne.project.database.DatabaseFactoryNormal;
import com.wayne.project.database.IAccountSummaryDatabase;
import com.wayne.project.database.ICardsDatabase;
import java.sql.SQLException;

public class RedeemCardPoints implements IRedeemCardPoints {

    private String accountNumber;
    private String customerId;
    private ICardsDatabase cardsDatabase;
    private IAccountSummaryDatabase accountSummaryDatabase;
    private DatabaseFactory databaseFactory;

    public RedeemCardPoints(String customerId) throws SQLException {

        this.customerId = customerId;
        databaseFactory = new DatabaseFactoryNormal();
        cardsDatabase = databaseFactory.createCardsDatabaseObject();
        accountSummaryDatabase = databaseFactory.createAccountSummaryDatabase();
        this.accountNumber = cardsDatabase.fetchCustomerAccountNumber(customerId);
    }

    @Override
    public int fetchRedeemCardPoints(String typeOfCard) throws SQLException {

        int redeemCardPoints = cardsDatabase.fetchRedeemCardPoints(accountNumber, typeOfCard);
        return redeemCardPoints;
    }

    @Override
    public String redeemCardPoints(String typeOfCard, int redeemCardPoints) throws SQLException {

        StringBuilder resultString = new StringBuilder();
        float addAmountToAccount;
        double accountBalance = accountSummaryDatabase.getAccountBalance(customerId);
        if(redeemCardPoints < 1000) {
            resultString.append("The redeem card points need to be at least 1000 points to redeem");
        }
        else {
            addAmountToAccount = (float)redeemCardPoints / 10;
            boolean isAmountAdded = accountSummaryDatabase.addAmountToAccount(accountNumber,accountBalance, addAmountToAccount);
            if(isAmountAdded) {
                resultString.append("The Redeem Points have been successfully added to your account.");
                cardsDatabase.setRedeemCardPointsToZero(accountNumber, typeOfCard);
                resultString.append("The Redeem card points have been set to zero.");
            }
        }
        return resultString.toString();
    }
}
