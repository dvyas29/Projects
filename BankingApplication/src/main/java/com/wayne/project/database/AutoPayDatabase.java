package com.wayne.project.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AutoPayDatabase extends ConnectToDatabase implements IAutoPayDatabase {

    private Connection connectionForAutoPay;
    private Statement statementForAutoPay;
    private ResultSet resultSetForAutoPay;

    public boolean addAutoPayForUtilityBills(String customerId, int dayOfMonth, float amountToPay, String cardType,
                                             String billType, String billRegistrationNumber) throws SQLException {

        String sqlQueryToAddAutoPayForUtilityBill = "INSERT INTO auto_pay " +
                "(customer_id, day_of_month, amount_to_pay, card_type, bill_type, bill_registration_number) " +
                "VALUES ('" + customerId + "', '"+ dayOfMonth + "', '" + amountToPay + "', '" + cardType + "', '" +
                billType + "', '" + billRegistrationNumber + "');";
        connectionForAutoPay = this.createDatabaseConnection();
        statementForAutoPay = connectionForAutoPay.createStatement();
        try {
            int resultOfInsertion = statementForAutoPay.executeUpdate(sqlQueryToAddAutoPayForUtilityBill);
            if(resultOfInsertion > 0) {
                return true;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            statementForAutoPay.close();
            this.closeDatabaseConnection();
        }
        return false;
    }

    public HashMap<String, List<String>> fetchAutoPaymentInformationForCustomer(String customerId) throws SQLException {

        HashMap<String, List<String>> hashMap = new HashMap<String, List<String>>();
        String sqlQueryTofetchAutoPaymentData = "Select id, customer_id, day_of_month, last_triggered_date  " +
                "from auto_pay where customer_id = '" + customerId + "';";
        List<String> temp = new ArrayList<String>();
        connectionForAutoPay = this.createDatabaseConnection();
        statementForAutoPay = connectionForAutoPay.createStatement();
        try {

            resultSetForAutoPay = statementForAutoPay.executeQuery(sqlQueryTofetchAutoPaymentData);
            while(resultSetForAutoPay.next()) {
                String key = resultSetForAutoPay.getString("id");
                temp.add(resultSetForAutoPay.getString("day_of_month"));
                temp.add(resultSetForAutoPay.getString("last_triggered_date"));
                hashMap.put(key, temp);
            }
            if(hashMap.size() == 0) {
                return null;
            }
            else {
                return hashMap;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            resultSetForAutoPay.close();
            statementForAutoPay.close();
            this.closeDatabaseConnection();
        }
        return null;
    }

    public double calculateSumOfAmountForAutoPay(String customerId, List<String> activateAutoPayForId) throws SQLException {

        String sqlQueryToCalculateSum = "Select id, amount_to_pay, customer_id from auto_pay where customer_id " +
                                                "= '" +78459683 + "';";
        connectionForAutoPay = this.createDatabaseConnection();
        statementForAutoPay = connectionForAutoPay.createStatement();
        try {

            resultSetForAutoPay = statementForAutoPay.executeQuery(sqlQueryToCalculateSum);
            double sumOfAmount = 0;
            while(resultSetForAutoPay.next()) {
                if(activateAutoPayForId.contains(resultSetForAutoPay.getString("id"))) {
                    sumOfAmount  += resultSetForAutoPay.getDouble("amount_to_pay");
                }
            }
            return sumOfAmount;
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            resultSetForAutoPay.close();
            statementForAutoPay.close();
            this.closeDatabaseConnection();
        }
        return 0;
    }

}
