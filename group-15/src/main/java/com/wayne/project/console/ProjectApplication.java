package com.wayne.project.console;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) throws SQLException, IOException {

		ConsoleFactory consoleFactory = new ConsoleFactoryNormal();
		IConsoleRunnerCustomer consoleRunnerCustomer = consoleFactory.createConsoleRunnerCustomer();
		IConsoleRunnerAdmin consoleRunnerAdmin = consoleFactory.createConsoleRunnerAdmin();
		Scanner userInput = consoleFactory.createScannerInstance();
		System.out.println("Welcome to Wayne Banking Corporation");
		System.out.println("1. To run the customer banking application");
		System.out.println("2. To run the Admin backend operations");
		System.out.println("Any other key. To quit the application");
		String commandEntered = userInput.next();
		if(commandEntered.equalsIgnoreCase("1")) {
			consoleRunnerCustomer.runBankingApplication();
		}
		else if(commandEntered.equalsIgnoreCase("2")) {
			consoleRunnerAdmin.runBackendOperations();
		}
		else {
			System.out.println("Exiting the application.");
		}
	}
}
