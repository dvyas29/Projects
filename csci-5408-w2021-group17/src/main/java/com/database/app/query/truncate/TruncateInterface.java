package com.database.app.query.truncate;

public interface TruncateInterface {

    boolean validateQuery(String query);

    String extractTableName(String query);

    boolean isTablePresent(String dbName, String tableName);

    void emptyTable(String dbName, String tableName);
}
