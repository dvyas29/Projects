package com.wayne.project.login;

import com.wayne.project.database.*;
import com.wayne.project.utility.IEmailService;
import com.wayne.project.utility.IRandomCharacter;
import com.wayne.project.utility.UtilityFactory;
import com.wayne.project.utility.UtilityFactoryNormal;
import java.io.FileNotFoundException;
import java.sql.SQLException;

public class CustomerLogin implements ICustomerLogin {

    private UtilityFactory utilityFactory = new UtilityFactoryNormal();
    private DatabaseFactory databaseFactory = new DatabaseFactoryNormal();
    private IAdminOperationsDatabase adminOperationsDatabase = databaseFactory.createAdminOperationsDatabaseObject();
    private ILoginDatabase loginDatabase = databaseFactory.createLoginDatabaseObject();

    @Override
    public boolean customerLogin(String username, String password, String forgotPassword) throws SQLException, FileNotFoundException {

        String credentials[];
        IEmailService emailService = utilityFactory.createEmailObject();

            if(forgotPassword.equalsIgnoreCase("yes")) {
                String email = adminOperationsDatabase.fetchEmail(username);
                IRandomCharacter randomCharacterObject = utilityFactory.createRandomGeneratorObject(10);
                String randomCharacterString = randomCharacterObject.generateRandomAlphaNumericCharacters();
                emailService.sendEmail(email,"Password Reset","Password Reset to:" + randomCharacterString);
                return false;
            }
            else if(forgotPassword.equalsIgnoreCase("no")) {
                String Credentials = loginDatabase.verifyCustomerCredentials(username, password);
                credentials = Credentials.split(" ");
                if (username.equals(credentials[0]) && password.equals(credentials[1])) {
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
