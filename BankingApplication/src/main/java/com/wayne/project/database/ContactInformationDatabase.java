package com.wayne.project.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class ContactInformationDatabase extends ConnectToDatabase implements IContactInformationDatabase {

    private Connection connectionForContactInformation;
    private Statement statementForContactInformation;
    private ResultSet resultSetForContactInformation;

    @Override
    public String loadContactInformation() throws SQLException {

        String details = "";
        String SqlQueryToLoadContactInformation = "select bank_name,bank_email,contact_number from contact_us;";
        connectionForContactInformation = this.createDatabaseConnection();
        statementForContactInformation = connectionForContactInformation.createStatement();
        try{
            resultSetForContactInformation = statementForContactInformation.executeQuery(SqlQueryToLoadContactInformation);
            while (resultSetForContactInformation.next()) {
                String bankName = resultSetForContactInformation.getString("bank_name");
                String email = resultSetForContactInformation.getString("bank_email");
                String phone = resultSetForContactInformation.getString("contact_number");
                details = details + bankName + " " + email + " " + phone;
                return details;
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            resultSetForContactInformation.close();
            statementForContactInformation.close();
            this.closeDatabaseConnection();
        }
        return details;
    }

    @Override
    public boolean saveContactQuery(String customerName, String customerId, String email, String query) throws SQLException {

        String query_status = "open";
        String message = "under processing";
        String SqlQueryToSaveContactQuery = "insert into contactus_query (customer_id,customer_name,email,contact_query,query_status,message) " +
                "values ('"+customerId+"', '"+customerName+"','"+email+"','"+query+"','"+query_status+"', '"+message+"');";
        connectionForContactInformation = this.createDatabaseConnection();
        statementForContactInformation = connectionForContactInformation.createStatement();
        try {
            int isQueryExecuted = statementForContactInformation.executeUpdate(SqlQueryToSaveContactQuery);
            if (isQueryExecuted > 0) {
                return true;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            statementForContactInformation.close();
            this.closeDatabaseConnection();
        }
        return true;
    }

    @Override
    public String fetchContactQueries() throws SQLException {

        String queries = "";
        String sqlQueryToFetchQueries = "Select * from contactus_query;";
        connectionForContactInformation = this.createDatabaseConnection();
        statementForContactInformation = connectionForContactInformation.createStatement();
        try {
            resultSetForContactInformation = statementForContactInformation.executeQuery(sqlQueryToFetchQueries);
            while (resultSetForContactInformation.next()) {
                String customerId = resultSetForContactInformation.getString("customer_id");
                String customerName = resultSetForContactInformation.getString("customer_name");
                String email = resultSetForContactInformation.getString("email");
                String query = resultSetForContactInformation.getString("contact_query");
                String status = resultSetForContactInformation.getString("query_status");
                String message = resultSetForContactInformation.getString("message");
                queries = queries + customerName + " " + customerId + " " + email + " " + query + " " + message + " " + status + "\n";
            }
            return queries;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            resultSetForContactInformation.close();
            statementForContactInformation.close();
            this.closeDatabaseConnection();
        }
        return queries;
    }
}

