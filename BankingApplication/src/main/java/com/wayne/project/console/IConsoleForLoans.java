package com.wayne.project.console;

import java.io.IOException;
import java.sql.SQLException;

public interface IConsoleForLoans {

    public void loans(String customerId) throws SQLException, IOException;
}
