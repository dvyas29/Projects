package com.wayne.project.cards;

import java.sql.SQLException;

public interface ICards {

	public String displayStatusOfCard(String typeOfCard) throws SQLException;

	public String getUpgradeCardStatus(String typeOfCard) throws SQLException;

	public boolean upgradeCard(String typeOfCardToUpgrade, String typeOfCard) throws SQLException;

	public boolean blockCard(String typeOfCard) throws SQLException;
	
	public boolean generatePinCode(String pinCode, String typeOfCard) throws SQLException;

	public boolean updateDueAmountForAutoPayment(double dueAmount, String typeOfCard) throws SQLException;
}
