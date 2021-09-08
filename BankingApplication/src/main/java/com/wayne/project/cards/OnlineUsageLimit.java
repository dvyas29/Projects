package com.wayne.project.cards;

import com.wayne.project.database.DatabaseFactory;
import com.wayne.project.database.DatabaseFactoryNormal;
import com.wayne.project.database.IAccountSummaryDatabase;
import com.wayne.project.database.ICardsDatabase;
import java.sql.SQLException;

public class OnlineUsageLimit implements IOnlineUsageLimit {

    private String accountNumber;
    private String customerId;
    private ICardsDatabase cardsDatabase;
    private IAccountSummaryDatabase accountSummaryDatabase;
    private DatabaseFactory databaseFactory;

    public OnlineUsageLimit(String customerId) throws SQLException {

        this.customerId = customerId;
        databaseFactory = new DatabaseFactoryNormal();
        cardsDatabase = databaseFactory.createCardsDatabaseObject();
        accountSummaryDatabase = databaseFactory.createAccountSummaryDatabase();
        this.accountNumber = cardsDatabase.fetchCustomerAccountNumber(customerId);
    }

    @Override
    public boolean setOnlineUsageLimit(int onlineUsageLimit, String typeOfCard) throws SQLException {

        if (onlineUsageLimit > 400 || onlineUsageLimit < 100) {
            return false;
        }
        boolean isOnlineUsageLimitSet = cardsDatabase.setUsageLimit(accountNumber, onlineUsageLimit, typeOfCard);
        return isOnlineUsageLimitSet;
    }
}
