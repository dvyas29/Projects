package com.wayne.project.console;

import com.wayne.project.cards.*;

import java.sql.SQLException;
import java.util.Scanner;

public class ConsoleForCreditCards implements IConsoleForCreditCards {

    Scanner userInput;
    String commandEntered = null;
    private CardsFactory cardsFactory;
    private ICards cards;
    private IPaymentReminders paymentReminders;
    private String customerId;
    private IAutoPay autoPay;
    private IOnlineUsageLimit onlineUsageLimit;
    private IRedeemCardPoints redeemCardPoints;
    private ConsoleFactory consoleFactory = new ConsoleFactoryNormal();
    private IConsoleForCards consoleForCards;

    public ConsoleForCreditCards(String customerId) throws SQLException {

        userInput = consoleFactory.createScannerInstance();
        this.customerId = customerId;
        consoleForCards = consoleFactory.createConsoleForCardsObject(customerId);
        cardsFactory = new CardsFactoryNormal();
        cards = cardsFactory.createCardsObject(customerId);
        paymentReminders = cardsFactory.createPaymentReminderObject(customerId);
        autoPay = cardsFactory.createAutoPayObject(customerId);
        onlineUsageLimit = cardsFactory.createOnlineUsageLimitObject(customerId);
        redeemCardPoints = cardsFactory.createRedeemCardPointsObject(customerId);
    }

    @Override
    public void displayCreditCardsInformation() throws SQLException {

        String typeOfCard = "credit";
        do {
            System.out.println("Credit Cards");
            System.out.println("Choose an option:");
            System.out.println("1. Credit Card Status");
            System.out.println("2. Upgrade Credit Card");
            System.out.println("3. Redeem Credit card points");
            System.out.println("4. Block Credit Card");
            System.out.println("5. Generate Credit Card pin code");
            System.out.println("6. Set Online limit for Online Usage");
            System.out.println("7. Request for a new credit card");
            System.out.println("8. Set payment reminders");
            System.out.println("9. Auto Payment for Utility Bills");
            System.out.println("'quit' To logout from the application");
            commandEntered = userInput.next();
            if(commandEntered.equals("1")) {
                String statusOfCreditCardcard= cards.displayStatusOfCard(typeOfCard);
                System.out.println(statusOfCreditCardcard);
            }
            else if(commandEntered.equals("2")) {
                String activeTypeOfCard = cards.getUpgradeCardStatus(typeOfCard);
                if(activeTypeOfCard.equalsIgnoreCase("platinum")) {
                    System.out.println("The Credit Card is already upgraded to the Best Type of Card.");
                    continue;
                }
                else if(activeTypeOfCard.equalsIgnoreCase("gold")) {
                    System.out.println("The Credit Card can be upgraded to Platinum. " +
                            "Charges per year for Platinum Credit card is 20CAD");
                }
                else {
                    // When the card is of titanium type Credit card
                    System.out.println("The Credit Card can be upgraded to Platinum or Gold " +
                            "Charges per year for Platinum Credit card is 20CAD and Gold Credit card is 10CAD.");
                }
                // When card is of gold type or titanium type
                if(activeTypeOfCard.equalsIgnoreCase("titanium")) {
                    System.out.println("Would you like to upgrade the Credit Card to Gold or Platinum?");
                    System.out.println("Type gold or platinum to upgrade");

                }
                if(activeTypeOfCard.equalsIgnoreCase("gold")) {
                    System.out.println("Would you like to upgrade the Credit Card to Platinum?");
                    System.out.println("Type platinum to upgrade");
                }
                commandEntered = userInput.next();
                if(commandEntered.equalsIgnoreCase("gold") && !(activeTypeOfCard.equalsIgnoreCase("gold"))) {
                    cards.upgradeCard( "gold", typeOfCard);
                    System.out.println("A new Credit card will be delivered and the money " +
                            "will be deducted from your account directly");
                }
                else if (commandEntered.equalsIgnoreCase("platinum")) {
                    cards.upgradeCard("platinum", typeOfCard);
                    System.out.println("A new Credit card will be delivered and the money " +
                            "will be deducted from your account directly");
                }
                else {
                    System.out.println("Wrong Command.");
                    continue;
                }
            }
            else if(commandEntered.equals("3")) {
                consoleForCards.redeemCardPoints(redeemCardPoints, typeOfCard);
            }
            else if(commandEntered.equals("4")) {
                boolean isBlockCardSuccessful = cards.blockCard(typeOfCard);
                if(isBlockCardSuccessful) {
                    System.out.println("Active Credit card has been successfully blocked.");
                    System.out.println("However, the customer has to report to the " +
                            "nearest branch to collect the new card.");
                }
                else {
                    System.out.println("The card is already blocked.");
                }
            }
            else if(commandEntered.equals("5")) {
                consoleForCards.generateCardPinCode(cards, typeOfCard);
            }
            else if(commandEntered.equals("6")) {
                System.out.println("Enter the Amount to be set as the Online Usage Limit?");
                commandEntered = userInput.next();
                boolean isOnlineUsageLimitSet = onlineUsageLimit.setOnlineUsageLimit(Integer.parseInt(commandEntered), typeOfCard);
                if(isOnlineUsageLimitSet){
                    System.out.println("The Usage Limit was set successfully.");
                }
                else {
                    System.out.println("The operation failed. Please try again after sometime.");
                }
            }
            else if(commandEntered.equals("7")) {
                System.out.println("A new Credit Card will be sent to your Address ");
            }
            else if(commandEntered.equals("8")) {
                consoleForCards.addPaymentReminder(customerId);
            }
            else if(commandEntered.equals("9")) {
                System.out.println("Please enter the day of month to make the auto payment");
                int dayOfMonth = userInput.nextInt();
                System.out.println("Please enter the amount for the payment towards the utility bill");
                float amountToPay = userInput.nextFloat();
                System.out.println("Please enter the type of utility bill");
                String billType = userInput.nextLine();
                System.out.println("Please enter the registration number of the utility bill");
                String billRegistrationNumber = userInput.next();
                boolean isAutoPaySuccessful = autoPay.addAutoPayForUtilityBills(dayOfMonth, amountToPay, typeOfCard,
                        billType, billRegistrationNumber);
                if(isAutoPaySuccessful) {
                    System.out.println("Auto Payment Entry was successfully added.");
                }
            }
            else if(commandEntered.equalsIgnoreCase("quit")) {
                break;
            }
            else {
                System.out.println("Wrong input command.");
            }
        }while(true);
    }
}