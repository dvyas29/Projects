package com.wayne.project.console;

import com.wayne.project.contactInformation.ContactInformationFactory;
import com.wayne.project.contactInformation.ContactInformationFactoryNormal;
import com.wayne.project.contactInformation.IContactInformation;
import com.wayne.project.utility.IValidations;
import com.wayne.project.utility.UtilityFactory;
import com.wayne.project.utility.UtilityFactoryNormal;
import java.sql.SQLException;
import java.util.Scanner;

public class ConsoleForContactInformation implements IConsoleForContactInformation {

    Scanner userInput;
    String commandEntered = null;
    private String customerId;
    private ContactInformationFactory contactInformationFactory;
    private UtilityFactory utilityFactory;
    private IContactInformation contactInformation;
    private IValidations validations;
    private ConsoleFactory consoleFactory;

    public ConsoleForContactInformation(String customerId) {

        this.customerId = customerId;
        contactInformationFactory = new ContactInformationFactoryNormal();
        consoleFactory = new ConsoleFactoryNormal();
        utilityFactory = new UtilityFactoryNormal();
        userInput = consoleFactory.createScannerInstance();
        validations = utilityFactory.createValidationsObject();
        contactInformation = contactInformationFactory.createContactInformationObject();
    }

    @Override
    public void contactInformation() throws SQLException {

        do {
            System.out.println("Contact Information");
            System.out.println("1. Bank contact details");
            System.out.println("2. Submit a Query");
            System.out.println("quit");
            System.out.println("Select an option:");
            commandEntered = userInput.next();
            if (commandEntered.equals("1")) {
                String bankContactInformation;
                String[] values;
                bankContactInformation = contactInformation.getContactInformation();
                values = bankContactInformation.split(" ");
                System.out.println(values[0] + "\t");
                System.out.println(values[1] + "\t");
                System.out.println(values[2] + "\t");
            }
            else if (commandEntered.equals("2")) {
                submitQuery(customerId);
            }
            else if (commandEntered.equals("quit")) {
                System.out.println("Exiting the Application...");
                return;
            }
            else {
                System.out.println("Bad command");
            }
        } while(true);
    }

    @Override
    public void submitQuery(String customerId) throws SQLException {

        System.out.print("Enter your name:");
        String name = userInput.next();
        System.out.print("Enter customer id:");
        customerId = userInput.next();
        System.out.print("Enter your email:");
        String email = userInput.next();
        validations.validateEmail(email);
        System.out.print("Enter your Query:");
        String query = userInput.next();
        contactInformation.sendQueryInformation(name,customerId,email,query);
        System.out.println("Your Query has been registered...Please check back for the response. Thank you!");
    }
}
