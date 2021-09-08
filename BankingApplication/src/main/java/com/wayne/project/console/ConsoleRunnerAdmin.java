package com.wayne.project.console;

import com.wayne.project.adminOperations.AdminOperationsFactory;
import com.wayne.project.adminOperations.AdminOperationsFactoryNormal;
import com.wayne.project.adminOperations.IAdminOperations;
import com.wayne.project.database.DatabaseFactory;
import com.wayne.project.database.DatabaseFactoryNormal;
import com.wayne.project.database.IContactInformationDatabase;
import com.wayne.project.login.IAdminLogin;
import com.wayne.project.login.LoginFactory;
import com.wayne.project.login.LoginFactoryNormal;
import com.wayne.project.utility.IValidations;
import com.wayne.project.utility.UtilityFactory;
import com.wayne.project.utility.UtilityFactoryNormal;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

/* Console runner for Administrator */
public class ConsoleRunnerAdmin implements IConsoleRunnerAdmin{

	private String commandEntered;
	private Scanner userInput = new Scanner(System.in);
	private String quitCommand = "quit";
	private String adminUsername;
	private String customerUsername;
	private String customerName;
	private String email;
	private String contact;
	private String address;
	private double amount;
	private double loanEstimate;
	private double annualIncome;
	private int timePeriod;
	private String adminPassword;
	private String customerPassword;
	private String forgotPassword;
	private IAdminOperations adminOperations;
	private AdminOperationsFactory adminOperationsFactory;
	private DatabaseFactory databaseFactory;
	private UtilityFactory utilityFactory;
	private IValidations validations;
	private IAdminLogin adminLogin;
	private LoginFactory loginFactory;
	private IContactInformationDatabase contactInformationDatabase;


	public ConsoleRunnerAdmin() throws SQLException {
		databaseFactory = new DatabaseFactoryNormal();
		utilityFactory = new UtilityFactoryNormal();
		adminOperationsFactory = new AdminOperationsFactoryNormal();
		loginFactory = new LoginFactoryNormal();
		adminLogin = loginFactory.createAdminLoginObject();
		validations = utilityFactory.createValidationsObject();
		contactInformationDatabase = databaseFactory.createContactInformationDatabaseObject();
		adminOperations = adminOperationsFactory.createAdminOperationsObject();
	}

	// Method to run via Console
	@Override
	public void runBackendOperations() throws SQLException, FileNotFoundException {
		System.out.println("Welcome to Wayne Backend Operations");
		System.out.println("1. To login ");
		System.out.println("quit. To exit the application");
		System.out.println("Choose 1 or quit");
			commandEntered = userInput.next();
			if(commandEntered.equalsIgnoreCase("1")) {
				System.out.println("Hello Administrator, Please enter your credentials");
				System.out.println("Enter your username:");
				adminUsername = userInput.next();
				System.out.println("Enter your password:");
				adminPassword = userInput.next();
				System.out.println("Forgot Password: yes or no:");
				forgotPassword = userInput.next();

				do {
					if(adminLogin.adminLogin(adminUsername, adminPassword, forgotPassword)) {
						if(forgotPassword.equalsIgnoreCase("yes")) {
							break;
						}
						System.out.println("Welcome Admin, Login was successful.");
						// now display all the options for the admin here

						System.out.println("What would like to do?");
						System.out.println("1.Create Customer Login Information");
						System.out.println("2.Approve loans");
						System.out.println("3. Close the account");
						System.out.println("4. Update customer Queries");
						System.out.println("quit to Exit the application");
						commandEntered = userInput.next();
						if (commandEntered.equalsIgnoreCase("1")) {
							System.out.println("Enter details to create customer:");
							System.out.println("Enter customer username/Id:");
							customerUsername = userInput.next();
							System.out.println("Enter customer Name:");
							customerName = userInput.next();
							System.out.println("Enter customer password:");
							customerPassword = userInput.next();
							validations.validatePassword(customerPassword);
							System.out.println("Enter customer email:");
							email = userInput.next();
							validations.validateEmail(email);
							System.out.println("Enter customer contact:");
							contact = userInput.next();
							validations.validateContact(contact);
							System.out.println("Enter customer address:");
							address = userInput.next();
							System.out.println("enter customer ssn:");
							String ssn = userInput.next();
							adminOperations.createCustomer(customerUsername, customerName, customerPassword, email, contact, address, ssn);
							System.out.println("Customer Created Successfully!");
						}
						else if (commandEntered.equalsIgnoreCase("2")) {
							System.out.println("Enter ");
							adminOperations.approveLoanRequest(customerUsername, customerName, email, amount, loanEstimate, timePeriod, annualIncome);
						}
						else if (commandEntered.equalsIgnoreCase("3")) {
							adminOperations.closeAccount(customerUsername);
						}
						else if (commandEntered.equalsIgnoreCase("4")) {
							String queries = contactInformationDatabase.fetchContactQueries();
							System.out.println(queries);
							System.out.println("Select the customerId to update the status");
							customerUsername = userInput.next();
							adminOperations.updateCustomerQuery(customerUsername);
						}
						else if (commandEntered.equalsIgnoreCase("quit")) {
							break;
						}
					}
				}while (true);
			}
			else if(commandEntered.equalsIgnoreCase("quit")) {
				System.out.println("Exiting the application.");
			}
			else {
				System.out.println("Bad Command, Please enter a valid input - 1 or quit");
			}
		}
}
