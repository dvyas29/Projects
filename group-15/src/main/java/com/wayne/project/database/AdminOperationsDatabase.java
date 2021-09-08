package com.wayne.project.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminOperationsDatabase extends ConnectToDatabase implements IAdminOperationsDatabase {

    private Connection connectionForAdminOperations;
    private Statement statementForAdminOperations;
    private ResultSet resultSetForAdminOperations;

    @Override
    public boolean persistCustomer(String customerUserName, String customerName, String customerPassword,String email,String contact,String address,String ssn) throws SQLException {
        String sqlQueryToCreateCustomer = "Insert into customer (customer_id,customer_name,customer_password,customer_email,contact,address,customer_ssn)" +
                "values ('"+ customerUserName + "','" + customerName + "','" + customerPassword + "','" + email + "','" + contact + "','" + address +"','"+ssn+"');";
        connectionForAdminOperations = this.createDatabaseConnection();
        statementForAdminOperations = connectionForAdminOperations.createStatement();
        try {
            int statements = statementForAdminOperations.executeUpdate(sqlQueryToCreateCustomer);
            if (statements > 0) {
                return true;
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            statementForAdminOperations.close();
            this.closeDatabaseConnection();
        }
        return true;
    }

    public boolean deleteAccount(String customerId) throws SQLException {

        String sqlQueryToDeleteAccount = "Delete from customer where customer_id = '"+customerId+"';";
        connectionForAdminOperations = this.createDatabaseConnection();
        statementForAdminOperations = connectionForAdminOperations.createStatement();
        try {
            int deletedRows = statementForAdminOperations.executeUpdate(sqlQueryToDeleteAccount);
            if (deletedRows > 0) {
                return true;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            statementForAdminOperations.close();
            this.closeDatabaseConnection();
        }
        return true;
    }

    @Override
    public boolean updateCustomerQueryStatus(String customerId) throws SQLException {

        String sqlQueryToUpdateCustomerQuery = "Update contactus_query set message = 'status updated', query_status = 'Resolved' where customer_id = '"+customerId+"';";
        connectionForAdminOperations = this.createDatabaseConnection();
        statementForAdminOperations = connectionForAdminOperations.createStatement();
        try {
            int updatedRows = statementForAdminOperations.executeUpdate(sqlQueryToUpdateCustomerQuery);
            if (updatedRows > 0) {
                return true;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            statementForAdminOperations.close();
            this.closeDatabaseConnection();
        }
        return true;
    }

    @Override
    public String fetchEmail(String customerId) throws SQLException {

        String email = null;
        String sqlQueryToFetchEmail = "select customer_email from customer where customer_id = '"+customerId+"';";
        connectionForAdminOperations = this.createDatabaseConnection();
        statementForAdminOperations = connectionForAdminOperations.createStatement();
        try {
            resultSetForAdminOperations = statementForAdminOperations.executeQuery(sqlQueryToFetchEmail);
            while (resultSetForAdminOperations.next()) {
                email = resultSetForAdminOperations.getString("customer_email");
            }
            return email;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            resultSetForAdminOperations.close();
            statementForAdminOperations.close();
            this.closeDatabaseConnection();
        }
        return email;
    }
}
