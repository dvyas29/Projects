package com.wayne.project.console;

import com.wayne.project.cards.*;
import com.wayne.project.utility.INotification;
import com.wayne.project.utility.UtilityFactory;
import com.wayne.project.utility.UtilityFactoryNormal;

import java.sql.SQLException;
import java.util.Scanner;

public class ConsoleForCards implements IConsoleForCards {
    Scanner userInput;
    String commandEntered = null;
    private CardsFactory cardsFactory;
    private ICards cards;
    private IPaymentReminders paymentReminders;
    private String customerId;
    private IAutoPay autoPay;
    private IOnlineUsageLimit onlineUsageLimit;
    private ConsoleFactory consoleFactory = new ConsoleFactoryNormal();
    private IRedeemCardPoints redeemCardPoints;
    private UtilityFactory utilityFactory;
    private INotification notification;

    public ConsoleForCards(String customerId) throws SQLException {

        userInput = consoleFactory.createScannerInstance();
        this.customerId = customerId;
        cardsFactory = new CardsFactoryNormal();
        utilityFactory = new UtilityFactoryNormal();
        cards = cardsFactory.createCardsObject(customerId);
        paymentReminders = cardsFactory.createPaymentReminderObject(customerId);
        autoPay = cardsFactory.createAutoPayObject(customerId);
        onlineUsageLimit = cardsFactory.createOnlineUsageLimitObject(customerId);
        redeemCardPoints = cardsFactory.createRedeemCardPointsObject(customerId);
        notification = utilityFactory.createNotificationObject(customerId);
        System.out.println(notification.fetchAllAlerts());
    }

    @Override
    public void displayCardsInformation() throws SQLException {

        System.out.println("Cards");
        System.out.println("Choose an option");
        System.out.println("1. Debit Cards");
        System.out.println("2. Credit Cards");
        System.out.println("'quit' To logout the application");
        commandEntered = userInput.next();
        if(commandEntered.equals("1")) {
            IConsoleForDebitCards consoleForDebitCards = consoleFactory.createConsoleForDebitCardsObject(customerId);
            consoleForDebitCards.displayDebitCardsInformation();
        }
        else if(commandEntered.equals("2")) {
            IConsoleForCreditCards consoleForCreditCards = consoleFactory.createConsoleForCreditCardsObject(customerId);
            consoleForCreditCards.displayCreditCardsInformation();
        }
    }

    @Override
    public boolean generateCardPinCode(ICards cards, String typeOfCard) throws SQLException {

        System.out.println("Would you like to generate a new pin? yes or no");
        commandEntered = userInput.next();
        if(commandEntered.equalsIgnoreCase("yes")) {
            System.out.println("Enter a new pin:");
            commandEntered = userInput.next();
            String hashOfPinCode = commandEntered;
            boolean statusOfPinGeneration = cards.generatePinCode(hashOfPinCode, typeOfCard);
            if(statusOfPinGeneration) {
                System.out.println("The pin generation was successful.");
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean redeemCardPoints(IRedeemCardPoints redeemCardPoints, String typeOfCard) throws SQLException {

        int redeemCreditCardPoints = redeemCardPoints.fetchRedeemCardPoints(typeOfCard);
        System.out.println("The redeem card points:" + redeemCreditCardPoints);
        if(redeemCreditCardPoints == 0) {
            return true;
        }
        else {
            System.out.println("Would you like to redeem the card points and transfer the " +
                    "money to your account? yes or no?");
            commandEntered = userInput.next();
        }
        if(commandEntered.equalsIgnoreCase("yes")) {
            String resultString = redeemCardPoints.redeemCardPoints(typeOfCard, redeemCreditCardPoints);
            System.out.println(resultString);
        }
        else {
            return true;
        }
        return false;
    }

    @Override
    public void addPaymentReminder(String customerId) throws SQLException {

        System.out.println("Please enter the query to remind about?");
        commandEntered = userInput.nextLine();
        String queryToRemindAbout = commandEntered;
        System.out.println("Please enter the day of month to remind about the payment?");
        commandEntered = userInput.next();
        int dayOfMonth = Integer.parseInt(commandEntered);
        String resultString = paymentReminders.addPaymentReminders(queryToRemindAbout, dayOfMonth);
        System.out.println(resultString);
    }
}
