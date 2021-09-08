package com.wayne.project.console;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public interface IConsoleRunnerCustomer {

    public void runBankingApplication() throws SQLException, IOException;

    public void inputLoginCredentials(Scanner userInput) throws SQLException;
}
