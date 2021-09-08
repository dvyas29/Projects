package com.database.app.query.create.db;

public interface CreateDbInterface {

    boolean validateQuery(String query);

    String extractDbName(String query);

    boolean isDbPresent(String dbName);

    void createDb(String dbName);

    void createDbMetadata(String dbName);
}
