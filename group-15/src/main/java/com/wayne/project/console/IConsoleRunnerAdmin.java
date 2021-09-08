package com.wayne.project.console;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public interface IConsoleRunnerAdmin {

    public void runBackendOperations() throws SQLException, FileNotFoundException;

}
