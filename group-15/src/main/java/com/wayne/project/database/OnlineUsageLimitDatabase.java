package com.wayne.project.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class OnlineUsageLimitDatabase extends ConnectToDatabase implements IOnlineUsageLimitDatabase {

    private Connection connectionForCards;
    private Statement statementForCards;

    @Override
    public boolean setUsageLimit(String accountNumber, int onlineUsageLimit, String typeOfCard) throws SQLException {

        connectionForCards = this.createDatabaseConnection();
        String sqlUpdateQueryToSetOnlineUsageLimit = "UPDATE cards SET online_usage_limit = '"+onlineUsageLimit+"' " +
                "WHERE account_number = '"+accountNumber+"' and card_type = '"+typeOfCard+"' and card_status = 'Active';";
        statementForCards = connectionForCards.createStatement();
        try{
            int resultOfUpdateQuery = statementForCards.executeUpdate(sqlUpdateQueryToSetOnlineUsageLimit);
            if(resultOfUpdateQuery > 0) {
                return true;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            statementForCards.close();
            this.closeDatabaseConnection();
        }
        return false;
    }
}
