package com.wayne.project.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDetailsDatabase extends ConnectToDatabase implements ICustomerDetailsDatabase {

    private Connection connectionForCustomerDetails;
    private Statement statementForCustomerDetails;

    @Override
    public boolean updateAddress(String customerId, String address) throws SQLException {
        try {
            connectionForCustomerDetails = this.createDatabaseConnection();
            statementForCustomerDetails = connectionForCustomerDetails.createStatement();
            String updateCustomerAddressQuery = "UPDATE customer SET address ='"+address+"' WHERE customer_id ='"+customerId+"';";
            boolean isAddressUpdated = statementForCustomerDetails.execute(updateCustomerAddressQuery);
            return isAddressUpdated;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            statementForCustomerDetails.close();
            this.closeDatabaseConnection();
        }
        return false;
    }

    @Override
    public boolean updateSSN(String customerId, double SSN) throws SQLException {

        try {
            connectionForCustomerDetails = this.createDatabaseConnection();
            statementForCustomerDetails = connectionForCustomerDetails.createStatement();
            String updateCustomerSSNQuery = "UPDATE customer SET customer_ssn ='"+ SSN +"' WHERE customer_id ='"+customerId+";";
            boolean isSSNUpdated = statementForCustomerDetails.execute(updateCustomerSSNQuery);
            return isSSNUpdated;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            statementForCustomerDetails.close();
            this.closeDatabaseConnection();
        }
        return false;
    }

    @Override
    public boolean updateEmail(String customerId, String email) throws SQLException {
        try {
            connectionForCustomerDetails = this.createDatabaseConnection();
            statementForCustomerDetails = connectionForCustomerDetails.createStatement();
            String updateCustomerEmailQuery = "UPDATE customer SET customer_email ='"+email+"' WHERE customer_id ='"+customerId+"';";
            boolean isEmailUpdated = statementForCustomerDetails.execute(updateCustomerEmailQuery);
            return isEmailUpdated;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {
            statementForCustomerDetails.close();
            this.closeDatabaseConnection();
        }
        return false;
    }
}
