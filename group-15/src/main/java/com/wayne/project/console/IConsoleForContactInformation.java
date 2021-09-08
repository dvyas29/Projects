package com.wayne.project.console;

import java.sql.SQLException;

public interface IConsoleForContactInformation {

    public void contactInformation() throws SQLException;
    public void submitQuery(String customerId) throws SQLException;
}
