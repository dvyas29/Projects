package com.wayne.project.console;

import java.sql.SQLException;

public interface IConsoleForAccountInformation {
    public void accountInformation() throws SQLException;
    public void displayCreateStatementMenu(String customerId) throws SQLException;
    public void displayUpdateCustomerDetailsMenu(String customerId) throws SQLException;
    public void displayChoiceForClosingAccount(String customerId) throws SQLException;
}
