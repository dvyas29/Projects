package com.wayne.project.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginDatabase extends ConnectToDatabase implements ILoginDatabase {

    @Override
    public String verifyAdminCredentials(String adminId, String adminPassword) throws SQLException {

        boolean areCredentialsCorrect;
        String credentials = "";
        String sqlQueryToVerifyAdminCredentials = "select * from admin where admin_id = '"+adminId+"' and password = '"+adminPassword+"';";
        Connection connectionForAdminLogin = this.createDatabaseConnection();
        Statement statementForAdminLogin = connectionForAdminLogin.createStatement();
        try {
            areCredentialsCorrect = statementForAdminLogin.execute(sqlQueryToVerifyAdminCredentials);
            ResultSet resultSetForAdminLogin = statementForAdminLogin.executeQuery(sqlQueryToVerifyAdminCredentials);
            while(resultSetForAdminLogin.next()) {
                adminId = resultSetForAdminLogin.getString("admin_id");
                adminPassword = resultSetForAdminLogin.getString("password");
                credentials = credentials + adminId + " " + adminPassword;
            }
            return credentials;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            statementForAdminLogin.close();
            this.closeDatabaseConnection();
        }
        return credentials;
    }

    @Override
    public String verifyCustomerCredentials(String customerId, String customerPassword) throws SQLException {

        String credentials = "";
        String sqlQueryToVerifyCustomerCredentials = "select customer_id, customer_password from customer where customer_id = '"+customerId+"';";
        Connection connectionForCustomerLogin = this.createDatabaseConnection();
        Statement statementForCustomerLogin = connectionForCustomerLogin.createStatement();
        try {
            ResultSet resultSetForCustomerLogin = statementForCustomerLogin.executeQuery(sqlQueryToVerifyCustomerCredentials);
            while (resultSetForCustomerLogin.next()){
                customerId = resultSetForCustomerLogin.getString("customer_id");
                customerPassword = resultSetForCustomerLogin.getString("customer_password");
                credentials = credentials + customerId + " " + customerPassword;
            }
            return credentials;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            statementForCustomerLogin.close();
            this.closeDatabaseConnection();
        }
        return credentials;
    }

    @Override
    public boolean setAdminPassword(String adminId, String adminPassword) throws SQLException {

        String sqlQueryToSetAdminPassword = "UPDATE admin SET password = '"+ adminPassword +
                "' WHERE admin_id = '" + adminId + "';";
        Connection connectionForSettingPasswordForAdmin = this.createDatabaseConnection();
        Statement statementForSettingPasswordForAdmin = connectionForSettingPasswordForAdmin.createStatement();
        try{
            int resultOfUpdateQuery = statementForSettingPasswordForAdmin.executeUpdate(sqlQueryToSetAdminPassword);
            if(resultOfUpdateQuery > 0) {
                return true;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            statementForSettingPasswordForAdmin.close();
            this.closeDatabaseConnection();
        }
        return false;
    }

    @Override
    public String fetchAdminEmail(String adminId) throws SQLException {

        String sqlQueryToFetchAdminEmail = "select * from admin;";
        Connection connectionToFetchAdminEmail = this.createDatabaseConnection();
        Statement statementToFetchAdminEmail = connectionToFetchAdminEmail.createStatement();
        ResultSet resultToFetchAdminEmail = null;
        String emailId = null;
        try{
            resultToFetchAdminEmail = statementToFetchAdminEmail.executeQuery(sqlQueryToFetchAdminEmail);
            while(resultToFetchAdminEmail.next()) {
                emailId = resultToFetchAdminEmail.getString("admin_email");
            }
            return emailId;
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            resultToFetchAdminEmail.close();
            statementToFetchAdminEmail.close();
            this.closeDatabaseConnection();
        }
        return null;
    }
}
