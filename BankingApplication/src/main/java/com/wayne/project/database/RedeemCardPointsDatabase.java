package com.wayne.project.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RedeemCardPointsDatabase extends ConnectToDatabase implements IRedeemCardPointsDatabase {

    private Connection connectionForCards;
    private Statement statementForCards;
    private ResultSet resultSetForCards;

    @Override
    public int fetchRedeemCardPoints(String accountNumber, String typeOfCard) throws SQLException {

        int redeemCardPoints = 0;
        connectionForCards = this.createDatabaseConnection();
        String sqlQueryToFetchRedeemCardPoints = "select redeem_card_points from cards " +
                "where account_number = '"+accountNumber+"' and card_type = '"+typeOfCard+"' and card_status = 'Active';";
        statementForCards = connectionForCards.createStatement();
        try {
            resultSetForCards = statementForCards.executeQuery(sqlQueryToFetchRedeemCardPoints);
            while(resultSetForCards.next()) {
                redeemCardPoints = resultSetForCards.getInt("redeem_card_points");
            }
            return redeemCardPoints;

        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            resultSetForCards.close();
            statementForCards.close();
            this.closeDatabaseConnection();
        }
        return 0;
    }

    @Override
    public boolean setRedeemCardPointsToZero(String accountNumber, String typeOfCard) throws SQLException {

        connectionForCards = this.createDatabaseConnection();
        String sqlQueryToSetRedeemCardPointsToZero = "UPDATE cards SET redeem_card_points = 0 " +
                "WHERE account_number = '"+accountNumber+"' and card_type = '"+typeOfCard+"' and card_status = 'Active';";
        statementForCards = connectionForCards.createStatement();
        try {
            boolean isRedeemCardPointsSetToZero = statementForCards.execute(sqlQueryToSetRedeemCardPointsToZero);
            return isRedeemCardPointsSetToZero;

        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            resultSetForCards.close();
            statementForCards.close();
            this.closeDatabaseConnection();
        }
        return false;
    }

}
