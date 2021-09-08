package com.wayne.project.database;

import java.sql.*;

public class TestDatabase extends ConnectToDatabase {
    private Connection connectionForCards;
    private Statement statementForCards;
    private ResultSet resultSetForCards;


    public void getRandomInformation() throws SQLException {

        connectionForCards = this.createDatabaseConnection();
        String sqlQuery = "select * from account_information;";
        statementForCards = connectionForCards.createStatement();
        try{
            resultSetForCards = statementForCards.executeQuery(sqlQuery);
            while(resultSetForCards.next()) {
                System.out.println("account number:"+ resultSetForCards.getString("account_number"));
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            resultSetForCards.close();
            statementForCards.close();
            this.closeDatabaseConnection();
        }
    }

    public void getCustomerInformation() throws SQLException {

        connectionForCards = this.createDatabaseConnection();
        String sqlQuery = "select * from customer;";
        statementForCards = connectionForCards.createStatement();
        try{
            resultSetForCards = statementForCards.executeQuery(sqlQuery);
            while(resultSetForCards.next()) {
                System.out.println(resultSetForCards.getString("customer_id"));
                System.out.println(resultSetForCards.getString("customer_name"));
                System.out.println(resultSetForCards.getString("customer_password"));
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            resultSetForCards.close();
            statementForCards.close();
            this.closeDatabaseConnection();
        }
    }

    public static void main(String[] args) {

        TestDatabase obj1 = new TestDatabase();
        try {
            obj1.getRandomInformation();
            obj1.getCustomerInformation();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
