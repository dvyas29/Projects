package com.database.app.main;

import java.util.Collections;
import java.util.Scanner;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DatabaseMain
{

	public final static String PRODUCT_NAME = "dql> ";
	public final static String EXIT = "EXIT";
	static Scanner scanner;

	public static void main(String[] args)
	{
		SpringApplication app = new SpringApplication(DatabaseMain.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "8080"));
		app.setDefaultProperties(Collections.singletonMap("logging.pattern.console", ""));
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
		new DatabaseInitialize();
		new DatabaseSecurity();
		DatabaseTransaction.setTransaction();
		System.out.print(PRODUCT_NAME);
		DatabaseQueryClassifier queryClassifier = new DatabaseQueryClassifier();
		String query = "";
		do
		{
			query = acceptOption();
			processQuery(query, queryClassifier);
		} while (!query.equalsIgnoreCase(EXIT));
		scanner.close();
	}

	private static String acceptOption()
	{
		scanner = new Scanner(System.in);
		return scanner.nextLine();
	}

	private static void processQuery(String query, DatabaseQueryClassifier queryClassifier)
	{
		if (query.equalsIgnoreCase(EXIT))
		{
			return;
		}
		queryClassifier.classifyQuery(query);
		System.out.print(PRODUCT_NAME);
	}

}
