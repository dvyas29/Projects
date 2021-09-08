package com.wayne.project.console;

import com.wayne.project.login.ICustomerLogin;
import com.wayne.project.login.LoginFactory;
import com.wayne.project.login.LoginFactoryNormal;
import com.wayne.project.utility.IValidations;
import com.wayne.project.utility.UtilityFactory;
import com.wayne.project.utility.UtilityFactoryNormal;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class ConsoleRunnerCustomer implements IConsoleRunnerCustomer {

	private String commandEntered = null;
	private Scanner userInput;
	private String customerUsername;
	private String customerPassword;
	private String forgotPassword;
	private String email;
	private LoginFactory loginFactory = new LoginFactoryNormal();
	private ICustomerLogin customerLogin = loginFactory.createCustomerLoginObject();
	private ConsoleFactory consoleFactory = new ConsoleFactoryNormal();
	private UtilityFactory utilityFactory = new UtilityFactoryNormal();
	private IValidations validations = utilityFactory.createValidationsObject();

	@Override
	public void runBankingApplication() throws SQLException, IOException {
		userInput = consoleFactory.createScannerInstance();
		String commandEntered;
		System.out.println("Welcome Customer");
		System.out.println("1. To login ");
		System.out.println("quit. To exit the application");
		System.out.println("Choose 1 or quit");

		do {
			commandEntered = userInput.next();
			if(commandEntered.equalsIgnoreCase("1")) {
				inputLoginCredentials(userInput);
				if(customerLogin.customerLogin(customerUsername, customerPassword,forgotPassword)) {
					System.out.println("Welcome Customer, Login was successful.");
					System.out.println("Features Menu");
					System.out.println("What would you like to do?");
					System.out.println("1. Account Information");
					System.out.println("2. Funds Transfer");
					System.out.println("3. Cards");
					System.out.println("4. Loans");
					System.out.println("5. Offers");
					System.out.println("6. Contact Us");
					System.out.println("'quit' to Exit the application");
					commandEntered = userInput.next();
					if(commandEntered.equalsIgnoreCase("1")) {
						IConsoleForAccountInformation consoleForAccountInformation = consoleFactory.createConsoleForAccountInformationObject(customerUsername);
						consoleForAccountInformation.accountInformation();
					}
					else if(commandEntered.equalsIgnoreCase("2")) {
						IConsoleForFundsTransfer consoleForFundsTransfer = consoleFactory.createConsoleForFundsTransferObject(customerUsername);
						consoleForFundsTransfer.fundsTransfer(customerUsername);
					}
					else if(commandEntered.equalsIgnoreCase("3")) {
						IConsoleForCards consoleForCards = consoleFactory.createConsoleForCardsObject(customerUsername);
						consoleForCards.displayCardsInformation();
					}
					else if(commandEntered.equalsIgnoreCase("4")) {
						IConsoleForLoans consoleForLoans = consoleFactory.createConsoleForLoansObject(customerUsername);
						consoleForLoans.loans(customerUsername);
					}
					else if(commandEntered.equalsIgnoreCase("5")) {
						IConsoleForOffers consoleForOffers = consoleFactory.createConsoleForOffers();
						consoleForOffers.displayOffersMenu();
					}
					else if(commandEntered.equalsIgnoreCase("6")) {
						IConsoleForContactInformation consoleForContactInformation = consoleFactory.createConsoleForContactInformationObject(customerUsername);
						consoleForContactInformation.contactInformation();
					}
					else if(commandEntered.equalsIgnoreCase("quit")) {
						break;
					}
					else {
						System.out.println("Bad Command! Please enter again.");
					}
				}
				else {
					break;
				}
			}
			else if(commandEntered.equalsIgnoreCase("quit")) {
				System.out.println("Exiting the application.");
				break;
			}
			else {
				System.out.println("Bad Command, Please enter a valid input - 1 or quit");
			}
		}while(true);
	}

	@Override
	public void inputLoginCredentials(Scanner userInput) {

		System.out.println("Hello User, Please enter your credentials");
		System.out.println("Enter your username:");
		customerUsername = userInput.next();
		System.out.println("Enter your password:");
		customerPassword = userInput.next();
		System.out.println("Forgot Password: 'yes' or 'no'");
		forgotPassword = userInput.next();
		}
}
