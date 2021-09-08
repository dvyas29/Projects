package com.wayne.project.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CardsDatabase extends ConnectToDatabase implements ICardsDatabase {
    private Connection connectionForCards;
    private Statement statementForCards;
    private ResultSet resultSetForCards;
    private String customerId;

    @Override
    public String fetchCustomerAccountNumber(String customerId) throws SQLException {
        String accountNumber = "";
        this.customerId = customerId;
        String sqlQueryToFetchCustomerId = "Select Account_number from account_information where customer_id = '"+customerId+"';";
        connectionForCards = this.createDatabaseConnection();
        statementForCards = connectionForCards.createStatement();
        try{
            resultSetForCards = statementForCards.executeQuery(sqlQueryToFetchCustomerId);
            while(resultSetForCards.next()) {
                accountNumber = resultSetForCards.getString("account_number");
            }
            return accountNumber;
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            resultSetForCards.close();
            statementForCards.close();
            this.closeDatabaseConnection();
        }
        return null;
    }

    @Override
    public String displayStatusForCards(String accountNumber, String typeOfCard) throws SQLException {

        List<String> cardNumber = new ArrayList<String>();
        List<String> cardModel = new ArrayList<String>();
        List<String> cardLimit = new ArrayList<String>();
        List<String> cardStatus = new ArrayList<String>();
        List<String> cardExpiryDate = new ArrayList<String>();

        StringBuilder debitCardsInformation = new StringBuilder();

        connectionForCards = this.createDatabaseConnection();
        String sqlQueryToFetchDebitCardStatus = "select card_number, card_type, card_model, " +
                "card_limit, card_status, card_expiry_date from cards where account_number = '"+accountNumber+"'" +
                " and card_type = '"+typeOfCard+"';";
        statementForCards = connectionForCards.createStatement();
        try{
            resultSetForCards = statementForCards.executeQuery(sqlQueryToFetchDebitCardStatus);
            while(resultSetForCards.next()) {
                cardNumber.add(resultSetForCards.getString("card_number"));
                cardModel.add(resultSetForCards.getString("card_model"));
                cardLimit.add(resultSetForCards.getString("card_limit"));
                cardStatus.add(resultSetForCards.getString("card_status"));
                cardExpiryDate.add(resultSetForCards.getString("card_expiry_date"));
            }
            for(int i=0;i<cardNumber.size();i++) {
                debitCardsInformation.append("Card Number:").append(cardNumber.get(i)).append("\t");
                debitCardsInformation.append("Card Model:").append(cardModel.get(i)).append("\t");
                debitCardsInformation.append("Card Limit:").append(cardLimit.get(i)).append("\t");
                debitCardsInformation.append("Card Status:").append(cardStatus.get(i)).append("\t");
                debitCardsInformation.append("Card Expiry Date:").append(cardExpiryDate.get(i)).append("\n");
            }
            return debitCardsInformation.toString();

        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            resultSetForCards.close();
            statementForCards.close();
            this.closeDatabaseConnection();
        }
        return null;
    }

    @Override
    public String fetchCardModelOfActiveCard(String accountNumber, String typeOfCard) throws SQLException {

        String cardType = null;
        connectionForCards = this.createDatabaseConnection();
        String sqlQueryToFetchDebitCardStatus = "select card_number, card_type, card_model, " +
                "card_limit, card_status, card_expiry_date from cards where account_number = '"+accountNumber+"'" +
                " and card_type = '"+typeOfCard+"' and card_status='Active'"+";";
        statementForCards = connectionForCards.createStatement();
        try {
            resultSetForCards = statementForCards.executeQuery(sqlQueryToFetchDebitCardStatus);
            while(resultSetForCards.next()) {
                cardType = resultSetForCards.getString("card_model");
            }
            return cardType;

        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            resultSetForCards.close();
            statementForCards.close();
            this.closeDatabaseConnection();
        }
        return null;
    }

    public boolean upgradeExistingCard(String accountNumber, String typeOfCardToUpgrade, String cardType) throws SQLException {

        Boolean isUpgradeSuccessful;
        connectionForCards = this.createDatabaseConnection();
        String sqlQueryToFetchDebitCardStatus = "UPDATE cards SET card_model = '"+typeOfCardToUpgrade+"' " +
                "WHERE account_number = '"+accountNumber+"' and card_type = '"+cardType+"' and card_status = 'Active';";
        statementForCards = connectionForCards.createStatement();
        try {
            isUpgradeSuccessful = statementForCards.execute(sqlQueryToFetchDebitCardStatus);
            return isUpgradeSuccessful;

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

        int redeemCardPoints = 0;
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

    @Override
    public boolean updateCardAsBlocked(String accountNumber, String typeOfCard) throws SQLException {

        connectionForCards = this.createDatabaseConnection();
        String sqlUpdateQueryToBlockCard = "UPDATE cards SET card_status = 'Blocked' " +
                "WHERE account_number = '"+accountNumber+"' and card_type = '"+typeOfCard+"' and card_status = 'Active';";
        statementForCards = connectionForCards.createStatement();
        try{
            int resultOfUpdateQuery = statementForCards.executeUpdate(sqlUpdateQueryToBlockCard);
            if(resultOfUpdateQuery > 0) {
                return true;
            }
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

    @Override
    public boolean storePinCode(String accountNumber, int hashOfPinCode, String typeOfCard) throws SQLException {

        connectionForCards = this.createDatabaseConnection();
        String sqlUpdateQueryForPinCode = "UPDATE cards SET pin_code = '"+hashOfPinCode+"' " +
                "WHERE account_number = '"+accountNumber+"' and card_type = '"+typeOfCard+"' and card_status = 'Active';";
        statementForCards = connectionForCards.createStatement();
        try {
            int resultOfUpdateQuery = statementForCards.executeUpdate(sqlUpdateQueryForPinCode);
            if(resultOfUpdateQuery > 0) {
                return true;
            }
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
            resultSetForCards.close();
            statementForCards.close();
            this.closeDatabaseConnection();
        }
        return false;
    }

    public boolean updateDueAmountForCard(String accountNumber, double dueAmount, String typeOfCard) throws SQLException {

        connectionForCards = this.createDatabaseConnection();
        String sqlUpdateQueryToSetOnlineUsageLimit = "UPDATE cards SET due_amount = " + dueAmount +
                " WHERE account_number = '" + accountNumber + "' and card_type = '" + typeOfCard + "' and " +
                "card_status = 'Active';";
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
            resultSetForCards.close();
            statementForCards.close();
            this.closeDatabaseConnection();
        }
        return false;
    }

}