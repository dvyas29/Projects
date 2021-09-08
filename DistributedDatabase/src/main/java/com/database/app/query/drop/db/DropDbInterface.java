package com.database.app.query.drop.db;

public interface DropDbInterface {

    boolean validateQuery(String query);

    String extractDbName(String query);

    boolean isDbPresent(String dbName);

    void dropDb(String dbName);

    void dropDbMetadata(String dbName);
}
