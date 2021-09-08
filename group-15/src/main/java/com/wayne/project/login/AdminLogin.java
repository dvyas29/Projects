package com.wayne.project.login;

import com.wayne.project.database.*;
import com.wayne.project.utility.*;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class AdminLogin implements IAdminLogin {

    private UtilityFactory utilityFactory;
    private DatabaseFactory databaseFactory;

    public AdminLogin() {

        utilityFactory = new UtilityFactoryNormal();
        databaseFactory = new DatabaseFactoryNormal();
    }

    @Override
    public boolean adminLogin(String adminUsername, String adminPassword, String forgotPassword) throws SQLException, FileNotFoundException {

        String credentials[];
        IEmailService emailService = utilityFactory.createEmailObject();
        IAdminOperationsDatabase adminOperationsDatabase = databaseFactory.createAdminOperationsDatabaseObject();
        ILoginDatabase adminLoginDatabase = databaseFactory.createLoginDatabaseObject();
        if(forgotPassword.equalsIgnoreCase("yes")) {
            String adminEmail = adminLoginDatabase.fetchAdminEmail(adminUsername);
            IRandomCharacter randomCharacterObject = utilityFactory.createRandomGeneratorObject(10);
            String randomCharacterString = randomCharacterObject.generateRandomAlphaNumericCharacters();
            emailService.sendEmail(adminEmail,"Password Reset","Password Reset to:" + randomCharacterString);
            adminLoginDatabase.setAdminPassword(adminUsername, randomCharacterString);
        }
        else if(forgotPassword.equalsIgnoreCase("no")) {
            String adminCredentials = adminLoginDatabase.verifyAdminCredentials(adminUsername, adminPassword);
            credentials = adminCredentials.split(" ");
            if (adminUsername.equals(credentials[0]) && adminPassword.equals(credentials[1])) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            System.out.println("Bad Command, Please enter yes or no");
        }
        return true;
    }
}