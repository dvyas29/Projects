package com.wayne.project.console;

import java.sql.SQLException;

public interface IConsoleForFundsTransfer {

    public void fundsTransfer(String customerId) throws SQLException;
    public void displayBeneficiaryFunctions(String customerId) throws SQLException;
    public void displayTransferFundsFunctions(String customerId) throws SQLException;
}
