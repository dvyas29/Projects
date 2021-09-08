package com.wayne.project.console;

import com.wayne.project.cards.ICards;
import com.wayne.project.cards.IRedeemCardPoints;

import java.sql.SQLException;

public interface IConsoleForCards {

    public void displayCardsInformation() throws SQLException;

    public boolean generateCardPinCode(ICards cards, String typeOfCard) throws SQLException;

    public boolean redeemCardPoints(IRedeemCardPoints redeemCardPoints, String typeOfCard) throws SQLException;

    public void addPaymentReminder(String customerId) throws SQLException;
}
