package com.wayne.project.database;

import com.wayne.project.utility.IFileParser;
import com.wayne.project.utility.UtilityFactory;
import com.wayne.project.utility.UtilityFactoryNormal;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.List;

public class ConnectToDatabase implements IDatabase {

    private String databaseConnectionURL;
    private String databaseConnectionUsername;
    private String databaseConnectionPassword;
    private Connection connectionToMysql;
    private final String localPath = "src/main/java/com/wayne/project/database/databaseCredentialsLocal";
    private final String timberleaConnectionPath = "src/main/java/com/wayne/project/database/databaseCredentialsTimberlea";
    private UtilityFactory utilityFactory = new UtilityFactoryNormal();

    public ConnectToDatabase() {

        try {
            IFileParser databaseHelper = utilityFactory.createFileParserObject();
            BufferedReader stream = new BufferedReader(new FileReader(timberleaConnectionPath));
            List<String> databaseCredentialsList;
            databaseCredentialsList = databaseHelper.loadCredentials(stream);
            databaseConnectionURL = databaseCredentialsList.get(0) + "?serverTimezone=UTC";
            databaseConnectionUsername = databaseCredentialsList.get(1);
            databaseConnectionPassword = databaseCredentialsList.get(2);
        }
        catch (Exception e) {
            System.out.println("Exception Occurred at Constructor of Government Class");
        }
    }

    @Override
    public Connection createDatabaseConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connectionToMysql = DriverManager.getConnection(databaseConnectionURL, databaseConnectionUsername, databaseConnectionPassword);
        }

        catch(SQLException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        return connectionToMysql;
    }

    @Override
    public void closeDatabaseConnection() {

        try {
            connectionToMysql.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
