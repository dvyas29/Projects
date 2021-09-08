package com.wayne.project.console;

import com.wayne.project.offers.*;

import java.sql.SQLException;
import java.util.Scanner;

public class ConsoleForOffers implements IConsoleForOffers{
    Scanner userInput;
    String commandEntered;
    IAutomotiveLoanOffers automotiveLoanOffers;
    ICreditCardOffers creditCardOffers;
    ILifeInsuranceOffers lifeInsuranceOffers;
    ILoanOffers loanOffers;
    IMutualFundsOffers mutualFundsOffers;
    ITouristLoanOffers touristLoanOffers;
    ConsoleFactory consoleFactory;
    OffersFactory offersFactory;

    ConsoleForOffers(){
        consoleFactory = new ConsoleFactoryNormal();
        userInput = consoleFactory.createScannerInstance();
        offersFactory = new OffersFactoryNormal();
        automotiveLoanOffers = offersFactory.createAutomotiveLoanOffersObject();
        creditCardOffers = offersFactory.createCreditCardOffersObject();
        lifeInsuranceOffers = offersFactory.createLifeInsuranceOffersObject();
        loanOffers = offersFactory.createLoanOffersObject();
        mutualFundsOffers = offersFactory.createMutualFundsOffersObject();
        touristLoanOffers = offersFactory.createTouristLoanOffersObject();
    }

    public void displayOffersMenu() throws SQLException {
        System.out.println("Offers");
        System.out.println("1. Credit Card Offers");
        System.out.println("2. Loan Offers");
        System.out.println("3. Mutual Funds Offers");
        System.out.println("4. Life Insurance Offers");
        System.out.println("5. Automotive Loan Offers");
        System.out.println("6. Tourist Loan Offers");
        commandEntered = userInput.next();

        if(commandEntered.equalsIgnoreCase("1")){
            creditCardOffers.showCreditCardOffers();
        }
        else if(commandEntered.equalsIgnoreCase("2")){
            loanOffers.showLoanOffers();
        }
        else if(commandEntered.equalsIgnoreCase("3")){
            mutualFundsOffers.showMutualFundsOffers();
        }
        else if(commandEntered.equalsIgnoreCase("4")){
            lifeInsuranceOffers.showLifeInsuranceOffers();
        }
        else if(commandEntered.equalsIgnoreCase("5")){
            automotiveLoanOffers.showAutomotiveLoanOffers();
        }
        else if(commandEntered.equalsIgnoreCase("6")){
            touristLoanOffers.showTouristLoanOffers();
        }
    }
}
