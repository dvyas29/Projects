package com.wayne.project.database;

import java.sql.Connection;

public interface IDatabase {

    public Connection createDatabaseConnection();
    public void closeDatabaseConnection();
}
